package GameGraphics.GameScreenComposition;

public interface IComFrameProcessMap extends IComMap {
	
//	lastMapFrames[this.viewedMap][1] = frameX;
//	lastMapFrames[this.viewedMap][2] = frameY;
//	
//	frameX = lastMapFrames[viewedMap][1]; 
//	frameY = lastMapFrames[viewedMap][2];
	public void setLastMapFrames(int frameX,int frameY);
	
//	this.viewedMap = viewedMap;
//	map = maps.getMap(viewedMap);
	public void setViewedMap(int viewedMap);
	public void setMapPlayer(int map, int player);
	//lastMapFrames[viewedMap][1]; 
	public int getLastMapFramesX();
	//lastMapFrames[viewedMap][2];
	public int getLastMapFrameY();
}
