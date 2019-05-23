package game.heuristics;

import game.GameBoard;

/**
 * Heuristic based on the distance to the wall
 */
public class DirectHeuristic extends Heuristic
{
      /**
       * Calculates the heuristic's value based on the distance to the wall.
       * @param board The board to which the heuristic's value is calculated.
       * @param color The color of the player
       */
      public void calculate(GameBoard board, char color, boolean move) {
            calculatedistances(board);

            if(otherWin(board, color))
                  value = Integer.MIN_VALUE;
            else
                  switch(color)
                  {
                        case 'R':
                              this.value = r;
                              break;
                        case 'G':
                              this.value = g;
                              break;
                        case 'B':
                              this.value = b;
                              break;
                        case 'Y':
                              this.value = y;
                              break;
                  }
      }

      /**
       * Returns a new instance of this heuristic.
       * @return New instance of DirectHeuristic.
       */
      public Heuristic getNewHeuristic()
      {
        return new DirectHeuristic();
      }
}