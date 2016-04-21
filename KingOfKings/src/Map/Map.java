package Map;

import java.util.ArrayList;

public class Map {
	
	private int[][] map;
	private int player;
	private ArrayList<int[]> transitionPoints;
	private String name;
	private int mapNo;
	
	public Map(int x, int y){
		
		map = new int[y][x];
		transitionPoints = new ArrayList<int[]>();
	}
	
	public void setPlayer(int player){
		
		this.player = player;
	}
	
	public void setName(String name){
		
		this.name = name;
	}
	
	public String getName(){
		
		return name;
	}
	
	public int getPlayer(){
		
		return player;
	}
	
	public void addTransitionPoint(int x, int y, int mapNo){
		
		//System.out.println(x + " " + y + " " + mapNo + " Map");
		int[] pt = new int[]{x,y,mapNo};
		map[y][x] = 4;
		
		transitionPoints.add(pt);
	}
	
	public int getTransitionMap(int x, int y){
		
		for(int t = 0; t < transitionPoints.size(); t++){
			
			if(x == transitionPoints.get(t)[0] && y == transitionPoints.get(t)[1]){
				
				return transitionPoints.get(t)[2];
			}
			
		}
		
		return -1;
	}
	
	public int[] getTransitionPoint(int mapNo){
		
		for(int t = 0; t < transitionPoints.size(); t++){
			
			if(mapNo == transitionPoints.get(t)[2]){
				
				return transitionPoints.get(t);
			}
		}
		
		return new int[]{-1,-1,-1};
	}
	
	public int getTransitionSize(){
		
		return transitionPoints.size();
	}
	
	public int[] getTransitionPointByIndex(int index){
		
		return transitionPoints.get(index);
	}
	
	
	public void setTile(int x, int y, int tile){
		
		map[y][x] = tile;
	}
	
	public int getTile(int x, int y){
		
		return map[y][x];
	}
	
	public int[] getRow(int row){
		
		return map[row];
	}
	
	public int[][] toArray(){
		
		return map;
	}
	
	public int getWidth(){
		
		return map.length;
	}
	
	public int getHeight(){
		
		return map[0].length;
	}
	
	public void printMap(){
		
		for(int y = 0; y < map.length; y++){
			for(int x = 0; x < map[y].length; x++){
				
				System.out.print(map[y][x]);
			}
			
			System.out.println();
		}
	}
	
	/*public Feature getFeature(int x, int y){
		
		int currentRow = y;
		int currentColumn = x;
		int type = map[y][x];
		
		while(map[currentRow][x] == type){
			while(map[currentRow][currentColumn] == type){
				
				currentColumn++;
			}
			
			currentRow++;
			
		}
	
		return new Feature((currentColumn-x),(currentRow-y),type);
	
	}*/
	

}
