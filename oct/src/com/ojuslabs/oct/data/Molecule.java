package com.ojuslabs.oct.data;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.ojuslabs.oct.common.Constants.LIST_SIZE_L;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ojuslabs.oct.common.BondOrder;

public class Molecule
{
    final long                 _id;       // A unique ID. This does not change
                                           // during the lifetime of the
                                           // molecule.

    List<Atom>                 _atoms;    // List of this molecule's atoms.
    List<Bond>                 _bonds;    // List of this molecule's bonds.
    List<Ring>                 _rings;    // List of this molecule's rings.

    int                        _peakAId;  // Keeps track of running IDs of
                                           // atoms.
    int                        _peakBId;  // Keeps track of running IDs of
                                           // bonds.
    int                        _peakRId;  // Keeps track of running IDs of
                                           // rings.

    public String              vendorId;
    public String              vendorName;

    public Map<String, String> tags;      // Stores input data items as well
                                           // as run-time attributes.

    // A running serial unique identifier for molecules.
    private static long        _molId;

    /**
     * Factory method for creating molecules with unique IDs.
     * 
     * @return A new, uniquely-identifiable molecule.
     */
    public static synchronized Molecule newInstance() {
        return new Molecule(++_molId);
    }

    /**
     * The molecule's initialisation.
     */
    Molecule(long id) {
        _id = id;

        _atoms = Lists.newArrayListWithCapacity(LIST_SIZE_L);
        _bonds = Lists.newArrayListWithCapacity(LIST_SIZE_L);
        _rings = Lists.newArrayListWithCapacity(LIST_SIZE_L);

        tags = Maps.newHashMap();
    }

    /**
     * @return The globally unique ID of this molecule.
     */
    public long id() {
        return _id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (int) _id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Molecule)) {
            return false;
        }

        Molecule other = (Molecule) obj;
        if (_id != other.id()) {
            return false;
        }

        return true;
    }

    /**
     * @param i
     *            Unique ID of the atom in this molecule.
     * @return Requested atom if found; <code>null</code> otherwise.
     */
    public Atom atom(int i) {
        for (Atom a : _atoms) {
            if (i == a.id()) {
                return a;
            }
        }

        return null;
    }

    /**
     * @param id
     *            Unique ID of the bond in this molecule.
     * @return Requested bond if found; <code>null</code> otherwise.
     */
    public Bond bond(int id) {
        for (Bond b : _bonds) {
            if (id == b.id()) {
                return b;
            }
        }

        return null;
    }

    /**
     * Answers the bond in this molecule between the two given atoms. The atoms
     * must belong to this molecule; else an exception is thrown.
     * 
     * @param a1
     *            One of the atoms in the bond.
     * @param a2
     *            The other atom in the bond.
     * @return The bond between the two given atoms, if one such exists;
     *         <code>null</code> otherwise.
     */
    public Bond bondBetween(Atom a1, Atom a2) {
        if ((this != a1.molecule()) || (this != a2.molecule())) {
            throw new NoSuchElementException(
                    String.format(
                            "At least one of the given atoms does not belong to this molecule: %d, atoms: %d->%d, %d->%d",
                            _id, a1.molecule().id(), a1.id(), a2.molecule()
                                    .id(), a2.id()));
        }

        return _bondBetween(a1, a2);
    }

    // This part is reusable internally within this package without incurring
    // the overhead of the membership checks.
    Bond _bondBetween(Atom a1, Atom a2) {
        if (_bonds.isEmpty()) {
            return null;
        }

        int hash = _bonds.get(0).hash(a1, a2);
        for (Bond b : _bonds) {
            if (b._hash == hash) { // TODO(js): Change this only in conjuction
                                   // with Bond#hashCode().
                return b;
            }
        }

        return null;
    }

    /**
     * @param id
     *            Unique ID of the ring in this molecule.
     * @return Requested ring if found; <code>null</code> otherwise.
     */
    public Ring ring(int id) {
        for (Ring r : _rings) {
            if (id == r.id()) {
                return r;
            }
        }

        return null;
    }

    /**
     * @return The number of atoms in this molecule.
     */
    public int numberOfAtoms() {
        return _atoms.size();
    }

    /**
     * @return The number of bonds in this molecule.
     */
    public int numberOfBonds() {
        return _bonds.size();
    }

    /**
     * @return The number of double bonds in this molecule.
     */
    public int numberOfDoubleBonds() {
        int c = 0;
        for (Bond b : _bonds) {
            if (BondOrder.DOUBLE == b.order()) {
                c++;
            }
        }
        return c;
    }

    /**
     * @return The number of triple bonds in this molecule.
     */
    public int numberOfTripleBonds() {
        int c = 0;
        for (Bond b : _bonds) {
            if (BondOrder.TRIPLE == b.order()) {
                c++;
            }
        }
        return c;
    }

    /**
     * @return The number of rings in this molecule.
     */
    public int numberOfRings() {
        return _rings.size();
    }

    /**
     * @return A read-only view of this molecule's atoms.
     */
    public List<Atom> atoms() {
        return ImmutableList.copyOf(_atoms);
    }

    /**
     * @return A read-only view of this molecule's bonds.
     */
    public List<Bond> bonds() {
        return ImmutableList.copyOf(_bonds);
    }

    /**
     * @return A read-only view of this molecule's rings.
     */
    public List<Ring> rings() {
        return ImmutableList.copyOf(_rings);
    }

    /**
     * Adds the given atom to this molecule. Should it be needed, it first
     * removes it from its previous containing molecule. The rest of the state
     * of the atom is not cleared.
     * 
     * @param a
     *            The atom to be added to this molecule.
     */
    public void addAtom(Atom a) {
        if ((null == a) || (this == a.molecule())) {
            return;
        }

        a.setMolecule(this, false);
        a.setId(++_peakAId);
        _atoms.add(a);
    }

    /**
     * Adds a bond in this molecule between the two given atoms. Caller should
     * ensure that the given atoms are not <code>null</code>.
     * 
     * @param a1
     *            One of the atoms to be bonded.
     * @param a2
     *            The other atom to be bonded.
     */
    public Bond addBond(Atom a1, Atom a2, BondOrder order) {
        if ((this != a1.molecule()) || (this != a2.molecule())) {
            throw new NoSuchElementException(
                    String.format(
                            "One of the atoms in the given bond does not exist in this molecule; atoms: %d->%d, %d->%d",
                            a1.molecule().id(), a1.id(), a2.molecule().id(),
                            a2.id()));
        }

        // Is this bond already present?
        Bond tb = bondBetween(a1, a2);
        if (null != tb) {
            return tb;
        }

        Bond b = new Bond(++_peakBId, a1, a2);
        b.setOrder(order);
        _bonds.add(b);

        // Set neighbours appropriately.
        a1.addBond(b);
        a2.addBond(b);

        return b;
    }

    /**
     * Breaks the given bond. Its participating atoms are suitably adjusted. The
     * rings in which it participates are all broken, and hence cleared.
     * 
     * @param b
     *            The bond to be broken.
     */
    public void breakBond(Bond b) {
        int idx = _bonds.indexOf(b);
        if (-1 == idx) {
            throw new NoSuchElementException(
                    String.format(
                            "Given bond is not in this molecule. Molecule: %d, bond: %d",
                            _id, b.id()));
        }

        _breakBond(b, idx);
    }

    // Bypasses the membership check.
    void _breakBond(Bond b, int idx) {
        // Update both atoms.
        b.atom1().removeBond(b);
        b.atom2().removeBond(b);
        for (Ring r : b.rings()) {
            removeRing(r);
        }

        if (Integer.MIN_VALUE == idx) {
            _bonds.remove(b);
        }
        else {
            _bonds.remove(idx);
        }
    }

    // Rings are broken only indirectly through one of their bonds. Hence this
    // method is package-internal.
    void removeRing(Ring r) {
        for (Atom a : r.atoms()) {
            a.removeRing(r);
        }
        for (Bond b : r.bonds()) {
            b.removeRing(r);
        }

        _rings.remove(r);
    }

    /**
     * Removes the given atom from this molecule.
     * 
     * Usually, but not always, this method is invoked indirectly, when the atom
     * is either detached or is attached to a different molecule.
     * 
     * @param a
     *            The atom to remove from this molecule.
     */
    public void removeAtom(Atom a) {
        int idx = _atoms.indexOf(a);
        if (-1 == idx) {
            throw new NoSuchElementException(
                    String.format(
                            "Given atom is not in this molecule. Molecule: %d, atom: %d",
                            _id, a.id()));
        }

        _removeAtom(a, idx);
    }

    // Bypasses the membership check.
    void _removeAtom(Atom a, int idx) {
        for (Bond b : a.bonds()) {
            _breakBond(b, Integer.MIN_VALUE);
        }

        if (Integer.MIN_VALUE == idx) {
            _atoms.remove(a);
        }
        else {
            _atoms.remove(idx);
        }
    }
}
