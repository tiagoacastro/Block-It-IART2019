package game.heuristics;

import game.BlockIt;
import game.GameBoard;

/**
 * Heuristic based on the distance to the wall
 */
public class CompetitiveHeuristic extends Heuristic
{
    /**
     * Calculates the heuristic's value based on the distance to the wall.
     * @param board The board to which the heuristic's value is calculated.
     * @param color The color of the player
     */
    public void calculate(GameBoard board, char color, boolean move) {
        calculatedistances(board);
        char other = BlockIt.getPlayerAfter(color).getColor();
        int mine, his;

        if (win(other))
            value = Integer.MIN_VALUE;
        else {
            if (win(color))
                value = Integer.MAX_VALUE;
            else {
                mine = getVal(color);
                his = getVal(other);

                value = mine-his;
            }
        }
    }

    /**
     * Returns a new instance of this heuristic.
     * @return New instance of DirectHeuristic.
     */
    public Heuristic getNewHeuristic()
    {
        return new CompetitiveHeuristic();
    }
}
