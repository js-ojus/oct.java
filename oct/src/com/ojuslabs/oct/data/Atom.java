package com.ojuslabs.oct.data;

import java.util.ArrayList;
import java.util.List;

import static com.ojuslabs.oct.common.Constants.LIST_SIZE_S;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.BondOrder;
import com.ojuslabs.oct.common.Element;
import com.ojuslabs.oct.exception.NotFoundException;
import com.ojuslabs.oct.exception.UniquenessException;

/**
 * Atom represents a chemical atom. It has various physical and chemical
 * properties, apart from the transient information attached to it during its
 * participation in reactions.
 * 
 * An atom is usually bound to a molecule, but may be free intermittently.
 */
class Atom
{
    final Element   _element;     // This atom's element type.

    Molecule        _m;           // Containing molecule, if the atom is
                                   // bound to one.

    short           _id;          // A unique ID within its molecule.
    short           _cid;         // A unique canonicalised ID.

    byte            _chirality;   // Chirality type.
    byte            _numH;        // Number of attached H atoms.
    byte            _charge;      // Residual charge on the atom.

    ArrayList<Bond> _bonds;       // Bonds this atom is a member of.
    byte            _valence;     // Current valence of this atom.

    ArrayList<Ring> _rings;       // Rings this atom is a member of.
    boolean         _inAroRing;   // Is this atom in an aromatic ring?
    boolean         _inHetAroRing; // Is this atom in a hetero-aromatic
                                   // ring?
    boolean         _isBridgeHead; // Is this atom a bridgehead?
    boolean         _isSpiro;     // Is the sole common atom of two
                                   // rings?

    /**
     * Initialisation of a new atom.
     */
    public Atom(Element elem) {
        _element = elem;

        _bonds = Lists.newArrayListWithCapacity(LIST_SIZE_S);
        _rings = Lists.newArrayListWithCapacity(LIST_SIZE_S);
    }

    /**
     * Resets the state of this atom. Useful when re-parenting. May be useful in
     * other scenarios as well. <b>N.B.</b> This method is package-internal.
     */
    void reset() {
        _bonds.clear();
        _rings.clear();

        _inAroRing = false;
        _inHetAroRing = false;
        _isBridgeHead = false;
        _isSpiro = false;
    }

    /**
     * @return This atom's containing molecule, if any; else, <code>null</code>.
     */
    public Molecule molecule() {
        return _m;
    }

    /**
     * Serves two purposes: (a) it can be used to re-parent the atom to a
     * different molecule, and (b) it can be used to detach this atom from its
     * current parent.
     * 
     * @param m
     *            Containing molecule, if any. To remove the current parent,
     *            pass <code>null</code>.
     * @param reset
     *            If true, resets the state of the atom; else, leaves it as is.
     * @throws NotFoundException
     */
    public void setMolecule(Molecule m, boolean reset) throws NotFoundException {
        if (_m == m) return;

        if (null != _m) {
            _m._removeAtom(this, Integer.MIN_VALUE);
        }

        _m = m;
        _id = 0;
        _cid = 0;
        if (reset) {
            reset();
        }
    }

    /**
     * @return The unique ID of this atom in its current molecule.
     */
    public short id() {
        return _id;
    }

    /**
     * @return The unique canonical ID of this atom in its current molecule.
     */
    public short cid() {
        return _cid;
    }

    /**
     * Sets the new unique ID of this atom. <b>N.B.</b> This method is
     * package-internal, since only a molecule sets the IDs of its atoms.
     * 
     * @param id
     *            The new unique ID of this atom.
     */
    void setId(short id) {
        _id = id;
    }

    /**
     * Sets the new canonical ID of this atom. <b>N.B.</b> This method is
     * package-internal, since only a molecule sets the IDs of its atoms.
     * 
     * @param id
     *            The new canonical ID of this atom.
     */
    void setCid(short id) {
        _cid = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Atom ID: %d, CID: %d, element: %s", _id, _cid,
                _element.symbol);
    }

    /**
     * Note that two atoms are considered equal only if their IDs are the same
     * AND they reside in the same molecule.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Atom)) return false;

        Atom other = (Atom) obj;
        if ((null == _m) || (null == other.molecule())) return false;
        if (_id != other._id) return false;
        if (_m.id() != other.molecule().id()) return false;

        return true;
    }

    // TODO(js): Implement a meaningful `hashCode'.

    /**
     * @return The element type of this atom.
     */
    public Element element() {
        return _element;
    }

    /**
     * @return Number of distinct neighbours of this atom.
     */
    public int numberOfNeighbours() {
        return _bonds.size();
    }

    public int numberOfBonds() {
        double c = 0.0;
        for (Bond b : _bonds) {
            switch (b.order()) {
            case BondOrder.SINGLE:
                c += 1;
                break;
            case BondOrder.DOUBLE:
                c += 2;
                break;
            case BondOrder.TRIPLE:
                c += 3;
                break;
            default: // Must be NONE.
                // Do nothing.
            }
        }
        return (int) c;
    }

    /**
     * @return An read-only view of the bonds of this atom.
     */
    public List<Bond> bonds() {
        ImmutableList<Bond> l = ImmutableList.copyOf(_bonds);
        return l;
    }

    /**
     * @return Number of rings this atom participates in.
     */
    public int numberOfRings() {
        return _rings.size();
    }

    /**
     * @param r
     *            Ring in which we check this atom's membership.
     * @return True if this atom participates in the given ring; false
     *         otherwise.
     */
    public boolean inRing(Ring r) {
        if (null == r.atom(_id)) {
            return false;
        }

        return true;
    }

    /**
     * @param n
     *            Required size of the ring in which this atom has to
     *            participate.
     * @return True if this atom participates in at least one ring of the given
     *         size; false otherwise.
     */
    public boolean inRingOfSize(int n) {
        for (Ring r : _rings) {
            if (r.size() == n) {
                if (null != r.atom(_id)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return The smallest ring in which this atom participates, if one such
     *         exists; <code>null</code> otherwise. If more than one ring of the
     *         smallest size if found, an exception is thrown.
     * @throws UniquenessException
     */
    public Ring smallestRing() throws UniquenessException {
        if (0 == _rings.size()) {
            return null;
        }

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

        return ret;
    }

    /**
     * @return A read-only view of the rings this atom participates in.
     */
    public List<Ring> rings() {
        ImmutableList<Ring> l = ImmutableList.copyOf(_rings);
        return l;
    }

    /**
     * @return True if this atom participates in an aromatic ring, with or
     *         without a hetero atom; false otherwise.
     */
    public boolean isAromatic() {
        if (_inAroRing || _inHetAroRing) {
            return true;
        }

        return false;
    }

    /**
     * Adds the given bond to this atom.
     * 
     * @param b
     *            The bond to add to this atom. <b>N.B.</b> The current atom
     *            must participate in the given bond; however, this is assumed
     *            to have been taken care of by the parent molecule.
     *            Accordingly, this method is package-internal.
     */
    void addBond(Bond b) {
        if (!_bonds.contains(b)) {
            _bonds.add(b);
        }
    }

    /**
     * Removes the given bond from this atom.
     * 
     * @param b
     *            The bond to remove from this atom. <b>N.B.</b> The current
     *            atom must participate in the given bond; however, this is
     *            assumed to have been taken care of by the parent molecule.
     *            Accordingly, this method is package-internal.
     * @return True if the given bond was actually removed; false otherwise.
     */
    boolean removeBond(Bond b) {
        return _bonds.remove(b);
    }

    /**
     * @param r
     *            The ring to add to this atom. <b>N.B.</b> The current atom
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
     *            The ring to add to this atom. <b>N.B.</b> The current atom
     *            must participate in the given ring; however, this is assumed
     *            to have been taken care of by the parent molecule.
     *            Accordingly, this method is package-internal.
     * @return True if the given ring was actually removed; false otherwise.
     */
    boolean removeRing(Ring r) {
        return _rings.remove(r);
    }
}
