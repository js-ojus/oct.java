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

import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.Constants;
import com.ojuslabs.oct.core.Molecule;
import com.ojuslabs.oct.core.Ring;
import com.ojuslabs.oct.core.RingSystem;
import com.ojuslabs.oct.xlate.mdl.MolReader;
import com.ojuslabs.oct.xlate.mdl.MolReaderV2k;
import com.ojuslabs.oct.xlate.mdl.SdfFile;
import com.ojuslabs.oct.xlate.mdl.SdfIterator;

/**
 * A set of tests for detection of rings in a molecule.
 */
public class RingDetectorTest {

    // static Molecule _mol;

    static class Data {
        public final String fileName;
        public final int    numRingSystems;
        public final int    numAroRingSystems;
        public final int    numRings;
        public final int    numAroRings;

        public Data(int nrss, int narss, int nrs, int nars, String fn) {
            fileName = fn;
            numRingSystems = nrss;
            numAroRingSystems = narss;
            numRings = nrs;
            numAroRings = nars;
        }
    }

    static List<Data> _data;

    static {
        _data = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_L);

        _data.add(new Data(1, 0, 4, 0,
                "oct/test/com/ojuslabs/oct/xlate/mdl/adamantane.sdf"));
        _data.add(new Data(2, 1, 3, 2,
                "oct/test/com/ojuslabs/oct/xlate/mdl/citalopram.sdf"));
        _data.add(new Data(1, 0, 6, 0,
                "oct/test/com/ojuslabs/oct/xlate/mdl/cubane.sdf"));
        _data.add(new Data(1, 0, 7, 0,
                "oct/test/com/ojuslabs/oct/xlate/mdl/cyclohexane-6-disjoint-quadranes.sdf"));
        _data.add(new Data(1, 0, 6, 0,
                "oct/test/com/ojuslabs/oct/xlate/mdl/dual-adamantane.sdf"));
        _data.add(new Data(1, 0, 17, 0,
                "oct/test/com/ojuslabs/oct/xlate/mdl/eight-spiro-hexanes.sdf"));
        _data.add(new Data(1, 0, 3, 0,
                "oct/test/com/ojuslabs/oct/xlate/mdl/norbornane.sdf"));
        _data.add(new Data(1, 0, 9, 0,
                "oct/test/com/ojuslabs/oct/xlate/mdl/three-fused-norbornanes-2.sdf"));
        _data.add(new Data(1, 0, 9, 0,
                "oct/test/com/ojuslabs/oct/xlate/mdl/three-fused-norbornanes.sdf"));
        _data.add(new Data(1, 0, 8, 0,
                "oct/test/com/ojuslabs/oct/xlate/mdl/two-cyclohexanes-cylinder.sdf"));

        _data.add(new Data(1, 0, 1, 0,
                "oct/test/com/ojuslabs/oct/lib/1,3-cyclopentadiene_false.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/1,3-cyclopentadienide_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/14-annulene_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/18-annulene_true.sdf"));
        _data.add(new Data(1, 1, 3, 0,
                "oct/test/com/ojuslabs/oct/lib/anthracene_true.sdf"));
        _data.add(new Data(1, 1, 2, 0,
                "oct/test/com/ojuslabs/oct/lib/azulene_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/benzene_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/furan_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/imidazole_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/isothiazole_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/isoxazole_true.sdf"));
        _data.add(new Data(1, 1, 2, 0,
                "oct/test/com/ojuslabs/oct/lib/napthalene_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/oxazole_true.sdf"));
        _data.add(new Data(1, 0, 3, 2,
                "oct/test/com/ojuslabs/oct/lib/phenalene_false.sdf"));
        _data.add(new Data(1, 1, 3, 0,
                "oct/test/com/ojuslabs/oct/lib/phenalenide_true.sdf"));
        _data.add(new Data(1, 1, 3, 0,
                "oct/test/com/ojuslabs/oct/lib/phenathrene_true.sdf"));
        _data.add(new Data(1, 0, 1, 0,
                "oct/test/com/ojuslabs/oct/lib/pyran_false.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/pyrazole_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/pyridine_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/pyrylium_true.sdf"));
        _data.add(new Data(1, 0, 1, 0,
                "oct/test/com/ojuslabs/oct/lib/test_false.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/thiazole_true.sdf"));
        _data.add(new Data(1, 1, 1, 1,
                "oct/test/com/ojuslabs/oct/lib/thiophene_true.sdf"));
        _data.add(new Data(1, 1, 6, 0,
                "oct/test/com/ojuslabs/oct/lib/triptycene_true.sdf"));
    }

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
        // _mol = null;
    }

    @Test
    public void test001() {
        int c = 0;
        for (Data d : _data) {
            // if (!d.fileName
            // .equals("oct/test/com/ojuslabs/oct/lib/pyrylium_true.sdf")) {
            // continue;
            // }

            SdfFile sdf = new SdfFile(d.fileName);
            SdfIterator it = sdf.iterator();
            it.hasNext();

            List<String> l = it.next();
            MolReader reader = new MolReaderV2k();
            Molecule _mol = reader.parse(l, false, false, false);
            assertNotNull(_mol);

            IRingDetector rd = RingDetectors.newInstance(RingDetectors.DEFAULT);
            _mol.normalise(rd);

            List<RingSystem> rss = rd.ringSystems();
            List<Ring> rings = rd.rings();

            // System.out.println(String.format("-- %s : %d rings.",
            // d.fileName.substring(d.fileName.lastIndexOf("/") + 1),
            // rings.size()));

            int nrs = 0;
            int nr = 0;
            for (RingSystem rs : rss) {
                // System.out.println(rs);
                if (rs.isAromatic()) {
                    nrs++;
                }
            }
            for (Ring r : rings) {
                // System.out.println(r);
                if (r.isAromatic()) {
                    nr++;
                }
            }
            System.out.println("-- " + d.fileName);

            try {
                assertEquals("Number of ring systems:", d.numRingSystems,
                        rss.size());
                assertEquals("Number of aromatic ring systems:",
                        d.numAroRingSystems, nrs);
                assertEquals("Number of rings:", d.numRings, rings.size());
                assertEquals("Number of aromatic rings:", d.numAroRings, nr);
            }
            catch (AssertionError e) {
                c++;
                System.out.println("   !! " + e.getMessage());
            }
        }

        assertEquals(0, c);
    }
}
