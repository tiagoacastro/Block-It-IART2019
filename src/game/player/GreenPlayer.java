package game.player;

import game.GameBoard;

public class GreenPlayer extends Player
{
    public GreenPlayer(int difficulty)
    {
        super(difficulty);

        int boardSize = GameBoard.getBoardSize();

        position[0] = (boardSize - 1) / 2;
        position[1] = boardSize - 1;
        color = 'G';
    }

    public boolean useBarrier(String coords)
    {
        return false;
    }

    public boolean isWinner()
    {
        return position[1] == 0;
    }

    public int getDistanceToBorder()
    {
        return position[1];
    }
}