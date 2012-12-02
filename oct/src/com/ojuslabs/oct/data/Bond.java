package com.ojuslabs.oct.data;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.BondOrder;
import com.ojuslabs.oct.common.BondStereo;
import com.ojuslabs.oct.common.Constants;
import com.ojuslabs.oct.exception.NotFoundException;
import com.ojuslabs.oct.exception.UniquenessException;

/**
 * Bond represents a chemical bond. This flavour is strictly between two atoms,
 * and does <b>not</b> cater to multi-bond requirements.
 */
public class Bond
{
    final short     _id;    // Unique ID of this bond in its molecule.

    final Atom      _a1;
    final Atom      _a2;
    BondOrder       _order; // Order of this bond.
    BondStereo      _stereo; // Stereo configuration of this bond.

    boolean         _isAro; // Is this bond aromatic?
    ArrayList<Ring> _rings; // The rings in which this bond
                             // participates.

    int             _hash;  // Cached in the object for faster search.

    /**
     * @param id
     *            The unique ID of this bond.
     * @param a1
     *            The first atom participating in this bond.
     * @param a2
     *            The second atom participating in this bond.
     */
    Bond(short id, Atom a1, Atom a2) {
        _id = id;
        _a1 = a1;
        _a2 = a2;

        _hash = hash(_a1, _a2);

        _rings = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_S);
    }

    /**
     * @return The unique ID of this bond.
     */
    public short id() {
        return _id;
    }

    /**
     * @return The first atom participating in this bond.
     */
    public Atom atom1() {
        return _a1;
    }

    /**
     * @return The second atom participating in this bond.
     */
    public Atom atom2() {
        return _a2;
    }

    /**
     * @return Bond order of this bond. See {@link BondOrder} for possible bond
     *         orders.
     */
    public BondOrder order() {
        return _order;
    }

    /**
     * Sets the new order for this bond. It also adjusts the valence of the
     * member atoms appropriately. Should that result in an invalid state, an
     * {@link IllegalStateException} is thrown.
     * 
     * @param o
     *            The new bond order to set. See {@link BondOrder} for possible
     *            bond orders.
     */
    public void setOrder(BondOrder o) {
        if (o == _order) {
            return;
        }

        int delta = o.value() - _order.value();
        int res = _a1.numberOfBonds() + delta;
        if (res > _a1.valence()) {
            throw new IllegalStateException(
                    String.format(
                            "Illegal state for atom: %d->%d. Number of bonds: %d, new bond order: %d",
                            _a1.molecule(), _a1.id(), _a1.numberOfBonds(),
                            o.value()));
        }
        res = _a2.numberOfBonds() + delta;
        if (res > _a2.valence()) {
            throw new IllegalStateException(
                    String.format(
                            "Illegal state for atom: %d->%d. Number of bonds: %d, new bond order: %d",
                            _a2.molecule(), _a2.id(), _a2.numberOfBonds(),
                            o.value()));
        }

        _order = o;
    }

    /**
     * Answers the other participating atom in this bond.
     * 
     * @param id
     *            ID of the atom whose pairing atom is requested.
     * @return The other atom participating in this bond.
     * @throws NotFoundException
     */
    public Atom otherAtom(short id) throws NotFoundException {
        if (_a1.id() == id) {
            return _a2;
        }
        else if (_a2.id() == id) {
            return _a1;
        }
        else {
            throw new NotFoundException(String.format(
                    "Atoms in this bond: %d, %d; given ID: %d", _a1.id(),
                    _a2.id(), id));
        }
    }

    /**
     * Computes and answers a unique hash for quick lookup.
     * 
     * <b>N.B.</b> A better design should have this as a thread-local top-level
     * <i>function</i>. Unfortunately, Java does not offer a simple way of doing
     * that.
     * 
     * @return A unique hash value that utilises the IDs of both the
     *         participating atoms.
     */
    int hash(Atom a1, Atom a2) {
        int result = 0;
        if (a1.id() < a2.id()) {
            result = 10000 * a1.id() + a2.id();
        }
        else {
            result = 10000 * a2.id() + a1.id();
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Bond ID: %d [%d, %d]", _id, _a1.id(), _a2.id());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Bond)) return false;

        Bond other = (Bond) obj;
        if ((_order != other._order) || (_hash != other._hash)) {
            return false;
        }

        return true;
    }

    // TODO(js): Implement a meaningful `hashCode'. The value in `_hash' is not
    // usable because `_order' is not immutable currently.

    /**
     * @return True if this bond has at least one aromatic atom participating.
     */
    public boolean isAromatic() {
        return _isAro;
    }

    /**
     * @return The stereo configuration of this bond. See {@link BondStereo} for
     *         possible stereo configurations.
     */
    public BondStereo stereo() {
        return _stereo;
    }

    /**
     * @param s
     *            The stereo configuration of this bond. See {@link BondStereo}
     *            for possible stereo configurations.
     */
    public void setStereo(BondStereo s) {
        _stereo = s;
    }

    /**
     * @param r
     *            The ring to add to this bond. <b>N.B.</b> The current bond
     *            must participate in the given ring; however, this is assumed
     *            to have been taken care of by the parent molecule.
     *            Accordingly, this method is package-internal.
     */
    void addRing(Ring r) {
        if (!_rings.contains(r)) {
            _rings.add(r);
        }
    }

    /**
     * @param r
     *            The ring to remove from this bond. <b>N.B.</b> The current
     *            bond must participate in the given ring; however, this is
     *            assumed to have been taken care of by the parent molecule.
     *            Accordingly, this method is package-internal.
     * @return True if the given ring was actually removed; false otherwise.
     */
    boolean removeRing(Ring r) {
        return _rings.remove(r);
    }

    /**
     * @param r
     *            Ring in which we check this bond's membership.
     * @return True if this bond participates in the given ring; false
     *         otherwise.
     */
    public boolean inRing(Ring r) {
        if (null == r.bond(_id)) {
            return false;
        }

        return true;
    }

    /**
     * @param n
     *            Required size of the ring in which this bond has to
     *            participate.
     * @return True if this bond participates in at least one such ring; false
     *         otherwise.
     */
    public boolean inRingOfSize(int n) {
        for (Ring r : _rings) {
            if (r.size() == n) {
                if (null != r.bond(_id)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return The smallest ring in which this bond participates, if one such
     *         exists; <code>null</code> otherwise. If more than one ring of the
     *         smallest size if found, an exception is thrown.
     * @throws UniquenessException
     */
    public Ring smallestRing() throws UniquenessException {
        int min = 0;
        int count = 0;
        Ring ret = null;

        for (Ring r : _rings) {
            int s = r.size();
            if (s == min) {
                count++;
            }
            else if (s < min) {
                ret = r;
                count = 1;
            }
        }

        if (count > 1) {
            throw new UniquenessException(String.format(
                    "Smallest ring size: %d, number of smallest rings: %d",
                    ret.size(), count));
        }
        if (1 == count) {
            return ret;
        }

        return null;
    }

    /**
     * @return The number of rings this bond participates in.
     */
    public int numberOfRings() {
        return _rings.size();
    }

    /**
     * @return A read-only view of the rings this bond participates in.
     */
    public List<Ring> rings() {
        ImmutableList<Ring> l = ImmutableList.copyOf(_rings);
        return l;
    }
}
