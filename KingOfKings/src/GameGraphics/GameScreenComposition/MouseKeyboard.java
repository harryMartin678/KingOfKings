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
	
	public void moveUnit(int tx, int ty){
		
		move.moveUnit(tx, ty, fDown, shiftDown);
	}
	
	
	public void regulateMouse(int frameX,int frameY,int FRAME_X_SIZE,int FRAME_Y_SIZE,int viewedMap){
		
	    if (mouse != null)
	    {
	      
	      if(mouse.getButton() == MouseEvent.BUTTON1){
	    	  
	    	  double x = (double)mouse.getX()/(double)width, y = (double)mouse.getY()/(double)height;
	
		      if(!selectMenu(x, y,food,gold)){	
		        	
		    	  int[] click = selectMap(x,y);

		    	  if(units.getSelectedUnitsSize() > 0 && !buildings.isBuildingGhost()){
		    		  
		    		  int outcome;
		    		  if((outcome = buildings.setSelectedBuilding(click[0], click[1],this.playerNumber)) == 0){
		    			  
		    			//System.out.println(units.getSelectedUnitsSize() + " regulateMouse");
		    		  	move.moveUnit(click[0],click[1],fDown,shiftDown);
		    		  	buildings.clearSelectedBuilding();
		    		  
		    		  }else if(outcome == 1){
		    		
		    			  	units.clearNonAttackSelectedUnits();
		    			  	
		    			  	if(units.getSelectedUnitsSize() > 0){
		    			  		
		    			  		//String msg = "atbl " + units.getUnitsSelectedString();
		    			  		
		    			  		cmsg.addMessage("atbl " + units.getUnitsSelectedString() + 
		    			  				buildings.getAttackBuildingNo());
		    			  	}
		    		  
		    		  }else{
		    			  
		    			  units.clearSelectedUnits();
		    		  }
		    		  
		    	  }else if(buildings.isBuildingGhost() && buildings.canBuildGhostBuilding()){
		    		  
		    		  
		    		  units.clearAttackSelectedUnits();
		    		  //buildings.begin();
		    		 // System.out.println(units.getUnitsSelectedString() + " MouseKeyboard");
		    		  cmsg.addMessage("bb " + buildings.getGhostBuildingX() + " " + buildings.getGhostBuildingY()
		    				   + " " + viewedMap + " " + units.getUnitsSelectedString() 
		    				   + buildings.getGhostBuildingName() );
		    		  
		    		  
		    		  buildings.removeGhostBuilding();
		    		  buildings.endGhostBuildingSession();
		    		 // buildings.end();
		    	  
		    	  }else{
		    	  
		    		  if(buildings.setSelectedBuilding(click[0], click[1], this.playerNumber) == 0){
		    			 
		    			 units.addSelectedUnit(click);
		    			 buildings.clearSelectedBuilding();
		    			 
		    		  }else{
		    			  
		    			  
		    		  }
		    	  }
		       }
	    	   mouse = null;
	     
	      }else if(mouse.getButton() == MouseEvent.BUTTON3){
	    		  
	    	  buildings.clearSelectedBuilding();
	    	  buildings.removeGhostBuilding();
	    	  buildings.endGhostBuildingSession();
	    	  units.clearSelectedUnits();
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
		
		
		for(int ys = 0; ys < 2; ys++){
			for(int xs = 0; xs < 3; xs++){
				
				if(inRect(0.012445095168374817 + (0.0395314787701317*xs),
						0.5843971631205673 + (0.0581560283687944*ys),
						0.05197657393850659 + (0.0395314787701317*xs),
						0.6425531914893617 + (0.0581560283687944*ys),x,y)){
					
					buildings.unitIconSelected(xs + (ys*3),food,gold);
				}
			}
		}
	}
	
	private void selectBuildingIcons(double x, double y,int food, int gold){
		
		//1 1
		if(inRect(0.013177159242331982,0.5843971371650696,0.05490483343601227,0.6425532102584839
				,x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.STOCKPILE), gold, food)){
			
			//System.out.println(1 + " " + 1);
			buildings.setGhostBuilding(new Building(Names.STOCKPILE));
		
		//2 1
		}else if(inRect(0.05710102617740631,0.5829787254333496,0.0907759889960289,0.6397163271903992,
				x,y)&& !BuildingModelList.SetEnoughRes(new Building(Names.WALL), gold, food)){
			
			//System.out.println(2 + " " + 1);
			buildings.setGhostBuilding(new Building(Names.WALL));
		
		//3 1
		}else if(inRect(0.0922401174902916,0.5829787254333496,0.1229868233203888,0.6368794441223145,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.MINE), gold, food)){
			
			//System.out.println(3 + " " + 1);
			buildings.setGhostBuilding(new Building(Names.MINE));
		
		//1 2
		}else if(inRect(0.012445094995200634,0.6439716219902039,0.04904831573367119,0.7191489338874817,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.FORT), gold, food)){
			
			//System.out.println(1 + " " + 2);
			buildings.setGhostBuilding(new Building(Names.FORT));
		
		//2 2
		}else if(inRect(0.055636897683143616,0.6397163271903992,0.0893118605017662,0.7262411117553711
				,x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.ROYALPALACE), gold, food)){
			
			//System.out.println(2 + " " + 2);
			//buildings.setGhostBuilding(new Building(Names.ROYALPALACE));
		
		//3 2
		}else if(inRect(0.09297218173742294,0.6354609727859497,0.1229868233203888,0.7248227000236511,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.STABLE), gold, food)){
			
			//System.out.println(3 + " " + 2);
			buildings.setGhostBuilding(new Building(Names.STABLE));
			
		//1 3
		}else if(inRect(0.012445094995200634,0.7276595830917358,0.059297218918800354,0.8283687829971313,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.CASTLE), gold, food)){
			
			//System.out.println(1 + " " + 3);
			buildings.setGhostBuilding(new Building(Names.CASTLE));
		
		//2 3
		}else if(inRect(0.0614934116601944,0.7290779948234558,0.08711566776037216,0.8184397220611572,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.DOCK), gold, food)){
			
			//System.out.println(2 + " " + 3);
			buildings.setGhostBuilding(new Building(Names.DOCK));
		
		//3 3
		}else if(inRect(0.0907759889960289,0.7262411117553711,0.12225475907325745,0.8141843676567078,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.FARM), gold, food)){
			
			//System.out.println(3 + " " + 3);
			buildings.setGhostBuilding(new Building(Names.FARM));
		
		//1 4
		}else if(inRect(0.012445094995200634,0.8382978439331055,0.04758418723940849,0.9063829779624939,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.ARCHERYTOWER), gold, food)){
			
			//System.out.println(1 + " " + 4);
			buildings.setGhostBuilding(new Building(Names.ARCHERYTOWER));
		
		//2 4
		}else if(inRect(0.058565154671669006,0.8269503712654114,0.08857979625463486,0.9092198610305786,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.BALLISTICTOWER), gold, food)){
			
			//System.out.println(2 + " " + 4);
			buildings.setGhostBuilding(new Building(Names.BALLISTICTOWER));
		
		//3 4
		}else if(inRect(0.08784773200750351,0.8255318999290466,0.1229868233203888,0.9063829779624939,
				x,y) && !BuildingModelList.SetEnoughRes(new Building(Names.BARRACK), gold, food)){
			
			//System.out.println(3 + " " + 4);
			buildings.setGhostBuilding(new Building(Names.BARRACK));
			
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
					
					return display.getFrameAdjustedPos(new int[]{x,y});
					
				}
			}
		}
		
		return new int[]{-1,-1};
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
	
		for(int m = 0; m < map.getMapListSize(); m++){
			
			if(x <= 0.9655929721815519 && x >= 0.8535871156661786 && 
					y >= 0.41702127659574467-(m*0.0283687943262412) && 
					y <= 0.4453900709219858 - (m*0.0283687943262412)){
				
				cmsg.addMessage("vwmp "+m);
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
	

}
