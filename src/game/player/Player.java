package game.player;

import game.GameBoard;
import game.GameNode;

public abstract class Player
{
    protected static int MAX_BARRIERS;
    protected static GameNode node;

    protected int[] position;
    protected int barriers;
    protected int difficulty;
    protected char color;

    public Player(int difficulty)
    {
        this.position = new int[2];
        this.barriers = MAX_BARRIERS;
        this.difficulty = difficulty;
    }

    public abstract boolean isWinner();
    public abstract int getDistanceToBorder();

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

    public abstract boolean useBarrier(String coords);

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
}