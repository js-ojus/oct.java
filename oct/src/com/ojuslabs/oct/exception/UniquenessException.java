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
