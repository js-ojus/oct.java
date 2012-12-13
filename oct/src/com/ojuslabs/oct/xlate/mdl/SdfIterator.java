package com.ojuslabs.oct.xlate.mdl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.Constants;

public class SdfIterator implements Iterator<List<String>>
{
    static final String          MOL_DELIM = "$$$$";

    private final BufferedReader _reader;
    private final List<String>   _text;
    private boolean              _haveMolInHand;
    private int                  _count;

    /**
     * @param istream
     *            An input stream from the underlying SdfFile.
     */
    SdfIterator(Reader reader) {
        _reader = new BufferedReader(reader, Constants.IO_BUFFER_SIZE);
        _text = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_L);
    }

    /**
     * Answers true if one more molecule's text is available; false otherwise.
     * <p>
     * <b>N.B.</b> Note that this method is not referentially transparent: there
     * is file I/O involved. However, it tries to be referentially transparent
     * for most practical purposes.
     * 
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        if (_haveMolInHand) {
            return true;
        }

        do {
            try {
                String s = _reader.readLine();
                if (null == s) { // We are expecting more data, but have reached
                                 // the end of the stream.
                    throw new IOException();
                }

                _text.add(s);
                if (s.startsWith(MOL_DELIM)) {
                    _haveMolInHand = true;
                }
            }
            catch (IOException e1) {
                try {
                    _reader.close();
                }
                catch (IOException e2) {
                    e1.printStackTrace();
                    e2.printStackTrace();
                }
                return false;
            }
        } while (!_haveMolInHand);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#next()
     */
    @Override
    public List<String> next() {
        if (!(_haveMolInHand || hasNext())) {
            throw new NoSuchElementException();
        }

        List<String> res = ImmutableList.copyOf(_text);
        _text.clear();
        _haveMolInHand = false;
        _count++;
        return res;
    }

    /**
     * Skips the requested number of molecules in the stream. It is advisable to
     * compare the answered actual number of molecules skipped to that
     * requested.
     * 
     * @param n
     *            The number of molecules to skip making available to
     *            {@link SdfIterator#next()}.
     * @return The actual number of molecules successfully skipped.
     */
    public int skip(int n) {
        int i = 0;

        while (i < n) {
            try {
                String s = _reader.readLine();
                if (null == s) { // We are expecting more data, but have reached
                                 // the end of the stream.
                    throw new IOException();
                }

                if (s.startsWith(MOL_DELIM)) {
                    _count++;
                    i++;
                }
            }
            catch (IOException e1) {
                try {
                    _reader.close();
                }
                catch (IOException e2) {
                    e1.printStackTrace();
                    e2.printStackTrace();
                }
            }
        }

        return i;
    }

    /**
     * Answers the number of the next available molecule. <b>N.B.</b> This
     * number is affected by all of {@link SdfIterator#next()},
     * {@link SdfIterator#skip(int)} and {@link SdfIterator#copyTo(Writer, int)}
     * , each advancing the iterator.
     * 
     * @return The sequence number of the next molecule in the stream.
     */
    public int numberOfNextMolecule() {
        return _count + 1;
    }

    /**
     * Bulk copies the requested number of molecules to the given writer
     * instance. The writer is <i>not</i> closed by this method. It is advisable
     * to compare the answered actual number of molecules copied to that
     * requested.
     * 
     * @param w
     *            The writer to which the molecules are written.
     * @param n
     *            The number of molecules to copy.
     * @return The actual number of molecules copied.
     */
    public int copyTo(Writer w, int n) {
        BufferedWriter writer = new BufferedWriter(w, Constants.IO_BUFFER_SIZE);

        int i = 0;

        while (i < n) {
            try {
                String s = _reader.readLine();
                if (null == s) { // We are expecting more data, but have reached
                                 // the end of the stream.
                    throw new IOException();
                }

                writer.write(s);
                if (s.startsWith(MOL_DELIM)) {
                    _count++;
                    i++;
                }
            }
            catch (IOException e1) {
                try {
                    _reader.close();
                }
                catch (IOException e2) {
                    e1.printStackTrace();
                    e2.printStackTrace();
                }
            }
        }

        return i;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException(
                "SdfIterator is a read-only facility, and does not handle a `remove' request.");
    }
}
