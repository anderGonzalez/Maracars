package recursos;

public class CocheRevoluciones {
	int stackRev=0;
	int currentRev=0;
	
	
	public int getRevTotal(){
		return currentRev+ stackRev*255;
	}
	public void motorRevol(int revSensor){
//		System.out.println("Rev sensor: "+revSensor);
		//System.out.println("Current: "+currentRev);
		if(currentRev>revSensor){
			stackRev++;
		}
		currentRev=revSensor;
	}
	
	

}
