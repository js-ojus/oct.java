/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.lib;

import java.util.List;

import com.ojuslabs.oct.core.Ring;

/**
 * A ring pruner instance applies a single test on the given ring to determine
 * if it should be pruned or retained.
 */
public interface IRingPruner {
    /**
     * Determines if the given ring should be pruned or retained, by subjecting
     * it to a unique test.
     * 
     * @param rs
     *            The ring system to which the test ring belongs.
     * @param r
     *            The test ring to be checked.
     * @param lastIncludedRingIdx
     *            The index of the last ring up to which the sorted rings are
     *            included in the basis for the given ring system.
     * @return {@code true} if the ring should be pruned; {@code false}
     *         otherwise.
     */
    boolean shouldPrune(List<Ring> rs, Ring r, int lastIncludedRingIdx);
}
