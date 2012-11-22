package com.ojuslabs.oct.exception;

public final class NotFoundException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 1249886354064764133L;

    /**
     * @param msg
     *            The text describing what is not found.
     */
    public NotFoundException(String msg) {
        super(msg);
    }
}
