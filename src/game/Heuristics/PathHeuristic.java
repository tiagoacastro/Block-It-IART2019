package game.heuristics;

import game.GameBoard;
import game.Player;
import game.node.Node;
import game.node.PlayerNode;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Heuristic based on the distance to the wall
 */
public class PathHeuristic extends Heuristic
{
    private int[] position;
    private char color;

    public PathHeuristic(int[] position, char color)
    {
        this.position = position;
        this.color = color;
    }

    /**
     * Calculates the heuristic's value based on the distance to the wall.
     * @param board The board to which the heuristic's value is calculated.
     * @param color The color of the player
     */
    public void calculate(GameBoard board, char color) {
        PriorityQueue<PlayerNode> activeNodes = new PriorityQueue<PlayerNode>();
        ArrayList<PlayerNode> children = new ArrayList<PlayerNode>();
        ArrayList<String> visitedNodes = new ArrayList<String>();
        PlayerNode currentNode;
        boolean active = false, visited = false;

        Node.getSolution().clear();
        Node.getSolution().trimToSize();

        activeNodes.add(new PlayerNode(null, 0, 1, "root", null, board, position, color));

        while (!activeNodes.isEmpty())
        {
            currentNode = activeNodes.peek();

            if(Player.isWinner(currentNode.getColor(), currentNode.getPosition()))
            {
                currentNode.traceSolutionUp();
                value = Node.getSolution().size();
                return;
            }


            activeNodes.poll();
            visitedNodes.add(currentNode.getId());

            children = currentNode.expandPlayerNode();

            for (Node child : children)
            {
                for (String id : visitedNodes)
                    if (id.equals(child.getId()))
                    {
                        visited = true;
                        break;
                    }

                if (visited)
                {
                    visited = false;
                    continue;
                }

                for (Node n : activeNodes)
                    if (n.getId().equals(child.getId())) {
                        active = true;
                        break;
                    }

                if (!active)
                    activeNodes.add((PlayerNode) child);

                active = false;
            }
        }

        children.clear();
        children.trimToSize();

        value = Integer.MAX_VALUE;
    }

    /**
     * Returns a new instance of this heuristic.
     * @return New instance of DirectHeuristic.
     */
    public Heuristic getNewHeuristic()
    {
        return new PathHeuristic(position, color);
    }
}