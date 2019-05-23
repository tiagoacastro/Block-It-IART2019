package game;

import game.GameBoard;
import game.heuristics.*;
import game.node.*;

import java.util.ArrayList;

public class Player
{
    protected static int MAX_BARRIERS;
    protected static GameNode node;

    protected int[] position;
    protected int barriers;
    protected int difficulty;
    protected char color;

    public Player(int difficulty, int[] position, char color)
    {
        this.position = position;
        this.barriers = MAX_BARRIERS;
        this.difficulty = difficulty;
        this.color = color;
    }

    public Player(int difficulty, char color)
    {
        this.difficulty = difficulty;
        this.color = color;

        switch(color)
        {
            case 'R':
                position = new int[]{0, (GameBoard.getBoardSize() - 1) / 2};
                break;

            case 'G':
                position = new int[]{(GameBoard.getBoardSize() - 1) / 2,  GameBoard.getBoardSize() - 1};
                break;

            case 'B':
                position = new int[]{ GameBoard.getBoardSize() - 1, ( GameBoard.getBoardSize() - 1) / 2};
                break;

            case 'Y':
                position = new int[]{( GameBoard.getBoardSize() - 1) / 2, 0};
                break;

            default:
                System.out.println("Unsuported color " + color);
                position = new int[]{0, 0};
        }
    }

    //Bot function
    public void play()
    {
        Node newNode = minimax(node, 0, true);

        if(newNode == null)
        {
            System.out.println("MiniMax returned null");
            return;
        }

        String[] operatorParams = newNode.getOperator().split(" ");

        if(operatorParams.length != 4)
        {
            System.out.println("Invalid operator obtained by minimax");
            return;
        }

        if(operatorParams[0].equals("move"))
            move(operatorParams[1]);
        else
            useBarrier(Integer.parseInt(operatorParams[3]), Integer.parseInt(operatorParams[2]), operatorParams[1].charAt(0));

        System.out.println(newNode.getOperator());
        System.out.println(newNode.getHeuristic().getValue());
    }

    public boolean move(String move)
    {
        GameBoard newBoard;
        int[] newPosition = position.clone();

        switch(move)
        {
            case "up":
                newBoard = node.getGameBoard().moveUp(position);
                newPosition[0] -= 2;
                break;

            case "down":
                newBoard = node.getGameBoard().moveDown(position);
                newPosition[0] += 2;
                break;

            case "left":    
                newBoard = node.getGameBoard().moveLeft(position);
                newPosition[1] -= 2;
                break;

            case "right":
                newBoard = node.getGameBoard().moveRight(position);
                newPosition[1] += 2;
                break;

            default:
                System.out.println("Invalid move: " + move);
                return false;
        }

        if(newBoard != null)
        {
            node.setGameBoard(newBoard);
            this.position = newPosition;
            return true;
        }
        else
            return false;
    }

    public boolean useBarrier(int x, int y, char direction)
    {
        GameBoard newBoard = node.getGameBoard().placeBarrier(x, y, direction);

        if(newBoard != null)
        {
            node.setGameBoard(newBoard);
            barriers--;
            return true;
        }
        else
        {
            System.out.println("Couldn't place a barrier there");
            return false;
        }
            
    }

    public boolean isWinner()
    {
        return isWinner(this.color, this.position);
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

    public String getName()
    {
        switch(color)
        {
            case 'R':
                return "Red";
                
            case 'Y':
                return "Yellow";

            case 'B':
                return "Blue";

            case 'G':
                return "Green";

            default:
                return "Unknown";
        }
    }

    public Heuristic getNewHeuristic()
    {
        switch(difficulty)
        {
            case 2:
                return new CompetitiveHeuristic();

            case 3:
                return new DirectHeuristic();

            case 4:
                return new PathHeuristic(color);

            default:
                return null;
        }
    }

    public boolean equals(Player p)
    {
        return p.getColor() == color;
    }

    public int getDifficulty()
    {
        return difficulty;
    }

    public int getBarriers()
    {
        return barriers;
    }

    public int[] getPosition()
    {
        return position;
    }

    public static int getMaxBarriers()
    {
        return MAX_BARRIERS;
    }

    public char getColor()
    {
        return color;
    }

    public static GameBoard getBoard()
    {
        return node.getGameBoard();
    }

    public static GameNode getNode()
    {
        return node;
    }

    public static void setMaxBarriers(int maxBarriers)
    {
        MAX_BARRIERS = maxBarriers;
    }

    public static void setNode(GameNode node)
    {
        Player.node = node;
    }

    public static void setBoard(GameBoard gameBoard)
    {
        node.setGameBoard(gameBoard);
    }

    /**
     * Determines the player bot's next move through the use of the minimax algorithm
     * @param node
     * @param depth
     * @param maximizingPlayer
     * @return
     */
    public GameNode minimax(GameNode node, int depth, boolean maximizingPlayer) {
        return minimaxAux(node, depth, null, null, maximizingPlayer);
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
    public GameNode alphaBeta(GameNode node, int depth, GameNode alpha, GameNode beta, boolean maximizingPlayer) {
        return minimaxAux(node, depth, alpha, beta, maximizingPlayer);
    }

    /**
     * Determines the player bot's next move through the use of the minimax algorithm, with or without alpha beta pruning depending on the user input
     * @param node
     * @param depth
     * @param maximizingPlayer
     * @return
     */
    private GameNode minimaxAux(GameNode father, int depth, GameNode alpha, GameNode beta, boolean maximizingPlayer) {

        GameNode value = null;
        boolean isAlphaBeta = (alpha != null && beta != null), returnFlag = false;
        ArrayList<Node> childNodes;

         // todo check search depth and isTerminal
        if (depth >= Node.MAX_SEARCH_DEPTH) 
            returnFlag = true;

        if(isWinner(color, father.getGameBoard().getPlayerPosition(color)))
        {
            value = new GameNode(father);
            value.getHeuristic().setValue(100);
            returnFlag = true;
        }
        else
            if(isWinner(BlockIt.getNextPlayer().getColor(), father.getGameBoard().getPlayerPosition(BlockIt.getNextPlayer().getColor())))
            {
                value = new GameNode(father);
                value.getHeuristic().setValue(-100);
                returnFlag = true;
            }        

        if(returnFlag)
            if(value == null)
            {
                value = new GameNode(father);
                value.calculateHeuristic(color);
                return value;
            }
            else
                return value;

        if(barriers > 0)
            childNodes = father.expandNodeWithBarrier(this);
        else
            childNodes = father.expandNode(this);

            System.out.println("--------------------------");
        for(Node n: childNodes)
        {
            ((GameNode) n).calculateHeuristic(color);
            System.out.print(n.getOperator() + "-" + n.getHeuristic().getValue() + " " ); 
        }
            
        System.out.println("\n");

        if (maximizingPlayer) 
        {
            for (Node child : childNodes) 
            {
                //((GameNode) child).calculateHeuristic(color);

                /*
                System.out.print("Before: ");

                if(value != null)
                    System.out.print(value.getHeuristic().getValue());
                else
                    System.out.print("null");

                GameNode mmChild = minimaxAux((GameNode) child, depth + 1, alpha, beta, false);

                System.out.print(" Contender: " + mmChild.getHeuristic().getValue());

                if(value == null || mmChild.getHeuristic().getValue() > value.getHeuristic().getValue())
                {
                    System.out.print(" (Replaced) ");
                    value = mmChild;
                }
                    
                System.out.println(" After: " + value.getHeuristic().getValue()); */

                value = (GameNode) Node.max(value, minimaxAux((GameNode) child, depth + 1, alpha, beta, false));

                if (isAlphaBeta) 
                {
                    alpha = (GameNode) Node.max(alpha, value);

                    if (alpha.ge(beta)) 
                        break;
                }
            }  
        } 
        else 
        {
            for (Node child : childNodes) 
            {
                //((GameNode) child).calculateHeuristic(color);
                value = (GameNode) Node.min(value, minimaxAux((GameNode) child, depth + 1, alpha, beta, true));

                if(isAlphaBeta) 
                {
                    beta = (GameNode) Node.min(beta, value);

                    if (beta.ge(alpha)) //Changed from alpha.ge(beta)
                        break;
                } 
            }
        }

        return value;
        
    }

}