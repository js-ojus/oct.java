package com.ojuslabs.oct.core;

import java.util.List;

import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.Constants;

/**
 * Reaction is central to this library. It represents a chemical reaction with
 * <ul>
 * <li>one or more reactants,</li>
 * <li>one or more products,</li>
 * <li>one or more catalysts,</li>
 * <li>one or more reagents and</li>
 * <li>one or more solvents.</li>
 * </ul>
 * <p>
 * In addition, it has a rich variety of characteristics including
 * <ul>
 * <li>yield,</li>
 * <li>allowed temperature range,</li>
 * <li>interfering groups (including potential destructions),</li>
 * <li>reaction conditions and</li>
 * <li>literature references.</li>
 * </ul>
 */
public class Reaction
{
    private final int      _reactionNumber; // Reaction number (from the
                                            // database of general reactions)
                                            // which is associated with this
                                            // reaction object.

    private List<Molecule> _reactants;     // Reactant and coreactants.
    private List<Molecule> _products;      // Primary product and byproducts.
    private List<Molecule> _catalysts;     // These do NOT contribute any atoms
                                            // to the products.
    private List<Molecule> _reagents;      // These contribute non-carbon
                                            // atoms.
    private List<Molecule> _solvents;      // These do NOT contribute any atoms
                                            // to the products.

    private double         _yield;         // Yield of this reaction as a
                                            // percentage.

    public Reaction(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    String.format("Reaction number has to be a positive integer."));
        }
        _reactionNumber = n;

        _reactants = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_T);
        _products = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_T);
        _catalysts = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_T);
        _reagents = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_T);
        _solvents = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_T);
    }
}
