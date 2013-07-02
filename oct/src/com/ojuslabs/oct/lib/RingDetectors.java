/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.lib;

/**
 * A factory class for creating ring detectors.
 */
public final class RingDetectors {
    /** The default ring detector. */
    public static final int DEFAULT = 1;

    private RingDetectors() {
        // Intentionally left blank.
    }

    /**
     * This factory method answers a new instance of the particular flavour of
     * ring detectors requested.
     * 
     * @param type
     *            The type of detector desired. Currently available:
     *            {@code DEFAULT}.
     * @return The desired type of detector if available; {@code null}
     *         otherwise.
     */
    public static IRingDetector newInstance(int type) {
        switch (type) {
        case DEFAULT:
            return new RingDetector();
        }

        return null;
    }
}
