package GameGraphics.Menu;

public interface IComMouseKeyboardMenu {

	public void SelectGhostBuilding(String buildingType);

	public void SelectUnitToBuild(int unitNo);

	public void MoveFrame(int[] frame);

	public void SetViewedMap(int mapNo);

}
