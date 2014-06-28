package com.chessmaster.client;

import com.chessmaster.data.Game;
import com.chessmaster.data.Player;

import java.util.List;

/**
 * Test for the Client class.
 * @author zyixc
 * @see Client
 */
public class runClientTest {
	/**
	 * Run a client test
	 * @param args
	 */
    public static void main(String[] args){
//        //step 1
//    	System.out.println("Getting player with id 146119:");
//    	Player dimitri = Client.INSTANCE.requestPlayer("146119");
//        if(dimitri != null)
//            System.out.println(dimitri.getFirstname()+" "+dimitri.getLastname());
//        else
//            System.out.println("Object returned null!");
//        System.out.println("Getting player with id 146119: Done");
//        
        //step 2
        System.out.println("Getting player with name Aagaard:");
        List<Player> playerlist = Client.INSTANCE.requestPlayerList("Aagaard");
        
        for(Player player: playerlist){
            System.out.println(player.getFirstname()+" "+player.getLastname());
            Game g = player.getWhite_games().get(0);
            System.out.println(g.getWhite() + g.getWhite_elo() + g.getBlack() + g.getBlack_elo());
        }
        System.out.println("Getting player with name Aagaard: done");
        
//        //step 3
//        System.out.println("Getting game ids:");
//        List<Game> gamelist = Client.INSTANCE.requestGameList(null,"1200","2000",null,null,null,null,null,null,"A32");
//        for(Game game: gamelist){
//            System.out.println(game.getGameid()+" "+game.getDate());
//        }
//        System.out.println("Getting game ids: Done");
    }
}
