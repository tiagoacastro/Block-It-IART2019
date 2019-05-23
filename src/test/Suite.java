package test;

import java.util.ArrayList;

import game.BlockIt;
import game.Player;
import game.node.Node;
import game.node.PlayerNode;

public class Suite
{
    public static void main(String args[])
    {
        ArrayList<Player> players = new ArrayList<Player>();
        new BlockIt(true);

        Player p1 = new Player(1, 'R');
        Player p2 = new Player(1, 'Y');

        players.add(p1);
        players.add(p2);

        BlockIt.setPlayers(players);
        BlockIt.buildBoard();

        int[] pos = BlockIt.getPlayers().get(0).getBoard().getPlayerPosition('Y');

        System.out.println(pos[0] + "|" + pos[1]);

        ArrayList<PlayerNode> children = p1.getPlayerNode().expandPlayerNode(true);

        for(Node n: children)
            System.out.println(n.getOperator());

        Player.getBoard().printBoard();
    }
}