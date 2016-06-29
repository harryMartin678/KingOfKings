package GameGraphics.Menu;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import Buildings.Names;
import GameGraphics.Building;
import GameGraphics.GameScreenComposition.IComBuildingListDisplay;
import GameGraphics.GameScreenComposition.IComBuildingListMouseKeyboard;
import GameGraphics.GameScreenComposition.IComMapDisplay;
import GameGraphics.GameScreenComposition.IComMapMouseKeyboard;
import GameGraphics.GameScreenComposition.IComUnitListDisplay;


public class Menu {
	
	private ArrayList<MenuShape> Shapes;
	private ArrayList<MenuShape> UnitIcons;
	private ArrayList<MenuShape> BuildingIcons;
	private ArrayList<MenuShape> TopIcons;
	private Minimap miniMap;
	private MapDiagram mapDiagram;
	private HoverPanel hoverPanel;
	private PopupInfoBox box;
	private SaveGameDialog saveGame;
	
	public Menu(){
		
		miniMap = new Minimap(0.5f, 0.05f,0.1f,0.15f);
		mapDiagram = new MapDiagram(0.675f, 0.09f, 0.075f, 0.035f);
		Shapes = new ArrayList<MenuShape>();
		UnitIcons = new ArrayList<MenuShape>();
		BuildingIcons = new ArrayList<MenuShape>();
		TopIcons = new ArrayList<MenuShape>();
		saveGame = new SaveGameDialog();
//		CreatePopup("Test Popup", "This is a text popup. This is meant as a text. \n "
//				+ "It should allow for multiple lines. It should also be in the center of \n"
//				+ "of the screen.");
		AddMainPanels();
		AddBuildingIcons();
		AddTopPanel();
	}
	
	public void DrawMenu(GL2 draw,int ScreenWidth,int ScreenHeight,UnitIconSelection unitIconSel,
			boolean DrawBuildingIcons,IComUnitListDisplay units,
			IComBuildingListDisplay buildings,IComMapDisplay map,int myPlayer
			,int FRAME_X_SIZE,int FRAME_Y_SIZE,int FrameX,int FrameY){
		
		
		StartMenu(draw,ScreenWidth,ScreenHeight,true);
		DrawShapes(draw, ScreenWidth, ScreenHeight);
		DrawTopIcons(draw, ScreenWidth, ScreenHeight);
		
		if(DrawBuildingIcons){
			
			DrawBuildingIcons(draw, ScreenWidth, ScreenHeight);
			
		}
		
		if(unitIconSel.isUnitCreatorSelected()){
			
			if(UnitIcons.size() == 0){
				
				AddUnitIcons(unitIconSel.NoOfUnits());
			}
			
			DrawUnitIcons(draw,ScreenWidth,ScreenHeight);
		}
		
		miniMap.DrawMinimap(draw,ScreenWidth, ScreenHeight, buildings,units
				,map.getWidth(),map.getHeight(),myPlayer,FRAME_X_SIZE,FRAME_Y_SIZE
				,FrameX,FrameY);
		mapDiagram.DrawMapDiagram(draw, map, ScreenWidth, ScreenHeight);
		
		if(box != null){
			
			box.DrawPopupInfo(draw, ScreenWidth, ScreenHeight);
		}
		
		if(saveGame != null){
			
			saveGame.DrawSaveGameDialog(draw, ScreenWidth, ScreenHeight);
		}
		
		
		EndMenu(draw,ScreenWidth,ScreenHeight,true);
		StartMenu(draw, ScreenWidth, ScreenHeight, false);
		if(hoverPanel != null){
			
			hoverPanel.DrawHoverPanel(draw,ScreenWidth,ScreenHeight);
		}
		EndMenu(draw, ScreenWidth, ScreenHeight, false);
	}
	
	public void CreatePopup(String Title, String Text){
		
		box = new PopupInfoBox(0.3f,0.2f, 0.4f, 0.75f, Title, Text);
	}
	
	public void DestroyPopup(){
		
		box = null;
	}
	
	public void ClearUnitIcons(){
		
		UnitIcons.clear();
	}
	
	public boolean DragMenuMouse(double x, double y,int mapWidth,int mapHeight,
			IComMouseKeyboardMenu command){
		
		double my = 1.0 - y;
		int frame[] = miniMap.SelectMinimap(x, my, mapWidth, mapHeight);
		
		if(frame[0] != -1){
			
			command.MoveFrame(frame);
			return true;
		}
		
		return false;
	}
	
	public boolean RegulateMenuMouse(double x, double y,boolean DrawBuildingIcons,
			boolean DrawUnitIcons,IComMouseKeyboardMenu command,IComMapMouseKeyboard map){
		
		y = 1.0f - y;
		
		if(box != null) {
			
			if(box.ClosePopup((float)x, (float)y)){
				
				DestroyPopup();
				return true;
			}
		}
		
		if(DrawBuildingIcons){
			
			for(int b = 0; b < BuildingIcons.size(); b++){
				
				if(BuildingIcons.get(b).InMouse((float)x, (float)y)){
					
					command.SelectGhostBuilding(getBuildingName(b));
					BuildingIcons.get(b).SetSelected();
					return true;
				}
			}
		}
		
		if(DrawUnitIcons){
			
			for(int u = 0; u < UnitIcons.size(); u++){
				
				if(UnitIcons.get(u).InMouse((float)x, (float)y)){
					
					command.SelectUnitToBuild(u);
					UnitIcons.get(u).SetSelected();
					return true;
				}
			}
		}
		
		if(saveGame != null && saveGame.RegulateSGDMouse((float)x, (float)y, map.getWidth(),
				map.getHeight())){
			
			return true;
		}
		
		for(int t = 0; t < TopIcons.size(); t++){
			
			if(TopIcons.get(t).InMouse((float)x, (float)y)){
				
				TopIcons.get(t).SetSelected();
				return true;
			}
		}
		
		int[] frame = miniMap.SelectMinimap(x, y, map.getWidth(), map.getHeight());
		
		if(frame[0] != -1){
			
			command.MoveFrame(frame);
			return true;
		}
		
		return mapDiagram.SelectMapDiagram(x, y, map,command);
	}
	
	private String getBuildingName(int index) {
		// TODO Auto-generated method stub

		return new String[]{Names.ARCHERYTOWER,Names.BALLISTICTOWER,Names.BARRACK,Names.CASTLE
				,Names.DOCK,Names.FARM,Names.FORT,Names.STABLE,Names.STOCKPILE,Names.WALL,
				Names.MINE}[index];
	}

	private void DrawShapes(GL2 draw,int ScreenWidth, int ScreenHeight){
		
		for(int s = 0; s < Shapes.size(); s++){
			
			Shapes.get(s).Draw(draw, ScreenWidth, ScreenHeight);
		}
	}
	
	private void DrawBuildingIcons(GL2 draw,int ScreenWidth,int ScreenHeight){
		
		for(int b = 0; b < BuildingIcons.size(); b++){
			
			BuildingIcons.get(b).IsDrawn();
			BuildingIcons.get(b).Draw(draw, ScreenWidth, ScreenHeight);
		}
	}
	
	
	private void DrawUnitIcons(GL2 draw,int ScreenWidth,int ScreenHeight){
		
		for(int u = 0; u < UnitIcons.size(); u++){
			
			UnitIcons.get(u).IsDrawn();
			UnitIcons.get(u).Draw(draw, ScreenWidth, ScreenHeight);
		}
	}
	
	private void DrawTopIcons(GL2 draw,int ScreenWidth,int ScreenHeight){
		
		for(int t = 0; t < TopIcons.size(); t++){
			
			TopIcons.get(t).IsDrawn();
			TopIcons.get(t).Draw(draw, ScreenWidth, ScreenHeight);
			
		}
	}
	public void AddUnitIcons(int unitNo){
		
		for(int u = 0; u < unitNo; u++){
			
			UnitIcons.add(new Rectangle(0.06f + (u*0.04f),0.14f,0.025f,0.05f,1.0f,0.0f,0.0f,u));
		}
	}
	
	public void AddBuildingIcons(){
		
		//ARCHERYTOWER (0.05f,0.05f,0.4f,0.15f,1.0f,1.0f,0.0f)
		BuildingIcons.add(new Rectangle(0.06f,0.14f,0.025f,0.05f,0.0f,1.0f,0.0f,0));
		//BALLISTICTOWER
		BuildingIcons.add(new Rectangle(0.1f,0.14f,0.025f,0.05f,0.0f,1.0f,0.0f,1));
		//BARRACK
		BuildingIcons.add(new Rectangle(0.14f,0.14f,0.025f,0.05f,0.0f,1.0f,0.0f,2));
		//CASTLE
		BuildingIcons.add(new Rectangle(0.18f,0.14f,0.025f,0.05f,0.0f,1.0f,0.0f,3));
		//DOCK
		BuildingIcons.add(new Rectangle(0.22f,0.14f,0.025f,0.05f,0.0f,1.0f,0.0f,4));
		//FARM
		BuildingIcons.add(new Rectangle(0.26f,0.14f,0.025f,0.05f,0.0f,1.0f,0.0f,5));
		//FORT
		BuildingIcons.add(new Rectangle(0.30f,0.14f,0.025f,0.05f,0.0f,1.0f,0.0f,6));
		//STABLE
		BuildingIcons.add(new Rectangle(0.34f,0.14f,0.025f,0.05f,0.0f,1.0f,0.0f,7));
		//STOCKPILE
		BuildingIcons.add(new Rectangle(0.38f,0.14f,0.025f,0.05f,0.0f,1.0f,0.0f,8));
		//WALL
		BuildingIcons.add(new Rectangle(0.14f,0.07f,0.025f,0.05f,0.0f,1.0f,0.0f,9));
		//MINE
		BuildingIcons.add(new Rectangle(0.30f,0.07f,0.025f,0.05f,0.0f,1.0f,0.0f,10));
	}
	
	public void AddMainPanels(){
		
		//top panel
		Shapes.add(new Rectangle(0.0f,0.965f,1.0f,0.035f,1.0f,1.0f,1.0f,0));
		//bottom panel
		Shapes.add(new Rectangle(0.0f,0.0f,1.0f,0.22f,1.0f,1.0f,1.0f,0));
		//left bottom panel
		Shapes.add(new Rectangle(0.05f,0.05f,0.4f,0.15f,1.0f,1.0f,0.0f,0));
		//middle bottom panel
		Shapes.add(new Rectangle(0.49f,0.04f,0.12f,0.17f,1.0f,1.0f,0.0f,0));
		//right bottom panel
		Shapes.add(new Rectangle(0.625f,0.04f,0.175f,0.17f,1.0f,1.0f,0.0f,0));
		//far right bottom panel
		Shapes.add(new Rectangle(0.825f,0.05f,0.15f,0.15f,1.0f,1.0f,0.0f,0));
	}
	
	public void AddTopPanel(){
		
		//quit button
		TopIcons.add(new Rectangle(0.025f,0.975f,0.015f,0.02f,1.0f,0.0f,0.0f,0));
		//save button
		TopIcons.add(new Rectangle(0.075f,0.975f,0.015f,0.02f,1.0f,0.0f,0.0f,0));
		//Pause button
		TopIcons.add(new Rectangle(0.125f,0.975f,0.015f,0.02f,1.0f,0.0f,0.0f,0));
	}
	
	
	
	public void StartMenu(GL2 draw,int ScreenWidth,int ScreenHeight,boolean under3D){
		
		if(under3D){
			draw.glDisable(draw.GL_DEPTH_TEST);
		}
		//we want to modify the projection matrix (without this, mesh normals will break)
		draw.glMatrixMode(draw.GL_PROJECTION);
		//clear any previous transforms the projection matrix may contain (otherwise it would be combined with the following glOrtho matrix)
		draw.glLoadIdentity();

		//set the projection (could use glTranslate/glScale but this utility function is simpler)
		draw.glOrtho(0, ScreenWidth, 0, ScreenHeight, -1, 1); //left,right,bottom,top,front,back
		//draw.glOrtho(0.0f, ScreenWidth, ScreenHeight, 0.0f, 0.0f, 1.0f);
		//common practice to leave modelview as the current matrix for editing
		draw.glMatrixMode(draw.GL_MODELVIEW);
	}
	
	public void EndMenu(GL2 draw,int ScreenWidth,int ScreenHeight,boolean under3D){
		//System.out.println(((float)ScreenWidth/(float)ScreenHeight) + " Menu");
		draw.glMatrixMode (draw.GL_PROJECTION);
		draw.glLoadIdentity();
		
		new GLU().gluPerspective(45,((float)ScreenWidth/(float)ScreenHeight),3,600);
		draw.glMatrixMode(draw.GL_MODELVIEW);

		draw.glLoadIdentity();
		
		if(under3D){
	    	draw.glEnable(draw.GL_DEPTH_TEST); // enables depth testing
		}

		
	}

	public void Hover(float px, float py,boolean BuildingSelected
			,IComBuildingListMouseKeyboard buildings
			,IComMapMouseKeyboard map) {
		// TODO Auto-generated method stub
		boolean isHover = false;
		py = 1.0f - py;
		if(BuildingSelected){
			for(int b = 0; b < BuildingIcons.size(); b++){
				
				if(BuildingIcons.get(b).InMouse(px, py)){
					
					hoverPanel = new HoverPanel(BuildingIcons.get(b).getCenterX()
									+ BuildingIcons.get(b).getSizeX(), 
									BuildingIcons.get(b).getCenterY() 
									- HoverPanel.SizeY);
					String buildingName = getBuildingName(BuildingIcons.get(b).getIndex());
					Buildings.Building building = Building.GetBuildingClass(buildingName);
					hoverPanel.SetText(buildingName, new Integer(building.FoodNeeded()).toString()
							, new Integer(building.GoldNeeded()).toString());
					isHover = true;
				}
			}
		}
		
		for(int u = 0; u < UnitIcons.size(); u++){
			
			if(UnitIcons.get(u).InMouse(px, py)){
				
				hoverPanel = new HoverPanel(UnitIcons.get(u).getCenterX()
								+ UnitIcons.get(u).getSizeX(), 
								UnitIcons.get(u).getCenterY() 
								- HoverPanel.SizeY);
				Units.Unit unit = 
						Units.Unit.GetUnit(
								buildings.getUnitType(UnitIcons.get(u).getIndex()));
				hoverPanel.SetText(buildings.getUnitType(UnitIcons.get(u).getIndex()),
						new Integer(unit.foodNeeded()).toString(), 
						new Integer(unit.goldNeeded()).toString());
				isHover = true;
			}
		}
		
		int selectedMap = mapDiagram.HoverMapDiagram(px, py, map);
		
		if(selectedMap != -1){
			
			float[] mapPosInfo = mapDiagram.getSelectedMapPos(selectedMap,
					map.getMapListSize());
			hoverPanel = new HoverPanel(mapPosInfo[0],mapPosInfo[1]);
			hoverPanel.SetText(map.getMapName(selectedMap), "Ruled by: " + 
			map.getMapPlayer(selectedMap), "");
			isHover = true;
		}
		
		if(!isHover){
			
			hoverPanel = null;
		}
	}

}
