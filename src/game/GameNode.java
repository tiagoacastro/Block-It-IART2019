package game;

import java.util.ArrayList;

/**
 * Represents an instance of a node in a search graph of a solution for a level in the game.
 */
public class GameNode extends Node 
{
    /**
     * The state of the board in this node.
     */
    private GameBoard board;

    /**
     * The number of moves performed to get to this node.
     */
    private int moves;

    /**
     * The number of nodes analyzed in the graph.
     */
    public static int analyzedNodes = 0;

    /**
     * Constructor of the class with most members.
     * @param parentNode The parent node of this node.
     * @param depth The depth at which this node is at.
     * @param pathCost The path cost to reach this node.
     * @param operator This node's operator.
     * @param heuristic This node's heuristic.
     * @param searchOption This node's search option
     * @param moves The number of mobes performed to get to this node.
     * @param board The state of the board in this node.
     */
    public GameNode(Node parentNode, int depth, int pathCost, String operator,  
        int searchOption, int moves, GameBoard board)
    {
        super(parentNode, depth, pathCost, operator, searchOption);

        this.board = board;
        this.moves = moves;
    }

    /**
     * Constructor of the class with only a few members, the ones remaining being instantiated automatically as if
     * this node was a regular child of the parent node indicated.
     * @param parentNode The parent node of this node.
     * @param operator This node's operator.
     * @param moves The number of mobes performed to get to this node.
     * @param board The state of the board in this node.
     */
    private GameNode(Node parentNode, String operator, int moves, GameBoard board)
    {
        super(parentNode, operator);

        this.board = board;
        this.moves = moves;
        
    }

    /**
     * Expands a node, i.e, returns its possible children.
     * @return The children of this node.
     */
    public ArrayList<Node> expandNode()
    {
        ArrayList<Node> nodeList = new ArrayList<>();
        char[][] board = getBoard();
        char piece;
        int[] coords = new int[2];

        return nodeList;
    }

    /**
     * Moves a piece in the board.
     * @param direction The direction in which to move the piece.
     * @param coords The coordinates of the piece.
     * @return A new node representing the board if the move was succesfull or null if not. 
     */
    private GameNode move(String direction, int[] coords)
    {
        GameBoard newBoard ;
        String op;
        
        if(board.testCoords(coords))
        {
            System.out.println("Invalid piece in move " + direction);
            return null;
        }

        switch(direction)
        {
            case "left":
                newBoard = board.moveLeft(coords);
                op = "Move left ";
                break;

            case "right":
                newBoard = board.moveRight(coords);
                op = "Move Right";
                break;

            case "up":
                newBoard = board.moveUp(coords);
                op = "Move Up";
                break;

            case "down":
                newBoard = board.moveDown(coords);
                op = "Move Down";
                break;

            default:
                System.out.println("Invalid move direction: " + direction);
                return null;
        }

        return new GameNode(this, op, this.moves + 1, newBoard);
    }

    /**
     * Executes the moves indicated by the operators in the Solution array.
     */
    public void traceSolution()
    {
        String[] move;
        String op, direction;
        int[] coords = new int[2];

        GameBoard.setShowMove(true);

        for(int i = 0; i < Node.solution.size(); i++)
        {
            move = Node.solution.get(i).split("\\s+");

            if(move.length != 4)
            {
                if(!move[0].equals("root"))
                    System.out.println("Invalid Solution: " + Node.solution.get(i));
                    
                continue;
            }
            
            op = move[0];
            direction = move[1];
            coords[0] = Integer.parseInt(move[2]);
            coords[1] = Integer.parseInt(move[3]);

            System.out.println("\n" + op + " " + direction + "(" + move[2] + "," + move[3] + ")\n");

            try 
            {
                if (op.equals("move"))
                    this.board = this.move(direction, coords).getGameBoard();
                //else put barrier
            } 
            catch(NullPointerException e)
            {
                System.out.println("Null pointer exception\n");
                return;
            }
        }
    }

    /**
     * Adds the correspondent node's operators to the Solution array all the way from the final/acceptance node.
     */
    public void traceSolutionUp()
    {
        if(!operator.equals("root"))
        {
            solution.add(0, operator);
            parentNode.traceSolutionUp();
        }
    }

    /**
     * Prints the current board.
     */
    public void printBoard()
    {
        board.printBoard();
    }

    /**
     * Retrieves the current game board.
     * @return The current game board.
     */
    public GameBoard getGameBoard()
    {
        return this.board;
    }

    /**
     * Retrieves the matrix instance of the board.
     * @return The matrix representing the actual board.
     */
    private char[][] getBoard()
    {
        return board.getBoard();
    }

    public void setGameBoard(GameBoard board)
    {
        this.board = board;
    }

}