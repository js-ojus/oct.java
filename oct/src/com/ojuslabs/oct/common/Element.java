/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.common;

/**
 * Element holds the essential chemical information of a given natural element.
 * The precision of the information given herein is <b>not</b> expected (or
 * required) to be scientifically fully accurate.
 */
public final class Element
{
    public final int    number;
    public final String symbol;
    public final double weight;
    public final int    valence;

    public Element(int i, String sym, double wt, int j) {
        number = i;
        symbol = sym;
        weight = wt;
        valence = j;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "Element [number=%s, symbol=%s, weight=%s, valence=%s]",
                number, symbol, weight, valence);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Element)) {
            return false;
        }

        Element other = (Element) obj;
        if (symbol == null) {
            if (other.symbol != null) {
                return false;
            }
        }
        else if (!symbol.equals(other.symbol)) {
            return false;
        }
        return true;
    }
}
