package com.ojuslabs.oct.common;

/**
 * BondStereo lists possible stereo states of a bond.
 */
public class BondStereo
{
    public static final byte NONE      = 0;
    public static final byte ANY       = 1;
    public static final byte E         = 2;
    public static final byte Z         = 3;
    public static final byte CIS       = 4;
    public static final byte TRANS     = 5;
    static final byte        _SENTINEL = 6;

    /**
     * @param bs
     *            Integer representing a stereo configuration for a bond.
     * @return True if the given integer represents a valid stereo
     *         configuration; false otherwise.
     */
    public static boolean isValid(byte bs) {
        if ((bs < 0) || (bs >= _SENTINEL)) {
            return false;
        }

        return true;
    }
}
