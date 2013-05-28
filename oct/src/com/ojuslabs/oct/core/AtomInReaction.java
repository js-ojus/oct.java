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
public final class AtomInReaction {
    // The underlying atom that is wrapped. The interface of this atom is NOT
    // wrapped. Rather, the atom itself is made available as a public member.
    public final Atom atom;
    // Direct correspondence with an atom in the goal molecule.
    private int       _goalAtomId;
    // Correspondence with an atom in the immediate product.
    private int       _productAtomId;
    // Correspondence with an atom in a reactant.
    private Molecule  _reactant;
    private int       _reactantAtomId;
    // Should the chirality of this atom be preserved throughout?
    private boolean   _preserveChirality;
    // Should this atom be prevented from getting modified anywhere at all?
    private boolean   _dontTouch;

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
