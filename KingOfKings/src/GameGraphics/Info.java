package GameGraphics;

import java.util.ArrayList;

public class Info {
	
	protected ArrayList<Float> pts;

	public Info(String information){
		
		DoConstructor(information);

	}
	
	public Info(boolean nothing){
		
		
	}
	
	protected void DoConstructor(String information){
		//ignore letter, then the space
		pts = new ArrayList<Float>();
		//split into the numbers 
		String[] numbers = information.split(" ");
		
		//start at 1 to ignore the descriptor char 
		//collect the floats
		for(int n = 1; n < numbers.length; n++){
			
			pts.add(new Float(numbers[n]));
		}
	}
	
	
	
	
}
