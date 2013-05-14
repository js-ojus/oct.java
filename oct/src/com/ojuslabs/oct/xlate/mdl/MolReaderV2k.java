/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.xlate.mdl;

import java.util.List;

import com.google.common.base.Joiner;
import com.ojuslabs.oct.common.BondOrder;
import com.ojuslabs.oct.common.BondStereo;
import com.ojuslabs.oct.common.Element;
import com.ojuslabs.oct.common.PeriodicTable;
import com.ojuslabs.oct.common.Radical;
import com.ojuslabs.oct.core.Atom;
import com.ojuslabs.oct.core.Bond;
import com.ojuslabs.oct.core.Molecule;
import com.ojuslabs.oct.util.Point3D;

public class MolReaderV2k implements MolReader
{
    static final String   _M_END = "M  END";
    static final String   _M_CHG = "M  CHG";
    static final String   _M_ISO = "M  ISO";
    static final String   _M_RAD = "M  RAD";

    private boolean       _skipCtab;
    private boolean       _skipProps;
    private boolean       _skipTags;

    // Line number where the current section began.
    private int           _sectionStart;
    // Line number of the current line.
    private int           _currentLine;

    private MolReaderHook _ctabHook;
    private MolReaderHook _propsHook;
    private MolReaderHook _tagsHook;

    public MolReaderV2k() {
        // Intentionally left blank.
    }

    /**
     * Registers a hook for post-processing CTAB data of the molecule.
     * 
     * @param h
     *            Post-processing hook for CTAB data.
     */
    public void registerCtabHook(MolReaderHook h) {
        if (null != h) {
            _ctabHook = h;
        }
    }

    /**
     * @return Current hook for post-processing CTAB data.
     */
    public MolReaderHook ctabHook() {
        return _ctabHook;
    }

    /**
     * Unregisters the current hook, if any, for post-processing CTAB data.
     */
    public void unregisterCtabHook() {
        _ctabHook = null;
    }

    /**
     * Registers a hook for post-processing property data of the molecule.
     * 
     * @param h
     *            Post-processing hook for property data.
     */
    public void registerPropertiesHook(MolReaderHook h) {
        if (null != h) {
            _propsHook = h;
        }
    }

    /**
     * @return Current hook for post-processing property data.
     */
    public MolReaderHook propertiesHook() {
        return _propsHook;
    }

    /**
     * Unregisters the current hook, if any, for post-processing property data.
     */
    public void unregisterPropertiesHook() {
        _propsHook = null;
    }

    /**
     * Registers a hook for post-processing tag data of the molecule.
     * 
     * @param h
     *            Post-processing hook for tag data.
     */
    public void registerTagsHook(MolReaderHook h) {
        if (null != h) {
            _tagsHook = h;
        }
    }

    /**
     * @return Current hook for post-processing tag data.
     */
    public MolReaderHook tagsHook() {
        return _tagsHook;
    }

    /**
     * Unregisters the current hook, if any, for post-processing tag data.
     */
    public void unregisterTagsHook() {
        _tagsHook = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ojuslabs.oct.xlate.mdl.MolReader#parse(java.util.List, boolean,
     * boolean, boolean)
     */
    @Override
    public Molecule parse(List<String> l, boolean skipCtab, boolean skipProps,
            boolean skipTags) {
        _skipCtab = skipCtab;
        _skipProps = skipProps;
        _skipTags = skipTags;

        // If we have to parse properties, we must first parse the CTAB section.
        if (!_skipProps) {
            _skipCtab = false;
        }

        Molecule mol = Molecule.newInstance();
        _sectionStart = 0;
        _currentLine = 0;

        try {
            parseHeader(l, mol);

            parseCtab(l, mol);
            parseProps(l, mol);
            parseTags(l, mol);
        }
        catch (final Exception e) {
            e.printStackTrace();
            return null;
        }

        return mol;
    }

    /**
     * Checks to see if the given list of strings has at least 4 elements (for
     * it to be of the required size of a valid MOL header. It then stores the
     * name of the molecule, if one is present.
     * 
     * @param l
     *            List of lines of input molecule's text.
     * @param mol
     *            The molecule object being constructed.
     */
    void parseHeader(List<String> l, Molecule mol) {
        if (l.size() < 4) {
            throw new IllegalArgumentException(String.format(
                    "Invalid MOL header:\n%s", Joiner.on("").join(l)));
        }

        String s = l.get(_currentLine).trim();
        if (s.isEmpty()) {
            mol.vendorMoleculeId = s;
        }

        _currentLine = 3;
    }

    /**
     * Parses the header's counts line and, accordingly, the atoms and bonds
     * available.
     * 
     * @param l
     *            List of lines of input molecule's text.
     * @param mol
     *            The molecule object being constructed.
     */
    void parseCtab(List<String> l, Molecule mol) {
        _sectionStart = _currentLine;

        String s = l.get(_currentLine);
        if (!s.endsWith("V2000")) {
            throw new UnsupportedOperationException(
                    String.format("This reader can parse only molecules in V2000 format."));
        }

        int numAtoms = Integer.parseInt(s.substring(0, 3).trim());
        int numBonds = Integer.parseInt(s.substring(3, 6).trim());

        if (_skipCtab) {
            _currentLine += numAtoms + numBonds;
            return;
        }

        for (int i = 0; i < numAtoms; i++) {
            _currentLine++;
            parseAtom(l.get(_currentLine), mol);
        }
        for (int i = 0; i < numBonds; i++) {
            _currentLine++;
            parseBond(l.get(_currentLine), mol);
        }

        if (null != _ctabHook) {
            _ctabHook.apply(l.subList(_sectionStart, _currentLine), mol);
        }
    }

    /**
     * Parses the given atom's properties, sets them, and finally adds the
     * constructed atom to the given molecule.
     * 
     * @param s
     *            Line containing the atom specification.
     * @param mol
     *            The current molecule.
     */
    private void parseAtom(String s, Molecule mol) {
        // We need the element type to be able to create an atom.
        String sym = s.substring(31, 34).trim();
        String iso = s.substring(34, 36).trim();
        Element el = PeriodicTable.instance().element(sym);
        el = (0 == Integer.parseInt(iso)) ?
                el :
                PeriodicTable.instance().isotope(sym,
                        (int) Math.round(el.weight) + Integer.parseInt(iso));

        Atom a = new Atom(el);

        // Set some of the other properties.
        a.coordinates = new Point3D(Double.parseDouble(s.substring(0, 10)),
                Double.parseDouble(s.substring(10, 20)),
                Double.parseDouble(s.substring(20, 30)));

        int charge = Integer.parseInt(s.substring(36, 39).trim());
        switch (charge) {
        case 1:
            a.setCharge(3);
            break;
        case 2:
            a.setCharge(2);
            break;
        case 3:
            a.setCharge(1);
            break;
        case 4:
            a.setRadical(Radical.DOUBLET);
            break;
        case 5:
            a.setCharge(-1);
            break;
        case 6:
            a.setCharge(-2);
            break;
        case 7:
            a.setCharge(-3);
            break;
        default:
            a.setCharge(0);
        }

        int val = Integer.parseInt(s.substring(48, 51).trim());
        if (val > 0 && val < 15) {
            a.setValence(val);
        }

        mol.addAtom(a);
    }

    /**
     * Parses the given bond's properties, sets them, and adds the constructed
     * bond to the given molecule.
     * 
     * @param s
     *            Line containing the bond specification.
     * @param mol
     *            The current molecule.
     */
    void parseBond(String s, Molecule mol) {
        Atom a1 = mol.atom(Integer.parseInt(s.substring(0, 3).trim()));
        Atom a2 = mol.atom(Integer.parseInt(s.substring(3, 6).trim()));
        BondOrder bo = BondOrder.ofValue(Integer.parseInt(s.substring(6, 9)
                .trim()));

        Bond b = mol.addBond(a1, a2, bo);

        BondStereo bs = BondStereo.NONE;
        int ibs = Integer.parseInt(s.substring(9, 12).trim());
        if (BondOrder.SINGLE == bo) {
            switch (ibs) {
            case 1:
                bs = BondStereo.UP;
                break;
            case 4:
                bs = BondStereo.UP_OR_DOWN;
                break;
            case 6:
                bs = BondStereo.DOWN;
                break;
            }
        }
        else if (BondOrder.DOUBLE == bo) {
            switch (ibs) {
            case 0:
                bs = BondStereo.UNSPECIFIED;
                break;
            case 3:
                bs = BondStereo.UP_OR_DOWN;
                break;
            }
        }
        b.setStereo(bs);
    }

    /**
     * Parses the properties section of the molecule.
     * 
     * @param l
     *            List of lines of input molecule's text.
     * @param mol
     *            The molecule object being constructed.
     */
    void parseProps(List<String> l, Molecule mol) {
        _sectionStart = ++_currentLine;

        loop:
        for (; _currentLine < l.size(); _currentLine++) {
            if (_skipProps) {
                continue;
            }

            String s = l.get(_currentLine);
            String prefix = s.substring(0, 6);

            switch (prefix) {
            case _M_END:
                break loop;
            case _M_CHG:
            case _M_ISO:
            case _M_RAD:
                _parseProp(s, mol, prefix);
                break;
            }
        }

        if ((!_skipProps) &&
                (_currentLine > _sectionStart) &&
                (null != _propsHook)) {
            _propsHook.apply(l.subList(_sectionStart, _currentLine), mol);
        }
    }

    /**
     * Parses one property, as present in the given line.
     * 
     * @param s
     *            The string with the property.
     * @param mol
     *            The molecule object being constructed.
     * @param prefix
     *            We currently handle <code>M  CHG</code>, <code>M  ISO</code>,
     *            <code>M  RAD</code> and <code>M  END</code>.
     */
    void _parseProp(String s, Molecule mol, String prefix) {
        int n = Integer.parseInt(s.substring(6, 9));

        int offset = 10;
        for (int i = 0; i < n; i++) {
            int atomId = Integer.parseInt(s.substring(offset, offset + 3));
            int value = Integer.parseInt(s.substring(offset + 4, offset + 7));

            switch (prefix) {
            case _M_CHG:
                mol.atom(atomId).setCharge(value);
                break;
            case _M_ISO:
                mol.atom(atomId).setIsotope(value);
                break;
            case _M_RAD:
                mol.atom(atomId).setRadical(Radical.ofValue(value));
                break;
            }

            offset += 8;
        }
    }

    /**
     * Parses the data items given in the input molecule.
     * 
     * @param l
     *            List of lines of input molecule's text.
     * @param mol
     *            The molecule object being constructed.
     */
    void parseTags(List<String> l, Molecule mol) {
        _sectionStart = ++_currentLine;

        String tag = null;
        for (; _currentLine < l.size(); _currentLine++) {
            String s = l.get(_currentLine).trim();
            if (s.isEmpty()) {
                _currentLine++;
                continue;
            }

            if (s.startsWith(SdfIterator.MOL_DELIM)) {
                break;
            }

            if (!_skipTags) {
                tag = _parseTag(mol, s, tag);
            }
        }

        if ((!_skipTags) &&
                (_currentLine > _sectionStart) &&
                (null != _tagsHook)) {
            _tagsHook.apply(l.subList(_sectionStart, _currentLine), mol);
        }
    }

    /**
     * Parses and sets a data item in the given molecule.
     * 
     * @param mol
     *            The molecule object being constructed.
     * @param s
     *            The string with either the tag (data item name) or its value.
     * @param tag
     *            The previously parsed line, which may be a tag.
     * @return The current tag or {@code null} depending on the previous value.
     */
    String _parseTag(Molecule mol, String s, String tag) {
        if (null != tag) {
            try {
                mol.addAttribute(tag, s);
            }
            catch (IllegalStateException e) {
                System.err.println("== Duplicate attribute: " + tag);
            }
            return null;
        }

        if ('>' == s.charAt(0)) {
            int idx1 = s.indexOf('<');
            int idx2 = s.indexOf(">", idx1 + 1);
            if (-1 != idx2) {
                return s.substring(idx1 + 1, idx2);
            }
        }

        // We shouldn't usually reach here.
        return null;
    }
}
