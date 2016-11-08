package Map;

import java.util.HashMap;

public class FogMapCache {
	
	private HashMap<Integer,boolean[][]> VisibleMaps;
	
	public FogMapCache(){
		
		VisibleMaps = new HashMap<Integer,boolean[][]>();
	}
	
	public void setVisibleMap(int mapNo,boolean[][] map){
		
		VisibleMaps.remove(mapNo);
		VisibleMaps.put(mapNo, map);
	}
	
	public boolean[][] getMap(int mapNo){
		
		if(VisibleMaps.containsKey(mapNo)){
			
			return VisibleMaps.get(mapNo);
			
		}else{
			
			System.out.println("CAN'T FIND MAP FogMapCache");
			return null;
		}
	}

}
