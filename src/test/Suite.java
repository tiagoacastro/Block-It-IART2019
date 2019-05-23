package test;

import java.util.ArrayList;

import game.BlockIt;
import game.Player;
import game.node.Node;

public class Suite
{
    public static void main(String args[])
    {
        ArrayList<Player> players = new ArrayList<Player>();
        new BlockIt(true);

        players.add(new Player(1, 'R'));
        players.add(new Player(1, 'Y'));

        BlockIt.setPlayers(players);
        BlockIt.buildBoard();

        ArrayList<Node> children = Player.getGameNode().expandNodeWithBarrier(BlockIt.getCurrentPlayer().getPosition());

        for(Node n: children)
            System.out.println(n.getOperator());

        Player.getBoard().printBoard();
    }
}