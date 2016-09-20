package GameGraphics.GameScreenComposition;

import GameGraphics.IBoundingBoxes;

public class MoveUnit {
	
	private IComUnitListMouseKeyboard units;
	private IComMapMouseKeyboard map;
	private ClientWrapper cmsg;
	private IBoundingBoxes boxes;
	
	public MoveUnit(IComUnitListMouseKeyboard units,IComMapMouseKeyboard map,
			ClientWrapper cmsg,IBoundingBoxes boxes){
		
		this.units = units;
		this.map = map;
		this.cmsg = cmsg;
		this.boxes = boxes;
	}
	
	private void singleFollow(int unitNo){
		
		units.addSelectedUnit(unitNo);
		
		//System.out.println(units.getSelectedUnitsSize() + " singleFollow");
		  
		if(units.getSelectedUnitsSize()  == 1){
		  
			units.clearSelectedUnits();

		}else{
		  
			int unitFollow = units.getFollowUnit();
		  	int unitSelected = units.getBaseSelectedUnit();
		  
		  	units.clearSelectedUnits();
		  
		  	cmsg.addMessage("utfl " + unitSelected + " " + unitFollow);
		}
	}
	
	private void singleAttack(int unitAttack){
		
//		 int unitAttack;
//		 
//		 System.out.println("ATTACK UNIT MOVEUNIT");
//		  
//		  if((unitAttack = getSelectedUnitToAttack(tx,ty,ScreenHeight,ScreenWidth,frameX,frameY)) != -1){
//			  
//		    
		  cmsg.addMessage("utak " + units.getBaseSelectedUnit() + " " +
				  unitAttack);
		  units.removeBaseUnit();
		  
//		  }else{
//			  
//			  return false;
//		  }
//		  else{
//		  
//			  cmsg.addMessage("utat " + units.getBaseSelectedUnit() + " "
//	  				  + tx + " " + ty + " " + map.getViewedMap());
//			  units.removeBaseUnit();
//		  }
	}
	
	private int getSelectedUnitToAttack(double tx, double ty,int ScreenWidth,int ScreenHeight,int frameX,
			int frameY) {
		// TODO Auto-generated method stub
		return units.getUnitAtttack(tx, ty, ScreenWidth, ScreenHeight, boxes, frameX, frameY);
	}

	private void singleWayPoint(int tx, int ty,boolean shiftDown){
		
		units.addWayPoints(new int[]{tx,ty,map.getViewedMap()});
		  
		  if(!shiftDown){
			  
			  //space at end of points
			  cmsg.addMessage("utwp " + units.getBaseSelectedUnit() + " " + units.getWayPoints());
			  
			  units.removeBaseUnit();
			  units.setWayPointSetting(false);
			  
			  units.clearWayPoints();
		  
		  }
	}
	
	private void groupFollow(int unitNo){
		
		int oldSize = units.getSelectedUnitsSize();
		
		  units.addSelectedUnit(unitNo);
		  
		  if(units.getSelectedUnitsSize() == oldSize){
			  
			  units.clearSelectedUnits();

		  }else{
			  
			  cmsg.addMessage("gtfl " + units.getUnitsSelectedString());
			  units.clearSelectedUnits();
		  }
	}
	
	private boolean groupAttack(double tx, double ty,int ScreenHeight,int ScreenWidth,int frameX,int frameY){
		
		int unitAttack;
		  
		  if((unitAttack = getSelectedUnitToAttack(tx,ty,ScreenHeight,ScreenWidth,frameX,frameY)) != -1){
			  
			  cmsg.addMessage("gtak " + units.getUnitsSelectedString() +
					  unitAttack);
			  units.clearSelectedUnits();
			  
			  return true;
		  
		  }
		  
		  return false;
//		  else{
//		  
//			  	String unitList = units.getUnitsSelectedString();
//			  	unitList = unitList.substring(0, unitList.length()-1);
//			
//				cmsg.addMessage("gtat "+ tx + " " + ty + " " + map.getViewedMap() + " " 
//				+ unitList);
//				
//		  }
		  
		  
	}
	
	private void groupWayPoints(int tx, int ty,boolean shiftDown){
		
		units.addWayPoints(new int[]{tx,ty,map.getViewedMap()});
		
		if(!shiftDown){
			  
			  //space at end of points
			  cmsg.addMessage("gtwp " + units.getUnitsSelectedString() + "-1 " + units.getWayPoints());
			  
			  units.clearSelectedUnits();
			  units.setWayPointSetting(false);
			  
			  units.clearWayPoints();
			  //check this might be a mistake
			  units.clearSelectedUnits();
		}
	}
	
	public boolean InteractWithOtherUnit(double tx, double ty,int unitNo,boolean fDown,boolean shiftDown,
			int ScreenHeight,int ScreenWidth,int frameX,int frameY,boolean isEnemy){

//		System.out.println(tx + " " + ty + " " + fDown + " " + shiftDown + " "
//				+ units.areWayPointSetting() + " moveUnit ");
		if(units.getSelectedUnitsSize() == 1){
			
			if(!units.areWayPointSetting()){
				
				if(fDown && !isEnemy){
					 
					//System.out.println("FOLLOW");
					singleFollow(unitNo);
					return true;
					  
				  }else if(isEnemy){
					  
					singleAttack(unitNo);
					return true;
				  }
	  		  
			}
//			else{
//				  
//				  singleWayPoint(tx,ty,shiftDown);
//				  
//			 }
			
			
		}else if(units.getSelectedUnitsSize() > 1){
			

			if(!units.areWayPointSetting()){
				
				if(fDown && !isEnemy){
					  groupFollow(unitNo);
					  return true;
					  
				  }else if(isEnemy){
					  
					  return groupAttack(tx,ty,ScreenHeight,ScreenWidth,frameX,frameY);
				  }
				
			}
//			else{
//				
//				groupWayPoints(tx,ty,shiftDown);
//				
//			}

		}
		
		return false;
	}
	
	public void setWayPoints(int tx,int ty, boolean shiftDown){
		
		if(units.getSelectedUnitsSize() == 1){
			
			singleWayPoint(tx,ty,shiftDown);
		
		}else{
			
			groupWayPoints(tx,ty,shiftDown);
		}
		
	}

	public void moveUnit(int tx, int ty) {
		// TODO Auto-generated method stub
		if(units.getSelectedUnitsSize() == 1){
			
			  cmsg.addMessage("utat " + units.getBaseSelectedUnit() + " "
			  + tx + " " + ty + " " + map.getViewedMap());
			  units.removeBaseUnit();
			
		}else{
			
			System.out.println(units.getSelectedUnitsSize() + " MoveUnit");
		  	String unitList = units.getUnitsSelectedString();
		  	unitList = unitList.substring(0, unitList.length()-1);
		
			cmsg.addMessage("gtat "+ tx + " " + ty + " " + map.getViewedMap() + " " 
			+ unitList);
		}
	}
	
//	private int getSelectedUnitToAttack(double x, double y){
//		
//		
		
//		for(int u = 0; u < units.size(); u++){
//			  
//			  if(units.canAttackUnit(click,u)){
//				  	
//				  	return units.get(u).getUnitNo();
//			  }
//			  
//		}
//		
//		return -1;
	//}

}
