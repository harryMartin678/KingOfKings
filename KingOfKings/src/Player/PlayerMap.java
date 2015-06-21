package Player;

import Map.Map;

public class PlayerMap {
	
	private Map reality;
	private int[][] vision;
	private int mapNo;
	
	public PlayerMap(Map reality){
		
		vision = new int[reality.getHeight()][reality.getWidth()];
		
		this.reality = reality;
		
		for(int y = 0; y < vision.length; y++){
			for(int x = 0; x < vision[y].length; x++){
				
				vision[y][x] = 2;
			}
		}
	}
	
	public void shown(int x, int y){
		
		if(y >= 0 && x >= 0 && y < vision.length && x < vision[0].length){
		
			vision[y][x] = reality.getTile(x, y);
		}
	}
	
	public int getTile(int x, int y){
		
		return vision[y][x];
	}
	
	public void printMap(){
		
		for(int y = 0; y < vision.length; y++){
			for(int x = 0; x < vision[y].length; x++){
				
				if(y == 2 && x == 2){
					
					System.out.print("*");
					continue;
				}
				
				System.out.print(vision[y][x]);
			}
			System.out.println();
		}
	}

}
