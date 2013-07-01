/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.core;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.BondOrder;
import com.ojuslabs.oct.common.BondStereo;
import com.ojuslabs.oct.common.Constants;

/**
 * Bond represents a chemical bond. This flavour is strictly between two atoms,
 * and does <b>not</b> cater to multi-bond requirements.
 */
public final class Bond
{
    // Unique ID of this bond in its molecule.
    private final int        _id;

    private final Atom       _a1;
    private final Atom       _a2;
    private BondOrder        _order;
    private BondStereo       _stereo;

    // Is this bond aromatic?
    private boolean          _isAro;
    // The rings in which this bond participates.
    private final List<Ring> _rings;

    // Cached in the object for faster search.
    private final int        _hash;

    /**
     * The atoms participating in a bond cannot change. Accordingly, they have
     * be non-null and valid in the current molecule. However, this constructor
     * is package-internal. It is the responsibility of {@link Molecule} to
     * comply with these requirements.
     * 
     * @param id
     *            The unique ID of this bond.
     * @param a1
     *            The first atom participating in this bond.
     * @param a2
     *            The second atom participating in this bond.
     * @param order
     *            The bond order of this bond. See {@link common.BondOrder} for
     *            possible values.
     */
    Bond(int id, Atom a1, Atom a2, BondOrder order) {
        _id = id;
        _a1 = a1;
        _a2 = a2;

        _hash = hash(_a1, _a2);

        _order = order;
        _stereo = BondStereo.NONE;

        _rings = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_S);
    }

    /**
     * @return The unique ID of this bond.
     */
    public int id() {
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
     * member atoms appropriately.
     * 
     * There are three broad scenarios.
     * <ol>
     * <li>The given bond order is the same as the current one. The method
     * returns immediately.</li>
     * <li>The given bond order violates the valence of at least one atom. An
     * exception is thrown.</li>
     * <li>The given bond order can be successfully set, and is set.</li>
     * </ol>
     * 
     * @param o
     *            The new bond order to set. See {@link BondOrder} for possible
     *            bond orders.
     * @throws IllegalStateException
     *             if the new bond order violates the valence configuration of
     *             at least one atom.
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
                            "Illegal state for atom: %d->%d. Current valence: %d. Number of bonds: %d, new bond order: %d",
                            _a1.molecule().id(), _a1.id(), _a1.valence(),
                            _a1.numberOfBonds(), o.value()));
        }
        res = _a2.numberOfBonds() + delta;
        if (res > _a2.valence()) {
            throw new IllegalStateException(
                    String.format(
                            "Illegal state for atom: %d->%d. Current valence: %d. Number of bonds: %d, new bond order: %d",
                            _a2.molecule().id(), _a2.id(), _a2.valence(),
                            _a2.numberOfBonds(), o.value()));
        }

        _order = o;
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
     * Answers the other participating atom in this bond.
     * 
     * @param ido
     *            ID of the atom whose pairing atom is requested.
     * @return The other atom participating in this bond.
     * @throws IllegalArgumentException
     *             if the given atom is not a part of this bond.
     */
    public Atom otherAtom(int ido) {
        if (_a1.id() == ido) {
            return _a2;
        }
        else if (_a2.id() == ido) {
            return _a1;
        }

        throw new IllegalArgumentException(String.format(
                "Atoms in this bond: %d, %d; given ID: %d", _a1.id(),
                _a2.id(), ido));
    }

    /**
     * Checks to see if the current bond binds the given atoms.
     * 
     * @param a1
     *            One of the atoms in the bond.
     * @param a2
     *            The other atom in the bond.
     * @return {@code true} if this bond binds the given atoms; {@code false}
     *         otherwise.
     */
    public boolean binds(Atom a1, Atom a2) {
        if ((_a1 == a1) && (_a2 == a2)) {
            return true;
        }
        if ((_a1 == a2) && (_a2 == a1)) {
            return true;
        }

        return false;
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
    public static int hash(Atom a1, Atom a2) {
        int result = 0;
        if (a1.id() < a2.id()) {
            result = 10000 * a1.id() + a2.id();
        }
        else {
            result = 10000 * a2.id() + a1.id();
        }

        return result;
    }

    /**
     * @return The precomputed hash value reflecting the atoms in this bond.
     */
    int hashValue() {
        return _hash;
    }

    /**
     * @return True if this bond has at least one aromatic atom participating.
     */
    public boolean isAromatic() {
        return _isAro;
    }

    /**
     * <b>N.B.</b> The current bond must participate in the given ring; however,
     * this is assumed to have been taken care of by the parent molecule.
     * Accordingly, this method is package-internal.
     * 
     * @param r
     *            The ring to add to this bond.
     */
    void addRing(Ring r) {
        if (!_rings.contains(r)) {
            _rings.add(r);
        }
    }

    /**
     * <b>N.B.</b> The current bond must participate in the given ring; however,
     * this is assumed to have been taken care of by the parent molecule.
     * Accordingly, this method is package-internal.
     * 
     * @param r
     *            The ring to remove from this bond.
     * @return {@code true} if the given ring was actually removed;
     *         {@code false} otherwise.
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
        return _rings.contains(r);
    }

    /**
     * @param n
     *            Required size of the ring in which this bond has to
     *            participate.
     * @return {@code true} if this bond participates in at least one such ring;
     *         {@code false} otherwise.
     */
    public boolean inRingOfSize(int n) {
        for (Ring r : _rings) {
            if (r.size() == n) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return The smallest ring in which this bond participates, if one such
     *         unique ring exists; {@code null} otherwise.
     * @throws IllegalStateException
     *             if more than one ring of the smallest size are found.
     */
    public Ring smallestRing() {
        int min = Integer.MAX_VALUE;
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
            throw new IllegalStateException(String.format(
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
     * @return A read-only copy of the rings this bond participates in.
     */
    public List<Ring> rings() {
        return ImmutableList.copyOf(_rings);
    }

    /**
     * Resets this bond's ring information, as well as its aromaticity status.
     */
    void resetRings() {
        _rings.clear();

        _isAro = false;
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
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Bond)) {
            return false;
        }

        Bond other = (Bond) obj;
        if ((_order != other._order) || (_hash != other._hash)) {
            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return _hash << _order.value();
    }
}
