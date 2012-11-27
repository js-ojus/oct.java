package com.ojuslabs.oct.common;

public enum BondOrder
{
    SINGLE(1),
    DOUBLE(2),
    TRIPLE(3),
    ALTERN(4); // InChI remark: `Avoid by all
                         // means'!

    private final int _order;

    BondOrder(int o) {
        _order = o;
    }

    public int value() {
        return _order;
    }
}
