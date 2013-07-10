/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ojuslabs.oct.core.Molecule;
import com.ojuslabs.oct.core.Ring;
import com.ojuslabs.oct.xlate.mdl.MolReader;
import com.ojuslabs.oct.xlate.mdl.MolReaderV2k;
import com.ojuslabs.oct.xlate.mdl.SdfFile;
import com.ojuslabs.oct.xlate.mdl.SdfIterator;

/**
 * A set of tests for detection of rings in a molecule.
 */
public class RingDetectorTest {

    static Molecule _mol;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        _mol = null;
    }

    @Test
    public void test001() {
        SdfFile sdf = new SdfFile(
                "test/com/ojuslabs/oct/xlate/mdl/citalopram.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        for (Ring r : _mol.rings()) {
            System.out.println(r);
        }
    }

    @Test
    public void test002() {
        SdfFile sdf = new SdfFile(
                "test/com/ojuslabs/oct/xlate/mdl/cubane.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        for (Ring r : _mol.rings()) {
            System.out.println(r);
        }
    }

    @Test
    public void test003() {
        SdfFile sdf = new SdfFile(
                "test/com/ojuslabs/oct/xlate/mdl/adamantane.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        assertEquals(_mol.numberOfAtoms(), 10);
        assertEquals(_mol.numberOfBonds(), 12);
        assertEquals(_mol.frerejacque(), 3);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        for (Ring r : _mol.rings()) {
            System.out.println(r);
        }
    }

    @Test
    public void test004() {
        SdfFile sdf = new SdfFile(
                "test/com/ojuslabs/oct/xlate/mdl/dual-adamantane.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        assertEquals(_mol.numberOfAtoms(), 14);
        assertEquals(_mol.numberOfBonds(), 18);
        assertEquals(_mol.frerejacque(), 5);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        for (Ring r : _mol.rings()) {
            System.out.println(r);
        }
    }
}
