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
     * Moves a piece to the right if possible.
     * @param pieceCoords The coordinates of the piece to be moved.
     * @return A new GameBoard with the move performed or null if it was invalid.
     */
    public GameBoard movePieceRight(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board);
        char[][] testBoard;

        if (!validateMoveRight(pieceCoords)) 
        {
            System.out.println("Can't move right: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        } 
        else 
        {
            int[] newPieceCoords = pieceCoords.clone();

            newPieceCoords[1] += 1;

            newBoard[pieceCoords[0]][pieceCoords[1]] = '_'; // Old position
            newBoard[newPieceCoords[0]][newPieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; // Switch place

            if(showMove)
                printBoardHighlightPiece(newBoard, newPieceCoords);

            // Make piece in question fall if that's the case
            if (newPieceCoords[0] < this.board.length - 1 && this.board[newPieceCoords[0] + 1][newPieceCoords[1]] == '_')
            {
                newBoard = dropPiece(newBoard, newPieceCoords);
                
                if(showMove)
                    printBoard(newBoard);
            }
                
            // Make the other pieces above the original one fall if they exist
            if (pieceCoords[0] > 0 && newBoard[pieceCoords[0] - 1][pieceCoords[1]] != '_')
            {
                newBoard = dropColumn(newBoard, pieceCoords[1]); 

                if(showMove)
                    printBoard(newBoard);
            } 
              
            testBoard = explodeAll(newBoard);

            if(testBoard != null)
            {
                newBoard = testBoard;
            }                
        }

        return new GameBoard(newBoard);
    }

    /**
     * Tests if a piece can be moved to the right.
     * @param pieceCoords The piece coordinates.
     * @return True if the move is valid or false otherwise.
     */
    public boolean validateMoveRight(int[] pieceCoords)
    {
        return pieceCoords[1] != board[0].length - 1 && this.board[pieceCoords[0]][pieceCoords[1] + 1] == '_';
    }

    /**
     * Moves a piece to the left if possible.
     * @param pieceCoords The coordinates of the piece to be moved.
     * @return A new GameBoard with the move performed or null if it was invalid.
     */
    public GameBoard movePieceLeft(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board), testBoard;

        if(!validateMoveLeft(pieceCoords))
        {
            System.out.println("Can't move left: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        }
        else
        {
            int[] newPieceCoords = pieceCoords.clone();
            newPieceCoords[1] -= 1;

            newBoard[pieceCoords[0]][pieceCoords[1]] = '_'; //Old position
            newBoard[newPieceCoords[0]][newPieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; //Switch place

            if(showMove)
                printBoardHighlightPiece(newBoard, newPieceCoords);

            //Make piece in question fall if that's the case
            if(newPieceCoords[0] < this.board.length - 1 && this.board[newPieceCoords[0] + 1][newPieceCoords[1]] == '_')
            {
                newBoard = dropPiece(newBoard, newPieceCoords);
                
                if(showMove)
                    printBoard(newBoard);
            }
        
            //Make the other pieces above the original one fall if they exist
            if(pieceCoords[0] > 0 && newBoard[pieceCoords[0] - 1][pieceCoords[1]] != '_')
            {
                newBoard = dropColumn(newBoard, pieceCoords[1]);

                if(showMove)
                    printBoard(newBoard);
            }    

            testBoard = explodeAll(newBoard);

            if(testBoard != null)
            {
                newBoard = testBoard;

            } 
               
        }

        return new GameBoard(newBoard);
    }

    /**
     * Tests if a piece can be moved to the left.
     * @param pieceCoords The piece coordinates.
     * @return True if the move is valid or false otherwise.
     */
    public boolean validateMoveLeft(int[] pieceCoords)
    {
        return pieceCoords[1] != 0 && this.board[pieceCoords[0]][pieceCoords[1] - 1] == '_';
    }

    /**
     * Switches a piece with the one to it's left.
     * @param pieceCoords The coordinates of the piece to be switched.
     * @return A new GameBoard with the move performed or null if it was invalid.
     */
    public GameBoard switchPieceLeft(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board), testBoard;

        if(!validateSwitchLeft(pieceCoords))
        {
            System.out.println("Can't switch left: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        }
        else
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0]][pieceCoords[1] - 1])
            {
                if(showMove)
                    printBoardHighlightPiece(newBoard, new int[] {pieceCoords[0], pieceCoords[1] - 1});

                return new GameBoard(newBoard);
            }
                

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] - 1]; 
            newBoard[pieceCoords[0]][pieceCoords[1] - 1] = board[pieceCoords[0]][pieceCoords[1]];
            
            if(showMove)
                printBoardHighlightPiece(newBoard, new int[] {pieceCoords[0], pieceCoords[1] - 1});
        }

        testBoard = explodeAll(newBoard);

        if(testBoard != null) 
        {
            newBoard = testBoard;

        }

        return new GameBoard(newBoard);
    }

    /**
     * Tests if a piece can be switched to the left.
     * @param pieceCoords The piece coordinates.
     * @return True if the move is valid or false otherwise.
     */
    public boolean validateSwitchLeft(int[] pieceCoords)
    {
        return pieceCoords[1] != 0 && this.board[pieceCoords[0]][pieceCoords[1] - 1] != '_';
    }

    /**
     * Switches a piece with the one to it's right.
     * @param pieceCoords The coordinates of the piece to be switched.
     * @return A new GameBoard with the move performed or null if it was invalid.
     */
    public GameBoard switchPieceRight(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board), testBoard;

        if(!validateSwitchRight(pieceCoords))
        {
            System.out.println("Can't switch right: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        }
        else
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0]][pieceCoords[1] + 1])
            {
                if(showMove)
                    printBoardHighlightPiece(newBoard, new int[] {pieceCoords[0], pieceCoords[1] + 1});

                return new GameBoard(newBoard);
            }

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] + 1];
            newBoard[pieceCoords[0]][pieceCoords[1] + 1] = board[pieceCoords[0]][pieceCoords[1]];
            
            if(showMove)
                printBoardHighlightPiece(newBoard, new int[] {pieceCoords[0], pieceCoords[1] + 1});
        }

        testBoard = explodeAll(newBoard);

        if(testBoard != null) 
        {
            newBoard = testBoard;

        }

        return new GameBoard(newBoard);
    }

    /**
     * Tests if a piece can be switched to the right.
     * @param pieceCoords The piece coordinates.
     * @return True if the move is valid or false otherwise.
     */
    public boolean validateSwitchRight(int[] pieceCoords)
    {
        return pieceCoords[1] != board[0].length - 1 && this.board[pieceCoords[0]][pieceCoords[1] + 1] != '_';
    }

    /**
     * Switches a piece with the one above it.
     * @param pieceCoords The coordinates of the piece to be switched.
     * @return A new GameBoard with the move performed or null if it was invalid.
     */
    public GameBoard switchPieceUp(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board), testBoard;

        if(!validateSwitchUp(pieceCoords))
        {
            System.out.println("Can't switch up: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        }
        else
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0] - 1][pieceCoords[1]])
            {
                if(showMove)
                    printBoardHighlightPiece(newBoard, new int[] {pieceCoords[0] - 1, pieceCoords[1]});

                return new GameBoard(newBoard);
            }

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0] - 1][pieceCoords[1]];
            newBoard[pieceCoords[0] - 1][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]];
            
            if(showMove)
                printBoardHighlightPiece(newBoard, new int[] {pieceCoords[0] - 1, pieceCoords[1]});
        }

        testBoard = explodeAll(newBoard);
        
        if (testBoard != null) 
        {
            newBoard = testBoard;

        }

        return new GameBoard(newBoard);
    }

    /**
     * Tests if a piece can be switched upwards.
     * @param pieceCoords The piece coordinates.
     * @return True if the move is valid or false otherwise.
     */
    public boolean validateSwitchUp(int[] pieceCoords)
    {
        return pieceCoords[0] != 0 && this.board[pieceCoords[0] - 1][pieceCoords[1]] != '_';
    }

    /**
     * Switches a piece with the one below it.
     * @param pieceCoords The coordinates of the piece to be switched.
     * @return A new GameBoard with the move performed or null if it was invalid.
     */
    public GameBoard switchPieceDown(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board), testBoard;

        if(!validateSwitchDown(pieceCoords))
        {
            System.out.println("Can't switch down: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        }
        else
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0] + 1][pieceCoords[1]])
            {
                if(showMove)
                    printBoardHighlightPiece(newBoard, new int[] {pieceCoords[0] + 1, pieceCoords[1]});

                return new GameBoard(newBoard);
            }

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0] + 1][pieceCoords[1]];
            newBoard[pieceCoords[0] + 1][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]];

            if(showMove)
                printBoardHighlightPiece(newBoard, new int[] {pieceCoords[0] + 1, pieceCoords[1]}); 
        }

        testBoard = explodeAll(newBoard);

        if(testBoard != null) 
        {
            newBoard = testBoard;

        }

        return new GameBoard(newBoard);
    }

    /**
     * Tests if a piece can be switched downwards.
     * @param pieceCoords The piece coordinates.
     * @return True if the move is valid or false otherwise.
     */
    public boolean validateSwitchDown(int[] pieceCoords)
    {
        return pieceCoords[0] != board.length - 1 && this.board[pieceCoords[0] + 1][pieceCoords[1]] != '_';
    }

    /**
     * "Drops" a piece until it hits another piece of the floor.
     * @param board The board in which to drop the piece.
     * @param pieceCoords The piece's coordinates.
     * @return A new board with the piece dropped.
     */
    public char[][] dropPiece(char[][] board, int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(board);
        int[] coords = pieceCoords.clone();
        char piece = newBoard[coords[0]][coords[1]];
        
        newBoard[coords[0]][coords[1]] = '_';

        do
        {
            coords[0] += 1;
        }
        while(coords[0] < board.length - 1 && newBoard[coords[0] + 1][coords[1]] == '_');

        newBoard[coords[0]][coords[1]] = piece;

        return newBoard;
    }

    /**
     * Gets the coordinates of the last dropped piece in a column.
     * @param column The column in which to search.
     * @param board The board containing the column.
     * @return The piece's coordinates if it finds one or the coordinates of the floor at that column.
     */
    public int[] getDroppedPieceCoords(int column, char[][] board)
    {
        int[] droppedPieceCoords = new int[] {board.length - 1, column};

        for(int i = 0; i < board.length; i++)
            if(board[i][column] != '_')
            {
                droppedPieceCoords[0] = i;
                droppedPieceCoords[1] = column;

                return droppedPieceCoords;
            }

        return droppedPieceCoords;
    }

    /**
     * Makes a column act in accordance to gravity, i.e, eliminates any "holes" in it.
     * @param board The board in wich to drop the column.
     * @param column The column to drop.
     * @return A new board with the column dropped.
     */
    public char[][] dropColumn(char[][] board, int column)
    {
        int i;
        char[][] newBoard = copyBoard(board);
        int[] pieceCoords = {0, column};

        for(i = 0; i < board.length; i++)
            if(board[i][column] != '_')
                break;

        for(; i < board.length - 1; i++)
            if(board[i + 1][column] == '_')
            {
                pieceCoords[0] = i;

                while(newBoard[pieceCoords[0]][pieceCoords[1]] != '_')
                {
                    newBoard = dropPiece(newBoard, pieceCoords);
                    pieceCoords[0]--;
                }

                break;
            }
                
        return newBoard;
    }

    /**
     * Analyzes the board for chains of 3 or more cubes and eliminates the blocks in these until there are no 
     * more active chains.
     * @param board The board to analyse.
     * @return The new board free of chains.
     */
    private char[][] explodeAll(char[][] board)
    {
        char[][] newBoard = copyBoard(board), testBoard;
        boolean recalculate = false;
        boolean differentBoard = false;
        
        for(int i = 0; i < board.length; i++)
        {
            testBoard = explodeLine(i, newBoard);

            if(testBoard != null)
            {
                newBoard = copyBoard(testBoard);
                recalculate = true;
                differentBoard = true;

                if(showMove)
                    printBoard(newBoard);
            }

            if(i < newBoard[i].length)
            {
                testBoard = explodeColumn(i, newBoard);

                if(testBoard != null)
                {
                    newBoard = copyBoard(testBoard);
                    recalculate = true;
                    differentBoard = true;

                    if(showMove)
                        printBoard(newBoard);
                }
            }
                
            if(i == board.length - 1 && recalculate)
            {
                recalculate = false;
                i = 0;
            }  
        }

        if(differentBoard)
            return newBoard;
        else
            return null;
    }

    /**
     * Eliminates any chain of 3 or more cubes in a line.
     * @param line The line to analyse.
     * @param board The board containing the line.
     * @return The new board after the line was cleared of chains.
     */
    private char[][] explodeLine(int line, char[][] board)
    {
        char[][] newBoard = copyBoard(board);
        char currentColor = 'X';
        int counter = 1;
        boolean foundPattern = false;

        for(int i = 0; i < newBoard[line].length; i++)
        {
            if(newBoard[line][i] != '_')
            {
                if(newBoard[line][i] == currentColor)
                {
                    counter++;

                    if(counter == 3)
                    {
                        foundPattern = true;

                        //Destroy previous blocks
                        if(showMove)
                        {
                            for(int j = 0; j < 3; j++)
                                newBoard[line][i - 2 + j] = '*';
                        }
                        else
                        {
                            for(int j = 0; j < 3; j++)
                            {
                                newBoard[line][i - 2 + j] = '_';
    
                                if(line > 0 && newBoard[line - 1][i - 2 + j] != '_')
                                    newBoard = dropColumn(newBoard, i - 2 + j);
                            }
                        }
                        
                    }
                    else
                        if(counter > 3)
                        {
                            if(showMove)
                            {
                                newBoard[line][i] = '*';
                            }
                            else
                            {
                                newBoard[line][i] = '_';

                                if(line > 0 && newBoard[line - 1][i] != '_')
                                    newBoard = dropColumn(newBoard, i);
                            }
                            
                        } 
                }
                else
                {
                    currentColor = newBoard[line][i];
                    counter = 1;
                }
            }
            else
            {
                currentColor = 'X';
            }
        }

        if(foundPattern)
        {
            if(showMove)
            {
                printBoard(newBoard);

                for(int i = 0; i < newBoard[line].length; i++)
                    if(newBoard[line][i] == '*')
                    {
                        newBoard[line][i] = '_';

                        if(line > 0 && newBoard[line - 1][i] != '_')
                            newBoard = dropColumn(newBoard, i);
                    }
            }

            return newBoard;
        }
        else
            return null;
    }

    /**
     * Eliminates any chain of 3 or more cubes in a column.
     * @param column The column to analyse.
     * @param board The board containing the column.
     * @return The new board after the column was cleared of chains.
     */
    private char[][] explodeColumn(int column, char[][] board)
    {
        char[][] newBoard = copyBoard(board);
        char currentColor = 'X';
        int counter = 1;
        boolean foundPattern = false;

        for(int i = newBoard.length - 1; i >= 0; i--)
            if(newBoard[i][column] == currentColor && newBoard[i][column] != '_')
            {
                counter++;

                if(counter == 3)
                {
                    foundPattern = true;

                    if(showMove)
                    {
                        newBoard[i + 2][column] = '*';
                        newBoard[i + 1][column] = '*';
                        newBoard[i][column] = '*';
                    }
                    else
                    {
                        newBoard[i + 2][column] = '_';
                        newBoard[i + 1][column] = '_';
                        newBoard[i][column] = '_';
                    }
                        
                }
                else
                {
                    if(counter > 3)
                    {
                        if(showMove)
                            newBoard[i][column] = '*';
                        else
                            newBoard[i][column] = '_';
                    }
                    
                }
                    
            }
            else 
            {
                if(counter >= 3)
                {
                    if(showMove)
                    {
                        printBoard(newBoard);

                        for(int j = 0; j < newBoard.length; j++)
                            if(newBoard[j][column] == '*')
                                newBoard[j][column] = '_';
                        
                    }

                    newBoard = dropColumn(newBoard, column);

                    i += counter;
                }

                counter = 1;
                currentColor =  newBoard[i][column];
            }

        if(foundPattern)
            return newBoard;
        else
            return null;

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
     * @param pieceCoords The piece's coordinates.
     */
    private void printBoardHighlightPiece(char[][] board, int[] pieceCoords)
    {
        for(int i = 0; i < board.length; i++)
        {
            System.out.print("|");

            for(int j = 0; j < board[i].length; j++)
            {
                if(i == pieceCoords[0] && j == pieceCoords[1])
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
     * @param pieceCoords The piece's coordinates.
     * @return True if the coordinates don't correspond to a piece and false otherwise.
     */
    public boolean testPieceCoords(int[] pieceCoords)
    {
        return pieceCoords[0] >= this.board.length || pieceCoords[1] >= this.board[0].length
        || pieceCoords[0] < 0 || pieceCoords[1] < 0 || this.board[pieceCoords[0]][pieceCoords[1]] == '_';
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