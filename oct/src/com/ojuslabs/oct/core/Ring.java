/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.BondOrder;
import com.ojuslabs.oct.common.Unsaturation;

/**
 * Ring represents a simple cycle in a molecule. It keeps track of its atoms and
 * bonds, as well as its neighbouring rings.
 * 
 * A ring is created bound to a molecule, and cannot be re-bound.
 */
public final class Ring
{
    // Containing molecule of this ring.
    private final Molecule         _mol;
    // Index of this ring in its molecule.
    private int                    _id;

    // The atoms in this ring. Atoms occur in order.
    private final LinkedList<Atom> _atoms;
    // The bonds forming this ring. Bonds occur in order.
    private final LinkedList<Bond> _bonds;
    // Other rings that share at least one bond with this ring.
    private final LinkedList<Ring> _nbrs;

    // Is this ring aromatic in its current configuration?
    private boolean                _isAro;
    // Is this ring aromatic, involving at least one hetero atom?
    private boolean                _isHetAro;

    // Is this ring completed and finalised?
    private boolean                _completed;

    /**
     * @param mol
     *            The containing molecule of this ring.
     * @param id
     *            The unique ID of this ring in its molecule.
     */
    Ring(Molecule mol) {
        _mol = mol;

        _atoms = Lists.newLinkedList();
        _bonds = Lists.newLinkedList();
        _nbrs = Lists.newLinkedList();
    }

    /**
     * Resets the entire state of this ring. This method is useful when a ring
     * is used as a candidate path, where frequent backtracking may be needed.
     */
    void reset() {
        _atoms.clear();
        _bonds.clear();
        _nbrs.clear();

        _isAro = false;
        _isHetAro = false;
        _completed = false;
    }

    /**
     * @return The containing molecule of this ring.
     */
    public Molecule molecule() {
        return _mol;
    }

    /**
     * @return The unique ID of this ring.
     */
    public int id() {
        return _id;
    }

    /**
     * This is used by the containing molecule when parenting the ring.
     * 
     * @param id
     *            The unique ID of this ring in its molecule.
     */
    void setId(int id) {
        _id = id;
    }

    /**
     * @return The size of this ring. This is equivalently the number of atoms
     *         or the number of bonds participating in this ring.
     */
    public int size() {
        return _atoms.size();
    }

    /**
     * @return {@code true} if this ring is aromatic; {@code false} otherwise.
     */
    public boolean isAromatic() {
        return _isAro;
    }

    /**
     * <b>N.B.</b> {@link Ring#isAromatic()} answers {@code true} whenever this
     * method answers {@code true}.
     * 
     * @return {@code true} if this ring is aromatic, involving at least one
     *         hetero atom; {@code false} otherwise.
     */
    public boolean isHeteroAromatic() {
        return _isHetAro;
    }

    /**
     * @return True if this ring is complete; false otherwise.
     */
    public boolean isCompleted() {
        return _completed;
    }

    /**
     * @param id
     *            Unique normalised ID of the requested atom.
     * @return The requested atom if it exists; {@code null} otherwise.
     */
    public Atom atom(int id) {
        for (Atom a : _atoms) {
            if (a.id() == id) {
                return a;
            }
        }

        return null;
    }

    /**
     * Adds the given atom to this ring.
     * 
     * The given atom is ignored if it is already a member of this ring. It
     * checks to see that a bond exists between the most-recently-added atom and
     * the current atom. An exception is thrown otherwise.
     * 
     * @param a
     *            The atom to add to this ring.
     * @throws IllegalStateException
     *             if an attempt is made at adding atoms to a <i>completed</i>
     *             ring, or if the given atom does not logically continue from
     *             the most-recently added atom.
     * @throws IllegalArgumentException
     *             if {@code null} is given instead of an atom.
     */
    public void addAtom(Atom a) {
        if (_completed) {
            throw new IllegalStateException(String.format(
                    "Ring is already completed. %s", toString()));
        }
        if (null == a) {
            throw new IllegalArgumentException("Null atom given.");
        }

        if (_atoms.contains(a)) {
            return;
        }

        if (!_atoms.isEmpty()) {
            Atom prev = _atoms.getLast();
            Bond b = _mol.bondBetween(prev, a);
            if (null == b) {
                throw new IllegalStateException(
                        String.format(
                                "There is no bond between previous atom %d and current atom %d",
                                prev.id(), a.id()));
            }

            _bonds.add(b);
        }
        _atoms.add(a);
    }

    /**
     * Completes the link between the last atom and the first. Completion also
     * effectively freezes the ring.
     * 
     * @throws IllegalStateException
     *             if the size of the ring is less than 3, or if there is no
     *             bond connecting the first atom and the last.
     */
    public void complete() {
        if (_completed) {
            return;
        }

        int len = _atoms.size();
        if (len < 3) {
            throw new IllegalStateException(
                    String.format(
                            "The smallest possible size for a ring is 3. Current ring size is: %d",
                            len));
        }

        Atom a1 = _atoms.getFirst();
        Atom a2 = _atoms.getLast();
        Bond b = _mol.unsafeBondBetween(a1, a2);
        if (null == b) {
            throw new IllegalStateException(String.format(
                    "No bond between the first and the last atoms: %d, %d",
                    a1.id(), a2.id()));
        }

        _bonds.add(b);

        _completed = true;
    }

    /**
     * Transforms the ring into a standard representation, where the ring
     * (logically) `begins' with that atom which has the lowest unique ID.
     */
    void normalise() {
        int min = Integer.MAX_VALUE;

        // Find the index at which the atom with the lowest ID occurs.
        int idx = 0;
        for (int i = 0; i < _atoms.size(); i++) {
            int id = _atoms.get(i).id();
            if (id < min) {
                min = id;
                idx = i;
            }
        }

        // Rotate the list so that the atom with the minimum ID comes first.
        Collections.rotate(_atoms, idx);

        // Determine the ring's aromaticity.
        determineAromaticity();
    }

    /**
     * 
     */
    void determineAromaticity() {
        // TODO(js): Implement aromaticity determination.
        // This should set both `_isAro` and `_isHetAro`, as applicable. It
        // should also set the applicable aromaticity flags for all of its
        // atoms.
    }

    /**
     * @param id
     *            The unique ID of the bond to locate.
     * @return The bond with the given ID, if it occurs in this ring;
     *         {@code null} otherwise.
     */
    public Bond bond(int id) {
        for (Bond b : _bonds) {
            if (b.id() == id) {
                return b;
            }
        }

        return null;
    }

    /**
     * @return A read-only copy of this ring's atoms.
     */
    public List<Atom> atoms() {
        return ImmutableList.copyOf(_atoms);
    }

    /**
     * @return A read-only copy of this ring's bonds.
     */
    public List<Bond> bonds() {
        return ImmutableList.copyOf(_bonds);
    }

    /**
     * @return A read-only copy of this ring's neighbouring rings.
     */
    public List<Ring> neighbours() {
        return ImmutableList.copyOf(_nbrs);
    }

    /**
     * @return Number of neighbouring rings, irrespective of configuration
     *         (spiro, fused, etc.).
     */
    public int numberOfNeighbours() {
        return _nbrs.size();
    }

    /**
     * @return {@code true} if the current ring is of size 6, and is aromatic;
     *         {@code false} otherwise.
     */
    public boolean isAromaticOfSize6() {
        if ((6 == _atoms.size()) && _isAro) {
            return true;
        }

        return false;
    }

    /**
     * A 6-membered ring is semi-aromatic if it is <b>not</b> already aromatic,
     * and the following assertions hold.
     * <p>
     * {@code 6 = num_aro_atoms + num_dbl_bond_atoms + num_NH + num_exo_C_hetero_dbl_bonds}
     * <p>
     * {@code num_NH = num_exo_C_hetero_dbl_bonds}
     * 
     * @return {@code true} if the above conditions are met; {@code false}
     *         otherwise.
     */
    public boolean isSemiAromaticOfSize6() {
        // If the ring does not have six members, or is known to be aromatic, it
        // can not be semi-aromatic.
        if ((6 != _atoms.size()) || _isAro) {
            return false;
        }

        int nAro = numberOfAromaticAtoms();
        int nDoubly = numberOfDoubleBonds() * 2;

        int nNH = 0;
        int nDblExoCHetero = 0;
        for (Atom a : _atoms) {
            if (701 == a.hashValue()) {
                nNH++;
            }
            else {
                if (6 == a.element().number) {
                    Atom t = a.firstDoublyBondedNeighbour();
                    if (null != t) {
                        if (6 != t.element().number) {
                            // The doubly-bonded hetero atom should not
                            // participate in this ring.
                            if (null == atom(t.id())) {
                                nDblExoCHetero++;
                            }
                        }
                    }
                }
            }
        }

        int sum = nAro + nDoubly + nNH + nDblExoCHetero;
        return ((6 == sum) && (nNH == nDblExoCHetero));
    }

    /**
     * <b>N.B.</b> It is possible for the current ring to <b>not</b> be
     * aromatic, yet contain some aromatic atoms.
     * 
     * @return The number of aromatic atoms in this ring.
     */
    public int numberOfAromaticAtoms() {
        int n = 0;
        for (Atom a : _atoms) {
            if (Unsaturation.AROMATIC == a.unsaturation()) {
                n++;
            }
        }

        return n;
    }

    /**
     * @return The number of double bonds in this ring.
     */
    public int numberOfDoubleBonds() {
        int n = 0;
        for (Bond b : _bonds) {
            if (BondOrder.DOUBLE == b.order()) {
                n++;
            }
        }

        return n;
    }

    /**
     * @return {@code true} if the current ring has adjacent carbonyl carbons;
     *         {@code false} otherwise.
     */
    public boolean hasAdjacentCarbonyls() {
        boolean found = false;
        for (Atom a : _atoms) {
            if (a.isCarbonylC()) {
                if (found) {
                    return true;
                }
                else {
                    found = true;
                }
            }
            else {
                found = false;
            }
        }

        // If the last atom is a carbonyl C, we check to see if the first is as
        // well (wrap-around).
        if (found) {
            if (_atoms.get(0).isCarbonylC()) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return {@code true} if the current ring has adjacent saturated carbon
     *         atoms, each having at least one hydrogen bound to it;
     *         {@code false} otherwise.
     */
    public boolean hasAdjacentCHCH() {
        boolean found = false;
        for (Atom a : _atoms) {
            if (a.isSaturatedC()
                    && (a.numberOfHydrogens() > 0)) {
                if (found) {
                    return true;
                }
                else {
                    found = true;
                }
            }
            else {
                found = false;
            }
        }

        // If the last atom is a CH, we check to see if the first is as well
        // (wrap-around).
        if (found) {
            Atom t = _atoms.get(0);
            if (t.isSaturatedC()
                    && (t.numberOfHydrogens() > 0)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return {@code true} if the current ring has adjacent saturated carbon
     *         atoms; {@code false} otherwise.
     */
    public boolean hasAdjacentSaturatedCC() {
        boolean found = false;
        for (Atom a : _atoms) {
            if (a.isSaturatedC()) {
                if (found) {
                    return true;
                }
                else {
                    found = true;
                }
            }
            else {
                found = false;
            }
        }

        // If the last atom is a saturated C, we check to see if the first is as
        // well (wrap-around).
        if (found) {
            Atom t = _atoms.get(0);
            if (t.isSaturatedC()) {
                return true;
            }
        }

        return false;
    }

    /**
     * A ring cannot be compared until it is `completed'. Two `completed' rings
     * are equal iff they have the same participating atoms, in the same order.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!_completed) {
            return false;
        }

        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ring)) {
            return false;
        }

        Ring other = (Ring) obj;
        if (!other.isCompleted()) {
            return false;
        }
        if (_mol.id() != other.molecule().id()) {
            return false;
        }
        if (other.id() == _id) { // Same ring.
            return true;
        }

        if (_atoms.size() != other.size()) {
            return false;
        }

        LinkedList<Atom> l = other._atoms;
        for (int i = 0; i < _atoms.size(); i++) {
            if (_atoms.get(i).id() != l.get(i).id()) {
                return false;
            }
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String
                .format("Ring %d: [%s]", _id, Joiner.on(", ").join(_atoms));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int res = 0;
        for (Atom a : _atoms) {
            res += a.id();
            res *= 13;
        }

        return res;
    }
}
