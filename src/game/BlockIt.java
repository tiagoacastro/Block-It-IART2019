package game;

import java.util.Scanner;

import game.player.*;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class BlockIt 
{
    private static  ArrayList<Player> players;

    public static void main(String[] args) throws Exception 
    {
        Player.setMaxBarriers(5);
        GameBoard.setBoardSize(17);
        players = new ArrayList<Player>();

        mainMenu();
    }

    public static void play()
    {
        for(Player player: players)
        {
            System.out.println(player.getColor() + " Player's turn");

            if(player.getDifficulty() == 1) //Human
            {
                
            }
            else
            {
                
            }
        }

        Player.getBoard().printBoard();



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
                            players.add(new RedPlayer(option - 1));
                            break;

                        case 2:
                            players.add(new GreenPlayer(option - 1));
                            break;

                        case 3:
                            players.add(new BluePlayer(option - 1));
                            break;

                        case 4:
                            players.add(new YellowPlayer(option - 1));
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
        Scanner scanner = new Scanner(System.in);
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
}