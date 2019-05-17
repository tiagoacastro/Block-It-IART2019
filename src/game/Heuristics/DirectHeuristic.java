package game.Heuristics;

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
      public void calculate(GameBoard board, char color) {
            switch(color)
            {
                  case 'R':
                        this.value = GameBoard.getBoardSize() - board.getPlayers()[0][1];
                        break;
                  case 'G':
                        this.value = board.getPlayers()[1][0];
                        break;
                  case 'B':
                        this.value = board.getPlayers()[2][1];
                        break;
                  case 'Y':
                        this.value = GameBoard.getBoardSize() - board.getPlayers()[3][0];
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