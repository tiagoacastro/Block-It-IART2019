package game.node;

import java.util.ArrayList;

import game.heuristics.*;
import game.Player;

/**
 * Represents a regular node in a search problem.
 */
public abstract class Node
{

    /**
     * Determines minimax's max search depth
     */
    public static int MAX_SEARCH_DEPTH = 1;

    /**
     * The parent node of this node.
     */
    protected Node parentNode;

    /**
     * The depth at which this node is at.
     */
    protected int depth;

    /**
     * The path cost to reach this node (always 1 since from one depth level to the other all possible plays
     * exhaust 1 move)
     */
    protected int pathCost;

    /**
     * This node's operator.
     */
    protected String operator;

    /**
     * List containing the solution to a level in order.
     */
    protected static ArrayList<String> solution = new ArrayList<String>();

    /**
     * Heuristic used to calculate the best path
     */
    protected Heuristic heuristic; 

    /**
     * Id of the node
     */
    protected String id;

    /**
     * Constructor of the class with most members.
     * @param parentNode The parent node of this node.
     * @param depth The depth at which this node is at.
     * @param pathCost The path cost to reach this node.
     * @param operator This node's operator.
     * @param searchOption This node's search option.
     * @param heuristic This node's heuristic.
     */
    public Node(Node parentNode, int depth, int pathCost, String operator, Heuristic heuristic)
    {
        this.parentNode = parentNode;
        this.depth = depth;
        this.pathCost = pathCost;
        this.operator = operator;
        this.heuristic = heuristic;

        if(parentNode == null)
            this.id = operator;
        else
            this.id = parentNode.id + " -> " + operator;
    }

    /**
     * Constructor of the class with only a few members, the ones remaining being instantiated automatically as if
     * this node was a regular child of the parent node indicated.
     * @param parentNode The parent node of this node.
     * @param operator This node's operator.
     */
    public Node(Node parentNode, String operator)
    {
        this.depth = parentNode.depth + 1;
        this.pathCost = 1;
        this.parentNode = parentNode;
        this.operator = operator;
        this.heuristic = parentNode.getHeuristic();

        this.id = parentNode.id + " -> " + operator;
    }

    /**
     * Expands a node, i.e, returns its possible children.
     * @return The children of this node.
     */
    public abstract ArrayList<Node> expandNode(Player player);

    /**
     * Executes the moves indicated by the operators in the Solution array.
     */
    public abstract void traceSolution();

    /**
     * Adds the correspondent node's operators to the Solution array all the way from the final/acceptance node.
     */
    public void traceSolutionUp()
    {
        if(!operator.equals("root"))
        {
            solution.add(0, operator);
            parentNode.traceSolutionUp();
        }
    }

    /**
     * Retrieves the depth.
     * @return The depth this node's at.
     */
    public int getDepth()
    {
        return depth;
    }

    /**
     * Retrieves the current solution.
     * @return The list containing the solution to a level.
     */
    public static ArrayList<String> getSolution()
    {
        return solution;
    }

    /**
     * Determines the greater of two nodes, in regards to the heuristic value
     * @param n
     * @param m
     * @return
     */

    public static Node max(Node n, Node m) {
        if (n == null) {
            return m;
        } 

        if (m == null) {
            return n;
        }

        return (n.heuristic.getValue() > m.heuristic.getValue() ? n : m);
    }

    /**
     * 
     * @param n
     * @param m
     * @return
     */

    public static Node min(Node n, Node m) {
        if (n == null) {
            return m;
        } 

        if (m == null) {
            return n;
        }

        return (n.heuristic.getValue() < m.heuristic.getValue() ? n : m);
    }


    /**
     * Checks if the heuristic value of node a is greater than or equal to that of node b
     * @param a
     * @param b
     * @return
     */

    public boolean ge(Node b) {
        return this.heuristic.getValue() >= b.heuristic.getValue();
    }

    public String getId()
    {
        return id;
    }

    public int getPathCost()
    {
        return pathCost;
    }

    public Node getParentNode()
    {
        return parentNode;
    }

    public String getOperator()
    {
        return operator;
    }

    public Heuristic getHeuristic()
    {
        return heuristic;
    }

    public void setHeuristic(Heuristic h)
    {
        heuristic = h;
    }

    public void setPathCost(int cost)
    {
        this.pathCost = cost;
    }
}