/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.lib;

import java.util.List;

import com.ojuslabs.oct.core.Atom;
import com.ojuslabs.oct.core.Molecule;

/**
 * A ring validator instance applies a single test on the given candidate ring,
 * either accepting it or rejecting.
 */
public interface IRingValidator {
    /**
     * Either approves or rejects as a valid ring, the given candidate path,
     * subject to the specific logic in each instance.
     * 
     * @param mol
     *            The containing molecule of the candidate path.
     * @param atoms
     *            The list of non-terminal atoms of the molecule.
     * @param nbrs
     *            A list of lists of neighbours of the above atoms.
     * @param path
     *            The candidate path that aspires to become a ring.
     * @return {@code true} if the path satisfies the criteria in the specific
     *         instance; {@code false} otherwise.
     */
    boolean isValid(Molecule mol, List<Atom> atoms, List<List<Atom>> nbrs,
            List<Atom> path);
}
