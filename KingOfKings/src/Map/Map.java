package Map;

public class Map {
	
	private int[][] map;
	private int player;
	
	public Map(int x, int y){
		
		map = new int[y][x];
	}
	
	public void setPlayer(int player){
		
		this.player = player;
	}
	
	public int getPlayer(){
		
		return player;
	}
	
	
	public void setTile(int x, int y, int tile){
		
		map[y][x] = tile;
	}
	
	public int getTile(int x, int y){
		
		return map[y][x];
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
