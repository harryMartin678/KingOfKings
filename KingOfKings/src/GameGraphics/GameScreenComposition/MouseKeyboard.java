package GameGraphics.GameScreenComposition;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Buildings.Names;
import GameClient.ClientMessages;
import GameGraphics.Building;

public class MouseKeyboard implements IComMouseKeyboard,IComMouseFrameProcess {
	
	private MouseEvent mouse;
	private boolean drag = false;
	private boolean dragBox = false;
	private double sx,sy,lx,ly;
	private int[] startDB,lastDB;
	private int width;
	private int height;
	private boolean fDown;
	private boolean shiftDown;
	
	private IComUnitListMouseKeyboard units;
	private IComBuildingListMouseKeyboard buildings;
	private IComDisplayMouseKeyboard display;
	private IComMapMouseKeyboard map;
	private int playerNumber;
	private ClientWrapper cmsg;
	private MoveUnit move;
	
	private int food;
	private int gold;
	
	private ButtonList buttons;
	
	public MouseKeyboard(){
		
		
	}
	
	public void setUpMouseKeyboard(IComUnitListMouseKeyboard units,
			IComBuildingListMouseKeyboard buildings, IComDisplayMouseKeyboard display,
			IComMapMouseKeyboard map,int playerNumber,ClientWrapper cmsg){
		
		this.units = units;
		this.buildings = buildings;
		this.display = display;
		this.map = map;
		this.cmsg = cmsg;
		
		this.playerNumber = playerNumber;
		
		move = new MoveUnit(units,map,cmsg);
	}
	
	public void SetHeight(int height){
		
		this.height = height;
	}
	
	public void SetWidth(int width){
		
		this.width = width;
	}
	
	public void moveUnit(int tx, int ty,int difference){
		
		move.moveUnit(tx, ty,difference, fDown, shiftDown);
	}
	
	
	public void regulateMouse(int frameX,int frameY,int FRAME_X_SIZE,int FRAME_Y_SIZE,int viewedMap){
		
		buttons.GetGroup("BuildingIcons").AreDrawn();
		buttons.GetGroup("UnitIcons").AreDrawn();
	    if (mouse != null)
	    {
	      
	      if(mouse.getButton() == MouseEvent.BUTTON1){
	    	  
	    	  double x = (double)mouse.getX()/(double)width, y = (double)mouse.getY()/(double)height;
	    	  
//	    	  System.out.println(x + " " + y + " MouseKeyboard");
//	    	  System.out.println("////////////////////////////");

		      if(!selectMenu(x, y,food,gold)){	
		        	
		    	  int[] click = selectMap(x,y);

		    	  if(units.getSelectedUnitsSize() > 0 && !buildings.isBuildingGhost()){
		    		  
		    		  int outcome;
		    		  if((outcome = buildings.setSelectedBuilding(click[0], click[1],this.playerNumber)) == 0){
		    			  
		    			//System.out.println(units.getSelectedUnitsSize() + " regulateMouse");
		    		  	move.moveUnit(click[0],click[1],click[2],fDown,shiftDown);
		    		  	buildings.clearSelectedBuilding();
		    		  
		    		  }else if(outcome == 1){
		    		
		    			  	units.clearNonAttackSelectedUnits();
		    			  	
		    			  	if(units.getSelectedUnitsSize() > 0){
		    			  		
		    			  		//String msg = "atbl " + units.getUnitsSelectedString();
		    			  		
		    			  		cmsg.addMessage("atbl " + units.getUnitsSelectedString() + 
		    			  				buildings.getAttackBuildingNo());
		    			  	}
		    		  
		    		  }else if(outcome == 3){
		    			  
		    			  units.clearAttackSelectedUnits();
		    			  if(units.getSelectedUnitsSize() > 0){
			    			  cmsg.addMessage("wnbb " + units.getUnitsSelectedString() +
			    					  buildings.getBuildBuildingBuildingNo());
		    			  }
		    			  
		    		  }else{
		    			  
		    			  
		    			  units.clearSelectedUnits();
		    		  }

		    		  ClearBuildingIconsButtonGroup();
		    		  
		    	  }else if(buildings.isBuildingGhost() && buildings.canBuildGhostBuilding()){
		    		  
		    		  units.clearAttackSelectedUnits();
		    		  //buildings.begin();
		    		 // System.out.println(units.getUnitsSelectedString() + " MouseKeyboard");
		    		  cmsg.addMessage("bb " + buildings.getGhostBuildingX() + " " + buildings.getGhostBuildingY()
		    				   + " " + viewedMap + " " + playerNumber + " " + units.getUnitsSelectedString() 
		    				   + buildings.getGhostBuildingName());
		    		  
		    		  
		    		  buildings.removeGhostBuilding();
		    		  buildings.endGhostBuildingSession();
		    		 // buildings.end();
		    	  
		    	  }else{
		    	  
		    		  if(buildings.setSelectedBuilding(click[0], click[1], this.playerNumber) == 0){
		    			 
		    			 if(buildings.selectedBuildingIsTower()){
		    				 
		    				 int unitNo = units.getUnitAtttack(click);
		    				 cmsg.addMessage("taku " + unitNo + " " + buildings.getSelectedBuildingNo());
		    				 
		    			 }else{

		    				 
		    				 units.addSelectedUnit(click);
			    			 buildings.clearSelectedBuilding();
		    			 }
		    			 
		    			 
		    			 
		    		  }else{
		    			  ClearUnitIconsButtonGroup();
		    		  }
		    		  
		    		  
		    		  ClearBuildingIconsButtonGroup();
		    	  }
		       }
	    	   mouse = null;
	     
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
	    	
	    double mx = (double) MouseInfo.getPointerInfo().getLocation().getX()/(double)width;
	    double my = (double) MouseInfo.getPointerInfo().getLocation().getY()/(double)height;
	    	
		int square[] = selectMap(mx,my);    
	
		display.moveMap(square);
		
		buildings.moveGhostBuilding(square);
	   
	}
	
	
	private boolean selectMenu(double x, double y,int food, int gold){
		
		
//		System.out.println("MENU");
//		System.out.println(xa + " " + ya);
		
		if(inRect(0.011713031,0.5829787,0.12445095,0.9106383,x,y)){
			
			if(units.isWorkerSelected()){
				
				selectBuildingIcons(x, y,food,gold);
			
			}else if(buildings.isBuildingSelected()){
				
				selectUnitIcons(x,y,food,gold);
			}
			return true;
		}
		
		if(inRect(0.85431916,0.5829787,0.9670571,0.9106383,x,y)){
			
			selectMiniMap(x,y);
			return true;
		}
		
		//quit button
		if(inRect(0.715959,0.012765957,0.8250366,0.09219858,x,y)){
			
			System.out.println("Quit");
			return true;
		
		//pause button
		}else if(inRect(0.44582725,0.012765957,0.5534407,0.09078014,x,y)){
		
			System.out.println("Pause");
			return true;
		
		//save button
		}else if(inRect(0.14787701,0.012765957,0.25549048,0.09219858,x,y)){
			
			System.out.println("Save");
			return true;
		}else{
			
			return selectMapInfo(x,y);
		}
		
		
	}
	
	private void selectUnitIcons(double x, double y,int food, int gold){
//		0.012445095168374817 0.5843971631205673
//		0.05197657393850659 0.6425531914893617
		ButtonGroup group = buttons.GetGroup("UnitIcons");
		
		for(int ys = 0; ys < 2; ys++){
			for(int xs = 0; xs < 3; xs++){
				
				if(xs + (ys*3) >= group.Size()){
					
					break;
				}
				
				if(inRect(0.015418502202643172 + (0.02569750364*xs),
						0.5843971631205673 + (0.0581560283687944*ys),
						0.0315712187958884 + (0.02569750364*xs),
						0.6425531914893617 + (0.0581560283687944*ys),x,y)){
					
					group.SelectedByIndex((group.Size()-1)- (xs + (ys*3)));
					buildings.unitIconSelected(xs + (ys*3),food,gold);
					break;
				}
			}
		}
	}
	
	private void selectBuildingIcons(double x, double y,int food, int gold){
		
		ButtonGroup group = buttons.GetGroup("BuildingIcons");
		//1 1
		if(inRect(0.01762114537444934,0.5931721194879089,0.04331864904552129,0.6443812233285917
				,x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.ARCHERYTOWER,playerNumber), gold, food)){
			
			//System.out.println(1 + " " + 1);
			group.Selected(Names.ARCHERYTOWER);
			buildings.setGhostBuilding(new Building(Names.ARCHERYTOWER,playerNumber));
		
		//2 1
		}else if(inRect(0.05506607929515418,0.5917496443812233,0.08223201174743025,0.6443812233285917,
				x,y)&& !BuildingModelList.SetEnoughRes(new Building(Names.BALLISTICTOWER,playerNumber), gold, food)){
			
			group.Selected(Names.BALLISTICTOWER);
			//System.out.println(2 + " " + 1);
			buildings.setGhostBuilding(new Building(Names.BALLISTICTOWER,playerNumber));
		
		//3 1
		}else if(inRect(0.0947136563876652,0.5931721194879089,0.1204111600587371,0.6443812233285917,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.BARRACK,playerNumber), gold, food)){
			
			group.Selected(Names.BARRACK);
			//System.out.println(3 + " " + 1);
			buildings.setGhostBuilding(new Building(Names.MINE,playerNumber));
		
		//1 2
		}else if(inRect(0.01762114537444934,0.6813655761024182,0.042584434654919234,0.7297297297297297,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.CASTLE,playerNumber), gold, food)){
			
			group.Selected(Names.CASTLE);
			//System.out.println(1 + " " + 2);
			buildings.setGhostBuilding(new Building(Names.CASTLE,playerNumber));
		
		//2 2
		}else if(inRect(0.055800293685756244,0.6813655761024182,0.08223201174743025,0.7297297297297297
				,x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.DOCK,playerNumber), gold, food)){
			
			//System.out.println(2 + " " + 2);
			buildings.setGhostBuilding(new Building(Names.DOCK,playerNumber));
		
		//3 2
		}else if(inRect(0.0947136563876652,0.6813655761024182,0.1196769456681351,0.7297297297297297,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.FARM,playerNumber), gold, food)){
			
			group.Selected(Names.FARM);
			//System.out.println(3 + " " + 2);
			buildings.setGhostBuilding(new Building(Names.FARM,playerNumber));
			
		//1 3
		}else if(inRect(0.016886930983847283,0.7681365576102418,0.042584434654919234,0.8165007112375533,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.FORT,playerNumber), gold, food)){
			
			group.Selected(Names.FORT);
			//System.out.println(1 + " " + 3);
			buildings.setGhostBuilding(new Building(Names.FORT,playerNumber));
		
		//2 3
		}else if(inRect(0.055800293685756244,0.7667140825035562,0.08223201174743025,0.817923186344239,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.STABLE,playerNumber), gold, food)){
			
			group.Selected(Names.STABLE);
			//System.out.println(2 + " " + 3);
			buildings.setGhostBuilding(new Building(Names.STABLE,playerNumber));
		
		//3 3
		}else if(inRect(0.0947136563876652,0.7695590327169275,0.12041116005873716,0.8193456614509246,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.STOCKPILE,playerNumber), gold, food)){
			
			group.Selected(Names.STOCKPILE);
			//System.out.println(3 + " " + 3);
			buildings.setGhostBuilding(new Building(Names.STOCKPILE,playerNumber));
		
		//1 4
		}else if(inRect(0.01762114537444934,0.8563300142247511,0.04331864904552129,0.9061166429587483,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.WALL,playerNumber), gold, food)){
			
			group.Selected(Names.WALL);
			//System.out.println(1 + " " + 4);
			buildings.setGhostBuilding(new Building(Names.WALL,playerNumber));
		
		//2 4
//		}else if(inRect(0.058565154671669006,0.8269503712654114,0.08857979625463486,0.9092198610305786,
//				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.BALLISTICTOWER,playerNumber), gold, food)){
//			
//			group.Selected(Names.BALLISTICTOWER);
//			//System.out.println(2 + " " + 4);
//			buildings.setGhostBuilding(new Building(Names.BALLISTICTOWER,playerNumber));
//		
//		//3 4
		}else if(inRect(0.0947136563876652,0.8549075391180654,0.12041116005873716,0.9061166429587483,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.MINE,playerNumber), gold, food)){
			
			group.Selected(Names.MINE);
			//System.out.println(3 + " " + 4);
			buildings.setGhostBuilding(new Building(Names.MINE,playerNumber));
			
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
		
		
		
		for(int y = 0; y < 25; y++){
			for(int x = 0; x < 40; x++){
			
				if(inRect(0.1398243010044098 + (x*(0.03513909876/2.0)),
						0.9347517490386963 - (y*(0.06950354576/2.0)),
						0.15739385783672333 + (x*(0.03513909876/2.0)),
						0.9702127575874329 - (y*(0.06950354576/2.0)),
							mx,my)){
					
					int difference = (int)(Math.abs((mx - 0.145 + (x*(0.03513909876/2.0)))) +
							Math.abs((my - 0.955 + (y*(0.06950354576/2.0))))) * 1000;
					
					return display.getFrameAdjustedPos(new int[]{x,y,difference});
					
				}
			}
		}
		
		return new int[]{-1,-1,-1};
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
				buttons.Selected(map.getMapName(m));
				cmsg.addMessage("vwmp "+ m + " " + playerNumber);
				return true;
			}
			
		}
	
		return false;
	}
	
	
	public void handleMouseDragged(MouseEvent e){
		
		if(!dragBox){
			
			sx = (double)e.getX()/(double)width;
			sy = (double)e.getY()/(double)height;
		}else{
			
			lx = (double)e.getX()/(double)width;
			ly = (double)e.getY()/(double)height;
		}
		drag = true;
		dragBox = true;
		mouse = e;
	}
	
	public void handleMouseClicked(MouseEvent e){
		
		//System.out.println("click");
		//System.out.println(e.getX() + " " + e.getY());
		mouse = e;
		dragBox = false;
		drag = false;
		
		//to remove a left over drag box 
		startDB = null;
		lastDB = null;
	}
	
	public void handleKeyboardPressed(KeyEvent e){
		
		System.out.println(e.getKeyChar() + " pressed");
		//System.out.println("MOUSEKEYBOARD KEY PRESSED: " + e.getKeyChar() + " " + e.isShiftDown());
		if(e.isShiftDown()){
			
			shiftDown = true;
			units.setWayPointSetting(true);
		
		}else if(e.getKeyChar() == 'f'){
			
			//System.out.println("FDOWN");
			fDown = true;

		}
		
	}
	
	public void handleKeyboardReleased(KeyEvent e){
		
		System.out.println(e.getKeyChar() + " released");
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

	public void handleReleasedMouse(MouseEvent e) {
		// TODO Auto-generated method stub
		lastDB = null;
		startDB = null;
		drag = false;
		dragBox = false;
	}

	@Override
	public void setButtonList(ButtonList buttons) {
		// TODO Auto-generated method stub
		this.buttons = buttons;
	}
	
	public void ClearBuildingIconsButtonGroup(){
		
		buttons.GetGroup("BuildingIcons").Clear();
		display.CreateBuildingIconButtons();
	}
	
	public void ClearUnitIconsButtonGroup(){
		
		display.CreateUnitIcons();
	}
	

}
