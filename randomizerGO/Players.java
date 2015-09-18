package randomizerGO;

import java.util.*;

class Players{
	
	private double playerPoints = 0.0;
	private int rank;
	private String playerName;
	private Players matchedPlayers[] = new Players[4];
	
	public Players(){
		rank = 0;
		playerName = "";
	}
	
	public Players(int r, String name){
		rank = r;
		playerName = name;
	}
	
	public double getPlayerPoints(){
		return playerPoints;
	}
	
	public void setPlayerPoints(double point){
		playerPoints += point;
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
	
	public void displayMatchUps(int round){
		System.out.println(getName()+" vs "+getMatchedPlayers(round).getName());
	}
	
	public static void main(String args[]){
		Players test = new Players(5, "David Chau");
		Players test2 = new Players(6, "Thomas Rike");
		System.out.println(test.getPlayerPoints());
		test.addMatchedPlayers(0, test2);
		test.displayMatchUps(0);
	}
}
