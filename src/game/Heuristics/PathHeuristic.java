package game.heuristics;

import game.BlockIt;
import game.GameBoard;
import game.node.Node;
import game.node.PlayerNode;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Heuristic based on the distance to the wall
 */
public class PathHeuristic extends Heuristic
{
    private char color;

    public PathHeuristic(char color)
    {
        this.color = color;
    }

    /**
     * Calculates the heuristic's value based on the distance to the wall.
     * @param board The board to which the heuristic's value is calculated.
     * @param color The color of the player
     */
    public void calculate(GameBoard board, char playerColor, boolean move) 
    {
        double currentPlayerValue = AStar(board, playerColor), 
            adversaryValue = AStar(board, BlockIt.getPlayerAfter(playerColor).getColor());

        if(!move)
            currentPlayerValue++;

        value = (GameBoard.getPlayBoardSize() - currentPlayerValue);
    }

    public double AStar(GameBoard board, char playerColor)
    {
        PriorityQueue<PlayerNode> activeNodes = new PriorityQueue<PlayerNode>();
        ArrayList<PlayerNode> children = new ArrayList<PlayerNode>();
        ArrayList<String> visitedNodes = new ArrayList<String>();
        PlayerNode currentNode;
        boolean active = false, visited = false, foundSolution;
        int[] playerPos = board.getPlayerPosition(playerColor);

        Node.getSolution().clear();
        Node.getSolution().trimToSize();
        
        foundSolution = false;

        activeNodes.add(new PlayerNode(null, 0, 1, "root", this, board, playerPos, playerColor));

        while (!activeNodes.isEmpty())
        {
            currentNode = activeNodes.peek();

            if(currentNode.isWinner())
            {
                currentNode.traceSolutionUp();
                foundSolution = true;
                break;
            }

            activeNodes.poll();
            visitedNodes.add(currentNode.getId());

            children = currentNode.expandPlayerNode(true);

            for (PlayerNode child : children)
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

                for (PlayerNode n : activeNodes)
                    if (n.getId().equals(child.getId())) {
                        active = true;
                        break;
                    }

                if (!active)
                    activeNodes.add((PlayerNode) child);

                active = false;
            }
        }

        if(foundSolution)
            return Node.getSolution().size();
        else
            return Integer.MAX_VALUE;
    }

    /**
     * Returns a new instance of this heuristic.
     * @return New instance of DirectHeuristic.
     */
    public Heuristic getNewHeuristic()
    {
        return new PathHeuristic(color);
    }
}