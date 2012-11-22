package com.ojuslabs.oct.common;

public class Chirality
{
    /** Atom is achiral. */
    public static final byte NONE      = 0;
    /** Atom has R chirality. */
    public static final byte CLOCK     = 1;
    /** Atom has S chirality. */
    public static final byte ANTICLOCK = 2;
    /** Atom is part of a racemic mixture. */
    public static final byte RACEMIC   = 3;
    /** Atom is chiral, but exact chirality is not yet known. */
    public static final byte UNDEFINED = 4;
    static final byte        _SENTINEL = 5;

    /**
     * @param ch
     *            Integer representing chirality of an atom.
     * @return True if the given integer represents a valid chirality; false
     *         otherwise.
     */
    public static boolean isValid(byte ch) {
        if ((ch < 0) || (ch >= _SENTINEL)) {
            return false;
        }

        return true;
    }
}
