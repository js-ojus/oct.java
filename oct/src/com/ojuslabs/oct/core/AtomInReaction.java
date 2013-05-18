/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.core;

/**
 * AtomInReaction extends the class {@link Atom} with additional properties and
 * methods needed for its participation in reactions.
 */
public class AtomInReaction {
    // The underlying atom that is wrapped.
    public final Atom atom;
    // Direct correspondence with an atom in the goal molecule.
    private int       _goalAtomId;
    // Correspondence with an atom in the immediate product.
    private int       _productAtomId;

    /**
     * Initialisation of a new atom.
     * 
     * @param a
     *            The atom being wrapped.
     * @throws IllegalArgumentException
     *             if {@code null} atom is given.
     */
    public AtomInReaction(Atom a) {
        if (null == a) {
            throw new IllegalArgumentException("Atom should not be null.");
        }

        atom = a;
    }
}
