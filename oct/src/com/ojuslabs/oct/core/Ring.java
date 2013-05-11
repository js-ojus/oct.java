package com.ojuslabs.oct.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ojuslabs.oct.exception.ImmutabilityException;

/**
 * Ring represents a simple cycle in a molecule. It keeps track of its atoms and
 * bonds, as well as its neighbouring rings.
 * 
 * A ring is created bound to a molecule, and cannot be re-bound.
 */
public class Ring
{
    private final int        _id;       // Unique ID of this ring in its
                                         // molecule.

    private final Molecule   _m;        // Containing molecule of this ring.

    private LinkedList<Atom> _atoms;    // The atoms in this ring. Atoms occur
                                         // in order.
    private LinkedList<Bond> _bonds;    // The bonds forming this ring. Bonds
                                         // occur in order.

    private boolean          _isAro;    // Is this ring aromatic in its current
                                         // configuration?

    private LinkedList<Ring> _nbrs;     // The neighbours of this ring that are
                                         // either pair-fused or

    private boolean          _completed; // Is this ring completed and
                                         // finalised?

    /**
     * @param m
     *            The containing molecule of this ring.
     * @param id
     *            The unique ID of this ring in its molecule.
     */
    Ring(Molecule m, int id) {
        _id = id;
        _m = m;

        _atoms = Lists.newLinkedList();
        _bonds = Lists.newLinkedList();
        _nbrs = Lists.newLinkedList();
    }

    /**
     * @return The containing molecule of this ring.
     */
    public Molecule molecule() {
        return _m;
    }

    /**
     * @return The unique ID of this ring.
     */
    public int id() {
        return _id;
    }

    /**
     * @return The size of this ring. This is equivalently the number of atoms
     *         or the number of bonds participating in this ring.
     */
    public int size() {
        return _atoms.size();
    }

    /**
     * @return True if this ring is aromatic; false otherwise.
     */
    public boolean isAromatic() {
        // TODO(js): Implement aromaticity determination.

        return _isAro;
    }

    /**
     * @param id
     *            Unique ID of the requested atom.
     * @return The requested atom if it exists; <code>null</code> otherwise.
     */
    public Atom atom(int id) {
        for (Atom a : _atoms) {
            if (a.id() == id) {
                return a;
            }
        }

        return null;
    }

    /**
     * Adds the given atom to this ring. The given atom is ignored if it is
     * already a member of this ring. It checks to see that a bond exists
     * between the most-recently-added atom and the current atom. An
     * {@link IllegalStateException} is thrown otherwise.
     * 
     * <b>N.B.</b> It is an error to attempt adding atoms to a <i>completed</i>
     * ring. It results in an exception getting thrown.
     * 
     * @param a
     *            The atom to add to this ring.
     * @throws ImmutabilityException
     */
    public void addAtom(Atom a) throws ImmutabilityException {
        if (_completed) {
            throw new ImmutabilityException(String.format(
                    "Ring is already completed. %s", toString()));
        }

        if (null != atom(a.id())) {
            return;
        }

        if (!_atoms.isEmpty()) {
            Atom prev = _atoms.getLast();
            Bond b = _m.bondBetween(prev, a);
            if (null == b) {
                throw new IllegalStateException(
                        String.format(
                                "There is no bond between previous atom %d and current atom %d",
                                prev.id(), a.id()));
            }

            _bonds.add(b);
        }
        _atoms.add(a);
    }

    /**
     * Completes the link between the last atom and the first. If the size of
     * the ring is less than 3, or if there is no bond connecting the first atom
     * and the last, an {@link IllegalStateException} is thrown. Completion also
     * effectively freezes the ring.
     */
    public void complete() {
        if (_completed) {
            return;
        }

        int len = _atoms.size();
        if (len < 3) {
            throw new IllegalStateException(
                    String.format(
                            "The smallest possible size for a ring is 3. Current ring size is: %d",
                            len));
        }

        Atom a1 = _atoms.getFirst();
        Atom a2 = _atoms.getLast();
        Bond b = _m._bondBetween(a1, a2);
        if (null == b) {
            throw new IllegalStateException(String.format(
                    "No bond between the first and the last atoms: %d, %d",
                    a1.id(), a2.id()));
        }

        _bonds.add(b);
        canonicalise();
        _completed = true;
    }

    // Transforms the ring into a standard representation.
    void canonicalise() {
        int min = Integer.MAX_VALUE;
        int idx = 0;
        for (int i = 0; i < _atoms.size(); i++) {
            int id = _atoms.get(i).id();
            if (id < min) {
                min = id;
                idx = i;
            }
        }

        // Rotate the list so that the atom with the minimum ID comes first.
        for (int i = 0; i < idx; i++) {
            _atoms.add(_atoms.removeFirst());
            _bonds.add(_bonds.removeFirst());
        }
    }

    /**
     * @return True if this ring is complete; false otherwise.
     */
    public boolean isCompleted() {
        return _completed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String
                .format("Ring %d: [%s]", _id, Joiner.on(", ").join(_atoms));
    }

    public Bond bond(int id) {
        Iterator<Bond> it = _bonds.iterator();
        Bond b = null;
        for (; it.hasNext(); b = it.next()) {
            if (b.id() == id) {
                return b;
            }
        }
        return null;
    }

    /**
     * A ring cannot be compared until it is `completed'. Two `completed' rings
     * are equal iff they have the same participating atoms, in the same order.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!_completed) {
            return false;
        }

        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ring)) {
            return false;
        }

        Ring other = (Ring) obj;
        if (!other.isCompleted()) {
            return false;
        }
        if (_m.id() != other.molecule().id()) {
            return false;
        }
        if (_atoms.size() != other.size()) {
            return false;
        }

        LinkedList<Atom> l = other._atoms;
        for (int i = 0; i < _atoms.size(); i++) {
            if (_atoms.get(i).id() != l.get(i).id()) {
                return false;
            }
        }

        return true;
    }

    // TODO(js): Implement a meaningful `hashCode'.

    /**
     * @return A read-only view of this ring's atoms.
     */
    public List<Atom> atoms() {
        return ImmutableList.copyOf(_atoms);
    }

    /**
     * @return A read-only view of this ring's bonds.
     */
    public List<Bond> bonds() {
        return ImmutableList.copyOf(_bonds);
    }

    /**
     * @return A read-only view of this ring's neighbouring rings.
     */
    public List<Ring> neighbours() {
        return ImmutableList.copyOf(_nbrs);
    }

    /**
     * @return Number of neighbouring rings, irrespective of configuration
     *         (spiro, fused, etc.).
     */
    public int numberOfNeighbours() {
        return _nbrs.size();
    }
}
