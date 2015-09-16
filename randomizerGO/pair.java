package randomizerGO;

import java.util.*;

class Pair{
	
	private List<Players> allPlayers;
	
	//Following method needed?
	private List<Players> playersMatchedHighDan;
	private List<Players> playersMatchedLowDanHighKyu;
	private List<Players> playersMatchedHighLowKyu;
	private List<Players> playersMatchedLowKyu;
	
	//need test, add better comment
	private boolean checkPairings(Players p1, Players p2){
		for(int i = 0; i<4; i++){
			if(p1.getName() == p2.getMatchedPlayers(i).getName()){
					return true;
			}
		}
		return false;
	}
	
	//need test, add better comment
	private List<Players> copy(){
		List<Players> newList = new ArrayList<Players>();
		for(int i = 0; i<allPlayers.size(); i++){
			newList.add(allPlayers.get(0));
		}
		return newList;
	}
	
	//need test, add better comment
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
	
	//need test, add better comment
	public void pairPlayers(Players current, int i){
		//copy allPlayers array. In copy, remove current player. Then call a random number generator
		//Then add the matched player, using the RNG, to current player's array of matched players.
		List<Players> temp = copy();
		Random randomGenerator = new Random();
		int indexOfFound = findPlayers(current, temp);
		temp.remove(indexOfFound);
		int randomNumber = randomGenerator.nextInt(temp.size());
		checkPairings(current, temp.get(randomNumber));
		current.addMatchedPlayers(i, temp.get(randomNumber));
	}
	
	//Need fix
	public void sortByRank(){
		List<Players> tempArray = new ArrayList<Players>();
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
				tempArray.add(allPlayers.get(minIndex));
				/*int temp = numbers[minIndex];
				numbers[minIndex] = numbers[i];
				numbers[i] = temp;*/
			}
		}
		allPlayers = tempArray;
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
		for(int i = 0; i<derp.getSize(); i++){
			System.out.println(derp.getPlayer(i).getName());
		}
		derp.sortByRank();
		for(int i = 0; i<derp.getSize(); i++){
			System.out.println(derp.getPlayer(i).getName());
		}
	}
}