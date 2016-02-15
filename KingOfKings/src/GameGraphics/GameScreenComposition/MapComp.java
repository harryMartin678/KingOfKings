package GameGraphics.GameScreenComposition;

import Map.Map;
import Map.MapList;

public class MapComp implements IComMapMouseKeyboard, IComMapDisplay, IComFrameProcessMap {
	
	private Map map;
	private int viewedMap;
	private MapList maps;
	private int[][] lastMapFrames;
	private boolean firstTimeViewedMapSet;
	private IComMapUpdateDisplayFrame display;
	
	//load.get(0)
	public MapComp(String loadMapList){
		
		maps = new MapList(loadMapList);
		
		firstTimeViewedMapSet = true;
		
		lastMapFrames = new int[maps.getSize()][3];

		for(int m = 0; m < lastMapFrames.length; m++){
			
			lastMapFrames[m][0] = m;
			lastMapFrames[m][1] = maps.getMapWidth(m)/2;
			lastMapFrames[m][2] = maps.getMapHeight(m)/2;
			//System.out.println(m + " " + lastMapFrames[m][0] + " mapComp");
		}
	}
	
	public void SetDisplay(IComMapUpdateDisplayFrame display){
		
		this.display = display;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return map.getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return map.getHeight();
	}

	@Override
	public int getMapListSize() {
		// TODO Auto-generated method stub
		return maps.getSize();
	}

	@Override
	public int getViewedMap() {
		// TODO Auto-generated method stub
		return viewedMap;
	}

	@Override
	public Map getMap() {
		// TODO Auto-generated method stub
		return map;
	}

	@Override
	public String getMapName(int mapNo) {
		// TODO Auto-generated method stub
		return maps.getMap(mapNo).getName();
	}

	@Override
	public int getMapPlayer(int mapNo) {
		// TODO Auto-generated method stub
		return maps.getMap(mapNo).getPlayer();
	}

	@Override
	public int getTile(int x, int y) {
		// TODO Auto-generated method stub
		
		if(x >= map.getWidth() || y >= map.getHeight()){
			
			return -1;
		}
		
		return map.getTile(x, y);
	}

	@Override
	public void setLastMapFrames(int frameX, int frameY) {
		// TODO Auto-generated method stub
		lastMapFrames[this.viewedMap][1] = frameX;
		lastMapFrames[this.viewedMap][2] = frameY;
		
		
	}

	@Override
	public void setViewedMap(int viewedMap) {
		// TODO Auto-generated method stub
		
		//System.out.println("Set ViewedMap MapComp " + viewedMap);
		
		this.viewedMap = viewedMap;
		map = maps.getMap(viewedMap);
		
		if(firstTimeViewedMapSet){
			
			display.setFrameXAdjusted(map.getWidth()/2);
			display.setFrameYAdjusted(map.getHeight()/2);
			
			firstTimeViewedMapSet = false;
		}
	}

	@Override
	public void setMapPlayer(int map, int player) {
		// TODO Auto-generated method stub
		maps.getMap(map).setPlayer(player);
	}

	@Override
	public int getLastMapFramesX() {
		// TODO Auto-generated method stub
		return lastMapFrames[viewedMap][1];
	}

	@Override
	public int getLastMapFrameY() {
		// TODO Auto-generated method stub
		return lastMapFrames[viewedMap][2];
	}

}
