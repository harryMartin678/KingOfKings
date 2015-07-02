package GameClient;

import java.util.ArrayList;

public class ParseText {
	
	private ArrayList<String> numbers;
	private String unitName;
	private String unitType;
	
	//for standard line
	public ParseText(String text){
		
		numbers = new ArrayList<String>();
		
		String nextNumber = "";
		String unitNameTree = "";
		unitType = "";
		
		for(int i = 0; i < text.length(); i++){
			
			if(text.charAt(i) == ' '){
				
				if(nextNumber != ""){
					numbers.add(nextNumber);
					nextNumber = "";
				}
			
			}else if((text.charAt(i) <='9' && text.charAt(i) >= '0') ||
					text.charAt(i) == '.'){
				
				nextNumber += text.charAt(i);
				
			}else{
			
				unitNameTree += text.charAt(i);
				parseUnitName(unitNameTree);
				
			}
		}
		
		numbers.add(nextNumber);
	}
	
	private void parseUnitName(String unitNameTree){
		
		ArrayList<String> unitNode = new ArrayList<String>();
		String lastNode = "";
		
		for(int i = 0; i < unitNameTree.length(); i++){
			
			if(unitNameTree.charAt(i) == ':'){
				
				unitNode.add(lastNode);
				lastNode = "";
				
			}else{
				
				lastNode += unitNameTree.charAt(i);
			}
		}
		
		unitNode.add(lastNode);
		
		unitName = unitNode.get(unitNode.size()-1);
		
		if(unitNode.size() > 1){
			unitType = unitNode.get(unitNode.size()-2);
		}
	}
	

	public ArrayList<String> getNumbers(){
		
		return numbers;
	}
	
	public String getUnitName(){
		
		return unitName;
	}
	
	public String getUnitType(){
		
		return unitType;
	}
	
	public static void main(String[] args) {
		
		ParseText text = new ParseText("0 message 24.5 23.5 1");
		ArrayList<String> result = text.getNumbers();
		String name = text.getUnitName();
		
		System.out.println(name);
		
		for(int t = 0; t < result.size(); t++){
			
			System.out.println(result.get(t));
		}
	}
	

}
