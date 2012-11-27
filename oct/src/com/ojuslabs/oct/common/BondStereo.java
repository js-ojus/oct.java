package com.ojuslabs.oct.common;

/**
 * BondStereo lists possible stereo states of a bond.
 */
public enum BondStereo
{
    NONE(0),
    ANY(1),
    E(2),
    Z(3),
    CIS(4),
    TRANS(5);

    private final int _stereo;

    BondStereo(int s) {
        _stereo = s;
    }

    public int value() {
        return _stereo;
    }
}
