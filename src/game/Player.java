package game;

import game.GameBoard;
import game.node.*;
import java.util.PriorityQueue;

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
        for(int i = 0; i < Node.getSolution().size(); i++)
            System.out.println(Node.getSolution().get(i));

        node = minimax(node, 0, true);
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

    public boolean useBarrier(String coords)
    {
        String[] params = coords.split(",");

        if(params.length != 3)
            return false;

        GameBoard newBoard = node.getGameBoard().placeBarrier(Integer.parseInt(params[0]), 
            Integer.parseInt(params[1]), params[2].charAt(0));

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

    private GameNode minimaxAux(GameNode node, int depth, GameNode alpha, GameNode beta, boolean maximizingPlayer) {

        GameNode value = null;
        boolean isAlphaBeta = (alpha != null && beta != null);

        if (depth == Node.MAX_SEARCH_DEPTH || node.isTerminal() ) { // todo check search depth and isTerminal
            node.calculateHeuristic(node.getGameBoard(), color);
            return node;
        }

        if (maximizingPlayer) {
            ArrayList<Node> childNodes = node.expandNodeWithBarrier(this); 
            for (Node child : childNodes) {
                value = (GameNode) child.max(value, minimax((GameNode) child, depth+1, false));
                if (isAlphaBeta) {
                    alpha = (GameNode) child.max(alpha, value);
                    if (alpha.ge(beta)) {
                        break;
                    }
                }
            }
            return value;
        } else {
            ArrayList<Node> childNodes = node.expandNodeWithBarrier(BlockIt.getNextPlayer(this)); 
            for (Node child : childNodes) {
                value = (GameNode) child.min(value, minimax((GameNode) child, depth+1, true));
                if(isAlphaBeta) {
                    beta = (GameNode) child.min(beta, value);
                    if (alpha.ge(beta)) {
                        break;
                    }
                } 
            }  
            return value;
        }
        
    }

}