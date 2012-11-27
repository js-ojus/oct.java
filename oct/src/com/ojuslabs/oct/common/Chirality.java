package com.ojuslabs.oct.common;

public enum Chirality
{
    /** Atom is achiral. */
    NONE(0),
    /** Atom has R chirality. */
    CLOCK(1),
    /** Atom has S chirality. */
    ANTICLOCK(2),
    /** Atom is part of a racemic mixture. */
    RACEMIC(3),
    /** Atom is chiral, but exact chirality is not yet known. */
    UNDEFINED(4);

    private final int _chirality;

    Chirality(int c) {
        _chirality = c;
    }

    public int value() {
        return _chirality;
    }
}
