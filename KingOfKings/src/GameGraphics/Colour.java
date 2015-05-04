package GameGraphics;

public class Colour {
	
	private float[] ambient;
	private float[] diffuse;
	private float[] specular;
	private float alpha; 
	
	public Colour(){
		
		ambient = new float[3];
		diffuse = new float[3];
		specular = new float[3];
	}
	
	public void postLine(String line){
		
		//ambient coefficient
		if(line.charAt(0) == 'K' && line.charAt(1) == 'a'){
			
			fillArray(ambient,line.substring(3,line.length()));
		
		//diffuse coefficient 
		}else if(line.charAt(0) == 'K' && line.charAt(1) == 'd'){
			
			fillArray(diffuse,line.substring(3,line.length()));
		
		//specular coefficient 
		}else if(line.charAt(0) == 'K' && line.charAt(1) == 's'){
			
			fillArray(specular,line.substring(3,line.length()));
		
		//alpha coefficient 
		}else if(line.charAt(0) == 'd'){
			
			alpha = new Float(getNumber(line.substring(2,line.length()))).floatValue();
		}
	}
	
	//same as method in info.java
	public String getNumber(String number){
		
		String no = "";
		
		for(int i = 0; i < number.length(); i++){
			
			if(number.charAt(i) == ' '){
				
				break;
			
			}else{
				
				no = no + number.charAt(i);
				
			}
		}
		
		return no;
		
	}
	
	public void fillArray(float[] array, String line){
		
		int index = 0;
		
		//gets all the numbers from the array 
		//similar to the constructor in info.java
		while(true){
			
			String stpt = getNumber(line);
			
			array[index] = new Float(stpt);
			
			if(line.length() == stpt.length()){
				
				break;
			}
			line = line.substring(stpt.length()+1, line.length());
			
			index ++;
		}
	}
	
	public float[] getAmbient(){
		
		return ambient;
	}
	
	public float[] getDiffuse(){
		
		return diffuse;
	}
	
	public float[] getSpecular(){
		
		return specular;
	}

	
	public void printData(){
		
		System.out.println("Ambient " + ambient[0] + " " + ambient[1] + " " + ambient[2]);
		System.out.println("Diffuse " + diffuse[0] + " " + diffuse[1] + " " + diffuse[2]);
		System.out.println("Specular " + specular[0] + " " + specular[1] + " " + specular[2]);
		System.out.println(alpha);
	}
	

}
