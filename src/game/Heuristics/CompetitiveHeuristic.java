package game.Heuristics;

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
        int r = Integer.MAX_VALUE, g = Integer.MAX_VALUE, b = Integer.MAX_VALUE, y = Integer.MAX_VALUE, diff = 0;

        if(board.getPlayers()[0][0] != -1)
            r = GameBoard.getBoardSize() - board.getPlayers()[0][1];

        if(board.getPlayers()[1][0] != -1)
            g = board.getPlayers()[1][0];

        if(board.getPlayers()[2][0] != -1)
            b = board.getPlayers()[2][1];

        if(board.getPlayers()[3][0] != -1)
            y = GameBoard.getBoardSize() - board.getPlayers()[3][0];

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
