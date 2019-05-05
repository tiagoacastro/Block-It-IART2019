package game.player;

import game.GameBoard;

public class BluePlayer extends Player
{
    public BluePlayer(int difficulty)
    {
        super(difficulty);

        int boardSize = GameBoard.getBoardSize();

        position[0] = boardSize - 1;
        position[1] = (boardSize - 1) / 2;
        color = 'B';
    }

    public boolean isWinner()
    {
        return position[0] == 0;
    }

    public int getDistanceToBorder()
    {
        return position[0];
    }
}