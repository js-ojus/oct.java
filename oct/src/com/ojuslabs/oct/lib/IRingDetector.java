/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.lib;

import java.util.List;

import com.ojuslabs.oct.core.Molecule;
import com.ojuslabs.oct.core.Ring;

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
     * @return Returns a copy of the rings detected (possibly after pruning).
     */
    List<Ring> rings();
}
