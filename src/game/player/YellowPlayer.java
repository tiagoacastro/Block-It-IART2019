package game.player;

import game.GameBoard;

public class YellowPlayer extends Player
{
    public YellowPlayer(int difficulty)
    {
        super(difficulty);

        int boardSize = GameBoard.getBoardSize();

        position[0] = (boardSize - 1) / 2;
        position[1] = 0;
        color = 'Y';
    }

    public boolean isWinner()
    {
        return position[1] == GameBoard.getBoardSize() - 1;
    }

    public int getDistanceToBorder()
    {
        return GameBoard.getBoardSize() - position[1];
    }
}