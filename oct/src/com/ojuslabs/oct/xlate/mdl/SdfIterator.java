package com.ojuslabs.oct.xlate.mdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.ojuslabs.oct.common.Constants;

public class SdfIterator implements Iterator<String>
{
    static final String _MOL_DELIM = "$$$$";

    final File          _file;
    BufferedReader      _reader;
    int                 _count;
    String              _text;

    /**
     * @param istream
     *            An input stream from the underlying SdfFile.
     * @throws FileNotFoundException
     */
    SdfIterator(File file) throws FileNotFoundException {
        _file = file;
        _reader = new BufferedReader(new FileReader(_file),
                Constants.IO_BUFFER_SIZE);
    }

    /**
     * Answers true if one more molecule's text is available; false otherwise.
     * 
     * Note that this method is not referentially transparent: there is file I/O
     * involved. However, it tries to be referentially transparent for most
     * practical purposes.
     * 
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        if (null != _text) return true;

        StringBuilder builder = new StringBuilder(Constants.STRING_BUFFER_SIZE);
        do {
            try {
                String s = _reader.readLine();
                if (null == s) { // We are expecting more data, but have reached
                                 // the end of the stream.
                    _reader.close();
                    return false;
                }

                builder.append(s);
                if (s.startsWith(_MOL_DELIM)) {
                    _text = builder.toString();
                }
            }
            catch (IOException e) {
                try {
                    _reader.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
                return false;
            }
        } while (null == _text);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#next()
     */
    @Override
    public String next() {
        if ((null == _text) && !hasNext()) {
            throw new NoSuchElementException();
        }

        String res = _text;
        _text = null;
        return res;
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
