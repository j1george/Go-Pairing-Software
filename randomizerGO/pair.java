package randomizerGO;

import java.util.*;

class Pair{
	
	private List<Players> allPlayers;
	
	//Add a randomizer for who gets black and who gets white
	//Add another private data field for a temporary list of allPlayers for each round, gets reset
	
	//Following method needed?
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
	
	//works, add comment
	private List<Players> copy(){
		List<Players> newList = new ArrayList<Players>();
		for(int i = 0; i<allPlayers.size(); i++){
			newList.add(allPlayers.get(i));
		}
		return newList;
	}
	
	//seemingly works
	private int findPlayers(Players p, List<Players> arr){
		int i = 0;
		for(int j = 0; j<arr.size(); j++){
			if(arr.get(j).getName().equals(p.getName())){
				i = j;
				break;
			}
		}
		return i;
	}
	
	//works, add better comment
	public Pair(){
		allPlayers = new ArrayList<Players>();
	}
	
	//works, add better comments
	public void addAllPlayers(){
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		System.out.println("Please enter the number of players who's registered");
		int num = input.nextInt();
		for(int i = 0; i<num; i++){
			System.out.println("Enter each player's ID, rank, and name; one by one:");
			int ID = input.nextInt();
			int rank = input.nextInt();
			String name = input2.nextLine();
			Players newPlayer = new Players(ID, rank, name);
			allPlayers.add(newPlayer);
		}
		input.close();
		input2.close();
	}
	
	//Seemingly works, need comment
	public void pairPlayers(Players current, int round){
		List<Players> temp = copy();
		Random randomGenerator = new Random();
		int indexOfFound = findPlayers(current, temp);
		temp.remove(indexOfFound);
		int randomNumber = randomGenerator.nextInt(temp.size());
		//Check pairings. If pairings have been made, do randomNumberGenerator again?
		if(round>0){
			checkPairings(current, temp.get(randomNumber), round);
		}
		current.addMatchedPlayers(round, temp.get(randomNumber));
		temp.get(randomNumber).addMatchedPlayers(round, current);
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
	
	//works, add better comment
	public int getSize(){
		return allPlayers.size();
	}
	
	//works, add better comment
	public Players getPlayer(int i){
		return allPlayers.get(i);
	}
	
	public static void main(String args[]){
		Pair derp = new Pair();
		derp.addAllPlayers();
		derp.sortByRank();
		derp.pairPlayers(derp.getPlayer(1), 0);
		Players test = derp.getPlayer(1).getMatchedPlayers(0);
		System.out.println(derp.getPlayer(1).getName());
		System.out.println(test.getName());
	}
}