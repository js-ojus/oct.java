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
                "oct/test/com/ojuslabs/oct/xlate/mdl/citalopram.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        List<Ring> rings = rd.rings();
        System.out.println(String.format("-- Citalopram : %d rings.",
                rings.size()));
        for (Ring r : rings) {
            System.out.println(r);
        }
        System.out.println();
        assertEquals(3, rings.size());
    }

    @Test
    public void test002() {
        SdfFile sdf = new SdfFile(
                "oct/test/com/ojuslabs/oct/xlate/mdl/cubane.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        List<Ring> rings = rd.rings();
        System.out.println(String.format("-- Cubane : %d rings.",
                rings.size()));
        for (Ring r : rings) {
            System.out.println(r);
        }
        System.out.println();
        assertEquals(6, rings.size());
    }

    @Test
    public void test003() {
        SdfFile sdf = new SdfFile(
                "oct/test/com/ojuslabs/oct/xlate/mdl/adamantane.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        List<Ring> rings = rd.rings();
        System.out.println(String.format("-- Adamantane : %d rings.",
                rings.size()));
        for (Ring r : rings) {
            System.out.println(r);
        }
        System.out.println();
        assertEquals(4, rings.size());
    }

    @Test
    public void test004() {
        SdfFile sdf = new SdfFile(
                "oct/test/com/ojuslabs/oct/xlate/mdl/dual-adamantane.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        List<Ring> rings = rd.rings();
        System.out.println(String.format("-- Double adamantane : %d rings.",
                rings.size()));
        for (Ring r : rings) {
            System.out.println(r);
        }
        System.out.println();
        assertEquals(6, rings.size());
    }

    @Test
    public void test005() {
        SdfFile sdf = new SdfFile(
                "oct/test/com/ojuslabs/oct/xlate/mdl/norbornane.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        List<Ring> rings = rd.rings();
        System.out.println(String.format("-- Norbornane : %d rings.",
                rings.size()));
        for (Ring r : rings) {
            System.out.println(r);
        }
        System.out.println();
        assertEquals(3, rings.size());
    }
}
