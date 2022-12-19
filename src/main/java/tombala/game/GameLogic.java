package tombala.game;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

	private int lastPoll=0;
	private int size = 90;
	private ArrayList<Integer> possible = new ArrayList<>();
	private Random random = new Random();

	public GameLogic(){
		for (int i=1;i<size+1;i++){
			possible.add(i);
		}
	}

	public int getSize(){
		return size;
	}

	public boolean hasBeenPicked(int n){
		for (Integer i : possible){
			if (i == n){
				return false;
			}
		}
		return true;
	}

	public int getLastPoll(){
		return lastPoll;
	}

	public Integer pollFromList(){
		if (possible.size() == 0){
			return -1;
		}
		int index = random.nextInt(possible.size());
		Integer number = possible.get(index);
		possible.remove(number);
		this.lastPoll = number;
		return number;
	}

}
