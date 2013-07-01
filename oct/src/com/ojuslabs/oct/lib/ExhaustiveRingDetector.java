/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.lib;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.Constants;
import com.ojuslabs.oct.core.Atom;
import com.ojuslabs.oct.core.Bond;
import com.ojuslabs.oct.core.Molecule;
import com.ojuslabs.oct.core.Ring;

/**
 * This ring detector detects all the rings in the given molecule, not just
 * SSSR, <i>etc.</i>
 */
public final class ExhaustiveRingDetector implements IRingDetector {

    // The molecule to analyse.
    private Molecule         _mol;

    // Internal data holders for analysis and detection.
    private List<Atom>       _atoms;
    private List<List<Atom>> _atomNbrs;
    private List<Ring>       _rings;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ojuslabs.oct.lib.IRingDetector#initialise(com.ojuslabs.oct.core.Molecule
     * )
     */
    @Override
    public void initialise(Molecule mol) {
        if (null == mol) {
            throw new IllegalArgumentException("Null molecule given.");
        }

        _mol = mol;
        _atoms = Lists.newArrayListWithCapacity(_mol.numberOfAtoms());
        _atomNbrs = Lists.newArrayListWithCapacity(_mol.numberOfAtoms());
        _rings = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_S);

        // Initialise the atoms and their neighbours.
        for (Atom a : _mol.atoms()) {
            _atoms.add(a);
            List<Atom> nbrs = Lists
                    .newArrayListWithCapacity(Constants.LIST_SIZE_S);
            _atomNbrs.add(nbrs);
            for (Bond b : a.bonds()) {
                nbrs.add(b.otherAtom(a.id()));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ojuslabs.oct.lib.IRingDetector#detectRings()
     */
    @Override
    public void detectRings() {
        // First, remove all terminal chains.
        pruneTerminalChains();
    }

    /**
     * A terminal atom is one that has only one neighbour. Removal of a terminal
     * atom may make its neighbour a terminal one, which then has to be removed
     * in turn. In effect, all such terminal chains have to be removed.
     */
    void pruneTerminalChains() {
        boolean running = true;

        outer:
        while (running) {
            running = false;

            for (int i = 0; i < _atoms.size(); i++) {
                if (1 == _atomNbrs.get(i).size()) {
                    pruneTerminalAtom(i);
                    running = true; // An atom has been pruned.
                    continue outer; // We should now check for cascades.
                }
            }
        }
    }

    /**
     * @param i
     *            Index of the atom already identified as a terminal atom.
     */
    void pruneTerminalAtom(int i) {
        Atom a = _atoms.get(i);

        // Remove references to this atom from the neighbour lists of its own
        // neighbours.
        outer:
        for (Atom nbr : _atomNbrs.get(i)) {
            int nbrIdx = _atoms.indexOf(nbr);
            Iterator<Atom> nit = _atomNbrs.get(nbrIdx).iterator();
            while (nit.hasNext()) {
                Atom nbrNbr = nit.next();
                if (a == nbrNbr) {
                    nit.remove();
                    continue outer;
                }
            }
        }

        // Now, we remove this atom itself.
        _atoms.remove(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ojuslabs.oct.lib.IRingDetector#numberOfRings()
     */
    @Override
    public int numberOfRings() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ojuslabs.oct.lib.IRingDetector#rings()
     */
    @Override
    public List<Ring> rings() {
        // TODO Auto-generated method stub
        return null;
    }

}
