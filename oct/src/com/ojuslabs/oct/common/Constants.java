/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.common;

/**
 * Constants holds some program-wide configuration constants.
 */
public final class Constants
{
    /** Default size for buffered streams. */
    public static final int IO_BUFFER_SIZE     = 64 * 1024;
    /** Default size for string builders. */
    public static final int STRING_BUFFER_SIZE = 2560;

    /** Initial size of tiny lists such as reactant and product lists. */
    public static final int LIST_SIZE_T        = 2;
    /** Initial size of small lists such as neighbour lists. */
    public static final int LIST_SIZE_S        = 6;
    /** Initial size of mid-sized lists such as functional group lists. */
    public static final int LIST_SIZE_M        = 16;
    /** Initial size of large lists such as atom and bond lists. */
    public static final int LIST_SIZE_L        = 64;

    /** The maximum number of rings allowed in <i>normal</i> molecules. */
    public static final int MAX_RINGS          = 15;

    private Constants() {
        /* Intentionally left blank. */
    }
}
