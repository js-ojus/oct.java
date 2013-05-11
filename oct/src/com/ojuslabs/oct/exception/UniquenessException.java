/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.exception;

public class UniquenessException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = -731186601453524529L;

    /**
     * @param msg
     *            The text describing what how uniqueness got violated.
     */
    public UniquenessException(String msg) {
        super(msg);
    }
}
