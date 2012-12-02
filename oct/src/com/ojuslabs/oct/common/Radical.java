package com.ojuslabs.oct.common;

import java.util.NoSuchElementException;

public enum Radical
{
    NONE(0),
    SINGLET(1),
    DOUBLET(2),
    TRIPLET(3);

    private final int _radical;

    Radical(int n) {
        _radical = n;
    }

    public int value() {
        return _radical;
    }

    /**
     * Answers the enumeration constant corresponding to the given value.
     * 
     * @param n
     *            The numerical value of the required enumeration constant.
     * @return The requested enumeration constant.
     */
    public static Radical ofValue(int n) {
        switch (n) {
        case 0:
            return NONE;
        case 1:
            return SINGLET;
        case 2:
            return DOUBLET;
        case 3:
            return TRIPLET;
        default:
            throw new NoSuchElementException(String.format(
                    "Illegal value specified: %d", n));
        }
    }
}
