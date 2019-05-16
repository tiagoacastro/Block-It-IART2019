package game.node;

import java.util.ArrayList;

import game.BlockIt;
import game.GameBoard;

/**
 * Represents an instance of a node in a search graph of a solution for a level in the game.
 */
public class GameNode extends Node 
{
    /**
     * The state of the board in this node.
     */
    protected GameBoard board;

    /**
     * The number of nodes analyzed in the graph.
     */
    protected static int analyzedNodes = 0;

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
        int searchOption, GameBoard board)
    {
        super(parentNode, depth, pathCost, operator, searchOption);

        this.board = board;
    }

    /**
     * Constructor of the class with only a few members, the ones remaining being instantiated automatically as if
     * this node was a regular child of the parent node indicated.
     * @param parentNode The parent node of this node.
     * @param operator This node's operator.
     * @param moves The number of mobes performed to get to this node.
     * @param board The state of the board in this node.
     */
    public GameNode(Node parentNode, String operator, GameBoard board)
    {
        super(parentNode, operator);

        this.board = board;
    }

    /**
     * Expands a node, i.e, returns its possible children.
     * @return The children of this node.
     */
    public ArrayList<Node> expandNode()
    {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        int[] currentPlayerCoords = BlockIt.getCurrentPlayer().getPosition();

        if(this.board.validateMoveDown(currentPlayerCoords))
            nodeList.add(new GameNode(this,  "move down " + currentPlayerCoords[0] + " " 
                + currentPlayerCoords[1], this.board.moveDown(currentPlayerCoords)));

        if(this.board.validateMoveUp(currentPlayerCoords))
            nodeList.add(new GameNode(this,  "move up " + currentPlayerCoords[0] + " " 
                + currentPlayerCoords[1], this.board.moveUp(currentPlayerCoords)));  

        if(this.board.validateMoveLeft(currentPlayerCoords))
            nodeList.add(new GameNode(this,  "move left " + currentPlayerCoords[0] + " " 
                + currentPlayerCoords[1], this.board.moveLeft(currentPlayerCoords)));    

        if(this.board.validateMoveRight(currentPlayerCoords))
            nodeList.add(new GameNode(this,  "move right " + currentPlayerCoords[0] + " " 
                + currentPlayerCoords[1], this.board.moveRight(currentPlayerCoords))); 

        return nodeList;
    }

    /**
     * Expands a node, i.e, returns its possible children, including barrier placements.
     * @return The children of this node.
     */
    public ArrayList<Node> expandNodeWithBarrier()
    {
        ArrayList<Node> nodeList = expandNode();
        char[][] currentBoard = board.getBoard();

        GameBoard newBoard;

        for(int i = 0; i < currentBoard.length; i++)
            for(int j = 0; j < currentBoard[i].length; j++)
            {
                if(i % 2 != 0)
                    if((newBoard = board.placeBarrier(j, i, 'h')) != null)
                        nodeList.add(new GameNode(this, "barrier h " + i + " " + j, newBoard));

                if(j % 2 != 0)
                    if((newBoard = board.placeBarrier(j, i, 'v')) != null)
                        nodeList.add(new GameNode(this, "barrier v " + i + " " + j, newBoard));
            }

        return nodeList;
    }

    public int[] getPlayerPosition(char color)
    {
        char[][] charBoard = board.getBoard();

        for(int i = 0; i < charBoard.length; i++)
            for(int j = 0; j < charBoard[i].length; j++)
                if(charBoard[i][j] == color)
                    return new int[] {i, j};

        return new int[] {-1};
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
                /*
                if (op.equals("move"))
                    this.board = board.move(direction, coords).getGameBoard(); */
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
    public char[][] getBoard()
    {
        return board.getBoard();
    }

    public void setGameBoard(GameBoard board)
    {
        this.board = board;
    }

    /**
     * Checks if current node is a leaf in the search tree
     * @return True if the node is a leaf
     */
    public boolean isTerminal() {
        return true;
    }

}