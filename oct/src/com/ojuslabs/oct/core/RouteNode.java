package com.ojuslabs.oct.core;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ojuslabs.oct.common.Constants;

/**
 * RouteNode is a node in its containing route's synthesis tree. Note that a
 * node has a single product molecule that it contributes to downstream
 * reactions (i.e., <b>higher</b> in the tree). A route node is a combination of
 * possibly multiple incoming nodes (reactants), a single reaction and the
 * outgoing product molecule.
 * <p>
 * To facilitate sorting the branches in the synthesis tree, it stores the
 * effective yield up to this point, convergence status, <i>etc.</i>, in it.
 */
public class RouteNode
{
    private final int       _id;            // A unique ID for this node.
    private String          _label;          // The unique dotted-decimal
                                             // notation ID of this node. This
                                             // is composed by walking down the
                                             // tree from the root node down to
                                             // this node.
    private Route           _route;         // The containing route of this
                                             // node.

    private double          _yield;         // The effective yield up to this
                                             // point.
    private boolean         _hasConvergence; // Does this branch have any
                                             // convergent reactions?

    private List<RouteNode> _children;      // In-coming reactants.
    private Molecule        _product;       // This node's product molecule.
    private List<Molecule>  _byproducts;    // Other products that may be
                                             // generated.
    private Reaction        _reaction;      // The reaction resulting in this
                                             // node's product molecule.

    /**
     * Each RouteNode instance should have an ID that is unique within its
     * route. It should, therefore, be provided by the route itself. Typical
     * usage is as follows.
     * 
     * <pre>
     * Route route = Route.newInstance();
     * ...
     * RouteNode goal = route.goalNode();
     * ...
     * RouteNode node = goal.newChildNode();
     * ...
     * RouteNode node2 = node.newChildNode();
     * </pre>
     * 
     * @param id
     */
    RouteNode(int id) {
        _id = id;

        _children = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_T);
        _byproducts = Lists.newArrayListWithCapacity(Constants.LIST_SIZE_T);
    }

    /**
     * @return The unique ID of this node.
     */
    public int id() {
        return _id;
    }

    /**
     * Answers the path-dependent label of this node, constructed by traversing
     * all the nodes from the root (goal) node to this node. <b>N.B.</b> This
     * method should not be called before the route has been labelled.
     * 
     * @return The hierarchical, dotted-decimal notation textual label of this
     *         node.
     */
    public String label() {
        return _label;
    }

    /**
     * Answers the effective yield of the route up to this point.
     * 
     * @return The effective yield.
     */
    public double yield() {
        return _yield;
    }

    /**
     * @return True if any of the nodes in this sub-tree have a convergent
     *         reaction; false otherwise.
     */
    public boolean hasConvergence() {
        return _hasConvergence;
    }

    /**
     * Creates a new child node, and adds it to this node.
     * 
     * @return The newly-created child node.
     */
    public RouteNode newChildNode() {
        RouteNode child = new RouteNode(_route.nextNodeId());
        _children.add(child);
        return child;
    }

    /**
     * @return A view of the children nodes of this route node.
     */
    public List<RouteNode> children() {
        return ImmutableList.copyOf(_children);
    }

    /**
     * @return The primary product molecule of this node's reaction.
     */
    public Molecule product() {
        return _product;
    }

    /**
     * @return The reaction underlying this route node.
     */
    public Reaction reaction() {
        return _reaction;
    }
}
