package Player;

import java.util.ArrayList;

import Map.MapList;

public class Player {
	
	private int food;
	private int gold;
	private int playerNo;
	private int frameX;
	private int frameY;
	private int viewedMap;
	private ArrayList<PlayerMap> mapInfo;
	
	public Player(int playerNo, int food, int gold){
		
		this.playerNo = playerNo;
		this.food = food;
		this.gold = gold;
		
		mapInfo = new ArrayList<PlayerMap>();
	}
	
	public void addMaps(MapList maps){
		
		for(int m = 0; m < maps.getSize(); m++){
			mapInfo.add(new PlayerMap(maps.getMap(m)));
		}
		
	}
	
	public void showTiles(int x, int y, int mapNo, int size){
		
		for(int j = -size; j <= size; j++){
			for(int i = -size*2; i <= size*2; i++){
					
					mapInfo.get(mapNo).shown(x+i, y+j);
			}
		}

	}
	
	public void setFramePos(int frameX, int frameY){
		
		this.frameX = frameX;
		this.frameY = frameY;
	}
	
	public void setViewedMap(int viewedMap){
		
		this.viewedMap = viewedMap;
	}
	
	public int getViewedMap(){
		
		return viewedMap;
	}
	
	public int getFrameX(){
		
		return frameX;
	}
	
	public int getFrameY(){
		
		return frameY;
	}
	
	public int getFood(){
		
		return food;
	}
	
	public int getGold(){
		
		return gold;
	}
	
	public void changeFood(int dFood){
		
		this.food += dFood;
	}
	
	public void changeGold(int dGold){
		
		this.gold += dGold;
	}
	
	public int getPlayerNo(){
		
		return playerNo;
	}
	

}
