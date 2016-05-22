package GameGraphics.GameScreenComposition;

import java.util.ArrayList;

public interface IComMapDisplay extends IComMap {

	public String getMapName(int mapNo);
	public int getMapPlayer(int mapNo);
	public int getTile(int x, int y);
	public ArrayList<int[]> GetTransitions();
}
