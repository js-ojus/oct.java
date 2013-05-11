/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.xlate.mdl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ojuslabs.oct.core.Molecule;

public class MolReaderV2kTest
{
    static MolReaderV2k _reader;

    @BeforeClass
    public static void setUpClass() throws Exception {
        _reader = new MolReaderV2k();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        _reader = null;
    }

    @Test
    public void testCtabHook() {
        _reader.registerCtabHook(new MolReaderHook() {
            @Override
            public void apply(List<String> l, Molecule m) {
                // Intentionally left blank.
            }
        });
        assertNotNull(_reader.ctabHook());

        _reader.unregisterCtabHook();
        assertNull(_reader.ctabHook());
    }

    @Test
    public void testPropertiesHook() {
        _reader.registerPropertiesHook(new MolReaderHook() {
            @Override
            public void apply(List<String> l, Molecule m) {
                // Intentionally left blank.
            }
        });
        assertNotNull(_reader.propertiesHook());

        _reader.unregisterPropertiesHook();
        assertNull(_reader.propertiesHook());
    }

    @Test
    public void testTagsHook() {
        _reader.registerTagsHook(new MolReaderHook() {
            @Override
            public void apply(List<String> l, Molecule m) {
                // Intentionally left blank.
            }
        });
        assertNotNull(_reader.tagsHook());

        _reader.unregisterTagsHook();
        assertNull(_reader.tagsHook());
    }

    @Test
    public void testParse() {
        SdfFile sdf = new SdfFile(
                "test/com/ojuslabs/oct/xlate/mdl/citalopram.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        Molecule m = _reader.parse(l, false, false, false);
        assertNotNull(m);

        assertEquals(24, m.numberOfAtoms());
        assertEquals(26, m.numberOfBonds());
        assertEquals(6, m.numberOfDoubleBonds());
        assertEquals(1, m.numberOfTripleBonds());

        m = null;
        it = null;
        sdf = null;
    }
}
