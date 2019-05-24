package game;

import game.GameBoard;
import game.node.GameNode;
import game.node.PlayerNode;

public class Player
{
    private static int MAX_BARRIERS;
    private static GameNode gameNode;
    private PlayerNode playerNode;

    public Player(int difficulty, int[] position, char color)
    {
        playerNode = new PlayerNode(null, 0, 1, "root", gameNode.getGameBoard().cloneGameBoard(), 
           difficulty, null, position, color, MAX_BARRIERS);        
    }

    public Player(int difficulty, char color)
    {
        int[] position = new int[2];

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

        playerNode = new PlayerNode(null, 0, 1, "root", null, difficulty, null, position, color, MAX_BARRIERS);
    }

    //Bot function
    public void play(boolean test)
    {
        String op = playerNode.minimax(0, true);

        String[] operatorParams = op.split(" ");

        if(operatorParams.length != 4)
        {
            System.out.println("Invalid operator obtained by minimax");
            return;
        }

        if(operatorParams[0].equals("move"))
            move(operatorParams[1]);
        else
            useBarrier(Integer.parseInt(operatorParams[3]), Integer.parseInt(operatorParams[2]), operatorParams[1].charAt(0));

            if(!test)
                System.out.println("\nChosen: " + op + "\n");
    }

    public boolean move(String move)
    {
        GameBoard newBoard;
        int[] newPosition = playerNode.getPosition().clone();

        switch(move)
        {
            case "up":
                newBoard = gameNode.getGameBoard().moveUp(playerNode.getPosition());
                newPosition[0] -= 2;
                break;

            case "down":
                newBoard = gameNode.getGameBoard().moveDown(playerNode.getPosition());
                newPosition[0] += 2;
                break;

            case "left":    
                newBoard = gameNode.getGameBoard().moveLeft(playerNode.getPosition());
                newPosition[1] -= 2;
                break;

            case "right":
                newBoard = gameNode.getGameBoard().moveRight(playerNode.getPosition());
                newPosition[1] += 2;
                break;

            default:
                System.out.println("Invalid move: " + move);
                return false;
        }

        if(newBoard != null)
        {
            gameNode.setGameBoard(newBoard);
            playerNode.setPosition(newPosition);
            playerNode.setGameBoard(newBoard.cloneGameBoard());
            BlockIt.getNextPlayer().getPlayerNode().setGameBoard(newBoard.cloneGameBoard());
            return true;
        }
        else
            return false;
    }

    public boolean useBarrier(int x, int y, char direction)
    {
        GameBoard newBoard = gameNode.getGameBoard().placeBarrier(x, y, direction);

        if(newBoard != null)
        {
            gameNode.setGameBoard(newBoard);
            playerNode.setGameBoard(newBoard.cloneGameBoard());
            BlockIt.getNextPlayer().getPlayerNode().setGameBoard(newBoard.cloneGameBoard());
            playerNode.useBarrier();
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
        return playerNode.isWinner(playerNode.getColor());
    }

    public String getName()
    {
        switch(playerNode.getColor())
        {
            case 'R':
                return "Red ";
                
            case 'Y':
                return "Yellow ";

            case 'B':
                return "Blue ";

            case 'G':
                return "Green ";

            default:
                return "Unknown ";
        }
    }

    public boolean equals(Player p)
    {
        return p.getColor() == playerNode.getColor();
    }

    public int getBarriers()
    {
        return playerNode.getBarriers();
    }

    public int getDifficulty()
    {
        return playerNode.getDifficulty();
    }

    public int[] getPosition()
    {
        return playerNode.getPosition();
    }

    public static int getMaxBarriers()
    {
        return MAX_BARRIERS;
    }

    public char getColor()
    {
        return playerNode.getColor();
    }

    public static GameBoard getBoard()
    {
        return gameNode.getGameBoard();
    }

    public GameBoard getPlayerBoard()
    {
        return playerNode.getGameBoard();
    }

    public static GameNode getGameNode()
    {
        return gameNode;
    }

    public PlayerNode getPlayerNode()
    {
        return playerNode;
    }

    public static void setMaxBarriers(int maxBarriers)
    {
        MAX_BARRIERS = maxBarriers;
    }

    public static void setGameNode(GameNode node)
    {
        Player.gameNode = node;
    }

    public static void setBoard(GameBoard gameBoard)
    {
        gameNode.setGameBoard(gameBoard);
    }

    public void setPlayerNodeBoard(GameBoard gameBoard)
    {
        playerNode.setGameBoard(gameBoard.cloneGameBoard());
    }

}