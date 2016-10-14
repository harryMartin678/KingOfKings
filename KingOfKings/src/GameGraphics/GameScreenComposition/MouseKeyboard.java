package GameGraphics.GameScreenComposition;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Buildings.Names;
import GameClient.ClientMessages;
import GameGraphics.Building;
import GameGraphics.IBoundingBoxes;
import GameGraphics.IComMouseKeyboardBuildingList;
import GameGraphics.Menu.IComMouseKeyboardMenu;
import GameGraphics.Menu.Menu;
import IntermediateAI.WallCreator;
import Util.Point;

public class MouseKeyboard implements IComMouseKeyboard,IComMouseFrameProcess,
	IComMouseKeyboardMenu,IComMouseKeyboardBuildingList {
	
	private MouseEvent mouse;
	private boolean drag = false;
	private boolean dragBox = false;
	private double sx,sy,lx,ly;
	private int[] startDB,lastDB;
	private int width;
	private int height;
	private boolean fDown;
	private boolean shiftDown;
	private ArrayList<Point> wallSpaces;
	
	private IComUnitListMouseKeyboard units;
	private IComBuildingListMouseKeyboard buildings;
	private IComDisplayMouseKeyboard display;
	private IComMapMouseKeyboard map;
	private int playerNumber;
	private ClientWrapper cmsg;
	private MoveUnit move;
	private IBoundingBoxes unitBoxes;
	private IBoundingBoxes buildingBoxes;
	
	private int food;
	private int gold;
	
	//private ButtonList buttons;
	private Menu menu;
	
	public MouseKeyboard(Menu menu){
		
		this.menu = menu;
		wallSpaces = new ArrayList<Point>();
	}
	
	public void setUpMouseKeyboard(IComUnitListMouseKeyboard units,
			IComBuildingListMouseKeyboard buildings, IComDisplayMouseKeyboard display,
			IComMapMouseKeyboard map,int playerNumber,ClientWrapper cmsg
			,IBoundingBoxes unitBoxes,IBoundingBoxes buildingBoxes){
		
		this.units = units;
		this.buildings = buildings;
		this.display = display;
		this.map = map;
		this.cmsg = cmsg;
		this.unitBoxes = unitBoxes;
		this.buildingBoxes = buildingBoxes;
		
		this.playerNumber = playerNumber;
		
		move = new MoveUnit(units,map,cmsg,unitBoxes);
	}
	
	public void SetHeight(int height){
		
		this.height = height;
	}
	
	public void SetWidth(int width){
		
		this.width = width;
	}
	
	public void moveUnit(int tx, int ty){
		
		move.moveUnit(tx, ty);
	}
	
	private void RegulateHovering(int x, int y) {
		// TODO Auto-generated method stub
		//System.out.println(x + " " + y + " MouseKeyboard");
		float px = (float)x/(float)width;
		float py = (float)y/(float)height;
		
		menu.Hover(px,py,units.isWorkerSelected(),buildings,map);
		
		
//		if(selectMenu(px,py,food,gold,true)){
//			
//			
//		}else{
//			
//			display.RemoveHoverPanel();
//		}
	}
	
	
	public void regulateMouse(int frameX,int frameY,int FRAME_X_SIZE,int FRAME_Y_SIZE,
			int viewedMap){
		
		//buttons.GetGroup("BuildingIcons").AreDrawn();
		//buttons.GetGroup("UnitIcons").AreDrawn();
	    if (mouse != null)
	    {
	    	double x = (double)mouse.getX()/(double)width, y = (double)mouse.getY()/(double)height;
	      
	      if(mouse.getButton() == MouseEvent.BUTTON1){
	    	  
	    	  

		      if(!selectMenu(x, y,food,gold,false)){	

		    	  //unit move
		    	  	//group
		    	  	//single
		    	  //unit attack
		    	  	//group
		    	  	//single
		    	  //follow 
		    	  //way point
		    	  //attack building
		    	  //build building 
		    	  //rebuild building
		    	  
		    	  int unitNo;
		    	  int buildingNo; 
		    	  
		    	  
//		    	  for(int u = 0; u < units.getUnitListSize(); u++){
//		    		  
//		    		  System.out.println(units.getUnitPlayer(u));
//		    	  }
		    	  
		    	  //if it's a ghost building
		    	  if(buildings.isBuildingGhost()){
		    		  
		    		  if(buildings.canBuildGhostBuilding()){
		    			  
			    		  if(buildings.isBuildingGhostWall()){
			    		  
			    			  int[] click = selectMap(x,y);
			    			  units.clearAttackSelectedUnits();
			    			  
		    				  buildings.moveGhostBuilding(click);
		    				  wallSpaces.add(new Point(click));
			    			  
			    			  if(!shiftDown){
			    				  
			    				  //WallCreator creator = new WallCreator(wallSpaces, viewedMap);
			    				  //ArrayList<int[]> walls = creator.getWallRoute();
			    				  
			    				  String msg = "bw ";
			    				  
			    				  for(int w = 0; w < wallSpaces.size(); w++){
			    					  
			    					  msg += wallSpaces.get(w).x + " " + wallSpaces.get(w).y + " ";
			    				  }
			    				  msg += "u " + units.getUnitsSelectedString();
			    				  
			    				  msg += viewedMap + " " + playerNumber;
			    				  
			    				  cmsg.addMessage(msg);
			    				  
			    				  wallSpaces.clear();
			    				  
			    				  buildings.removeGhostBuilding();
					    		  buildings.endGhostBuildingSession();
			    			  }
			    			  
			    		  }else{
			    			  
			    			  
			    			  int[] click = selectMap(x,y);
			    			  buildings.moveGhostBuilding(click);
			    			  
			    			  units.clearAttackSelectedUnits();
				    		  //buildings.begin();
				    		 // System.out.println(units.getUnitsSelectedString() + " MouseKeyboard");
				    		  cmsg.addMessage("bb " + buildings.getGhostBuildingX() + " " 
				    				  	+ buildings.getGhostBuildingY()+ " " + viewedMap + " " + 
				    				    playerNumber + " " + units.getUnitsSelectedString() 
				    				    + buildings.getGhostBuildingName());
				    		  
				    		  
				    		  buildings.removeGhostBuilding();
				    		  buildings.endGhostBuildingSession();
			    		  }
		    		  }
		    		  
		    	  //unit selected
		    	  }else if((unitNo = selectUnit(x, y, frameX, frameY)) != -1){
		    		  
//		    		  System.out.println(unitNo + " " + units.getUnitPlayer(unitNo) + " " 
//		    		  + this.playerNumber + " MouseKeyboard");
		    		  if(units.getUnitPlayerByUnitNo(unitNo) == this.playerNumber){
		    			  
		    		  	  units.addSelectedUnit(unitNo);
		    		  }
		    		  
		    		  if(buildings.selectedBuildingIsTower()){
		    				 
	    				 cmsg.addMessage("taku " + unitNo + " " + buildings.getSelectedBuildingNo());
	    				 
	    			 }else if(!move.InteractWithOtherUnit(x, y, unitNo, fDown, shiftDown,height,width,
	    					  frameX,frameY,units.getUnitPlayerByUnitNo(unitNo) != this.playerNumber)){
		    			  
		    			  
		    		  }
		    		  	
		    		
		    	  //building select
		    	  }else if((buildingNo = selectBuilding(x, y, frameX, frameY)) != -1){
		    		  
		    		  
		    		  int outcome = buildings.setSelectedBuilding(buildingNo, this.playerNumber);
		    		  
		    		  //1 - Attack building with selected unit 
		    		  if(outcome == 1){
		    			  
		    			  units.clearNonAttackSelectedUnits();
		    			  	
		    			  	if(units.getSelectedUnitsSize() > 0){
		    			  		
		    			  		//String msg = "atbl " + units.getUnitsSelectedString();
		    			  		
		    			  		cmsg.addMessage("atbl " + units.getUnitsSelectedString() + 
		    			  				buildings.getAttackBuildingNo());
		    			  	}
		    		  }
		    		  //2 - Select building 
		    		  else if(outcome == 2){
		    			  
		    			  units.clearSelectedUnits();
		    			  
		    		  }
		    		  //3 - Is site hence build with selected unit
		    		  else if(outcome == 3){
		    			  
		    			  
			    			  
	    				  units.clearAttackSelectedUnits();
		    			  if(units.getSelectedUnitsSize() > 0){
			    			  cmsg.addMessage("wnbb " + units.getUnitsSelectedString() +
			    					  buildings.getBuildBuildingBuildingNo());
		    			  }
		    			  
		    		  }
		    		  
		    	  //if the map is just being selected
		    	  }else{
		    		  
		    		  	int[] click = selectMap(x,y);
		    		  
		    		  	if(units.areWayPointSetting()){
		    			  
		    			  move.setWayPoints(click[0], click[1], shiftDown);
		    			  
		    		  	}else if(units.getSelectedUnitsSize() > 0){
			    			  
			    			 move.moveUnit(click[0],click[1]);
		    		  	}
		    		  	
		    		  	
		    	  }
		    	  
		    	  mouse = null;


	    
	    	
		  }
	    }else if(mouse.getButton() == MouseEvent.BUTTON3){
		  
			  if(buildings.isBuildingGhost()){
				  
				  buildings.removeGhostBuilding();
				  buildings.endGhostBuildingSession();
				  
			  }else{
				  
				  buildings.clearSelectedBuilding();
		    	  units.clearSelectedUnits();
			  }
			  
			  ClearBuildingIconsButtonGroup();
			  mouse = null;
		  }
	     
	      if(buildings.isBuildingGhost()){
	         int[] click = selectMap(x,y);
		  	 buildings.moveGhostBuilding(click);
	      }
	   } 
	    
	   //deal with dragging the mouse 
	   if(drag){
	    	
	    	if(inRect(0.8631039261817932,0.6014184355735779,0.9546120166778564,
	    			0.8978723287582397,lx,ly)){
	    		
	    		selectMiniMap(lx,ly);
	    		
	    		drag = false;
	    		
	    	}else{
	    	
	    		startDB = selectMap(sx,sy);
	    		lastDB = selectMap(lx,ly);
	    		
    			units.addSelectedUnits(startDB, lastDB);
    			buildings.clearSelectedBuilding();
	    	
	    		drag = false;
	    	}
	    	
	    	
	    
	    }
		
		
	    
	   
	}
	
	private int selectBuilding(double x, double y, int frameX, int frameY) {
		// TODO Auto-generated method stub
		return buildings.getSelectedBuilding(x,y,width,height,buildingBoxes,frameX,frameY);
	}

	private int selectUnit(double x, double y,int frameX,int frameY) {
		// TODO Auto-generated method stub
		
		return units.getSelectedUnit(x,y,width,height,unitBoxes,frameX,frameY);
		
//		if(unitNo == -1 || units.getUnitPlayer(unitNo) != playerNumber){
//			
//			return -1;
//		
//		}else{
//			
//			return unitNo;
//		}
	}

	public void moveMap(){
		
		double mx = (double) MouseInfo.getPointerInfo().getLocation().getX()/(double)width;
	    double my = (double) MouseInfo.getPointerInfo().getLocation().getY()/(double)height;
	    
		int square[] = selectMap(mx,my);    
		display.moveMap(square);
	}
	
	public void moveGhostBuilding(){
		
		double mx = (double) MouseInfo.getPointerInfo().getLocation().getX()/(double)width;
	    double my = (double) MouseInfo.getPointerInfo().getLocation().getY()/(double)height;
	    	
		int square[] = selectMap(mx,my);  
		
		buildings.moveGhostBuilding(square);
	}
	
	private boolean selectMenu(double x, double y,int food, int gold,boolean hover){
		
		
//		System.out.println("MENU");
		//System.out.println(x + " " + y + " MouseKeyboard");
		
		
		if(menu.RegulateMenuMouse(x, y, units.isWorkerSelected(),
				buildings.isUnitCreatorSelected(),this,map)){
			
			return true;
		}
		
		
		if(hover){
			
			return false;
		}
		
		if(inRect(0.85431916,0.5829787,0.9670571,0.9106383,x,y)){
			
			selectMiniMap(x,y);
			return true;
		}
		
		//quit button
		if(inRect(0.020558002936857563,0.004267425320056899,
				0.0381791483113069,0.03840682788051209,x,y)){
			
			System.out.println("Quit");
			return true;
		
		//pause button
		}else if(inRect(0.05066079295154185,0.002844950213371266,
				0.0697503671071953,0.03840682788051209,x,y)){
		
			System.out.println("Pause");
			return true;
		
		//save button
		}else if(inRect(0.08223201174743025,0.004267425320056899,
				0.09985315712187959,0.03698435277382646,x,y)){
			
			menu.SaveGameMenu();
			return true;
		}else{
			
			return selectMapInfo(x,y);
		}
		
		
	}
	
	private boolean inRect(double xs,double ys,double xl,double yl, double xa, double ya){
		
		return (xa >= xs && xa <= xl && ya >= ys && ya <= yl);
	}
	
	private int[] selectMap(double mx, double my){

		//check with square has been selected 
//		for(int y = 0; y < 25; y++){
//			for(int x = 0; x < 40; x++){
//				if(mx >= 191 + (24*x) 
//						&& mx <= 215 + (24*x)
//						&& my >= 659 - (25*y)
//						&& my <= 684 - (25*y)){
//				
//					return new int[]{x+frameX,y+frameY};
//				}
//			}
		//0.1398243010044098 + (x*(0.03513909876/2.0))
		//0.96505049 - (y*(0.06950354576/2.0)),
		//0.15739385783672333 + (x*(0.03513909876/2.0)),
		//0.99776741 - (y*(0.06950354576/2.0)),
//		for(int y = 0; y < display.getFrameSizeY(); y++){
//			for(int x = 0; x < display.getFrameSizeX(); x++){
//				//0.99776741 //0.96505049 //0.9347517490386963 // 0.9702127575874329
//				if(inRect(0.0 + (x*(0.03513909876/2.0)),
//						0.748221906116643 - (y*(0.06950354576/2.0)),
//						0.01762114537444934 + (x*(0.03513909876/2.0)),
//						0.7809388335704125 - (y*(0.06950354576/2.0)),
//							mx,my)){
//					
//					int difference = (int)(Math.abs((mx - 0.145 + (x*(0.03513909876/2.0)))) +
//							Math.abs((my - 0.955 + (y*(0.06950354576/2.0))))) * 1000;
//					
//					//System.out.println(x + " " + y + " MouseKeyboard");
//					return display.getFrameAdjustedPos(new int[]{x,y,difference});
//					
//				}
//			}
//		}
		
		//0.013048016701461378,0.7567567567567568 MouseKeyboard
		//////////////////////////////
		//0.029749478079331943,0.7237237237237237 MouseKeyboard
		//////////////////////////////
		//diffX = 0.01670146138  diffY = -0.033033033033
		//0.01756954938
		//0.748221906116643   0.03475177288
		
		int x = (int)((mx-0.003653444679) / 0.01670146138);
		int y = (int)((0.789789789789789 - my)/0.033033033033);
		
		if(x >= display.getFrameSizeX() || y >= display.getFrameSizeY()){
			
			return new int[]{-1,-1,-1};
		}
		
//		int difference = (int)(Math.abs((mx - 0.145 + (x*(0.03513909876/2.0)))) +
//				Math.abs((my - 0.955 + (y*(0.06950354576/2.0))))) * 1000;
		
		
		return display.getFrameAdjustedPos(new int[]{x,y});
		
	}
	
	private void selectMiniMap(double x, double y){
		
		float squareSizeX = ((float) (0.9560761346998536-0.8638360175695461))/ (float) map.getWidth();
		float squareSizeY = ((float) (0.8964539007092198-0.6042553191489362))/ (float) map.getHeight();
		
		int squareX = 0;
		int squareY = map.getHeight();
		
		for(double xc = 0.8638360175695461; xc <= 0.9560761346998536; xc+=squareSizeX){
			
			if(xc > x){
				
				break;
				
			}else{
				
				squareX++;
			}
		}
		
		for(double yc = 0.6042553191489362; yc <= 0.8964539007092198; yc += squareSizeY){
			
			if(yc > y){
				
				break;
			}else{
				
				squareY--;
			}
			
		}
		
		display.handleMiniMapSelection(squareX, squareY);
		

	}

	private boolean selectMapInfo(double x, double y){
		//0.86f,0.56f
		double iy = 1.0 - y;

		for(int m = 0; m < map.getMapListSize(); m++){
			
			if(x <= 0.9655929721815519 && x >= 0.8535871156661786 && 
					iy >= 0.56+(m*0.0325) && 
							iy <= 0.56 + ((m+1)*0.0325)){
			//	buttons.Selected(map.getMapName(m));
				cmsg.addMessage("vwmp "+ m + " " + playerNumber);
				return true;
			}
			
		}
	
		return false;
	}
	
	
	public void handleMouseDragged(MouseEvent e,int frameX, int frameY, 
			int FRAME_X_SIZE, int FRAME_Y_SIZE, int viewedMap){
		
		if(!dragBox){
			
			sx = (double)e.getX()/(double)width;
			sy = (double)e.getY()/(double)height;
		}else{
			
			lx = (double)e.getX()/(double)width;
			ly = (double)e.getY()/(double)height;
		}
		menu.DragMenuMouse(e.getX()/(double)width, e.getY()/(double)height,
				map.getWidth(), map.getHeight(), this);
		drag = true;
		dragBox = true;
		mouse = e;
		
		regulateMouse(frameX, frameY, FRAME_X_SIZE, FRAME_Y_SIZE, viewedMap);
	}
	
	public void handleMouseClicked(MouseEvent e,int frameX, int frameY, 
			int FRAME_X_SIZE, int FRAME_Y_SIZE, int viewedMap){
		
		//System.out.println("click");
		//System.out.println(e.getX() + " " + e.getY());
		mouse = e;
		dragBox = false;
		drag = false;
		
		//to remove a left over drag box 
		startDB = null;
		lastDB = null;
		
		regulateMouse(frameX, frameY, FRAME_X_SIZE, FRAME_Y_SIZE, viewedMap);
	}
	
	public void handleHover(MouseEvent e){//,int frameX, int frameY, 
			//int FRAME_X_SIZE, int FRAME_Y_SIZE, int viewedMap){
		
		//RegulateHovering(e.getX()/display.getScreenWidth(),e.getY()/display.getScreenHeight());
		RegulateHovering(e.getX(),e.getY());
		//regulateMouse(frameX, frameY, FRAME_X_SIZE, FRAME_Y_SIZE, viewedMap);
	}
	
	

	public void handleKeyboardPressed(KeyEvent e){
		
		//System.out.println("MOUSEKEYBOARD KEY PRESSED: " + e.getKeyChar() + " " + e.isShiftDown());
		if(e.isShiftDown()){
			
			shiftDown = true;
			units.setWayPointSetting(true);
		
		}else if(e.getKeyChar() == 'f'){
			
			//System.out.println("FDOWN");
			fDown = true;

		}
		
		menu.RegisterKeyStroke(e.getKeyChar());
		
	}
	
	public void handleKeyboardReleased(KeyEvent e){
		
		//System.out.println(e.getKeyChar() + " released");
		if(!e.isShiftDown()){
			
			shiftDown = false;
		
		}
		
		if(e.getKeyChar() == 'f'){
			
			fDown = false;
		}

	}

	@Override
	public boolean isInDragBox(int x, int y) {
		// TODO Auto-generated method stub
		return (dragBox &&
				lastDB != null && startDB != null 
				&& lastDB[0] != -1 && startDB[0] != -1
				&& y <= Math.max(lastDB[1],startDB[1]) 
				&& y >= Math.min(lastDB[1],startDB[1])
				&& x <= Math.max(lastDB[0],startDB[0]) 
				&& x >= Math.min(lastDB[0],startDB[0]));
	}

	@Override
	public void setResources(int food, int gold) {
		// TODO Auto-generated method stub
		this.food = food;
		this.gold = gold;
	}

	public void handleReleasedMouse(MouseEvent e,int frameX, int frameY, 
			int FRAME_X_SIZE, int FRAME_Y_SIZE, int viewedMap) {
		// TODO Auto-generated method stub
		lastDB = null;
		startDB = null;
		drag = false;
		dragBox = false;
		regulateMouse(frameX, frameY, FRAME_X_SIZE, FRAME_Y_SIZE, viewedMap);
	}

//	@Override
//	public void setButtonList(ButtonList buttons) {
//		// TODO Auto-generated method stub
//		this.buttons = buttons;
//	}
	
	public void ClearBuildingIconsButtonGroup(){
		
//		buttons.GetGroup("BuildingIcons").Clear();
//		display.CreateBuildingIconButtons();
	}

	@Override
	public void SelectGhostBuilding(String buildingName) {
		// TODO Auto-generated method stub
		if(!BuildingModelList.SetEnoughRes(new Building(buildingName,playerNumber), 
				gold, food)){
			buildings.setGhostBuilding(new Building(buildingName,playerNumber));
		}
		
	}

	@Override
	public void SelectUnitToBuild(int unitNo) {
		// TODO Auto-generated method stub
		buildings.unitIconSelected(unitNo,food,gold);
	}

	@Override
	public void MoveFrame(int[] frame) {
		// TODO Auto-generated method stub
		display.setFrameX(frame[0] - (display.getFrameSizeX()/2));
		display.setFrameY(frame[1] - (display.getFrameSizeY()/2));
	}

	@Override
	public void SetViewedMap(int mapNo) {
		// TODO Auto-generated method stub
		cmsg.addMessage("vwmp "+ mapNo + " " + playerNumber);
	}

	@Override
	public void SaveGame(String fileName) {
		// TODO Auto-generated method stub
		cmsg.addMessage("sgme " + fileName);
	}
	
//	public void ClearUnitIconsButtonGroup(){
//		
//		display.CreateUnitIcons();
//	}
	
//	  if(units.getSelectedUnitsSize() > 0 && !buildings.isBuildingGhost()){
//	  
//	  int outcome;
//	  if((outcome = buildings.setSelectedBuilding(selectBuilding(x,y,frameX,frameY)
//			  ,this.playerNumber)) == 0){
//		  
//		//System.out.println(units.getSelectedUnitsSize() + " regulateMouse");
//	  	move.moveUnit(click[0],click[1],this.selectUnit(x, y, frameX, frameY)
//	  			,fDown,shiftDown);
//	  	buildings.clearSelectedBuilding();
//	  
//	  }else if(outcome == 1){
//	
//		  	units.clearNonAttackSelectedUnits();
//		  	
//		  	if(units.getSelectedUnitsSize() > 0){
//		  		
//		  		//String msg = "atbl " + units.getUnitsSelectedString();
//		  		
//		  		cmsg.addMessage("atbl " + units.getUnitsSelectedString() + 
//		  				buildings.getAttackBuildingNo());
//		  	}
//	  
//	  }else if(outcome == 3){
//		  
//		  units.clearAttackSelectedUnits();
//		  if(units.getSelectedUnitsSize() > 0){
//			  cmsg.addMessage("wnbb " + units.getUnitsSelectedString() +
//					  buildings.getBuildBuildingBuildingNo());
//		  }
//		  
//	  }else{
//		  
//		  
//		  units.clearSelectedUnits();
//	  }
//
//	  //ClearBuildingIconsButtonGroup();
//	  
//}else if(buildings.isBuildingGhost() && buildings.canBuildGhostBuilding()){
//	  
//	  units.clearAttackSelectedUnits();
//	  //buildings.begin();
//	 // System.out.println(units.getUnitsSelectedString() + " MouseKeyboard");
//	  cmsg.addMessage("bb " + buildings.getGhostBuildingX() + " " + buildings.getGhostBuildingY()
//			   + " " + viewedMap + " " + playerNumber + " " + units.getUnitsSelectedString() 
//			   + buildings.getGhostBuildingName());
//	  
//	  
//	  buildings.removeGhostBuilding();
//	  buildings.endGhostBuildingSession();
//	 // buildings.end();
//
//}else{
//
//	  if(buildings.setSelectedBuilding(selectBuilding(x,y,frameX,frameY), 
//			  this.playerNumber) == 0){
//		 
//		 if(buildings.selectedBuildingIsTower()){
//			 
//			 int unitNo = units.getUnitAtttack(x,y,width,height,unitBoxes,frameX,frameY);
//			 cmsg.addMessage("taku " + unitNo + " " + buildings.getSelectedBuildingNo());
//			 
//		 }else{
//
//			 int unitNo = selectUnit(x,y,frameX,frameY);
//			 System.out.println(unitNo + " MouseKeyboard");
//			 units.addSelectedUnit(unitNo);
//			 buildings.clearSelectedBuilding();
//		 }
//		 
//		 
//		 
//	  }else{
//		  menu.ClearUnitIcons();
//		 // ClearUnitIconsButtonGroup();
//	  }
//	  
//	  
//	  //ClearBuildingIconsButtonGroup();
//}
//}
//mouse = null;
//
//}else if(mouse.getButton() == MouseEvent.BUTTON3){
//
//if(buildings.isBuildingGhost()){
//
//buildings.removeGhostBuilding();
//buildings.endGhostBuildingSession();
//
//}else{
//
//buildings.clearSelectedBuilding();
//units.clearSelectedUnits();
//}
//
//ClearBuildingIconsButtonGroup();
//mouse = null;
//}

//    }

}
