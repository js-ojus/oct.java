/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.core;

import java.util.BitSet;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.Constants;

/**
 * RingSystem represents a set of physically fused rings. Fusion is usually of
 * one of the following types.
 * <ul>
 * <li>One bond in common between a given pair of rings. This occurs in a
 * majority of molecules.</li>
 * <li>More than one bond in common between a given pair of rings. This
 * configuration involves bridges.</li>
 * <li>One atom in common between a given pair of rings. This is a spiro
 * configuration.</li>
 * </ul>
 * <p>
 * A ring system is always bound to its molecule. It is mutable, <i>i.e.,</i> it
 * can change during the course of the life of a molecule.
 */
public final class RingSystem {
    /* Containing molecule of this ring. */
    private final Molecule _mol;
    /* Index of this ring in its molecule. */
    private int            _id;
    /* The list of rings in this ring system. */
    private List<Ring>     _rings;
    /* A bit set tracking all the atoms in all the rings in this system. */
    private BitSet         _atomBitSet;
    /* A bit set tracking all the bonds in all the rings in this system. */
    private BitSet         _bondBitSet;

    /**
     * @param mol
     *            The containing molecule of this ring system.
     */
    public RingSystem(Molecule mol) {
        _mol = mol;

        _rings = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_S);

        _atomBitSet = new BitSet(Constants.LIST_SIZE_M);
        _bondBitSet = new BitSet(Constants.LIST_SIZE_M);
    }

    /**
     * @return The containing molecule of this ring system.
     */
    public Molecule molecule() {
        return _mol;
    }

    /**
     * @return The unique ID of this ring system.
     */
    public int id() {
        return _id;
    }

    /**
     * This is used by the ring detector when forming the ring system.
     * 
     * @param id
     *            The unique ID of this ring system in its molecule.
     */
    public void setId(int id) {
        _id = id;
    }

    /**
     * @return The size of this ring system.
     */
    public int size() {
        return _rings.size();
    }

    /**
     * @return A clone of the bit set of the input IDs of the atoms in this ring
     *         system.
     */
    public BitSet atomBitSet() {
        return (BitSet) _atomBitSet.clone();
    }

    /**
     * @return A clone of the bit set of the IDs of the bonds in this ring
     *         system.
     */
    public BitSet bondBitSet() {
        return (BitSet) _bondBitSet.clone();
    }

    /**
     * @param id
     *            The unique ID of the desired ring.
     * @return The ring corresponding to the given ID, if found; {@code null}
     *         otherwise.
     */
    public Ring ring(int id) {
        for (Ring r : _rings) {
            if (r.id() == id) {
                return r;
            }
        }

        return null;
    }

    /**
     * @param idx
     *            The index the ring at which is desired.
     * @return The ring corresponding to the given index, if valid.
     * @see IndexOutOfBoundsException
     */
    public Ring ringAt(int idx) {
        return _rings.get(idx);
    }

    /**
     * @return A read-only copy of the rings in this system.
     */
    public List<Ring> rings() {
        return ImmutableList.copyOf(_rings);
    }

    /**
     * Adds the given ring to this ring system. It also updates the internal bit
     * sets appropriately.
     * 
     * @param r
     *            The ring to be added to this system.
     * @throws IllegalArgumentException
     *             if the given ring is either {@code null} or does not belong
     *             to this molecule.
     * @throws IllegalStateException
     *             if the given ring has no bonds or atoms in common with this
     *             system.
     */
    public void addRing(Ring r) {
        addRingAt(_rings.size(), r);
    }

    /**
     * Adds the given ring to this ring system. It also updates the internal bit
     * sets appropriately.
     * 
     * @param r
     *            The ring to be added to this system.
     * @param idx
     *            The index at which the ring has to be inserted.
     * @throws IllegalArgumentException
     *             if the given ring is either {@code null} or does not belong
     *             to this molecule.
     * @throws IllegalStateException
     *             if the given ring has no bonds or atoms in common with this
     *             system.
     */
    public void addRingAt(int idx, Ring r) {
        if (null == r) {
            throw new IllegalArgumentException("Null ring given.");
        }
        if (r.molecule() != _mol) {
            throw new IllegalArgumentException(String.format(
                    "The given ring does not belong to this molecule: %d.",
                    r.id()));
        }

        if (_bondBitSet.cardinality() > 0) {
            BitSet tbs = (BitSet) _bondBitSet.clone();
            tbs.and(r.bondBitSet());
            if (0 == tbs.cardinality()) {
                tbs = (BitSet) _atomBitSet.clone();
                tbs.and(r.atomBitSet());
                if (0 == tbs.cardinality()) {
                    throw new IllegalStateException(
                            "The given ring has no bonds or atoms in common with this system.");
                }
            }
        }

        _rings.add(idx, r);
        _atomBitSet.or(r.atomBitSet());
        _bondBitSet.or(r.bondBitSet());
    }

    /**
     * Removes the given ring from this system. Note that this invalidates the
     * atom and bond bit sets, so they are rebuilt for consistency.
     * 
     * @param r
     *            The ring to remove.
     */
    public void removeRing(Ring r) {
        int idx = _rings.indexOf(r);
        if (-1 == idx) {
            throw new IllegalArgumentException(String.format(
                    "The given ring does not belong to this system: %d.",
                    r.id()));
        }

        _rings.remove(idx);
        rebuildBitSets();
    }

    /**
     * Removes the given ring from this system. Note that this invalidates the
     * atom and bond bit sets, so they are rebuilt for consistency.
     * 
     * @param idx
     *            The index of the ring to remove.
     */
    public void removeRingAt(int idx) {
        _rings.remove(idx);
        rebuildBitSets();
    }

    /**
     * Rebuilds the atom and bond bitsets from the currently present list of
     * rings in this system.
     */
    void rebuildBitSets() {
        _atomBitSet.clear();
        _bondBitSet.clear();

        for (Ring r : _rings) {
            _atomBitSet.or(r.atomBitSet());
            _bondBitSet.or(r.bondBitSet());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((_atomBitSet == null) ? 0 : _atomBitSet.hashCode());
        result = prime * result
                + ((_bondBitSet == null) ? 0 : _bondBitSet.hashCode());
        result = prime * result + ((_mol == null) ? 0 : _mol.hashCode());
        return result;
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
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RingSystem)) {
            return false;
        }
        RingSystem other = (RingSystem) obj;

        if (_mol == null) {
            if (other._mol != null) {
                return false;
            }
        }
        else if (_mol.id() != other.molecule().id()) {
            return false;
        }

        if (_atomBitSet == null) {
            if (other._atomBitSet != null) {
                return false;
            }
        }
        else if (!_atomBitSet.equals(other._atomBitSet)) {
            return false;
        }

        if (_bondBitSet == null) {
            if (other._bondBitSet != null) {
                return false;
            }
        }
        else if (!_bondBitSet.equals(other._bondBitSet)) {
            return false;
        }
        return true;
    }
}
