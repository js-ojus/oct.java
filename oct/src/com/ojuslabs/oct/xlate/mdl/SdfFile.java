package com.ojuslabs.oct.xlate.mdl;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * SdfFile represents an MDL-format SDF file with molecule data. It can be used
 * to both read from and write to SDF files.
 */
public class SdfFile implements Iterable<String>
{
    final String  _path;
    final boolean _outMode;
    File          _file;

    /**
     * @param path
     *            Fully-qualified path to the SDF file.
     * @param write
     *            If true, opens the file in write-mode; else, in read-mode.
     */
    public SdfFile(String path, boolean write) {
        _path = path;
        _outMode = write;
        _file = new File(_path);
    }

    /**
     * @param path
     *            Fully-qualified path to the SDF file.
     */
    public SdfFile(String path) {
        this(path, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    public SdfIterator iterator() {
        try {
            return new SdfIterator(_file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
