package game.node;

import game.GameBoard;
import java.util.ArrayList;


public class PlayerNode extends GameNode implements Comparable<PlayerNode>
{
    private int[] position;
    private char color;

    public PlayerNode(Node parentNode, String operator, GameBoard board, int[] position, char color)
    {
        super(parentNode, operator, board);

        this.position = position;
        this.color = color;
        this.id = position[0] + "-" + position[1];
    }

    public PlayerNode(GameNode modelNode, int[] position, char color)
    {
        super(modelNode.getParentNode(), modelNode.getDepth(), modelNode.getPathCost(), 
            modelNode.getOperator(), modelNode.getSearchOption(), modelNode.getGameBoard());

        this.position = position;
        this.color = color;
        this.id = position[0] + "-" + position[1];
    }

    public ArrayList<PlayerNode> expandPlayerNode()
    {
        ArrayList<PlayerNode> nodeList = new ArrayList<PlayerNode>();

        if(this.board.validateMoveDown(position))
            nodeList.add(new PlayerNode(this,  "move down " + position[0] + " " 
                + position[1], this.board.moveDown(position), 
                new int[] {position[0] + 2, position[1]}, color));

        if(this.board.validateMoveUp(position))
            nodeList.add(new PlayerNode(this,  "move up " + position[0] + " " 
                + position[1], this.board.moveUp(position), 
                new int[] {position[0] - 2, position[1]}, color));  

        if(this.board.validateMoveLeft(position))
            nodeList.add(new PlayerNode(this,  "move left " + position[0] + " " 
                + position[1], this.board.moveLeft(position), 
                new int[] {position[0], position[1] - 2}, color));    

        if(this.board.validateMoveRight(position))
            nodeList.add(new PlayerNode(this,  "move right " + position[0] + " " 
                + position[1], this.board.moveRight(position), 
                new int[] {position[0], position[1] + 2}, color)); 

        return nodeList;
    }

    public int[] getPosition()
    {
        return position;
    }

    public char getColor()
    {
        return color;
    }

    @Override
    public int compareTo(PlayerNode pn) 
    {
        int heurComp, depthComp, comp;

        if(pn.getColor() == color)
        {
            switch(color)
            {
                case 'R':
                    heurComp = position[0] - pn.getPosition()[0];
                    break;
        
                case 'B':
                    heurComp = (GameBoard.getBoardSize() - 1 - position[0]) 
                        - (GameBoard.getBoardSize() - 1 - pn.getPosition()[0]);
                    break;
    
                case 'G':
                    heurComp = (GameBoard.getBoardSize() - 1 - position[1]) 
                        - (GameBoard.getBoardSize() - 1 - pn.getPosition()[1]);
                    break;
    
                case 'Y':
                    heurComp = position[1] - pn.getPosition()[1];
                    break;
    
                default:
                    heurComp = 0;
            }
        }
        else
            heurComp = 0;

        depthComp = depth - pn.getDepth();

        comp = heurComp + depthComp;

        if(comp < 0)
            return -1;
        else if(comp > 0)
            return 1;
        return 0;
    }
}