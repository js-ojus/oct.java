/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.util;

/**
 * AbstractGroup is an abstract base class for groups such as functional groups
 * and leaving groups.
 */
public abstract class AbstractGroup {
    // A unique ID for this group.
    protected final int    _id;
    // A textual description of this group.
    protected final String _desc;

    /**
     * @param id
     *            A unique ID for this functional group; must be > {@code 0}.
     * @param s
     *            A human-readable description of this functional group; must be
     *            non-{@code null} and non-empty.
     */
    public AbstractGroup(int id, String s) {
        if (id <= 0 || null == s || s.isEmpty()) {
            throw new IllegalArgumentException(
                    "Either the functional group ID or its description is invalid.");
        }

        _id = id;
        _desc = s;
    }

    /**
     * @return The unique ID of this functional group.
     */
    public int id() {
        return _id;
    }

    /**
     * @return A textual description of this functional group.
     */
    public String description() {
        return _desc;
    }
}
