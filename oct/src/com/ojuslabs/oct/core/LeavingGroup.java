/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.core;

import com.ojuslabs.oct.util.AbstractGroup;

/**
 * LeavingGroup represents a chemical group that is liable to get detached from
 * its molecule during a reaction.
 */
public final class LeavingGroup extends AbstractGroup {
    LeavingGroup(int id, String s) {
        super(id, s);
    }
}
