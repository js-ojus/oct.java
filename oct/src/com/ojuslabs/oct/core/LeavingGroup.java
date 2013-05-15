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
public final class LeavingGroup extends Molecule {
    // A running serial unique identifier for leaving groups.
    private static long _nextId = 0;

    // This group's unique ID from the database of leaving groups.
    private int         _lgId;

    /**
     * Factory method for creating leaving groups with unique IDs.
     * 
     * @return A new, uniquely-identifiable leaving group.
     */
    public static synchronized LeavingGroup newInstance() {
        return new LeavingGroup(++_nextId);
    }

    /*
     * Minimal initialisation.
     */
    LeavingGroup(long id) {
        super(id);
    }

    /**
     * @return The unique ID of this leaving group.
     */
    public int leavingGroupId() {
        return _lgId;
    }

    /**
     * @param id
     *            The unique ID of this leaving group. This is obtained from the
     *            database of leaving groups.
     */
    public void setLeavingGroupId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Leaving group ID must be a positive integer.");
        }

        _lgId = id;
    }
}
