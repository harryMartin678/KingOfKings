package Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadMap {
	
	private Map map;
	
	public LoadMap(){
		
		File file = new File("Maps/map1.txt");

		ArrayList<String> info = null;
		try {
			 info = readMap(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		parseHeader(info);
		parseMap(info);
		
	}
	
	private void parseHeader(ArrayList<String> header){
		
		ArrayList<Integer> size = parseLine(header.get(0));
		
		map = new Map(size.get(0),size.get(1));
	}
	
	private void parseMap(ArrayList<String> mapStr){
		
		ArrayList<Integer> mapInt = new ArrayList<Integer>();
		
		for(int i = 1; i < mapStr.size(); i++){
			
			mapInt = parseLine(mapStr.get(i));
			
			for(int j = 0; j < mapInt.size(); j++){
				
				map.setTile(j, i-1, mapInt.get(j));
				
			}
		}
	}
	
	private ArrayList<Integer> parseLine(String line){
		
		ArrayList<Integer> values = new ArrayList<Integer>();
		
		String currentInt = "";
		
		for(int i = 0; i < line.length(); i++){
			
			if(line.charAt(i) == '|'){
				
				values.add(new Integer(currentInt));
				currentInt = "";
				
			}else{
				
				currentInt = currentInt + line.charAt(i);
			}
		}
		
		values.add(new Integer(currentInt));
		
		return values;
	}
	
	private ArrayList<String> readMap(File file) throws IOException, FileNotFoundException{
		
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		
		while((line = reader.readLine()) != null){
			
			lines.add(line);
		}
		
		return lines;
	}
	
	public Map getMap(){
		
		return map;
	}
	
}
