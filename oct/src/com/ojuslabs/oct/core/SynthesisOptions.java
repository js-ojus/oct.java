/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.core;

/**
 * SynthesisOptions holds the user's preferences for a run of retrosynthetic
 * route exploration. All options that can be specified externally, are
 * represented here.
 */
public class SynthesisOptions
{
    /** Default maximum number of routes to generate. */
    public static final int DEFAULT_MAX_ROUTES          = 20;
    /** Default maximum number of steps allowed per route. */
    public static final int DEFAULT_MAX_STEPS           = 20;

    /**
     * Maximum number of routes to generate. Defaults to
     * {@link #DEFAULT_MAX_ROUTES}, value {@value #DEFAULT_MAX_ROUTES}.
     */
    public int              maxRoutes                   = DEFAULT_MAX_ROUTES;
    /**
     * Maximum number of steps to allow per route. Defaults to
     * {@link #DEFAULT_MAX_STEPS}, value {@value #DEFAULT_MAX_STEPS}.
     */
    public int              maxStepsPerRoute            = DEFAULT_MAX_STEPS;
    /**
     * Maximum number of atoms allowed per reactant. Defaults to {@code 0},
     * meaning no limit.
     */
    public int              maxAtoms                    = 0;
    /**
     * Maximum number of carbon atoms allowed per reactant. Defaults to
     * {@code 0}, meaning no limit.
     */
    public int              maxCarbonAtoms              = 0;
    /**
     * Maximum number of hetero atoms allowed per reactant. Defaults to
     * {@code 0}, meaning no limit.
     */
    public int              maxHeteroAtoms              = 0;
    /**
     * Maximum number of chiral centres allowed per reactant. Defaults to
     * {@code 0}, meaning no limit.
     */
    public int              maxChiralCentres            = 0;
    /**
     * Maximum number of functional groups allowed per reactant. Defaults to
     * {@code 0}, meaning no limit.
     */
    public int              maxFunctionalGroups         = 0;
    /**
     * Maximum number of rings allowed per reactant. Defaults to {@code 0},
     * meaning no limit.
     */
    public int              maxRings                    = 0;
    /**
     * Maximum number of non-aromatic rings of size 6 allowed per reactant.
     * Defaults to {@code 0}, meaning no limit.
     */
    public int              maxNonAromaticRingsOfSize6  = 0;

    /**
     * Is it acceptable to generate routes differing only in halides? Defaults
     * to {@code false}.
     */
    public boolean          areHalideVariationsOk       = false;
    /**
     * Is it acceptable to generate routes differing only in sulfonates?
     * Defaults to {@code false}.
     */
    public boolean          areSulfonateVariationsOk    = false;
    /**
     * Is it acceptable to generate routes differing only in stereoisomers?
     * Defaults to {@code false}.
     */
    public boolean          areStereoIsomerVariationsOk = false;
    /** Should we try convergent syntheses? Defaults to {@code true}. */
    public boolean          tryConvergentSyntheses      = true;

    /**
     * Should we perform a selective search based on key bonds specified by the
     * user? Defaults to {@code false}.
     */
    public boolean          doSelectiveSearch           = false;

    /**
     * All of this class' data members are public. Consequently, the constructor
     * does not do anything specific.
     */
    public SynthesisOptions() {
        /* Intentionally left blank. */
    }
}
