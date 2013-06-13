/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.core;

import com.google.common.collect.ImmutableMap;

/**
 * This collection class holds the reference list of functional groups
 * understood by OCT.
 */
public final class FunctionalGroups {
    // Singleton instance.
    private static FunctionalGroups                _instance;

    // The map from functional group numbers to corresponding instances.
    private ImmutableMap<Integer, FunctionalGroup> _groups;

    /**
     * A single instance of {@code FunctionalGroups} is ever present in the
     * program. This method ensures that, and always answers the same singleton
     * instance.
     * 
     * @return The singleton instance of {@code FunctionalGroups}.
     */
    public static FunctionalGroups instance() {
        if (null != _instance) {
            return _instance;
        }

        _instance = new FunctionalGroups();
        _instance.init();
        return _instance;
    }

    FunctionalGroups() {
        // Intentionally left blank.
    }

    /**
     * Builds the list of functional groups in the singleton instance.
     */
    void init() {
        ImmutableMap.Builder<Integer, FunctionalGroup> builder = ImmutableMap
                .builder();

        _groups = builder.build();
    }

    /**
     * @param id
     *            The ID of the desired functional group.
     * @return The functional group corresponding to the given ID, if one such
     *         exists; {@code null} otherwise.
     */
    public FunctionalGroup group(int id) {
        return _groups.get(id);
    }
}
