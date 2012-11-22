package com.ojuslabs.oct.common;

public class BondOrder
{
    public static final byte NONE      = 0;
    public static final byte SINGLE    = 1;
    public static final byte DOUBLE    = 2;
    public static final byte TRIPLE    = 3;
    public static final byte ALTERN    = 4; // InChI remark: `Avoid by all
                                            // means'!
    static final byte        _SENTINEL = 5;

    /**
     * @param bo
     *            Integer value representing a bond order.
     * @return True if the given integer represents a valid bond order; false
     *         otherwise.
     */
    public static boolean isValid(byte bo) {
        if ((bo < 1) || (bo >= _SENTINEL)) {
            return false;
        }

        return true;
    }
}
