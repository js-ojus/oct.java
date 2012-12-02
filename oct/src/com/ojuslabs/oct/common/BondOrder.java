package com.ojuslabs.oct.common;

import java.util.NoSuchElementException;

public enum BondOrder
{
    SINGLE(1),
    DOUBLE(2),
    TRIPLE(3),
    AROMATIC(4),
    SINGLE_OR_DOUBLE(5),
    SINGLE_OR_AROMATIC(6),
    DOUBLE_OR_AROMATIC(7),
    UNSPECIFIED(8); // Could be any of the above.

    private final int _order;

    BondOrder(int o) {
        _order = o;
    }

    public int value() {
        return _order;
    }

    /**
     * Answers the enumeration constant corresponding to the given value.
     * 
     * @param n
     *            The numerical value of the required enumeration constant.
     * @return The requested enumeration constant.
     */
    public static BondOrder ofValue(int n) {
        for (BondOrder o : BondOrder.values()) {
            if (n == o.value()) return o;
        }

        throw new NoSuchElementException(String.format(
                "Illegal value specified: %d", n));
    }
}
