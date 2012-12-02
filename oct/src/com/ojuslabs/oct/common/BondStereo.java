package com.ojuslabs.oct.common;

/**
 * BondStereo lists possible stereo states of a bond.
 */
public enum BondStereo
{
    NONE(0),
    UP(1),
    DOWN(2),
    UP_OR_DOWN(3),
    E(4),
    Z(5),
    CIS(6),
    TRANS(7),
    UNSPECIFIED(8);

    private final int _stereo;

    BondStereo(int s) {
        _stereo = s;
    }

    public int value() {
        return _stereo;
    }
}
