package game.node;

import java.util.ArrayList;

import game.BlockIt;
import game.GameBoard;
import game.Player;
import game.heuristics.Heuristic;

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
    public GameNode(Node parentNode, int depth, int pathCost, String operator, Heuristic heuristic, GameBoard board)
    {
        super(parentNode, depth, pathCost, operator, heuristic);

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

    public GameNode(GameNode node)
    {
        super(node.getParentNode(), node.getDepth(), node.getPathCost(), node.getOperator(), 
            node.getHeuristic().getNewHeuristic()); 
            
        board = node.getGameBoard().cloneGameBoard();
    }

    public GameNode(String operator, Heuristic heuristic, GameBoard board)
    {
        super(operator, heuristic);

        this.board = board;
    }
    

    /**
     * Expands a node, i.e, returns its possible children.
     * @return The children of this node.
     */
    public ArrayList<Node> expandNode(int[] playerPos)
    {
        ArrayList<Node> nodeList = new ArrayList<Node>();

        if(this.board.validateMoveDown(playerPos))
            nodeList.add(new GameNode(this,  "move down " + playerPos[0] + " " 
                + playerPos[1], this.board.moveDown(playerPos)));

        if(this.board.validateMoveUp(playerPos))
            nodeList.add(new GameNode(this,  "move up " + playerPos[0] + " " 
                + playerPos[1], this.board.moveUp(playerPos)));  

        if(this.board.validateMoveLeft(playerPos))
            nodeList.add(new GameNode(this,  "move left " + playerPos[0] + " " 
                + playerPos[1], this.board.moveLeft(playerPos)));    

        if(this.board.validateMoveRight(playerPos))
            nodeList.add(new GameNode(this,  "move right " + playerPos[0] + " " 
                + playerPos[1], this.board.moveRight(playerPos))); 

        return nodeList;
    }

    /**
     * Expands a node, i.e, returns its possible children, including barrier placements.
     * @return The children of this node.
     */
    public ArrayList<Node> expandNodeWithBarrier(int[] playerPos)
    {
        ArrayList<Node> nodeList = expandNode(playerPos);

        GameBoard newBoard;

        /*
        for(Player p: BlockIt.getPlayers())
        {
            if(!p.equals(player))
            {
                if((newBoard = 
                    Player.getNode().getGameBoard().placeBarrier(p.getPosition()[1] + 1, p.getPosition()[0] - 1, 'v')) 
                    != null)
                    nodeList.add(new 
                        GameNode(this, "barrier v " + (p.getPosition()[0] - 1) + " " + (p.getPosition()[1] + 1), newBoard));

                if((newBoard = 
                    Player.getNode().getGameBoard().placeBarrier(p.getPosition()[1] + 1, p.getPosition()[0] + 1, 'v')) 
                    != null)
                    nodeList.add(new 
                        GameNode(this, "barrier v " + (p.getPosition()[0] + 1) + " " + (p.getPosition()[1] + 1), newBoard));

                if((newBoard = 
                    Player.getNode().getGameBoard().placeBarrier(p.getPosition()[1] - 1, p.getPosition()[0] - 1, 'v')) 
                    != null)
                    nodeList.add(new 
                        GameNode(this, "barrier v " + (p.getPosition()[0] - 1) + " " + (p.getPosition()[1] - 1), newBoard));

                if((newBoard = 
                    Player.getNode().getGameBoard().placeBarrier(p.getPosition()[1] - 1, p.getPosition()[0] + 1, 'v')) 
                    != null)
                    nodeList.add(new 
                        GameNode(this, "barrier v " + (p.getPosition()[0] + 1) + " " + (p.getPosition()[1] - 1), newBoard));

                if((newBoard = 
                    Player.getNode().getGameBoard().placeBarrier(p.getPosition()[1] + 1, p.getPosition()[0] - 1, 'h')) 
                    != null)
                    nodeList.add(new 
                        GameNode(this, "barrier h " + (p.getPosition()[0] - 1) + " " + (p.getPosition()[1] + 1), newBoard));

                if((newBoard = 
                    Player.getNode().getGameBoard().placeBarrier(p.getPosition()[1] + 1, p.getPosition()[0] + 1, 'h')) 
                    != null)
                    nodeList.add(new 
                        GameNode(this, "barrier h " + (p.getPosition()[0] + 1) + " " + (p.getPosition()[1] + 1), newBoard));


                if((newBoard = 
                    Player.getNode().getGameBoard().placeBarrier(p.getPosition()[1] - 1, p.getPosition()[0] - 1, 'h')) 
                    != null)
                    nodeList.add(new 
                        GameNode(this, "barrier h " + (p.getPosition()[0] - 1) + " " + (p.getPosition()[1] - 1), newBoard));

                if((newBoard = 
                    Player.getNode().getGameBoard().placeBarrier(p.getPosition()[1] - 1, p.getPosition()[0] + 1, 'h')) 
                    != null)
                    nodeList.add(new 
                        GameNode(this, "barrier h " + (p.getPosition()[0] + 1) + " " + (p.getPosition()[1] - 1), newBoard));
            }
        } */


        return nodeList;
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
        for (int i = 0; i < BlockIt.getPlayers().size(); i++) {
            if(BlockIt.getPlayers().get(i).isWinner()) {
                return true;
            }
        }
        return false; 
    }

    public void calculateHeuristic(char color) {
        this.heuristic.calculate(board, color, operator.split(" ")[0].equals("move"));
    }
}