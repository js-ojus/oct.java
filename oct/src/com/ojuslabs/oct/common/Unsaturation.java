/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.common;

public enum Unsaturation
{
    /** All bonds are single. */
    NONE(0),
    /** Atom is part of an aromatic ring. */
    AROMATIC(1),
    /** Double bond with a carbon atom. */
    DBOND_C(2),
    /** Double bond with a hetero atom. */
    DBOND_X(3),
    /** Two double bonds, both with carbon atoms. */
    DBOND_C_C(4),
    /** Two double bonds, one with a hetero atom. */
    DBOND_C_X(5),
    /** Two double bonds, both with hetero atoms. */
    DBOND_X_X(6),
    /** Triple bond with a carbon atom. */
    TBOND_C(7),
    /** Triple bond with a hetero atom. */
    TBOND_X(8);

    private final int _unsaturation;

    Unsaturation(int u) {
        _unsaturation = u;
    }

    public int value() {
        return _unsaturation;
    }
}
