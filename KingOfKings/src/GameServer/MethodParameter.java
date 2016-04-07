package GameServer;

public class MethodParameter {
	
	//move unit
	public int unitNo;
	public int targetX;
	public int targetY;
	public int targetMap;
	public int ignoreUnit;
	
	//group follow
	public int[] unitNos;
	public int unitFollow;
	
	//unit in boat
	public int boatNo;
	
	//set way point
	public int[] targetXs;
	public int[] targetYs;
	public int[] targetMaps;
	
	//attack unit
	public int targetUnit;
	
	//attack building
	public int buildingNo;
	
	//build building
	public int x;
	public int y;
	public int map;
	public int player;
	public String buildingType;
	
	//add Unit to building queue
	public String unitType;
	
	//remove unit from building queue
	public int queueNo;
	
	//attack unit tower
	public int towerNo;
	
	//get map information
	public int mapNo;
	
	//update Graphical Frame Pos
	public int playerNo;
	public int frameX;
	public int frameY;
	
	//add message to chat
	public String message;
	
	//alliance
	public int player1;
	public int player2;
	
	public void SetMoveUnit(int unitNo, int targetX, int targetY, int targetMap){
		
		this.unitNo = unitNo;
		this.targetX = targetX;
		this.targetY = targetY;
		this.targetMap = targetMap;
		this.ignoreUnit = -1;
	}
	
	public void SetMoveUnit(int unitNo, int targetX, int targetY, int targetMap,int ignoreUnit){
		
		this.unitNo = unitNo;
		this.targetX = targetX;
		this.targetY = targetY;
		this.targetMap = targetMap;
		this.ignoreUnit = ignoreUnit;
	}
	
	public void setUnitFollow(int unitNo, int unitFollow){
		
		this.unitNo = unitNo;
		this.unitFollow = unitFollow;
	}
	
	public void setUnitWayPoints(int unitNo, int[] targetXs, int[] targetYs, int[] targetMaps){
		
		this.unitNo = unitNo;
		this.targetXs = targetXs;
		this.targetYs = targetYs;
		this.targetMaps =targetMaps;
		
	}
	
	public void setGroupMovement(int[] unitNos, int targetX, int targetY, int targetMap){
		
		this.unitNos = unitNos;
		this.targetX = targetX;
		this.targetY = targetY;
		this.targetMap = targetMap;
	}
	
	public void setGroupWayPoints(int[] unitNos, int[] targetXs,
			int[] targetYs, int[] targetMaps){
		
		this.unitNos = unitNos;
		this.targetXs = targetXs;
		this.targetYs = targetYs;
		this.targetMaps = targetMaps;
	}
	
	public void setGroupFollow(int[] unitNos, int unitFollow){
		
		this.unitNos = unitNos;
		this.unitFollow = unitFollow;
	}
	
	public void setAttackGroup(int[] unitNos, int targetUnit){
		
		this.unitNos = unitNos;
		this.targetUnit = targetUnit;
	}
	
	public void setUnitAttack(int unitNo, int targetUnit){
		
		this.unitNo = unitNo;
		this.targetUnit = targetUnit;
	}
	
	public void setBuildBuilding(int x, int y,int map,int player, String buildingType,int[] unitNos){
		
		this.x = x;
		this.y = y;
		this.map = map;
		this.player = player;
		this.buildingType = buildingType;
		this.unitNos = unitNos;
	}
	
	public void setAddUnitToBuildQueue(int buildingNo, String unitType,int player){
		
		this.buildingNo = buildingNo;
		this.unitType = unitType;
		this.player = player;
	}
	
	public void setNewViewMap(int mapNo,int player){
		
		this.mapNo = mapNo;
		this.player = player;
	}

	public void setAddWorkerToSite(int buildingNo, int[] unitNos) {
		// TODO Auto-generated method stub
		this.buildingNo = buildingNo;
		this.unitNos = unitNos;
	}
	

}
