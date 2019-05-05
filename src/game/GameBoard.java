package game;

import java.util.concurrent.TimeUnit;
/**
 * Contains the actual playing board (matrix) and performs the moves and tests on it.
 */
public class GameBoard
{
    private static int BOARD_SIZE;
    /**
     * The actual board.
     */
    private char[][] board;

    /**
     * Flag indicating if the moves performed are to be displayed.
     */
    private static boolean showMove = false;

    /**
     * Constructor of the class.
     * @param board The board matrix.
     * @param maxMoves The maximum number of moves allowed.
     * @param optimalMoves The optimal number of moves.
     */
    public GameBoard(char[][] board)
    {
        this.board = board;
    }

    /**
     * Moves a player to the right if possible.
     * @param coords The coordinates of the piece to be moved.
     * @return A new GameBoard with the move performed or null if it was invalid.
     */
    public GameBoard moveRight(int[] coords)
    {
        char[][] newBoard = copyBoard(this.board);

        if (!validateMoveRight(coords)) 
        {
            System.out.println("Can't move right: (" + coords[0] + "," + coords[1] + ")");
            return null;
        } 
        else 
        {
            int[] newcoords = coords.clone();

            newcoords[1] += 1;

            newBoard[coords[0]][coords[1]] = '_'; // Old position
            newBoard[newcoords[0]][newcoords[1]] = board[coords[0]][coords[1]]; // Switch place

            if(showMove)
                printBoardHighlightPiece(newBoard, newcoords);       
        }

        return new GameBoard(newBoard);
    }

    /**
     * Tests if a player can be moved to the right.
     * @param coords The piece coordinates.
     * @return True if the move is valid or false otherwise.
     */
    public boolean validateMoveRight(int[] coords)
    {
        return coords[1] < board[0].length - 1 && this.board[coords[0]][coords[1] + 2] == '_';
    }

    /**
     * Moves a player to the left if possible.
     * @param coords The coordinates of the piece to be moved.
     * @return A new GameBoard with the move performed or null if it was invalid.
     */
    public GameBoard moveLeft(int[] coords)
    {
        char[][] newBoard = copyBoard(this.board);

        if(!validateMoveLeft(coords))
        {
            System.out.println("Can't move left: (" + coords[0] + "," + coords[1] + ")");
            return null;
        }
        else
        {
            int[] newcoords = coords.clone();
            newcoords[1] -= 1;

            newBoard[coords[0]][coords[1]] = '_'; //Old position
            newBoard[newcoords[0]][newcoords[1]] = board[coords[0]][coords[1]]; //Switch place

            if(showMove)
                printBoardHighlightPiece(newBoard, newcoords);               
        }

        return new GameBoard(newBoard);
    }

    /**
     * Tests if a player can be moved to the left.
     * @param coords The piece coordinates.
     * @return True if the move is valid or false otherwise.
     */
    public boolean validateMoveLeft(int[] coords)
    {
        return coords[1] > 0 && this.board[coords[0]][coords[1] - 2] == '_';
    }

    /**
     * Moves a player up if possible.
     * @param coords The coordinates of the piece to be moved.
     * @return A new GameBoard with the move performed or null if it was invalid.
     */
    public GameBoard moveUp(int[] coords)
    {
        char[][] newBoard = copyBoard(this.board);

        if(!validateMoveUp(coords))
        {
            System.out.println("Can't move up: (" + coords[0] + "," + coords[1] + ")");
            return null;
        }
        else
        {
            int[] newcoords = coords.clone();
            newcoords[0] -= 1;

            newBoard[coords[0]][coords[1]] = '_'; //Old position
            newBoard[newcoords[0]][newcoords[1]] = board[coords[0]][coords[1]]; //Switch place

            if(showMove)
                printBoardHighlightPiece(newBoard, newcoords);               
        }

        return new GameBoard(newBoard);
    }

    /**
     * Tests if a player can be moved up.
     * @param coords The piece coordinates.
     * @return True if the move is valid or false otherwise.
     */
    public boolean validateMoveUp(int[] coords)
    {
        return coords[0] > 0 && this.board[coords[0] - 2][coords[1]] == '_';
    }

    /**
     * Moves a player down if possible.
     * @param coords The coordinates of the piece to be moved.
     * @return A new GameBoard with the move performed or null if it was invalid.
     */
    public GameBoard moveDown(int[] coords)
    {
        char[][] newBoard = copyBoard(this.board);

        if(!validateMoveDown(coords))
        {
            System.out.println("Can't move down: (" + coords[0] + "," + coords[1] + ")");
            return null;
        }
        else
        {
            int[] newcoords = coords.clone();
            newcoords[0] += 1;

            newBoard[coords[0]][coords[1]] = '_'; //Old position
            newBoard[newcoords[0]][newcoords[1]] = board[coords[0]][coords[1]]; //Switch place

            if(showMove)
                printBoardHighlightPiece(newBoard, newcoords);               
        }

        return new GameBoard(newBoard);
    }

    /**
     * Tests if a player can be moved up.
     * @param coords The piece coordinates.
     * @return True if the move is valid or false otherwise.
     */
    public boolean validateMoveDown(int[] coords)
    {
        return coords[0] < this.board.length && this.board[coords[0] + 2][coords[1]] == '_';
    }

    /**
     * Tests if a set of coordinates evaluates to an actual piece.
     * @param coords The piece's coordinates.
     * @return True if the coordinates don't correspond to a piece and false otherwise.
     */
    public boolean testCoords(int[] coords)
    {
        return coords[0] >= this.board.length || coords[1] >= this.board[0].length
        || coords[0] < 0 || coords[1] < 0 || this.board[coords[0]][coords[1]] == '_';
    }

    /**
     * Copies and returns a board (2D matrix of chars).
     * @param board The board to be copied.
     * @return The cloned board.
     */
    private char[][] copyBoard(char[][] board)
    {
        char newBoard[][] = new char[board.length][board[0].length];

        for(int i = 0; i < board.length; i++)
            newBoard[i] = board[i].clone();

        return newBoard;
    }

    /**
     * Retrieves a copy of the current board.
     * @return A copy of the current board.
     */
    public char[][] getBoard()
    {
        return copyBoard(board);
    }

    /**
     * Sets the show move flag.
     * @param showMove The new value of the show move flag.
     */
    public static void setShowMove(boolean showMove)
    {
        GameBoard.showMove = showMove;
    }

    /**
     * Prints the current board.
     */
    public void printBoard()
    {
        printBoard(board);
    }

    /**
     * Prints a board.
     * @param board The board to be printed.
     */
    private void printBoard(char[][] board)
    {
        System.out.print(" ");

        for(int i = 0; i < GameBoard.getBoardSize(); i++)
            System.out.print("|" + "b");

        System.out.println('|');

        for(char[] line : board)
        {
            System.out.print("g" + "|");

            for(char cell : line) 
            {
                System.out.print(cell);
                
                System.out.print('|');
            }

            System.out.println("y");
        }

        System.out.print(" ");

        for(int i = 0; i < GameBoard.getBoardSize(); i++)
            System.out.print("|" + "b");

        System.out.println('|');

        try
        {
            TimeUnit.SECONDS.sleep(2);
        }
        catch(Exception e)
        {
            System.out.println("Error in sleep in print board");
        }

        System.out.print("\n");
    }

    /**
     * Prints a board while highlighting a piece.
     * @param board The board to print.
     * @param coords The piece's coordinates.
     */
    private void printBoardHighlightPiece(char[][] board, int[] coords)
    {
        for(int i = 0; i < board.length; i++)
        {
            System.out.print("|");

            for(int j = 0; j < board[i].length; j++)
            {
                if(i == coords[0] && j == coords[1])
                    System.out.print(Character.toUpperCase(board[i][j]));
                else
                    System.out.print(board[i][j]);

                System.out.print('|');
            }

            System.out.print("\n");
        }

        System.out.print("\n");
            
    }

    /**
     * Tests if a set of coordinates evaluates to an actual piece.
     * @param coords The piece's coordinates.
     * @return True if the coordinates don't correspond to a piece and false otherwise.
     */
    public boolean testcoords(int[] coords)
    {
        return coords[0] >= this.board.length || coords[1] >= this.board[0].length
        || coords[0] < 0 || coords[1] < 0 || this.board[coords[0]][coords[1]] == '_';
    }
    
    /**
     * Returns the number of blocks in a board.
     * @param board The board in question.
     * @return The number of blocks left in the board.
     */
    public static int getBlocksLeft(char[][] board)
    {
        int count = 0;

        for(char[] line : board)
        {
            for(char cell : line)
            {
                if(cell != '_')
                    count++;
            } 
        }

        return count;
    }

    public boolean isEqual(GameBoard b) {

        for (int i = 0; i < 12; i++) {

            for (int j = 0; j < 7; j++) {
                if (b.board[i][j] != this.board[i][j])
                    return false;
            }

        }

        return true;
    }

    public static int getBoardSize()
    {
        return BOARD_SIZE;
    }

    public static void setBoardSize(int boardSize)
    {
        BOARD_SIZE = boardSize;
    }
}