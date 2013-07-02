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
    boolean validate(Molecule mol, List<Atom> atoms, List<List<Atom>> nbrs,
            List<Atom> path);
}
