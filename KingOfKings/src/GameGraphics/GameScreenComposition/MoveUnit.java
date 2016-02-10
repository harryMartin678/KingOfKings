package GameGraphics.GameScreenComposition;

import GameClient.ClientMessages;

public class MoveUnit {
	
	private IComUnitListMouseKeyboard units;
	private IComMapMouseKeyboard map;
	private ClientWrapper cmsg;
	
	public MoveUnit(IComUnitListMouseKeyboard units,IComMapMouseKeyboard map,
			ClientWrapper cmsg){
		
		this.units = units;
		this.map = map;
		this.cmsg = cmsg;
	}
	
	private void singleFollow(int tx,int ty){
		
		units.addSelectedUnit(new int[]{tx,ty});
		
		//System.out.println(units.getSelectedUnitsSize() + " singleFollow");
		  
		if(units.getSelectedUnitsSize()  == 1){
		  
			units.clearSelectedUnits();

		}else{
		  
			int unitFollow = units.getFollowUnit();
		  	int unitNo = units.getBaseSelectedUnit();
		  
		  	units.clearSelectedUnits();
		  
		  	cmsg.addMessage("utfl " + unitNo + " " + unitFollow);
		}
	}
	
	private void singleAttack(int tx, int ty){
		
		 int unitAttack;
		  
		  if((unitAttack = getSelectedUnitToAttack(new int[]{tx,ty})) != -1){
			  
			  
			  cmsg.addMessage("utak " + units.getBaseSelectedUnit() + " " +
					  unitAttack);
			  units.removeBaseUnit();
		  
		  }else{
		  
			  cmsg.addMessage("utat " + units.getBaseSelectedUnit() + " "
	  				  + tx + " " + ty + " " + map.getViewedMap());
			  units.removeBaseUnit();
		  }
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
	
	private void groupFollow(int tx, int ty){
		
		int oldSize = units.getSelectedUnitsSize();
		
		  units.addSelectedUnit(new int[]{tx,ty});
		  
		  if(units.getSelectedUnitsSize() == oldSize){
			  
			  units.clearSelectedUnits();

		  }else{
			  
			  cmsg.addMessage("gtfl " + units.getUnitsSelectedString());
			  units.clearSelectedUnits();
		  }
	}
	
	private void groupAttack(int tx, int ty){
		
		int unitAttack;
		  
		  if((unitAttack = getSelectedUnitToAttack(new int[]{tx,ty})) != -1){
			  
			  cmsg.addMessage("gtak " + units.getUnitsSelectedString() +
					  unitAttack);
		  
		  }else{
		  
			  	String unitList = units.getUnitsSelectedString();
			  	unitList = unitList.substring(0, unitList.length()-1);
			
				cmsg.addMessage("gtat "+ tx + " " + ty + " " + map.getViewedMap() + " " 
				+ unitList);
				
		  }
		  
		  units.clearSelectedUnits();
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
	
	public void moveUnit(int tx, int ty,boolean fDown,boolean shiftDown){

		//System.out.println(tx + " " + ty + " " + fDown + " " + shiftDown + " "
			//	+ units.areWayPointSetting() + " moveUnit ");
		if(units.getSelectedUnitsSize() == 1){
			
			if(!units.areWayPointSetting()){
				
				if(fDown){
					 
					System.out.println("FOLLOW");
					singleFollow(tx,ty);
					  
				  }else{
					  
					 singleAttack(tx,ty);
				  }
	  		  
			}else{
				  
				  singleWayPoint(tx,ty,shiftDown);
				  
			  }
			
		}else{
			

			if(!units.areWayPointSetting()){
				
				if(fDown){
					  groupFollow(tx,ty);
					  
				  }else{
					  
					  groupAttack(tx,ty);
				  }
				
			}else{
				
				groupWayPoints(tx,ty,shiftDown);
				
			}

		}
	}
	
	private int getSelectedUnitToAttack(int[] click){
		
		for(int u = 0; u < units.size(); u++){
			  
			  if(units.canAttackUnit(click,u)){
				  	
				  	return units.get(u).getUnitNo();
			  }
			  
		}
		
		return -1;
	}

}
