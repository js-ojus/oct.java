package com.ojuslabs.oct.common;

public enum Unsaturation
{
    /** All bonds are single. */
    NONE(0),
    /** Atom is part of an aromatic ring. */
    ARO_RING(1),
    /** C=C bond. */
    CC_DBOND(2),
    /** Double bond with a hetero atom. */
    HET_DBOND(3),
    /** Two double bonds, possibly with hetero atoms. */
    TWO_DBONDS(4),
    /** Triple bond, possibly with a hetero atom. */
    TBOND(5);

    private final int _unsaturation;

    Unsaturation(int u) {
        _unsaturation = u;
    }

    public int value() {
        return _unsaturation;
    }
}
