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
            if (r.isAromatic()) {
                System.out.println("-- Ring is aromatic.");
            }
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
            if (r.isAromatic()) {
                System.out.println("-- Ring is aromatic.");
            }
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
            if (r.isAromatic()) {
                System.out.println("-- Ring is aromatic.");
            }
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
            if (r.isAromatic()) {
                System.out.println("-- Ring is aromatic.");
            }
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
            if (r.isAromatic()) {
                System.out.println("-- Ring is aromatic.");
            }
        }
        System.out.println();
        assertEquals(3, rings.size());
    }

    @Test
    public void test006() {
        SdfFile sdf = new SdfFile(
                "oct/test/com/ojuslabs/oct/xlate/mdl/two-cyclohexanes-cylinder.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        List<Ring> rings = rd.rings();
        System.out.println(String.format(
                "-- Cylindrical cyclohexanes : %d rings.",
                rings.size()));
        for (Ring r : rings) {
            System.out.println(r);
            if (r.isAromatic()) {
                System.out.println("-- Ring is aromatic.");
            }
        }
        System.out.println();
        assertEquals(8, rings.size());
    }

    @Test
    public void test007() {
        SdfFile sdf = new SdfFile(
                "oct/test/com/ojuslabs/oct/xlate/mdl/three-fused-norbornanes.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        List<Ring> rings = rd.rings();
        System.out.println(String.format(
                "-- Fused norbornanes : %d rings.",
                rings.size()));
        for (Ring r : rings) {
            System.out.println(r);
            if (r.isAromatic()) {
                System.out.println("-- Ring is aromatic.");
            }
        }
        System.out.println();
        assertEquals(9, rings.size());
    }

    @Test
    public void test008() {
        SdfFile sdf = new SdfFile(
                "oct/test/com/ojuslabs/oct/xlate/mdl/three-fused-norbornanes-2.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        List<Ring> rings = rd.rings();
        System.out.println(String.format(
                "-- Fused norbornanes - 2 : %d rings.",
                rings.size()));
        for (Ring r : rings) {
            System.out.println(r);
            if (r.isAromatic()) {
                System.out.println("-- Ring is aromatic.");
            }
        }
        System.out.println();
        assertEquals(9, rings.size());
    }

    @Test
    public void test009() {
        SdfFile sdf = new SdfFile(
                "oct/test/com/ojuslabs/oct/xlate/mdl/cyclohexane-6-disjoint-quadranes.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        List<Ring> rings = rd.rings();
        System.out.println(String.format(
                "-- Cyclohexane with 6 disjoint quadranes : %d rings.",
                rings.size()));
        for (Ring r : rings) {
            System.out.println(r);
            if (r.isAromatic()) {
                System.out.println("-- Ring is aromatic.");
            }
        }
        System.out.println();
        assertEquals(7, rings.size());
    }

    @Test
    public void test010() {
        SdfFile sdf = new SdfFile(
                "oct/test/com/ojuslabs/oct/xlate/mdl/eight-spiro-hexanes.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        MolReader reader = new MolReaderV2k();
        _mol = reader.parse(l, false, false, false);
        assertNotNull(_mol);

        IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
        _mol.normalise(rd);

        List<Ring> rings = rd.rings();
        System.out.println(String.format(
                "-- Eight spiro hexanes : %d rings.",
                rings.size()));
        for (Ring r : rings) {
            System.out.println(r);
            if (r.isAromatic()) {
                System.out.println("-- Ring is aromatic.");
            }
        }
        System.out.println();
        assertEquals(17, rings.size());
    }
}
