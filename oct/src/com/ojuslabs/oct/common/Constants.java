package com.ojuslabs.oct.common;

public class Constants
{
    /** Default size for buffered streams. */
    public static final int IO_BUFFER_SIZE = 64 * 1024;
    /** Default size for string builders. */
    public static final int STRING_BUFFER_SIZE = 2560;

    /** Initial size of small lists such as neighbour lists. */
    public static final int LIST_SIZE_S    = 6;
    /** Initial size of mid-sized lists such as functional group lists. */
    public static final int LIST_SIZE_M    = 16;
    /** Initial size of large lists such as atom and bond lists. */
    public static final int LIST_SIZE_L    = 64;
}
