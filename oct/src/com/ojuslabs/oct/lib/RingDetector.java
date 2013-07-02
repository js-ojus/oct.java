/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.lib;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.ojuslabs.oct.common.Constants;
import com.ojuslabs.oct.core.Atom;
import com.ojuslabs.oct.core.Bond;
import com.ojuslabs.oct.core.Molecule;
import com.ojuslabs.oct.core.Ring;

/**
 * The default ring detector.
 */
public final class RingDetector implements IRingDetector {

    // The molecule to analyse.
    private Molecule             _mol;

    // Internal data holders for analysis and detection.
    private List<Atom>           _atoms;
    private List<List<Atom>>     _nbrs;
    private Deque<List<Atom>>    _candidates;

    // The validators to employ before approving a candidate path as a ring.
    private List<IRingValidator> _validators;

    public RingDetector() {
        // Intentionally left blank.
    }

    /**
     * <b>N.B.</b> <b><i>This method answers the live internal list of atoms,
     * only for efficiency reasons. Modification of this list leads to undefined
     * results.</i></b>
     * 
     * @return The internal list of atoms in this molecule. During ring
     *         detection, this list contains only non-terminal atoms.
     */
    List<Atom> atoms() {
        return _atoms;
    }

    /**
     * <b>N.B.</b> <b><i>This method answers the live internal list of atom
     * neighbours, only for efficiency reasons. Modification of this list leads
     * to undefined results.</i></b>
     * 
     * @return The internal list of neighbour lists of atoms in this molecule.
     *         During ring detection, this list of lists contains those only for
     *         non-terminal atoms.
     */
    List<List<Atom>> neighbours() {
        return _nbrs;
    }

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
        _nbrs = Lists.newArrayListWithCapacity(_mol.numberOfAtoms());
        _candidates = Queues.newArrayDeque();

        // Initialise the atoms and their neighbours.
        for (Atom a : _mol.atoms()) {
            _atoms.add(a);
            List<Atom> nbrs = Lists
                    .newArrayListWithCapacity(Constants.LIST_SIZE_S);
            _nbrs.add(nbrs);
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

        // At this point, if all atoms have exactly two neighbours, there can be
        // only one ring.
        if (noAtomsWithGT2Bonds()) {
            detectTheOnlyRing();
            return;
        }

        detectMultipleRings();
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
                if (1 == _nbrs.get(i).size()) {
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
        for (Atom nbr : _nbrs.get(i)) {
            int nbrIdx = _atoms.indexOf(nbr);
            Iterator<Atom> nit = _nbrs.get(nbrIdx).iterator();
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

    /**
     * @return {@code true} if there are no atoms having more than two
     *         neighbours; {@code false} otherwise.
     */
    boolean noAtomsWithGT2Bonds() {
        for (List<Atom> l : _nbrs) {
            if (l.size() > 2) {
                return false;
            }
        }

        return true;
    }

    /**
     * Forms a ring out of the remaining atoms, and adds it to the molecule.
     */
    void detectTheOnlyRing() {
        Ring r = new Ring(_mol);
        Atom start = _atoms.get(0);
        r.addAtom(start);

        int i = 0;
        Atom prev = start;
        Atom curr = start;
        Atom next = null;
        while (true) {
            i = _atoms.indexOf(curr);
            List<Atom> nbrs = _nbrs.get(i);
            next = nbrs.get(0);
            if (next == prev) { // We don't want to turn around!
                next = nbrs.get(1);
            }
            if (next == start) { // We have completed the ring.
                break;
            }

            r.addAtom(next);
            prev = curr;
            curr = next;
        }

        r.complete();
        _mol.addRing(r);
    }

    /**
     * Detects the multitude of rings in the given molecule.
     */
    void detectMultipleRings() {
        for (Atom a : _atoms) {
            List<Atom> path = Lists
                    .newArrayListWithCapacity(Constants.LIST_SIZE_S);
            path.add(a);
            _candidates.add(path);
        }

        while (!_candidates.isEmpty()) {
            tryPath(_candidates.remove());
        }
    }

    /**
     * @param path
     *            A list of atoms potentially forming a candidate ring.
     */
    void tryPath(List<Atom> path) {
        int size = path.size();
        Atom start = path.get(0);
        Atom curr = path.get(size - 1);
        Atom prev = (size > 1) ? path.get(size - 2) : curr;

        int i = _atoms.indexOf(curr);
        for (Atom next : _nbrs.get(i)) {
            if (next == prev) { // We don't want to turn around!
                continue;
            }
            if (next == start) { // We have a candidate.
                if (validatePath(path)) { // We have a valid ring.
                    makeRingFrom(path);
                    continue;
                }
            }

            List<Atom> newPath = Lists.newArrayList(path);
            newPath.add(next);
            _candidates.add(newPath);
        }
    }

    /**
     * @param path
     *            A list of atoms potentially forming a candidate ring.
     * @return {@code true} if the path passes all tests to become a valid ring;
     *         {@code false} otherwise.
     */
    boolean validatePath(List<Atom> path) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @param path
     */
    void makeRingFrom(List<Atom> path) {
        // TODO Auto-generated method stub

    }
}
