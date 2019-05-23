package game.node;

import game.BlockIt;
import game.GameBoard;
import game.Player;
import game.heuristics.Heuristic;

import java.util.ArrayList;


public class PlayerNode extends GameNode implements Comparable<PlayerNode>
{
    private int[] position;
    private char color;
    private int barriers;

    public PlayerNode(Node parentNode, int depth, int pathCost, String operator, Heuristic heuristic, GameBoard board,
        int[] position, char color)
    {
        super(parentNode, depth, pathCost, operator, heuristic, board);

        this.position = position;
        this.color = color;
        this.id = position[0] + "-" + position[1];
    }

    public PlayerNode(Node parentNode, String operator, GameBoard board, int[] position, char color, int barriers)
    {
        super(parentNode, operator, board);

        this.position = position;
        this.color = color;
        this.id = position[0] + "-" + position[1];
    }

    public PlayerNode(GameNode modelNode, int[] position, char color)
    {
        super(modelNode.getParentNode(), modelNode.getDepth(), modelNode.getPathCost(), 
            modelNode.getOperator(), modelNode.getHeuristic(), modelNode.getGameBoard());

        this.position = position;
        this.color = color;
        this.id = position[0] + "-" + position[1];
    }

    public PlayerNode(String operator, Heuristic heuristic, GameBoard board, int[] position, char color, int barriers)
    {
        super(operator, heuristic, board);

        this.position = position;
        this.color = color;
        this.barriers = barriers;
        this.id = position[0] + "-" + position[1];
    }

    public PlayerNode(PlayerNode father)
    {
        super(father);

        this.position = father.getPosition().clone();
        this.color = father.getColor();
        this.barriers = father.getBarriers();
        this.id = position[0] + "-" + position[1];
    }

    public ArrayList<PlayerNode> expandPlayerNode(boolean sameColor)
    {
        ArrayList<PlayerNode> nodeList = new ArrayList<PlayerNode>();
        GameBoard newBoard;
        char nextColor = color;
        int[] nextPosition = position.clone();

        if(sameColor)
        {
            nextColor = color;
            nextPosition = position.clone();
        } 
        else
        {
            for(Player player: BlockIt.getPlayers())
                if(player.getColor() != color)
                {
                    nextPosition = player.getPosition().clone();
                    nextColor = player.getColor();
                    break;
                }
        }

        if((newBoard = this.board.moveDown(nextPosition)) != null)
            nodeList.add(new PlayerNode(this, "move down " + nextPosition[0] + " " 
                + nextPosition[1], newBoard, new int[] {nextPosition[0] + 2, nextPosition[1]}, nextColor, barriers));

        if((newBoard = this.board.moveUp(nextPosition)) != null)
            nodeList.add(new PlayerNode(this, "move up " + nextPosition[0] + " " 
                + nextPosition[1], newBoard, new int[] {nextPosition[0] - 2, nextPosition[1]}, nextColor, barriers));  

        if((newBoard = this.board.moveLeft(nextPosition)) != null)
            nodeList.add(new PlayerNode(this, "move left " + position[0] + " " 
                + nextPosition[1], newBoard, new int[] {nextPosition[0], nextPosition[1] - 2}, nextColor, barriers));    

        if((newBoard = this.board.moveRight(nextPosition)) != null)
            nodeList.add(new PlayerNode(this, "move right " + position[0] + " " 
                + nextPosition[1], newBoard, new int[] {nextPosition[0], nextPosition[1] + 2}, nextColor, barriers)); 

        if(barriers <= 0)
            return nodeList;

        char[][] charBoard = board.getBoard();

        for(int i = 0; i < charBoard.length; i++)
            for(int j = 0; j < charBoard.length; j++)
                if(charBoard[i][j] != '_' && charBoard[i][j] != 'X' && charBoard[i][j] != ' ')
                {
                    if(charBoard[i][j] != nextColor)
                    {
                        if ((newBoard = board.placeBarrier(j + 1, i - 1, 'v')) != null)
                            nodeList.add(new PlayerNode(this, "barrier v " + (i - 1) + " " 
                            + (j + 1), newBoard, position, nextColor, barriers - 1));

                        if ((newBoard = board.placeBarrier(j + 1, i + 1, 'v')) != null)
                            nodeList.add(new PlayerNode(this, "barrier v " + (i + 1) + " " 
                            + (j + 1), newBoard, position, nextColor, barriers - 1));

                        if ((newBoard = board.placeBarrier(j - 1, i - 1, 'v')) != null)
                            nodeList.add(new PlayerNode(this, "barrier v " + (i - 1) + " " 
                            + (j - 1), newBoard, position, nextColor, barriers - 1));

                        if ((newBoard = board.placeBarrier(j - 1, i + 1, 'v')) != null)
                            nodeList.add(new PlayerNode(this, "barrier v " + (i + 1) + " " 
                            + (j - 1), newBoard, position, nextColor, barriers - 1));

                        if ((newBoard = board.placeBarrier(j + 1, i - 1, 'h')) != null)
                            nodeList.add(new PlayerNode(this, "barrier h " + (i - 1) + " " 
                            + (j + 1), newBoard, position, nextColor, barriers - 1));

                        if ((newBoard = board.placeBarrier(j + 1, i + 1, 'h')) != null)
                            nodeList.add(new PlayerNode(this, "barrier h " + (i + 1) + " " 
                            + (j + 1), newBoard, position, nextColor, barriers - 1));

                        if ((newBoard = board.placeBarrier(j - 1, i - 1, 'h')) != null)
                            nodeList.add(new PlayerNode(this, "barrier h " + (i - 1) + " " 
                            + (j - 1), newBoard, position, nextColor, barriers - 1));

                        if ((newBoard = board.placeBarrier(j - 1, i + 1, 'h')) != null)
                            nodeList.add(new PlayerNode(this, "barrier h " + (i + 1) + " " 
                            + (j - 1), newBoard, position, nextColor, barriers - 1));
                    }
                } 
                
                
        return nodeList;
    }

    /**
     * Determines the player bot's next move through the use of the minimax algorithm
     * @param node
     * @param depth
     * @param maximizingPlayer
     * @return
     */
    public GameNode minimax(int depth, boolean maximizingPlayer) {        
        return minimaxAux(this, depth, null, null, maximizingPlayer);
    }

    /**
     * Determines the player bot's next move through the use of the minimax algorithm with alpha beta pruning
     * @param node
     * @param depth
     * @param alpha
     * @param beta
     * @param maximizingPlayer
     * @return
     */
    public GameNode alphaBeta(int depth, PlayerNode alpha, PlayerNode beta, boolean maximizingPlayer) {
        return minimaxAux(this, depth, alpha, beta, maximizingPlayer);
    }

    /**
     * Determines the player bot's next move through the use of the minimax algorithm, with or without alpha beta pruning depending on the user input
     * @param node
     * @param depth
     * @param maximizingPlayer
     * @return
     */
    private PlayerNode minimaxAux(PlayerNode father, int depth, PlayerNode alpha, PlayerNode beta, boolean maximizingPlayer) {

        PlayerNode value = null;
        boolean isAlphaBeta = (alpha != null && beta != null), returnFlag = false;
        ArrayList<PlayerNode> childNodes;
        char playerColor = (maximizingPlayer ? color : BlockIt.getNextPlayer().getColor());

         // todo check search depth and isTerminal
        if (depth >= Node.MAX_SEARCH_DEPTH) 
            returnFlag = true;

        if(isWinner())
        {
            value = new PlayerNode(father);
            value.getHeuristic().setValue(100);
            returnFlag = true;
        }
        else
            if(isWinner(BlockIt.getNextPlayer().getColor(), father.getGameBoard().getPlayerPosition(BlockIt.getNextPlayer().getColor())))
            {
                value = new PlayerNode(father);
                value.getHeuristic().setValue(-100);
                returnFlag = true;
            }        

        if(returnFlag)
            if(value == null)
            {
                value = new PlayerNode(father);
                value.calculateHeuristic(playerColor);
                return value;
            }
            else
                return value;

        childNodes = father.expandPlayerNode(maximizingPlayer);

        //System.out.println("--------------------------");

        for(PlayerNode n: childNodes)
        {
            n.calculateHeuristic(playerColor);
            System.out.print(n.getOperator() + "-" + n.getHeuristic().getValue() + " " ); 
        }
            
        //System.out.println("\n");

        if (maximizingPlayer) 
        {
            for (PlayerNode child : childNodes) 
            {
                //((GameNode) child).calculateHeuristic(playerColor);

                /*
                System.out.print("Before: ");

                if(value != null)
                    System.out.print(value.getHeuristic().getValue());
                else
                    System.out.print("null"); */
                    
                
                if(Node.max(value, minimaxAux(child, depth + 1, alpha, beta, false)) == 1)
                {
                   // System.out.print(" (Replaced with " + child.getHeuristic().getValue() + " " + child.getOperator() + ") ");
                    value = child; 
                }
                    
                //System.out.println(" After: " + value.getHeuristic().getValue());

                if (isAlphaBeta) 
                {
                    if(Node.max(alpha, value) == 1)
                        alpha = value;

                    if (alpha.ge(beta)) 
                        break;
                }
            }  
        } 
        else 
        {
            for (PlayerNode child : childNodes) 
            {
                //((GameNode) child).calculateHeuristic(color);
                if(Node.min(value, minimaxAux(child, depth + 1, alpha, beta, true)) == 1)
                    value = child;

                if(isAlphaBeta) 
                {
                    if(Node.min(beta, value) == 1)
                        beta = value;

                    if (beta.ge(alpha)) //Changed from alpha.ge(beta)
                        break;
                } 
            }
        }

        return value;
        
    }

    public boolean isWinner()
    {
        return isWinner(this.color, this.position);
    }

    public void useBarrier()
    {
        barriers--;
    }

    public static boolean isWinner(char playerColor, int[] playerPosition)
    {
        switch(playerColor)
        {
            case 'R':
                return playerPosition[0] == GameBoard.getBoardSize() - 1;

            case 'B':
                return playerPosition[0] == 0;

            case 'G':
                return playerPosition[1] == 0;

            case 'Y':
                return playerPosition[1] == GameBoard.getBoardSize() - 1;

            default:
                return false;
        }
    }

    public int[] getPosition()
    {
        return position;
    }

    public char getColor()
    {
        return color;
    }

    public int getBarriers()
    {
        return barriers;
    }

    public void setPosition(int[] newPosition)
    {
        position = newPosition.clone();
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