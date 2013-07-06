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

    /**
     * Registers a set of validators that each has to approve the candidate path
     * as a ring, with this detector.
     */
    public RingDetector() {
        // A junction atom cannot have _all_ of its neighbours in any one ring!
        _validators.add(new IRingValidator() {
            @Override
            public boolean validate(Molecule mol, List<Atom> atoms,
                    List<List<Atom>> nbrs, List<Atom> path) {
                for (Atom a : path) {
                    if (!a.isJunction()) {
                        continue;
                    }

                    int idx = atoms.indexOf(a);
                    boolean allFound = true;
                    for (Atom n : nbrs.get(idx)) {
                        if (-1 == path.indexOf(n)) {
                            allFound = false;
                            break;
                        }
                    }

                    // If all neighbours exist in this path, then it is a
                    // spurious outer shell path, not a genuine ring.
                    if (allFound) {
                        return false;
                    }
                }

                return true;
            }
        });
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
        List<Atom> path = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_S);
        Atom start = _atoms.get(0);
        path.add(start);

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

            path.add(next);
            prev = curr;
            curr = next;
        }

        makeRingFrom(path);
    }

    /**
     * Detects the multitude of rings in the given molecule.
     */
    void detectMultipleRings() {
        for (Atom a : _atoms) {
            if (a.isJunction()) {
                continue;
            }

            List<Atom> path = Lists
                    .newArrayListWithCapacity(Constants.LIST_SIZE_S);
            path.add(a);
            _candidates.add(path);
            break;
        }

        while (!_candidates.isEmpty()) {
            tryPath(_candidates.remove());
        }
    }

    /**
     * @param path
     *            A list of atoms potentially forming a part of a candidate
     *            ring.
     */
    void tryPath(List<Atom> path) {
        int size = path.size();
        Atom start = path.get(0);
        Atom curr = path.get(size - 1);
        Atom prev = (size > 1) ? path.get(size - 2) : curr;

        int i = _atoms.indexOf(curr);
        int inbrs = _nbrs.size();
        for (Atom next : _nbrs.get(i)) {
            inbrs--;

            if (next == start) { // We have a candidate.
                if (validatePath(path)) {
                    makeRingFrom(path);
                    continue;
                }
            }
            if (next == prev) { // We don't want to traverse backwards!
                continue;
            }

            // We could encounter a previously encountered atom. In that case,
            // we have a potential ring.
            int idx = path.indexOf(next);
            if (-1 != idx) {
                List<Atom> tpath = path.subList(idx, path.size());
                if (validatePath(tpath)) {
                    makeRingFrom(tpath);
                    continue;
                }
            }

            if (inbrs > 0) {
                List<Atom> newPath = Lists.newArrayList(path);
                newPath.add(next);
                _candidates.add(newPath);
            }
            else { // Last neighbour; extend the in-coming path.
                path.add(next);
                _candidates.add(path);
            }
        }
    }

    /**
     * @param path
     *            A list of atoms potentially forming a candidate ring.
     * @return {@code true} if the path passes all tests to become a valid ring;
     *         {@code false} otherwise.
     */
    boolean validatePath(List<Atom> path) {
        // A 3-membered path that comes this far is a valid ring!
        if (3 == path.size()) {
            return true;
        }

        // Otherwise, we run the path through all the registered validators.
        for (IRingValidator v : _validators) {
            if (!v.validate(_mol, _atoms, _nbrs, path)) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param path
     *            A list of atoms validated as forming a ring.
     */
    void makeRingFrom(List<Atom> path) {
        Ring r = new Ring(_mol);
        for (Atom a : path) {
            r.addAtom(a);
        }
        r.complete();
        _mol.addRing(r);
    }
}
