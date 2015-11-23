package GameGraphics.GameScreenComposition;

public interface IComMapDisplay extends IComMap {

	public String getMapName(int mapNo);
	public int getMapPlayer(int mapNo);
	public int getTile(int x, int y);
}
