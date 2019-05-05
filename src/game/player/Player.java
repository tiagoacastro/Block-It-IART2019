package game.player;

import game.GameBoard;

public abstract class Player
{
    protected static int MAX_BARRIERS;
    protected static GameBoard board;

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
                newBoard = board.moveUp(position);
                break;

            case "down":
                newBoard = board.moveDown(position);
                break;

            case "left":    
                newBoard = board.moveLeft(position);
                break;

            case "right":
                newBoard = board.moveRight(position);
                break;

            default:
                System.out.println("Unknown move: " + move);
                return false;
        }

        if(newBoard != null)
        {
            board = newBoard;
            return true;
        }
        else
            return false;
    }

    public void useBarrier()
    {
        barriers--;
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
        return board;
    }

    public static void setMaxBarriers(int maxBarriers)
    {
        MAX_BARRIERS = maxBarriers;
    }

    public static void setBoard(GameBoard gameBoard)
    {
        board = gameBoard;
    }
}