package com.ojuslabs.oct.common;

public class Unsaturation
{
    /** All bonds are single. */
    public static final byte NONE       = 0;
    /** Atom is part of an aromatic ring. */
    public static final byte ARO_RING   = 1;
    /** C=C bond. */
    public static final byte CC_DBOND   = 2;
    /** Double bond with a hetero atom. */
    public static final byte HET_DBOND  = 3;
    /** Two double bonds, possibly with hetero atoms. */
    public static final byte TWO_DBONDS = 4;
    /** Triple bond, possibly with a hetero atom. */
    public static final byte TBOND      = 5;
    static final byte        _SENTINEL  = 6;

    /**
     * @param unsat
     *            Integer representing a state of unsaturation of an atom.
     * @return True if the given integer represents a valid state of
     *         unsaturation; false otherwise.
     */
    public static boolean isValid(byte unsat) {
        if ((unsat < 0) || (unsat >= _SENTINEL)) {
            return false;
        }

        return true;
    }
}
