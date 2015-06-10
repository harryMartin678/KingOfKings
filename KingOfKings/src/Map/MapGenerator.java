package Map;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MapGenerator {

	public MapGenerator(int sizeX, int sizeY) throws IOException{
		
		BufferedWriter writer = 
				new BufferedWriter(new FileWriter("C:/Users/harry/Documents"
						+ "/harry/Documents/backup/Program/java/KingOfKings"
						+ "/Maps/map1.map"));
		
		Random gen = new Random();
		
		writer.write(Integer.toString(sizeX) +"|"+Integer.toString(sizeY)+"\n");
		for(int y = 0; y < sizeY; y++){
			for(int x = 0; x < sizeX-1; x++){
				
				int next = gen.nextInt(400);
				
				if(next < 380){
					
					writer.write("0|");
				
				}else if(next < 390){
					
					writer.write("1|");
				
				}else if(next < 395){
					
					writer.write("2|");
				
				}else if(next == 399){
					
					writer.write("3|");
				}
			}
			
			if(y < (sizeY-1)){
				writer.write("0\n");
			}else{
				writer.write("0");
			}
		}
		
		writer.close();
	}
	
	public static void main(String[] args) throws IOException {
		
		new MapGenerator(500,500);
	}
}
