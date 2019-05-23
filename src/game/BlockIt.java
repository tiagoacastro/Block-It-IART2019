package game;

import java.util.Arrays;
import java.util.Scanner;

import game.node.GameNode;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class BlockIt 
{
    private static ArrayList<Player> players;
    private static Scanner scanner;
    private static int currentPlayer;

    public static void main(String[] args) throws Exception 
    {
        new BlockIt(false);
    }

    public BlockIt(boolean debug)
    {
        Player.setMaxBarriers(5);
        GameBoard.setBoardSize(17);
        scanner = new Scanner(System.in);
        players = new ArrayList<Player>();
        currentPlayer = 0;

        if(!debug)
            mainMenu();
    }

    public static void play()
    {
        Player.getBoard().printBoard();

        for(Player player: players)
            player.setPlayerNodeBoard(Player.getBoard());
        
        do
        {
            currentPlayer = 0;

            for(; currentPlayer < players.size(); currentPlayer++)
            {
                if(currentPlayer >= players.size())
                    currentPlayer = 0;

                Player player = players.get(currentPlayer);

                //Player.getGameNode().setHeuristic(player.getNewHeuristic());

                System.out.println(player.getName() + " Player's turn\n");
    
                if(!player.isBot()) //Human
                {
                    int option;
    
                    System.out.println
                    (
                        "+----------+-------------------+\n" +
                        "| 1 - Move | 2 - Place Barrier |\n" +
                        "+----------+-------------------+\n"
                    );
    
                    option = getOption(2);
    
                    boolean validPlay;
    
                    switch(option)
                    {
                        case 1:
                            System.out.println("Type the direction in which you wish to move (up, down, left or right)");
                            scanner.nextLine();
                            
                            do
                            {
                                validPlay = player.move(scanner.nextLine());
                            }
                            while(!validPlay);
    
                            break;
    
                        case 2:
                            
                            int x, y;
                            char direction;

                            do
                            {
                                do
                                {
                                    System.out.println("Type the X coordinate of the barrier's central piece");

                                    try
                                    {
                                        x = scanner.nextInt();
                                        break;
                                    }
                                    catch(InputMismatchException e)
                                    {
                                        System.out.println("Invalid Input");
                                        continue;
                                    }
                                }
                                while(true);

                                do
                                {
                                    System.out.println("Type the Y coordinate of the barrier's central piece");

                                    try
                                    {
                                        y = scanner.nextInt();
                                        break;
                                    }
                                    catch(InputMismatchException e)
                                    {
                                        System.out.println("Invalid Input");
                                        continue;
                                    }
                                }
                                while(true);

                                do
                                {
                                    System.out.println("Type the direction of the barrier ('v' for vertical or 'h' for horizontal)");

                                    scanner.nextLine(); //To flush I guess...

                                    String line = scanner.nextLine();

                                    if(line.length() > 1)
                                    {
                                        System.out.println("Invalid Input");
                                        continue;
                                    }
                                        
                                    direction = line.charAt(0);
                                    break;
                                }
                                while(true);
                                
                                validPlay = player.useBarrier(x, y, direction);
                            }
                            while(!validPlay);       
                    }
                }
                else //Bot
                    player.play();
    
                Player.getBoard().printBoard();

                if(player.isWinner())
                {
                    System.out.println("\n" + player.getName() + "Player's Victory!");
                    return;
                }
            }
        }
        while(true);
    }

    public static void buildBoard()
    {
        int boardSize = GameBoard.getBoardSize();
        char[][] charBoard = new char[boardSize][boardSize];

        for(int i = 0; i < boardSize; i++)
            for(int j = 0; j < boardSize; j++)
                if(i % 2 != 0)
                    charBoard[i][j] = ' ';
                else
                    if(j % 2 == 0)
                        charBoard[i][j] = '_';
                    else
                        charBoard[i][j] = ' ';

        int[][] pos = new int[4][2];
        for(int[] p : pos)
            Arrays.fill(p, -1);

        for(Player player: players) {
            charBoard[player.getPosition()[0]][player.getPosition()[1]] = player.getColor();
            switch(player.getColor()){
                case 'R':
                    pos[0][0] = player.getPosition()[0];
                    pos[0][1] = player.getPosition()[1];
                    break;
                case 'G':
                    pos[1][0] = player.getPosition()[0];
                    pos[1][1] = player.getPosition()[1];
                    break;
                case 'B':
                    pos[2][0] = player.getPosition()[0];
                    pos[2][1] = player.getPosition()[1];
                    break;
                case 'Y':
                    pos[3][0] = player.getPosition()[0];
                    pos[3][1] = player.getPosition()[1];
                    break;
            }
        }

        if(Player.getGameNode() == null)
            Player.setGameNode(new GameNode(null, 0, 0, "root", null, new GameBoard(charBoard, pos)));
        else
            Player.setBoard(new GameBoard(charBoard, pos));
    }

    public static void mainMenu()
    {
        int option;

        do
        {
            System.out.println
            (
                "+--------------+\n" + 
                "|   Block It   |\n" + 
                "+--------------+\n" + 
                "| 1 - Play     |\n" + 
                "+--------------+\n" + 
                "| 2 - Settings |\n" +
                "+--------------+\n" +
                "| 3 - Rules    |\n" +
                "+--------------+\n" +
                "| 4 - Exit     |\n" +
                "+--------------+\n"
            );
    
            option = getOption(4);

            switch(option)
            {
                case 1:
                    playMenu();
                    buildBoard();
                    play();
                    break;

                case 2:
                    settingsMenu();
                    break;

                case 3:
                    displayRules();
            }
        }
        while(option != 4);

        scanner.close();
    }

    public static void playMenu()
    {
        int colorOption, option;
        boolean alreadyTaken;
        char[] colors = new char[] {'R', 'G', 'B', 'Y'};

        players.clear();
        players.trimToSize();

        for(int i = 0; i < 2; i++)
        {
            do
            {
                alreadyTaken = false;

                System.out.println
                (
                    "+------------+\n"
                    + "|  Player " + (i + 1) + "  |\n"
                    + "+------------+\n"
                    + "| 1 - Red    |\n"
                    + "+------------+\n"
                    + "| 2 - Green  |\n"
                    + "+------------+\n"
                    + "| 3 - Blue   |\n"
                    + "+------------+\n"
                    + "| 4 - Yellow |\n"
                    + "+------------+\n"
                );
    
                colorOption = getOption(4);

                for(Player player: players)
                {
                    if(player.getColor() == colors[colorOption - 1])
                    {
                        alreadyTaken = true;
                        System.out.println("That color has already been chosen");
                        break;
                    }
                }
                    
            }
            while(alreadyTaken);

            System.out.println
            (
                "+---------------+\n"
                + "|   Difficulty  |\n"
                + "+---------------+\n"
                + "| 1 - Human     |\n"
                + "+---------------+\n"
                + "| 2 - Easy      |\n"
                + "+---------------+\n"
                + "| 3 - Normal    |\n"
                + "+---------------+\n"
                + "| 4 - Hard      |\n"
                + "+---------------+\n"
            );

            option = getOption(4);

            players.add(new Player(option, colors[colorOption - 1]));
        }
    }

    public static void settingsMenu()
    {
        int option;

        System.out.println
        (
            "+--------------------------------+\n" 
            + "|            Settings            |\n"
            + "+--------------------------------+\n"
            + "| 1 - Set Max Number of Barriers |\n"
            + "+--------------------------------+\n"
            + "| 2 - Set Map Size               |\n"
            + "+--------------------------------+\n"
            + "| 3 - Go Back                    |\n"
            + "+--------------------------------+\n"
        );

        option = getOption(3);

        switch(option)
        {
            case 1:
                setMaxNumberOfBarriers();
                break;

            case 2:
                setMapSize();
        }
    }

    public static void setMapSize()
    {
        int option;

        System.out.println
        (
            "+-------------+\n"
            + "|   Map Size  |\n"
            + "+-------------+\n"
            + "| 1 - Small   |\n"
            + "+-------------+\n"
            + "| 2 - Normal  |\n"
            + "+-------------+\n"
            + "| 3 - Big     |\n"
            + "+-------------+\n"
            + "| 4 - Go Back |\n"
            + "+-------------+\n"
        );

        option = getOption(4);

        switch(option)
        {
            case 1:
                GameBoard.setBoardSize(13);
                break;

            case 2:
                GameBoard.setBoardSize(17);
                break;

            case 3:
                GameBoard.setBoardSize(21);
        }
    }

    public static void setMaxNumberOfBarriers()
    {
        int option, maxBarriers;

        System.out.println
        (
            "+------------------------+\n"
            + "| Max Number of Barriers |\n"
            + "+------------------------+\n"
            + "| 1 - Few                |\n"
            + "+------------------------+\n"
            + "| 2 - Normal             |\n"
            + "+------------------------+\n"
            + "| 3 - Many               |\n"
            + "+------------------------+\n"
            + "| 4 - Go Back            |\n"
            + "+------------------------+\n"
        );

        option = getOption(4);

        switch(option)
        {
            case 1:
                maxBarriers = 3;
                break;

            case 2:
                maxBarriers = 5;
                break;

            case 3:
                maxBarriers = 6;

            default:
                return;
        }

        if(GameBoard.getBoardSize() != 17)
        {
            if(GameBoard.getBoardSize() == 13)
                maxBarriers -= 2;
            else
                if(GameBoard.getBoardSize() == 21)
                    maxBarriers++;
        }

        if(maxBarriers == 1)
            maxBarriers = 2;

        Player.setMaxBarriers(maxBarriers);
    }

    public static void displayRules()
    {
        System.out.println
        (
            "- The first player who gets across the board, reaching the respective color's line, wins the game.\n"
            + "- This is a turn based game. At each turn you have two options: 'Move' or 'Put a barrier'.\n"
            + "- On every turn you'll always have a move available. If another player is blocking your only way to move, you can pass through him.\n"
            + "- The player can place a barrier only if he has any available.\n"
        );
    }

    /**
     * Gets an option from the user and tests its validity.
     * @param maxValue The maximum number of options allowed in this instance.
     * @return The option chosen.
     */
    private static int getOption(int maxValue)
    {
        int option;

        try
        {
            option = scanner.nextInt();
        }
        catch(InputMismatchException e)
        {
            option = 0;
        }

        while(option < 1 || option > maxValue)
        {
            System.out.println("Invalid option.\nPlease try again");
            
            try
            {
                option = scanner.nextInt();
            }
            catch(InputMismatchException e)
            {
                option = 0;
            }
        }

        return option;
    }

    public static Player getCurrentPlayer()
    {
        return players.get(currentPlayer);
    }

    public static int getPlayerIndex()
    {
        return currentPlayer;
    }

    public static ArrayList<Player> getPlayers()
    {
        return players;
    }

    public static Player getNextPlayer() {
        int nextPlayer;
        return ((nextPlayer = currentPlayer + 1) >= players.size() ? players.get(0) : players.get(nextPlayer));
    }

    public static Player getPlayerAfter(char color)
    {
        for(int i = 0; i < players.size(); i++)
            if(players.get(i).getColor() == color)
            {
                if(i == players.size() - 1)
                    return players.get(0);
                else
                    return players.get(i + 1);
            }

        return null;
    }

    public static void setPlayers(ArrayList<Player> list)
    {
        players = list;
    }
}