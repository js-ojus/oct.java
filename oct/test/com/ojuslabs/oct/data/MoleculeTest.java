/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ojuslabs.oct.data.Molecule;
import com.ojuslabs.oct.xlate.mdl.MolReader;
import com.ojuslabs.oct.xlate.mdl.MolReaderV2k;
import com.ojuslabs.oct.xlate.mdl.SdfFile;
import com.ojuslabs.oct.xlate.mdl.SdfIterator;

/**
 * A set of tests for parsing an external molecule in MDL format, and performing
 * simple operations on it.
 */
public class MoleculeTest
{
    static Molecule _m;

    @Before
    public void setUp() throws Exception {
        SdfFile sdf = new SdfFile(
                "test/com/ojuslabs/oct/xlate/mdl/citalopram.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _m = reader.parse(l, false, false, false);
        assertNotNull(_m);
    }

    @After
    public void tearDown() throws Exception {
        _m = null;
    }

    @Test
    public void test001() {
        assertEquals(24, _m.numberOfAtoms());
        assertEquals(26, _m.numberOfBonds());
        assertEquals(6, _m.numberOfDoubleBonds());
        assertEquals(1, _m.numberOfTripleBonds());
    }

    @Test
    public void test002() {
        _m.removeAtom(_m.atomByInputId(23));

        assertEquals(23, _m.numberOfAtoms());
        assertEquals(0, _m.numberOfTripleBonds());
    }

    @Test
    public void test003() {
        _m.removeAtom(_m.atomByInputId(2));

        assertEquals(23, _m.numberOfAtoms());
        assertEquals(24, _m.numberOfBonds());
        assertEquals(5, _m.numberOfDoubleBonds());
    }
}
