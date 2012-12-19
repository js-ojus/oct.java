package com.ojuslabs.oct.xlate.mdl;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ojuslabs.oct.data.Molecule;

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
        assert (null != _reader.ctabHook());

        _reader.unregisterCtabHook();
        assert (null == _reader.ctabHook());
    }

    @Test
    public void testPropertiesHook() {
        _reader.registerPropertiesHook(new MolReaderHook() {
            @Override
            public void apply(List<String> l, Molecule m) {
                // Intentionally left blank.
            }
        });
        assert (null != _reader.propertiesHook());

        _reader.unregisterPropertiesHook();
        assert (null == _reader.propertiesHook());
    }

    @Test
    public void testTagsHook() {
        _reader.registerTagsHook(new MolReaderHook() {
            @Override
            public void apply(List<String> l, Molecule m) {
                // Intentionally left blank.
            }
        });
        assert (null != _reader.tagsHook());

        _reader.unregisterTagsHook();
        assert (null == _reader.tagsHook());
    }

    @Test
    public void testParse() {
        SdfFile sdf = new SdfFile(
                "test/com/ojuslabs/oct/xlate/mdl/citalopram.sdf");
        SdfIterator it = sdf.iterator();
        it.hasNext();

        List<String> l = it.next();
        Molecule m = _reader.parse(l, false, false, false);
        assert (null != m);

        assert (24 == m.numberOfAtoms());
        assert (26 == m.numberOfBonds());
        assert (6 == m.numberOfDoubleBonds());
        assert (1 == m.numberOfTripleBonds());

        m = null;
        it = null;
        sdf = null;
    }
}
