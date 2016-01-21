package recursos;

public class Revoluciones {
	int stackRev=0;
	int currentRev=0;

	
	
	public int getRevTotal(){
		return currentRev+ stackRev*255;
	}
	
	public void SetRevTotal(int newRevs){
		stackRev = newRevs/255;
		currentRev = newRevs%255;
	}
	
	
	public void motorRevol(int revSensor){
		if(currentRev>revSensor){
			stackRev++;
		}
		
		currentRev=revSensor;
	}
	
	

}
