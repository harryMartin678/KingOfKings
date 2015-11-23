package GameGraphics.GameScreenComposition;

import Map.Map;

public interface IComMap {
	
	public int getWidth();
	public int getHeight();
	public int getMapListSize();
	//wayPoints.add(new int[]{tx,ty,viewedMap});
	public int getViewedMap();
	public Map getMap();
	

}
