package com.ojuslabs.oct.exception;

public final class ImmutabilityException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 8610882639444385501L;

    /**
     * @param msg
     *            Text describing where and which violation occurred.
     */
    public ImmutabilityException(String msg) {
        super(msg);
    }
}
