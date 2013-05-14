/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

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
    // Reaction number (from the database of general reactions) which is
    // associated with this reaction object.
    private final int            _reactionNumber;
    // Reactant and coreactants.
    private final List<Molecule> _reactants;
    // Primary product and byproducts.
    private final List<Molecule> _products;
    // These do NOT contribute any atoms to the products.
    private final List<Molecule> _catalysts;
    // These contribute non-carbon atoms.
    private final List<Molecule> _reagents;
    // These do NOT contribute any atoms to the products.
    private final List<Molecule> _solvents;
    // Yield of this reaction as a percentage.
    private double               _yield;

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
