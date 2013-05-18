/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.core;

/**
 * 
 */
public final class FunctionalGroup extends Molecule {
    // A running serial unique identifier for functional groups.
    private static long _nextId = 0;

    // This group's unique ID from the database of functional groups.
    private int         _fgId;

    /**
     * Factory method for creating functional groups with unique IDs.
     * 
     * @return A new, uniquely-identifiable functional group.
     */
    public static synchronized FunctionalGroup newInstance() {
        return new FunctionalGroup(++_nextId);
    }

    /*
     * Minimal initialisation.
     */
    FunctionalGroup(long id) {
        super(id);
    }

    /**
     * @return The unique ID of this functional group.
     */
    public int funGroupId() {
        return _fgId;
    }

    /**
     * @param id
     *            The unique ID of this functional group. This is obtained from
     *            the database of functional groups.
     * @throws IllegalArgumentException
     *             if the given ID is not a positive integer.
     */
    public void setFunGroupId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Functional group ID must be a positive integer.");
        }

        _fgId = id;
    }
}
