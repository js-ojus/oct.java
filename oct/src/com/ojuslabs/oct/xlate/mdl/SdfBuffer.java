/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.xlate.mdl;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

/**
 * SdfBuffer represents an MDL-format in-memory buffer with molecule data. This
 * is intended only for convenient reading through its iterator. It can
 * <i>not</i> be written to.
 */
public class SdfBuffer implements Iterable<List<String>>
{
    private final String _buffer;

    /**
     * @param buf
     *            The string which shall act as the underlying molecule data.
     */
    public SdfBuffer(String buf) {
        _buffer = buf;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<List<String>> iterator() {
        return new SdfIterator(new StringReader(_buffer));
    }
}
