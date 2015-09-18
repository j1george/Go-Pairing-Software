package randomizerGO;

import java.util.*;

class Pair{
	
	private List<Players> allPlayers;
	private List<Players> secondPlayersList;
	
	//add UI
	//Find a way to implement the bottom
	//private List<Players> playersMatchedHighDan;
	//private List<Players> playersMatchedLowDanHighKyu;
	//private List<Players> playersMatchedHighLowKyu;
	//private List<Players> playersMatchedLowKyu;
	
	/*
	 * Method to check if pairings have been made and also check if both players have the same points
	 * Checking if points are same to enforce winners playing against winners and losers against losers
	 * set as private because it's a helper method to the pair() method.
	 */
	private boolean checkPairings(Players p1, Players p2, int round){
		for(int i = 0; i<round; i++){
			if((p2.getName().equals(p1.getMatchedPlayers(i).getName())) 
				&& (p1.getPlayerPoints() == p2.getPlayerPoints())){
					return true;
			}
		}
		return false;
	}
	
	/*
	 * Method to find the index of players given an array.
	 * Helper method when adding and removing players from the second list
	 */
	private int findPlayers(String p, List<Players> arr){
		int i = 0;
		for(int j = 0; j<arr.size(); j++){
			if(arr.get(j).getName().equals(p)){
				i = j;
				break;
			}
		}
		return i;
	}
	
	//Method to choose colors using a Random object
	private void chooseColor(Players p1, Players p2){
		Random col = new Random();
		int color = col.nextInt(2);
		if(color == 0){
			p1.setColor("Black");
			p2.setColor("White");
		}else{
			p2.setColor("Black");
			p1.setColor("White");
		}
	}
	
	//Default Constructor.
	public Pair(){
		allPlayers = new ArrayList<Players>();
		secondPlayersList = new ArrayList<Players>();
	}
	
	/*
	 * Method to add players to the main list of players, while also copying it to the second list.
	 * Second list is for keeping track of who's been matched.
	 */
	public void addAllPlayers(){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the number of players who's registered");
		int num = input.nextInt();
		for(int i = 0; i<num; i++){
			System.out.println("Enter each player's rank and name; one by one:");
			int rank = input.nextInt();
			input.nextLine();
			String name = input.nextLine();
			Players newPlayer = new Players(rank, name);
			allPlayers.add(newPlayer);
		}
		secondPlayersList.addAll(allPlayers);
	}
	
	/*
	 * Method to pair players together. Utilizes a random number generator.
	 * After finding the players to match up and confirming the prerequisites, delete from the
	 * second list.
	 * One glitch I've found is that when I enter in 10 players, when it comes to second round pairing
	 * it will have one match where there's someone who won and someone who lost 
	 * This is because there's 5 winners and 5 losers, so you can't match within those brackets.
	 * This will be true for any even numbered list where after the first round, the winner and losers equal an odd.
	 */
	public void pairPlayers(Players current, int round){
		Random randomGenerator = new Random();
		int indexForRemove = findPlayers(current.getName(), secondPlayersList);
		secondPlayersList.remove(indexForRemove);
		int randomNumber = randomGenerator.nextInt(secondPlayersList.size());
		while(checkPairings(current, secondPlayersList.get(randomNumber), round)){
			randomNumber = randomGenerator.nextInt(secondPlayersList.size());
		}
		int indexOfFound = findPlayers(current.getName(), allPlayers);
		int indexOfFound2 = findPlayers(secondPlayersList.get(randomNumber).getName(), allPlayers);
		chooseColor(allPlayers.get(indexOfFound), allPlayers.get(indexOfFound2));
		allPlayers.get(indexOfFound).addMatchedPlayers(round, secondPlayersList.get(randomNumber));
		allPlayers.get(indexOfFound2).addMatchedPlayers(round, current);
		secondPlayersList.remove(randomNumber);
	}
	
	/*
	 * Sorting method, utilizes selection sort. For aesthetic purposes to show each player in descending order
	 * by rank.
	 */
	public void sortByRank(){
		int minIndex = 0;
		int run = allPlayers.size()-1;
		for(int i = 0; i<run; i++){
			minIndex = i;
			for(int j = i+1; j < allPlayers.size(); j++){
				if(allPlayers.get(j).getRank() < allPlayers.get(minIndex).getRank()){
					minIndex = j;
				}
			}
			if(minIndex != i){
				Players temp = allPlayers.get(minIndex);
				allPlayers.remove(minIndex);
				allPlayers.add(minIndex, allPlayers.get(i));
				allPlayers.remove(i);
				allPlayers.add(i, temp);
			}
		}
	}
	
	/*
	 * Method to input results, giving 1 point to winner and 0.5 to loser. Helps for matching up losers and winners.
	 */
	public void inputResults(String winner, String loser){
		int win = findPlayers(winner, allPlayers);
		int lose = findPlayers(loser, allPlayers);
		allPlayers.get(win).setPlayerPoints(1);
		allPlayers.get(lose).setPlayerPoints(0.5);
	}
	
	/*
	 * Method to reset second list each round.
	 */
	public void resetSecondList(){
		secondPlayersList.clear();
		secondPlayersList.addAll(allPlayers);
	}
	
	//Method to print points for each player and player's name
	public void printPoints(){
		for(int i = 0; i<allPlayers.size(); i++){
			System.out.println(allPlayers.get(i).getName()+" Points: "+allPlayers.get(i).getPlayerPoints());
		}
	}
	
	//method to print the player's name and rank
	public void printPlayerList(){
		for(int i = 0; i<allPlayers.size(); i++){
			System.out.println(allPlayers.get(i).getName()+" Rank: "+allPlayers.get(i).getRank());
		}
	}
	
	//getter method for size of the first list
	public int getSize(){
		return allPlayers.size();
	}
	
	//getter method for size of the second list
	public int getSecondListSize(){
		return secondPlayersList.size();
	}
	
	//getter method for getting a player from the first list
	public Players getPlayer(int i){
		return allPlayers.get(i);
	}
	
	//getter method for getting a player from the second list
	public Players getPlayerSecondList(int i){
		return secondPlayersList.get(i);
	}
	
	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		boolean loop = true;
		int round = 0;
		Random playerRand = new Random();
		Pair derp = new Pair();
		derp.addAllPlayers();
		derp.sortByRank();
		derp.printPlayerList();
		System.out.println("----------------------");
		while(loop){
			for(int i = 0; i<derp.getSize()/2; i++){
				int randomPlayerIndex = playerRand.nextInt(derp.getSecondListSize());
				derp.pairPlayers(derp.getPlayerSecondList(randomPlayerIndex), round);
			}
			for(int i = 0; i<derp.getSize(); i++){
				derp.getPlayer(i).displayMatchUps(round);
			}
			System.out.println("----------------------");
			derp.resetSecondList();
			System.out.println("Please enter the results for round "+(round+1));
			for(int i = 0; i<derp.getSize()/2; i++){
				System.out.println("Type the winner here: ");
				String winner = input.nextLine();
				System.out.println("Type the loser here: ");
				String loser = input.nextLine();
				derp.inputResults(winner, loser);
			}
			System.out.println("----------------------");
			derp.printPoints();
			System.out.println("----------------------");
			if(round == 3){
				loop = false;
				System.out.println("This tournament is over, bye");
			}
			round++;
		}
		input.close();
	}
}