package randomizerGO;

import java.util.*;

class Pair{
	
	private List<Players> allPlayers;
	private List<Players> secondPlayersList;
	
	//Add a randomizer for who gets black and who gets white
	
	//Find a way to implement
	private List<Players> playersMatchedHighDan;
	private List<Players> playersMatchedLowDanHighKyu;
	private List<Players> playersMatchedHighLowKyu;
	private List<Players> playersMatchedLowKyu;
	
	//seemingly works, add comment
	private boolean checkPairings(Players p1, Players p2, int round){
		for(int i = 0; i<round; i++){
			if(p2.getName() == p1.getMatchedPlayers(i).getName()){
					return true;
			}
		}
		return false;
	}
	
	//seemingly works
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
	
	//works, add better comment
	public Pair(){
		allPlayers = new ArrayList<Players>();
		secondPlayersList = new ArrayList<Players>();
	}
	
	//works, add better comments
	public void addAllPlayers(){
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		System.out.println("Please enter the number of players who's registered");
		int num = input.nextInt();
		for(int i = 0; i<num; i++){
			System.out.println("Enter each player's rank and name; one by one:");
			int rank = input.nextInt();
			String name = input2.nextLine();
			Players newPlayer = new Players(rank, name);
			allPlayers.add(newPlayer);
		}
		secondPlayersList.addAll(allPlayers);
	}
	
	//Seemingly works, need comment
	public void pairPlayers(Players current, int round){
		Random randomGenerator = new Random();
		int indexForRemove = findPlayers(current.getName(), secondPlayersList);
		secondPlayersList.remove(indexForRemove);
		int randomNumber = randomGenerator.nextInt(secondPlayersList.size());
		if(round>0){
			while(checkPairings(current, secondPlayersList.get(randomNumber), round)){
				randomNumber = randomGenerator.nextInt(secondPlayersList.size());
			}
		}
		int indexOfFound = findPlayers(current.getName(), allPlayers);
		int indexOfFound2 = findPlayers(secondPlayersList.get(randomNumber).getName(), allPlayers);
		allPlayers.get(indexOfFound).addMatchedPlayers(round, secondPlayersList.get(randomNumber));
		allPlayers.get(indexOfFound2).addMatchedPlayers(round, current);
		secondPlayersList.remove(randomNumber);
	}
	
	//works, add better comment
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
	
	//Probably works, add comment
	public void inputResults(String winner, String loser){
		int win = findPlayers(winner, allPlayers);
		int lose = findPlayers(loser, allPlayers);
		allPlayers.get(win).setPlayerPoints(1);
		allPlayers.get(lose).setPlayerPoints(0.5);
	}
	
	public void resetSecondList(){
		secondPlayersList.clear();
		secondPlayersList.addAll(allPlayers);
	}
	
	public void printPoints(){
		for(int i = 0; i<allPlayers.size(); i++){
			System.out.println(allPlayers.get(i).getName()+" Points: "+allPlayers.get(i).getPlayerPoints());
		}
	}
	
	public void printPlayerList(){
		for(int i = 0; i<allPlayers.size(); i++){
			System.out.println(allPlayers.get(i).getName()+" Rank: "+allPlayers.get(i).getRank());
		}
	}
	
	//works, add better comment
	public int getSize(){
		return allPlayers.size();
	}
	
	public int getSecondListSize(){
		return secondPlayersList.size();
	}
	
	//works, add better comment
	public Players getPlayer(int i){
		return allPlayers.get(i);
	}
	
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
			if(round == 3){
				loop = false;
				System.out.println("This tournament is over, bye");
			}
			round++;
		}
		input.close();
	}
}