package GameGraphics;

import java.util.ArrayList;

public class Info {
	
	protected ArrayList<Float> pts;

	public Info(String information){
		
		//ignore letter, then the space 
		information = information.substring(2, information.length());
		pts = new ArrayList<Float>();
		
		//note important area in optimisation 
		while(true){
			
			String stpt = getNumber(information);
			
			//convert number in the form of a string in to a Float 
			pts.add(new Float(stpt));
			
			//if the last number was the last number in the line 
			if(information.length() == stpt.length()){
				
				break;
			}
			//removes the last number from the line
			information = information.substring(stpt.length()+1, information.length());
		}
	
	}
	
	//gets the next number 
	public String getNumber(String number){
		
		String no = "";
		
		for(int i = 0; i < number.length(); i++){
			
			//the end of the number 
			if(number.charAt(i) == ' '){
				
				break;
			
			}else{
				
				//else keep adding digits 
				no = no + number.charAt(i);
				
			}
		}
		
		return no;
		
	}
	
	
}
