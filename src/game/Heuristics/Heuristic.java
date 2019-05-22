package game.heuristics;

import game.GameBoard;

/**
 * General heuristic class, serves as basis for more complex heuristics associated with a node.
 */
public abstract class Heuristic {
	/**
	 * The current value of the heuristic.
	 */
	public double value;

	int r = Integer.MAX_VALUE;
	int g = Integer.MAX_VALUE;
	int b = Integer.MAX_VALUE;
	int y = Integer.MAX_VALUE;
	
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

	/**
	 * Compare for comparation purposes
	 * @param h heuristic to compare to
	 * @return difference
	 */
	public double compare(Heuristic h) {
		return this.value - h.value;
	}

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

	protected boolean otherWin(GameBoard board, char color){
		switch(color) {
			case 'R':
				return g == 0 || b == 0 || y == 0;
			case 'G':
				return r == 0 || b == 0 || y == 0;
			case 'B':
				return r == 0 || g == 0 || y == 0;
			case 'Y':
				return r == 0 || g == 0 || b == 0;
		}

		return false;
	}
}