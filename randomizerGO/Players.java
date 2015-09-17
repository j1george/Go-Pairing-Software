package randomizerGO;

import java.util.*;

class Players{
	//Change playerID to playerPoints
	//Make another file for Results to edit playerPoints
	private int playerID;
	private int rank;
	private String playerName;
	private Players matchedPlayers[] = new Players[4];
	
	public Players(){
		playerID = 0;
		rank = 0;
		playerName = "";
	}
	
	public Players(int ID, int r, String name){
		playerID = ID;
		rank = r;
		playerName = name;
	}
	
	public int getPlayerID(){
		return playerID;
	}
	
	public int getRank(){
		return rank;
	}
	
	public String getName(){
		return playerName;
	}
	
	public Players getMatchedPlayers(int i){
		return matchedPlayers[i];
	}
	
	public void addMatchedPlayers(int round, Players name){
		matchedPlayers[round] = name;
	}
	
	public static void main(String args[]){
		Players test = new Players(92146, 5, "David Chau");
		Players test2 = new Players(921, 6, "Thomas Rike");
		Players test3 = new Players(12042, 4, "Roger Schrag");
		System.out.println(test.getPlayerID());
		test.addMatchedPlayers(0, test2);
		test.addMatchedPlayers(1, test3);
		Players get = test.getMatchedPlayers(0);
		System.out.println(get.getName());
	}
}
