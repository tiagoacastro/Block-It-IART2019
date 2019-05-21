package game.heuristics;

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
    public void calculate(GameBoard board, char color) {
        int diff = 0;

        calculatedistances(board);

        if(otherWin(board, color))
            value = Integer.MAX_VALUE;
        else
            switch(color)
            {
                case 'R':
                    if(g < r)
                        diff++;
                    if(b < r)
                        diff++;
                    if(y < r)
                        diff++;
                    this.value = r + diff;
                    break;
                case 'G':
                    if(r < g)
                        diff++;
                    if(b < g)
                        diff++;
                    if(y < g)
                        diff++;
                    this.value = g + diff;
                    break;
                case 'B':
                    if(r < b)
                        diff++;
                    if(g < b)
                        diff++;
                    if(y < b)
                        diff++;
                    this.value = b + diff;
                    break;
                case 'Y':
                    if(r < y)
                        diff++;
                    if(g < y)
                        diff++;
                    if(b < y)
                        diff++;
                    this.value = y + diff;
                    break;
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
