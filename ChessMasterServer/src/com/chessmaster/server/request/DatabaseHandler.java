package com.chessmaster.server.request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.chessmaster.data.Game;
import com.chessmaster.data.Player;

/**
 * Handles connections with the database.
 * Hardcoded values: 	url = "jdbc:mysql://localhost:3306/chess"
    					login = "root"
    					password = "root"
 * @author zyixc
 */
public class DatabaseHandler {
    private String url = "jdbc:mysql://localhost:3306/chess";
    private String login = "root";
    private String password = "root";
    private Connection conn;
    
    /**
     * Constructor
     */
    public DatabaseHandler(){
        try{
            conn = DriverManager.getConnection(url,login,password);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Retrieve a Player from the database
     * @param ID the player id
     * @return Player 
	 * @return null if no results found
     * @see Player
     */
    public Player getPlayer(String ID){
        Player result = null;
        try(PreparedStatement spn = conn.prepareStatement("SELECT * FROM players WHERE id = ?");){
            spn.setString(1, ID);
            ResultSet spn_rs = spn.executeQuery();
            if(spn_rs.next()){            	
            	result = new Player(spn_rs.getString(1),spn_rs.getString(2), spn_rs.getString(3));
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Retrieve Players from the database
     * @param player_name string to compare against database
     * @return List of Players 
     * @return null if no results found
     * @see List
     * @see Player
     */
    public List<Player> getPlayers(String player_name){
        List<Player> result = new ArrayList<>();
        try(PreparedStatement spn = conn.prepareStatement("SELECT * FROM players WHERE lastName LIKE ? LIMIT 20");){
            spn.setString(1, player_name + "%");
            ResultSet spn_rs = spn.executeQuery();
            while(spn_rs.next()){
                Player player = new Player(spn_rs.getString(1),spn_rs.getString(2), spn_rs.getString(3));
                player = getGamesFromPlayer(player);
                player.calculateNeededData();
                if(Integer.parseInt(player.getNumberofgames())>1&&!player.getFirstname().equals("0"))
                	result.add(player);
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private List<Game> queryGames(String query){
        List<Game> games = new ArrayList<>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	Player player_white = getPlayer(rs.getString(6));
            	Player player_black = getPlayer(rs.getString(7));
            	games.add(new Game(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        player_white.getFirstname()+" "+player_white.getLastname(),
                        player_black.getFirstname()+" "+player_black.getLastname(),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getString(11),
                        rs.getString(12),
                        new String[]{rs.getString(13),rs.getString(15),rs.getString(17),rs.getString(19),rs.getString(21)},
                        new String[]{rs.getString(14),rs.getString(16),rs.getString(18),rs.getString(20),rs.getString(22)}
                ));
            }
            return games;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
     
    private Player getGamesFromPlayer(Player player){
        String queryWhiteGames = "SELECT * FROM games WHERE white = "+player.getId();
        String queryBlackGames = "SELECT * FROM games WHERE black = "+player.getId();
        List<Game> whitegames = queryGames(queryWhiteGames);
        List<Game> blackgames = queryGames(queryBlackGames);
        if(!whitegames.isEmpty() && whitegames != null) {
            for (Game game : whitegames) {
                player.addWhite_games(game);
            }
        }
        if(!blackgames.isEmpty() && blackgames != null) {
            for (Game game : blackgames) {
                player.addBlack_games(game);
            }
        }
        return player;
    }
    
    /**
     * Retrieve Games from the database
     * @param resultfor 1-0, 1/2-1/2, 0-1
     * @param minrating 0 - 9999
     * @param maxrating 0 - 9999
     * @param whiteopening1 chess move
     * @param whiteopening2 chess move
     * @param whiteopening3 chess move
     * @param blackopening1 chess move
     * @param blackopening2 chess move
     * @param blackopening3 chess move
     * @param eco Universal chess openings
     * @return List of Games 
     * @return null no results found
     * @see List
     * @see Game
     */
    public List<Game> getGames(String resultfor, String minrating, String maxrating, String whiteopening1,
                            String whiteopening2, String whiteopening3, String blackopening1, String blackopening2,
                            String blackopening3, String eco){
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM `games` WHERE ");
        if (resultfor.equals("null")) query.append("1 ");
        else query.append("result = \""+resultfor+"\" ");
        if (!minrating.equals("null")) query.append("AND `white_elo` >= " + minrating + " AND `black_elo` >= " + minrating + " ");
        if (!minrating.equals("null")) query.append("AND `white_elo` <= " + maxrating + " AND `black_elo` <= " + maxrating + " ");
        if (!whiteopening1.equals("null")) query.append("AND `w1` = '" + whiteopening1 + "' ");
        if (!whiteopening2.equals("null")) query.append("AND `w2` = '" + whiteopening2 + "' ");
        if (!whiteopening3.equals("null")) query.append("AND `w3` = '" + whiteopening3 + "' ");
        if (!blackopening1.equals("null")) query.append("AND `b1` = '" + blackopening1 + "' ");
        if (!blackopening2.equals("null")) query.append("AND `b2` = '" + blackopening2 + "' ");
        if (!blackopening3.equals("null")) query.append("AND `b3` = '" + blackopening3 + "' ");
        if (!eco.equals("null")) query.append("AND `eco` = '" + eco + "' ");
        query.append("LIMIT 100");
        System.out.println(">>>> "+query.toString());
        return queryGames(query.toString());
    }
}
