package com.ojuslabs.oct.data;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ojuslabs.oct.common.Constants.LIST_SIZE_S;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.Chirality;
import com.ojuslabs.oct.common.Element;
import com.ojuslabs.oct.common.PeriodicTable;
import com.ojuslabs.oct.common.Radical;
import com.ojuslabs.oct.exception.UniquenessException;
import com.ojuslabs.oct.util.Point3D;

/**
 * Atom represents a chemical atom. It has various physical and chemical
 * properties, apart from the transient information attached to it during its
 * participation in reactions.
 * 
 * An atom is usually bound to a molecule, but may be free intermittently.
 */
public class Atom
{
    private Element    _element;     // This atom's element type.

    private Molecule   _m;           // Containing molecule, if the atom is
                                      // bound to one.

    private int        _id;          // A unique ID within its molecule.
    private int        _cid;         // A unique canonicalised ID.

    public Point3D     coordinates;

    private Chirality  _chirality;   // Chirality type.
    private byte       _numH;        // Number of attached H atoms.
    private byte       _charge;      // Residual charge on the atom.
    private Radical    _radical;

    private List<Bond> _bonds;       // Bonds this atom is a member of.
    private byte       _valence;     // Current valence of this atom.

    private List<Ring> _rings;       // Rings this atom is a member of.
    private boolean    _inAroRing;   // Is this atom in an aromatic ring?
    private boolean    _inHetAroRing; // Is this atom in a hetero-aromatic ring?
    private boolean    _isBridgeHead; // Is this atom a bridgehead?
    private boolean    _isSpiro;     // Is the sole common atom of two rings?

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
     * @return The element type of this atom.
     */
    public Element element() {
        return _element;
    }

    /**
     * Sets the isotope value of this atom. <b>N.B.</b> This method actually
     * alters the element type of this atom. Use with extreme caution.
     * 
     * @param n
     *            The mass difference with respect to that of the naturally most
     *            abundant variety.
     */
    public void setIsotope(int n) {
        _element = PeriodicTable.instance().element(
                String.format("%s_%d", _element.symbol,
                        Math.round(_element.weight) + n));
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
     */
    public void setMolecule(Molecule m, boolean reset) {
        if (_m == m) {
            return;
        }

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
    public int id() {
        return _id;
    }

    /**
     * @return The unique canonical ID of this atom in its current molecule.
     */
    public int cid() {
        return _cid;
    }

    /**
     * Sets the new unique ID of this atom. <b>N.B.</b> This method is
     * package-internal, since only a molecule sets the IDs of its atoms.
     * 
     * @param id
     *            The new unique ID of this atom.
     */
    void setId(int id) {
        _id = id;
    }

    /**
     * Sets the new canonical ID of this atom. <b>N.B.</b> This method is
     * package-internal, since only a molecule sets the IDs of its atoms.
     * 
     * @param id
     *            The new canonical ID of this atom.
     */
    void setCid(int id) {
        _cid = id;
    }

    /**
     * @return The current residual charge on this atom.
     */
    public int charge() {
        return _charge;
    }

    /**
     * @param chg
     *            The new residual charge on this atom.
     */
    public void setCharge(int chg) {
        _charge = (byte) chg;
    }

    /**
     * @return The radical configuration of this atom.
     */
    public Radical radical() {
        return _radical;
    }

    /**
     * @param r
     *            The new radical configuration of this atom.
     */
    public void setRadical(Radical r) {
        _radical = r;
    }

    /**
     * @return The current valence configuration of this atom.
     */
    public byte valence() {
        return _valence;
    }

    /**
     * @param v
     *            The new valence configuration of this atom.
     */
    public void setValence(int v) {
        _valence = (byte) v;
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
     * AND they reside in the same molecule. Effectively, a free (unbound) atom
     * <b>cannot</b> be compared.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Atom)) {
            return false;
        }

        Atom other = (Atom) obj;
        if ((null == _m) || (null == other.molecule())) {
            return false;
        }
        if (_id != other._id) {
            return false;
        }
        if (_m.id() != other.molecule().id()) {
            return false;
        }

        return true;
    }

    // TODO(js): Implement a meaningful `hashCode'.

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
            case SINGLE:
                c += 1;
                break;
            case DOUBLE:
                c += 2;
                break;
            case TRIPLE:
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
        return ImmutableList.copyOf(_bonds);
    }

    /**
     * Answers the bond between this atom and the given other atom, if one
     * exists.
     * 
     * @param other
     *            The other atom potentially bound to this atom.
     * @return The requested bond between this atom and the other atom.
     */
    public Bond bondTo(Atom other) {
        if ((other.molecule() != _m) || (null == _m.atom(other.id()))) {
            throw new NoSuchElementException(String.format(
                    "Given atom does not belong this molecule: %d->%d", other
                            .molecule().id(), other.id()));
        }

        for (Bond b : _bonds) {
            if (other == b.otherAtom(_id)) {
                return b;
            }
        }

        return null;
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
            if ((r.size() == n) && (null != r.atom(_id))) {
                return true;
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
        if (_rings.isEmpty()) {
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
        return ImmutableList.copyOf(_rings);
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
     * Should the addition of this bond increase the number of bonds of this
     * atom beyond its current valence configuration, an
     * {@link IllegalStateException} exception is thrown.
     * 
     * @param b
     *            The bond to add to this atom. <b>N.B.</b> The current atom
     *            must participate in the given bond; however, this is assumed
     *            to have been taken care of by the parent molecule.
     *            Accordingly, this method is package-internal.
     */
    void addBond(Bond b) {
        if (_bonds.contains(b)) {
            return;
        }
        if (b.order().value() + numberOfBonds() > _valence) {
            throw new IllegalStateException(
                    String.format(
                            "Too many bonds; atom: %d, valence: %d, current number of bonds: %d, order of the new bond: %d",
                            _id, _valence, numberOfBonds(), b.order()));
        }

        _bonds.add(b);
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
