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
        long startTime, endTime;
        
        new BlockIt(true);
    
        for(int i = 2; i <= 3; i++)
        {
            for(int ii = 2; ii <= 3; ii++) {
                for (int j = 3; j <= 7; j=j+2) {
                    Node.MAX_SEARCH_DEPTH = j;

                    Player p1 = new Player(i, 'R');
                    Player p2 = new Player(ii, 'G');

                    players.add(p1);
                    players.add(p2);

                    BlockIt.setPlayers(players);
                    BlockIt.buildBoard();

                    for (Player player : players)
                        player.setPlayerNodeBoard(Player.getBoard());

                    startTime = System.currentTimeMillis();
                    p1.play(true);
                    endTime = System.currentTimeMillis();

                    System.out.println("Simultating move for Bot1 with difficulty " + i + ", Bot2 with difficulty " + ii +" and max search depth of " + j + ": " + (endTime - startTime) + " ms\n");

                    players.clear();
                    players.trimToSize();
                }
            }
        }
    }
}