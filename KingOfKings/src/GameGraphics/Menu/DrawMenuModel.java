package GameGraphics.Menu;

import com.jogamp.opengl.GL2;

import Buildings.UnitCreator;
import GameGraphics.Building;
import GameGraphics.GameScreenComposition.BuildingModelList;
import GameGraphics.GameScreenComposition.IComBuildingListDisplay;
import GameGraphics.GameScreenComposition.IComUnitListDisplay;
import GameGraphics.GameScreenComposition.UnitModelList;

public class DrawMenuModel {

	private IComUnitListDisplay units;
	private IComBuildingListDisplay buildings;
	private BuildingModelList buildingModels;
	private UnitModelList unitModels;
	
	public DrawMenuModel(IComUnitListDisplay units, IComBuildingListDisplay buildings,
			BuildingModelList buildingModels,UnitModelList unitModels){
		
		this.units = units;
		this.buildings = buildings;
		this.buildingModels = buildingModels;
		this.unitModels = unitModels;
	}
	
	public void drawBuildingIcons(float x, float y,float z, GL2 draw,
			int frameX,int frameY,int food,int gold,boolean isHoverPanel,int dontShowIndex){
		
		units.begin();
		buildings.begin();
		if(units.workSelected()){
			
			buildingModels.drawBuildingIcon(draw, x, y, z, frameX, frameY,food,gold,isHoverPanel,dontShowIndex);
		}
		units.end();
		buildings.end();
	}
	
	public void drawUnitIcons(float x, float y,float z, GL2 draw,
			int playerNumber,Building SelectedBuilding,int food,int gold){
		
			if(SelectedBuilding != null && Building.GetBuildingClass(SelectedBuilding.getName())
					instanceof UnitCreator){
//				float offsetX = 1.75f;
//				float offsetY = 2.7f;
				//drawMenuQuad(draw,-13.52f,-5.2f, -18.0f,0.5f, 0.0f, 0.5f,1.75f,1.5f, 1.5f);
				units.begin();
				unitModels.drawBuildingUnitIcons(x, y, z, draw, 
						playerNumber, SelectedBuilding,food,gold);
				units.end();
				//drawMenuQuad(draw,-13.52f,-2.85f, -18.0f,1.0f, 0.84f, 0.0f,1.75f,1.5f, 1.5f);
				buildings.begin();
				unitModels.drawBuildingQueue(x + 2.0f,y,z,draw,SelectedBuilding,playerNumber);
				buildings.end();
			}
	}
	
}
