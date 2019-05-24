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
            for(int j = 1; j <= 10; j++)
            {
                Node.MAX_SEARCH_DEPTH = j;

                Player p1 = new Player(i, 'R');

                players.add(p1);
    
                BlockIt.setPlayers(players);
                BlockIt.buildBoard();
    
                for(Player player: players)
                    player.setPlayerNodeBoard(Player.getBoard());
    
                startTime = System.currentTimeMillis();
                p1.play(true);
                endTime = System.currentTimeMillis();

                System.out.println("Bot with difficulty " + i + " and max search depth of " + j + ": " + (endTime - startTime) + " ms\n");

                players.clear();
                players.trimToSize();
            }
            
        }
    }
}