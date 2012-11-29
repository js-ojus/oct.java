package com.ojuslabs.oct.common;

/**
 * Element holds the essential chemical information of a given natural element.
 * The precision of the information given herein is <b>not</b> expected (or
 * required) to be scientifically fully accurate.
 */
public class Element
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
}
