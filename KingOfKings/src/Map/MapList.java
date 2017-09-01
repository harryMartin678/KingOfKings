package Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Buildings.IComMapBATTList;

public class MapList implements IComMapBATTList {
	
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
	
	public Map getMap(int mapNo){
		
		return maps.get(mapNo);
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

	@Override
	public void changeMapPlayer(int unitPlayer,int mapNo) {
		// TODO Auto-generated method stub
		maps.get(mapNo).setPlayer(unitPlayer);
	}

	public boolean NoWinner() {
		// TODO Auto-generated method stub
		
		return true;
		/*for(int w = 1; w < maps.size(); w++){
			
			if(maps.get(w).getPlayer() != maps.get(w-1).getPlayer()){
				
				return true;
			}
		}
		
		return false;*/
	}

	public ArrayList<int[]> GetTransitions() {
		// TODO Auto-generated method stub
		ArrayList<int[]> transitions = new ArrayList<int[]>();
		
		for(int t = 0; t < maps.size(); t++){
			
			int[] trans = maps.get(t).getAllTransitionPoints();
			for(int m = 0; m < trans.length; m++){
				
				transitions.add(new int[]{t,trans[m]-1});
			}
			
		}
		
		//System.out.println(transitions.size() + " MapList");
		
		for(int c = 0; c < transitions.size(); c++){
			for(int t = 0; t < transitions.size(); t++){
				
				if(t == c){
					
					continue;
				}
				//System.out.println(transitions.get(c)[0] + " " + transitions.get(t)[1] + " MapList");
				if(transitions.get(c)[0] == transitions.get(t)[1]){
					
					transitions.remove(t);
					break;
				}
			}
			
		}	
		
		//System.out.println(transitions.size() + " MapList");
		
		return transitions;
	}
	
	
}
