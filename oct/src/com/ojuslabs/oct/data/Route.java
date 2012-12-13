package com.ojuslabs.oct.data;

/**
 * Route represents a chemical synthesis route. A route is a tree, with its
 * origin (root) in the goal molecule. It can have any branching factor at any
 * level, though factors of 1 (linear with one or more trivial coreactants) and
 * 2 (convergent synthesis) constitute the majority. Each branch in a route is a
 * sub-tree itself, with its product molecule as the (intermediate) goal
 * molecule.
 * <p>
 * Note that in the absence of convergent synthesis reactions, the said tree
 * degenerates to linear list.
 */
public class Route
{
    private static int _routeId;

    private final int  _id;          // A unique ID for this route.
    private double     _yield;       // Effective yield of this route.
    private int        _numSteps;    // Maximum depth of this synthesis tree.

    private Molecule   _goalMolecule; // The goal molecule of this route.
    private RouteNode  _goalNode;    // The node representing the final step
                                      // that produces the goal molecule.

    private int        _peakNodeId;  // A running serial ID for the nodes in
                                      // this route.

    /**
     * Factory method for creating new routes.
     * 
     * @param goal
     *            The goal molecule of this route.
     * @return A new route with a unique identifier.
     */
    public static synchronized Route newInstance(Molecule goal) {
        return new Route(++_routeId, goal);
    }

    Route(int id, Molecule goal) {
        _id = id;
        _peakNodeId = 0;

        if (null == goal) {
            throw new IllegalArgumentException(
                    String.format("The goal molecule of a route should not be null."));
        }
        _goalMolecule = goal;
        _goalNode = new RouteNode(++_peakNodeId);
    }

    /**
     * @return The unique identifier of this route.
     */
    public int id() {
        return _id;
    }

    /**
     * @return The next available serial node ID.
     */
    int nextNodeId() {
        return ++_peakNodeId;
    }

    /**
     * @return The final node (root of the synthesis tree) representing the goal
     *         molecule of this route.
     */
    public RouteNode goalNode() {
        return _goalNode;
    }

    /**
     * @return The goal molecule of this route. <b>N.B.</b> Modifying this
     *         molecule may lead to undefined and unpredictable behaviour!
     */
    public Molecule goalMolecule() {
        return _goalMolecule;
    }

    public void label() {
        // TODO(js): Implement a labelling scheme.
    }
}
