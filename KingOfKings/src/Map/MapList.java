package Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapList {
	
	private ArrayList<Map> maps;
	
	public MapList(String mapEntry){
		
		maps = new ArrayList<Map>();
		File file = new File("Maps/"+mapEntry+".gme");
		ArrayList<String> mapNames = new ArrayList<String>();
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			
			String lastName;
			
			while((lastName = reader.readLine()) != null){
				
				mapNames.add(lastName);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < mapNames.size(); i++){
			maps.add(new LoadMap(mapNames.get(i)).getMap());
		}

	}
	
	private void printMaps(){
		
		for(int j = 0; j < maps.size(); j++){
			maps.get(j).printMap();
			System.out.println("/////////////////NEXT MAP///////////////////");
		}
	}
	
	public int getTile(int mapNo, int x, int y){
		
		return maps.get(mapNo).getTile(x, y);
	}
	
	public int getMapHeight(int mapNo){
		
		return maps.get(mapNo).getHeight();
	}
	
	public int getMapWidth(int mapNo){
		
		return maps.get(mapNo).getWidth();
	}
	
	public int getPlayer(int mapNo){
		
		return maps.get(mapNo).getPlayer();
	}
	
	public int getSize(){
		
		return maps.size();
	}
	
	
}
