/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.core;

import static com.ojuslabs.oct.common.Constants.LIST_SIZE_S;

import java.util.Iterator;
import java.util.List;

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
    private Element          _element;     // This atom's element type.

    private Molecule         _mol;         // Containing molecule, if the atom
                                            // is
                                            // bound to one.

    private int              _id;          // A unique canonical ID within its
                                            // molecule; 1-based.
    private int              _inputId;     // The input order serial number of
                                            // this
                                            // atom; 1-based.

    public Point3D           coordinates;  // These may be given or computed.

    private byte             _numH;        // Number of attached H atoms.
    private byte             _charge;      // Residual charge on the atom.
    private byte             _valence;     // Current valence configuration of
                                            // this
                                            // atom.

    private final List<Bond> _bonds;       // Bonds this atom is a member of.
    private final List<Atom> _nbrs;        // This list in-line expands
                                            // `_bonds` with
    // repetitions for double/triple bonds.

    private Chirality        _chirality;   // Chirality type.
    private Radical          _radical;

    private final List<Ring> _rings;       // Rings this atom is a member of.
    private boolean          _inAroRing;   // Is this atom in an aromatic ring?
    private boolean          _inHetAroRing; // Is this atom in a hetero-aromatic
                                            // ring?
    private boolean          _isBridgeHead; // Is this atom a bridgehead?
    private boolean          _isSpiro;     // Is the sole common atom of two
                                            // rings?

    /**
     * Initialisation of a new atom.
     * 
     * @param elem
     *            The element type of this atom.
     */
    public Atom(Element elem) {
        _element = elem;
        if (null != elem) {
            _valence = (byte) _element.valence;
        }

        _chirality = Chirality.NONE;
        _radical = Radical.NONE;

        _bonds = Lists.newArrayListWithCapacity(LIST_SIZE_S);
        _nbrs = Lists.newArrayListWithCapacity(LIST_SIZE_S);
        _rings = Lists.newArrayListWithCapacity(LIST_SIZE_S);
    }

    /**
     * Resets the state of this atom. This method is useful when re-parenting an
     * atom. It may be useful in other scenarios as well, but use with caution!
     * <b>N.B.</b> This method is package-internal.
     */
    void reset() {
        _chirality = Chirality.NONE;
        _radical = Radical.NONE;

        _bonds.clear();
        _nbrs.clear();
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
        return _mol;
    }

    /**
     * Serves two purposes: (a) it can be used to re-parent the atom to a
     * different molecule, and (b) it can be used to detach this atom from its
     * current parent.
     * 
     * @param mol
     *            Containing molecule, if any. To remove the current parent,
     *            pass <code>null</code>.
     * @param clear
     *            If true, resets the state of the atom; else, leaves it as is.
     */
    public void setMolecule(Molecule mol, boolean clear) {
        if (_mol == mol) {
            return;
        }

        if (null != _mol) {
            _mol._removeAtom(this, Integer.MIN_VALUE);
        }

        _mol = mol;
        _id = 0;
        _inputId = 0;
        if (clear) {
            reset();
        }
    }

    /**
     * @return The canonical ID of this atom in its current molecule.
     */
    public int id() {
        return _id;
    }

    /**
     * <b>N.B.</b> The return value of this method makes sense only as long as
     * the molecule is in a pristine state after initial construction.
     * 
     * @return The input order ID of this atom in its current molecule.
     */
    public int inputId() {
        return _inputId;
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
    void setInputId(int id) {
        _inputId = id;
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
        if (v > 0) { // We do not deal with rare gases.
            _valence = (byte) v;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Atom ID: %d, input ID: %d, element: %s", _id,
                _inputId, _element.symbol);
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
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Atom)) return false;

        Atom other = (Atom) obj;
        if ((null == _mol) || (null == other.molecule())) {
            return false;
        }
        if ((_id != other._id) || (_mol.id() != other.molecule().id())) {
            return false;
        }

        return true;
    }

    /**
     * A free (unbound) atom has a hash code of 0. The hash code of a bound atom
     * depends on its molecule's globally unique ID as well as its own canonical
     * ID in that molecule.
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (null == _mol) {
            return 0;
        }

        return _id + 10000 * (int) _mol.id();
    }

    /**
     * This method answers the number of distinct neighbours of this atom. It is
     * the same as the number of bonds in which this atom participates.
     * 
     * @return The number of bonds of which this atom is a member.
     */
    public int numberOfBonds() {
        return _bonds.size();
    }

    /**
     * The number of neighbours is what we get by in-line expanding the list of
     * bonds, accounting for repetitions of neighbours whenever the bond is
     * either double or triple.
     * 
     * @return The total number of electrons of this atom that are participating
     *         in its bonds.
     */
    public int numberOfNeighbours() {
        return _nbrs.size();
    }

    /**
     * @return The total number of hydrogen atoms bound to this atom.
     */
    public int numberOfHydrogens() {
        return _numH;
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
     * @return The requested bond between this atom and the other atom;
     *         <code>null</code> if one such does not exist.
     * @throws IllegalArgumentException
     */
    public Bond bondTo(Atom other) throws IllegalArgumentException {
        if (null == other) {
            throw new IllegalArgumentException("Null atom given.");
        }
        if ((other.molecule() != _mol) || (null == _mol.atom(other.id()))) {
            throw new IllegalArgumentException(String.format(
                    "Given atom does not belong this molecule: %d->%d", other
                            .molecule().id(), other.id()));
        }

        for (Bond b : _bonds) {
            if (b.otherAtom(_id) == other) {
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
     * Checks to see that the proposed +ve or -ve change in the number of
     * neighbours is valid for this atom's current valence configuration. This
     * method is package-internal currently.
     * 
     * @param delta
     *            The number by which the number of neighbours may be liable to
     *            change.
     * @return <code>true</code> if the proposed change is within acceptable
     *         valence limits; <code>false</code> otherwise.
     */
    boolean tryChangeNumberOfNeighbours(int delta) {
        if (_nbrs.size() + delta > _valence) {
            return false;
        }

        return true;
    }

    /**
     * Adds the given bond to this atom, performing no validations.
     * 
     * This method does <b><i>not</i></b> check to see if the proposed increment
     * in the number of neighbours is valid for this atom's current valence
     * configuration. <b>N.B.</b> The current atom must be a part of the given
     * bond. However, this is assumed to have been taken care of by the parent
     * molecule.
     * 
     * @param b
     *            The bond to be added to this atom.
     * @return <code>true</code> upon successful addition; <code>false</code>
     *         otherwise.
     */
    boolean unsafeAddBond(Bond b) {
        if (_bonds.contains(b)) {
            return false;
        }

        _bonds.add(b);
        int delta = b.order().value();
        Atom other = b.otherAtom(_id);
        for (int i = 0; i < delta; i++) {
            _nbrs.add(other);
        }

        return true;
    }

    /**
     * Adds the given bond to this atom.
     * 
     * Should the addition of this bond increase the number of bonds of this
     * atom beyond its current valence configuration, an exception is thrown.
     * <b>N.B.</b> The current atom must participate in the given bond; however,
     * this is assumed to have been taken care of by the parent molecule.
     * 
     * @param b
     *            The bond to add to this atom.
     * @return <code>true</code> upon successful addition; <code>false</code>
     *         otherwise.
     * @throws IllegalStateException
     */
    boolean addBond(Bond b) throws IllegalStateException {
        if (tryChangeNumberOfNeighbours(b.order().value())) {
            throw new IllegalStateException(
                    String.format(
                            "Requested state illegal: atom: %d, valence: %d, current number of bonds: %d, order of the new bond: %d",
                            _id, _valence, numberOfBonds(), b.order().value()));
        }

        return unsafeAddBond(b);
    }

    /**
     * Removes the given bond from this atom. It also adjusts the neighbours
     * list appropriately. <b>N.B.</b> The current atom must participate in the
     * given bond; however, this is assumed to have been taken care of by the
     * parent molecule. Accordingly, this method is package-internal.
     * 
     * @param b
     *            The bond to remove from this atom.
     * @return <b>true</b> if the given bond was actually removed; <b>false</b>
     *         otherwise.
     */
    boolean removeBond(Bond b) {
        Atom other = b.otherAtom(_id);
        Iterator<Atom> it = _nbrs.iterator();
        while (it.hasNext()) {
            if (it.next() == other) {
                it.remove();
            }
        }

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
