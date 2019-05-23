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
    public void calculate(GameBoard board, char color, boolean move) {
        PriorityQueue<PlayerNode> activeNodes = new PriorityQueue<PlayerNode>();
        ArrayList<PlayerNode> children = new ArrayList<PlayerNode>();
        ArrayList<String> visitedNodes = new ArrayList<String>();
        PlayerNode currentNode;
        boolean active = false, visited = false, foundSolution;
        int currentPlayerValue = 0, adversaryValue = 0;

        for(int i = 0; i < BlockIt.getPlayers().size(); i++)
        {
            Node.getSolution().clear();
            Node.getSolution().trimToSize();
            
            foundSolution = false;
    
            activeNodes.add(new PlayerNode(null, 0, 1, "root", this, board, board.getPlayerPosition(color), color));
    
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
    
                children = currentNode.expandPlayerNode();
    
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
            {
                if(i == BlockIt.getPlayerIndex())
                    currentPlayerValue = Node.getSolution().size();
                else
                    adversaryValue += Node.getSolution().size();

                continue;
            }
            else
            {
                if(i == BlockIt.getPlayerIndex())
                    currentPlayerValue = Integer.MAX_VALUE;
                else
                    adversaryValue += Integer.MAX_VALUE;

                children.clear();
                children.trimToSize();
            }
                
        }

        value = (GameBoard.getBoardSize() - currentPlayerValue);
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