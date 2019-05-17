package game.Heuristics;

import game.GameBoard;
import game.Player;

/**
 * General heuristic class, serves as basis for more complex heuristics associated with a node.
 */
public abstract class Heuristic {
	/**
	 * The current value of the heuristic.
	 */
	public double value;
	
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
	public abstract void calculate(GameBoard board, char color);

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
}