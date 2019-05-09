package game.player;

import game.GameBoard;

public class RedPlayer extends Player
{
    public RedPlayer(int difficulty)
    {
        super(difficulty);

        int boardSize = GameBoard.getBoardSize();

        position[0] = 0;
        position[1] = (boardSize - 1) / 2;
        color = 'R';
    }

    public boolean useBarrier(String coords)
    {
        return false;
    }

    public boolean isWinner()
    {
        return position[0] == GameBoard.getBoardSize() - 1;
    }

    public int getDistanceToBorder()
    {
        return GameBoard.getBoardSize() - position[0];
    }
}