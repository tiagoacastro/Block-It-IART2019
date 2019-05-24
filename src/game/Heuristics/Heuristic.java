package game.heuristics;

import game.GameBoard;

/**
 * General heuristic class, serves as basis for more complex heuristics associated with a node.
 */
public abstract class Heuristic {
	/**
	 * The current value of the heuristic.
	 */
	int value;

	private int r = Integer.MAX_VALUE;
	private int g = Integer.MAX_VALUE;
	private int b = Integer.MAX_VALUE;
	private int y = Integer.MAX_VALUE;
	
	/**
	 * Constructor of the class, initiates it's value to 0.
	 */
	Heuristic()
	{
		this.value = 0;
	}
	
	/**
	 * Calculates the heuristic's value based on a board.
	 * @param board The board to which the heuristic's value is calculated.
	 * @param color The color of the player
	 */
	public abstract void calculate(GameBoard board, char color, boolean move);

	/**
	 * Returns a new instance of this heuristic sub-class.
	 * @return A new instance of a heuristic sub-class.
	 */
	public abstract Heuristic getNewHeuristic();

	void calculatedistances(GameBoard board){
		if(board.getPlayers()[0][0] != -1)
			r = GameBoard.getBoardSize() - board.getPlayers()[0][1];

		if(board.getPlayers()[1][0] != -1)
			g = board.getPlayers()[1][0];

		if(board.getPlayers()[2][0] != -1)
			b = board.getPlayers()[2][1];

		if(board.getPlayers()[3][0] != -1)
			y = GameBoard.getBoardSize() - board.getPlayers()[3][0];
	}

	boolean win(char color){
		switch(color) {
			case 'R':
				return r == 0;
			case 'G':
				return g == 0;
			case 'B':
				return b == 0;
			case 'Y':
				return y == 0;
		}

		return false;
	}

	int getVal(char color) {
		int val = 0;
		switch (color) {
			case 'R':
				val = r;
				break;
			case 'G':
				val = g;
				break;
			case 'B':
				val = b;
				break;
			case 'Y':
				val = y;
				break;
		}
		return val;
	}

	public int getValue()
	{
		return value;
	}
}