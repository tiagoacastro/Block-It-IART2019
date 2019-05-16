package game;

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
        new BlockIt();
    }

    public BlockIt()
    {
        Player.setMaxBarriers(5);
        GameBoard.setBoardSize(17);
        scanner = new Scanner(System.in);
        players = new ArrayList<Player>();
        currentPlayer = 0;

        mainMenu();
    }

    public static void play()
    {
        Player.getBoard().printBoard();
        
        do
        {
            currentPlayer = 0;

            for(; currentPlayer < players.size(); currentPlayer++)
            {
                Player player = players.get(currentPlayer);

                System.out.println(player.getName() + " Player's turn");
    
                if(player.getDifficulty() == 1) //Human
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
                            System.out.println("Type the coordinates of the central piece of the barrier in the format x,y,d (no spaces), with d being the direction (h for horizontal and v for vertical)");
                            
                            do
                            {
                                validPlay = player.useBarrier(scanner.nextLine());
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

        for(Player player: players)
            charBoard[player.getPosition()[0]][player.getPosition()[1]] = player.getColor();

        if(Player.getNode() == null)
            Player.setNode(new GameNode(null, 0, 0, "root", 1, new GameBoard(charBoard)));
        else
            Player.setBoard(new GameBoard(charBoard));
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
        int option, playerCounter = 0;
        String colors[] = {"Red", "Green", "Blue", "Yellow"};

        do
        {
            players.clear();
            players.trimToSize();

            for(int i = 1; i <= 4; i++)
            {
                System.out.println
                (
                    "+---------------+\n"
                    + "| " + colors[i - 1] + " Player |\n"
                    + "+---------------+\n"
                    + "| 1 - None      |\n"
                    + "+---------------+\n"
                    + "| 2 - Human     |\n"
                    + "+---------------+\n"
                    + "| 3 - Easy      |\n"
                    + "+---------------+\n"
                    + "| 4 - Normal    |\n"
                    + "+---------------+\n"
                    + "| 5 - Hard      |\n"
                    + "+---------------+\n"
                );
    
                option = getOption(5);
    
                if(option > 1)
                {
                    switch(i)
                    {
                        case 1:
                            players.add(new Player(option - 1, new int[]{0, (GameBoard.getBoardSize() - 1) / 2}, 'R'));
                            break;

                        case 2:
                            players.add(new Player(option - 1, 
                                new int[]{(GameBoard.getBoardSize() - 1) / 2,  GameBoard.getBoardSize() - 1}, 'G'));
                            break;

                        case 3:
                            players.add(new Player(option - 1, 
                                new int[]{ GameBoard.getBoardSize() - 1, ( GameBoard.getBoardSize() - 1) / 2}, 'B'));
                            break;

                        case 4:
                            players.add(new Player(option - 1, new int[]{( GameBoard.getBoardSize() - 1) / 2, 0} ,'Y'));
                            break;
                    }

                    playerCounter++;
                }
            }

            if(playerCounter <= 1)
                System.out.println("There must be at least two players");
        }
        while(playerCounter <= 1);
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
            System.out.println("Invalid input");
            return 0;
        }


        while(option < 1 && option > maxValue)
        {
            System.out.println("Invalid option.\nPlease try again");
            
            try
            {
                option = scanner.nextInt();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Invalid input");
                return 0;
            }
        }

        return option;
    }

    public static Player getCurrentPlayer()
    {
        return players.get(currentPlayer);
    }

    public static ArrayList<Player> getPlayers()
    {
        return players;
    }
}