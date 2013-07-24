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
import com.ojuslabs.oct.core.Ring;
import com.ojuslabs.oct.core.RingSystem;

/**
 * An interface satisfied by all ring detectors. <b>N.B.</b> The detector does
 * <b>not</b> check the basics again; the molecule is supposed to have already
 * checked the Frerejacque number, <i>etc.</i>.
 */
public interface IRingDetector {
    /**
     * Sets up the detector. It is possible to re-use a detector on multiple
     * molecules.
     * 
     * @param mol
     *            The molecule to analyse for rings.
     */
    void initialise(Molecule mol);

    /**
     * The work-horse method that detects all the rings in the molecule.
     */
    void detectRings();

    /**
     * @return A copy of the rings detected (possibly after pruning of spurious
     *         rings).
     */
    List<Ring> rings();

    /**
     * @return A copy of the ring systems formed by the detected rings, after
     *         appropriate pruning of spurious rings.
     */
    List<RingSystem> ringSystems();

    /**
     * @return A copy of the bridge head atoms, if any, in the ring systems
     *         detected.
     */
    List<Atom> bridgeHeads();
}
