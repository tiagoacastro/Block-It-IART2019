package game;

import game.GameBoard;
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

    public Player(int difficulty, int[] postion, char color)
    {
        this.position = postion;
        this.barriers = MAX_BARRIERS;
        this.difficulty = difficulty;
        this.color = color;
    }

    //Bot function
    public void play()
    {

    }

    public boolean move(String move)
    {
        GameBoard newBoard;

        switch(move)
        {
            case "up":
                newBoard = node.getGameBoard().moveUp(position);
                break;

            case "down":
                newBoard = node.getGameBoard().moveDown(position);
                break;

            case "left":    
                newBoard = node.getGameBoard().moveLeft(position);
                break;

            case "right":
                newBoard = node.getGameBoard().moveRight(position);
                break;

            default:
                System.out.println("Invalid move: " + move);
                return false;
        }

        if(newBoard != null)
        {
            node.setGameBoard(newBoard);
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
        switch(color)
        {
            case 'R':
                return position[0] == GameBoard.getBoardSize() - 1;

            case 'B':
                return position[0] == 0;

            case 'G':
                return position[1] == 0;

            case 'Y':
                return position[1] == GameBoard.getBoardSize() - 1;

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

    // Algorithms: pseudo-code

    /*
    function minimax(node, depth, maximizingPlayer) is
    if depth = 0 or node is a terminal node then
        return the heuristic value of node
    if maximizingPlayer then
        value := −∞
        for each child of node do
            value := max(value, minimax(child, depth − 1, FALSE))
        return value
    else (* minimizing player *)
        value := +∞
        for each child of node do
            value := min(value, minimax(child, depth − 1, TRUE))
        return value
    */

    public int minimax(GameNode node, int depth, boolean maximazingPlayer) {
        if (depth == 0 || node.isTerminal()) {
            //return the heuristic value of node
        }

        if (maximazingPlayer) {
            //value = minInfinity
            ArrayList<Node> childNodes = node.expandNode();
            for (Node child : childNodes) {
                //value = max(value, minimax(child, depth − 1, false))
            }
            //return value;
        } else {
            //value = maxInfinity
            ArrayList<Node> childNodes = node.expandNode();
            for (Node child : childNodes) {
                //value = min(value, minimax(child, depth − 1, false))
            }  
            //return value;
        }
        return 1;
    }
}