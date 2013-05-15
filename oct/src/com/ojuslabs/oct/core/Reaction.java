/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.core;

import static com.ojuslabs.oct.common.Constants.LIST_SIZE_S;

import java.util.List;

import com.google.common.collect.Lists;
import com.ojuslabs.oct.util.Reference;

/**
 * Reaction is central to this library. It represents a chemical reaction
 * characterised by:
 * <ul>
 * <li>one product and</li>
 * <li>one or more characteristic produced substructures in the product.</li>
 * </ul>
 * <p>
 * In addition, it is qualified by:
 * <ul>
 * <li>criteria that atoms and bonds of the product have to meet, for this
 * reaction to produce a characteristic substructure,</li>
 * <li>criteria that specific reactants getting generated have to meet, for this
 * reaction to generate them from generic reactant structures,</li>
 * <li>reagents needed in the process,</li>
 * <li>required temperature range,</li>
 * <li>percentage yield of the product, subject to all of the above, and</li>
 * <li>literature references.</li>
 * </ul>
 */
public class Reaction
{
    /*
     * A unique reaction number (from the database of general reactions) which
     * is associated with this reaction object.
     */
    private final int             _reactionNumber;

    /*
     * A list of the characteristic produced substructures of this general
     * reaction.
     */
    private final List<Integer>   _pSubstructures;
    // Criteria that the atoms and the bonds of the product should satisfy.
    private final List<Integer>   _prodCriteria;
    // Criteria that the atoms and the bonds of the reactant(s) should satisfy.
    private final List<Integer>   _reacCriteria;
    // Reagents participating in this reaction.
    private final List<Integer>   _reagents;

    // Lower limit of the required temperature range.
    private int                   _tempLower;
    // Upper limit of the required temperature range.
    private int                   _tempUpper;

    // Percentage yield of the product molecule.
    private double                _yield;

    // Literature references.
    private final List<Reference> _references;

    public Reaction(int n) {
        _reactionNumber = n;

        _pSubstructures = Lists.newArrayListWithCapacity(LIST_SIZE_S);
        _prodCriteria = Lists.newArrayListWithCapacity(LIST_SIZE_S);
        _reacCriteria = Lists.newArrayListWithCapacity(LIST_SIZE_S);
        _reagents = Lists.newArrayListWithCapacity(LIST_SIZE_S);

        _references = Lists.newArrayListWithCapacity(LIST_SIZE_S);
    }
}
