package GameServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Buildings.Building;
import Buildings.BuildingSite;
import Buildings.Names;
import Buildings.Tower;
import Buildings.Wall;
import IntermediateAI.MapRouteFinder;
import IntermediateAI.WallCreator;
import Map.CollisionMap;
import Map.GameEngineCollisionMap;
import Units.Unit;
import Units.Worker;
import Util.Point;

public class MethodCallup implements Commands {

	
	private GameEngineContext context;
	private String methodName;
	
	public static String MOVEUNIT = "moveunit";
	public static String GROUPFOLLOW = "groupFollow";
	public static String FOLLOWUNIT = "followunit";
	public static String UNITINBOAT = "unitinboat";
	public static String SETWAYPOINTS = "setwaypoints";
	public static String GROUPMOVEMENT = "groupMovement";
	public static String GROUPWAYPOINTSMOVEMENT = "groupWayPointsMovement";
	public static String ATTACKUNIT = "attackUnit";
	public static String ATTACKGROUP = "attackGroup";
	public static String ATTACKBUILDING = "attackBuilding";
	public static String BUILDBUILDING =  "buildBuilding";
	public static String ADDUNITTOBUILDINGQUEUE = "addUnitToBuildingQueue";
	public static String REMOVEUNITFROMBUIDLINGQUEUE = "removeUnitFromBuildingQueue";
	public static String ATTACKUNITFROMTOWER = "attackUnitFromTower";
	public static String REQUESTEDUNITINFORMATION = "requestedUnitInformation";
	public static String REQUESTEDBUILDINGINFORMATION = "requestedBuildingInformation";
	public static String GETMAPINFORMATION = "getMapInformation";
	public static String UPDATEGRAPHICALFRAMEPOS = "updateGraphicalFramePos";
	public static String DESTROYBUILDING = "destroyBuilding";
	public static String NEWVIEWMAP = "newViewMap";
	public static String SAVEGAME = "saveGame";
	public static String ADDMESSAGETOCHAT = "addMessageToChat";
	public static String ALLIANCE = "alliance";
	public static String ATWAR = "atWar";
	public static String ADDWORKERTOSITE = "addWordToSite";
	public static String TOWERATTACKUNIT = "towerAttackUnit";
	public static String BUILDWALLS = "buildwall";
	public static String ADDAI = "addai";
	
	private MethodParameter parameters;
	private int CommunicationTurnNo;
	
	
	
	public MethodCallup(GameEngineContext context,String methodName,MethodParameter parameters,
			int CommunicationTurnNo){
		
		this.context = context;
		this.parameters = parameters;
		this.methodName = methodName;
		this.CommunicationTurnNo = CommunicationTurnNo;
	}
	
	public int getCommunicationTurnNo(){
		
		return CommunicationTurnNo;
	}
	
	public String getMethodCallup(){
		
		return this.methodName;
	}
	
	public void call(){
		
		if(methodName.equals(MOVEUNIT)){
			
			if(this.parameters.ignoreUnit == -1){
				this.moveUnit(parameters.unitNo, parameters.targetX, 
					parameters.targetY, parameters.targetMap,false);
			}else{
				
				this.moveUnit(parameters.unitNo, parameters.targetX, parameters.targetY,
						parameters.targetMap, parameters.ignoreUnit);
			}
			
		}else if(methodName.equals(GROUPFOLLOW)){
			
			this.groupFollow(parameters.unitNos, parameters.unitFollow);
			
		}else if(methodName.equals(FOLLOWUNIT)){
			
			this.followUnit(parameters.unitNo, parameters.unitFollow);
			
		}else if(methodName.equals(SETWAYPOINTS)){
			
			this.setWayPoints(parameters.unitNo, parameters.targetXs, 
					parameters.targetYs, parameters.targetMaps);
			
		}else if(methodName.equals(GROUPMOVEMENT)){
			
			this.groupMovement(parameters.unitNos, parameters.targetX,
					parameters.targetY, parameters.targetMap);
			
		}else if(methodName.equals(GROUPWAYPOINTSMOVEMENT)){
			
			this.groupWayPointsMovement(parameters.unitNos, parameters.targetXs,
					parameters.targetYs, parameters.targetMaps);
			
		}else if(methodName.equals(ATTACKUNIT)){
			
			this.attackUnit(parameters.unitNo, parameters.targetUnit);
			
		}else if(methodName.equals(ATTACKGROUP)){
			
			this.attackGroup(parameters.unitNos, parameters.targetUnit);
			
		}else if(methodName.equals(ATTACKBUILDING)){
			
			this.attackBuilding(parameters.unitNos, parameters.buildingNo);
			
		}else if(methodName.equals(BUILDBUILDING)){
			
			this.buildBuilding(parameters.x, parameters.y, parameters.map, parameters.player,
					parameters.buildingType, parameters.unitNos);
			
		}else if(methodName.equals(ADDUNITTOBUILDINGQUEUE)){
			
			this.addUnitToBuildingQueue(parameters.buildingNo,parameters.unitType,parameters.player);
			
		}else if(methodName.equals(REMOVEUNITFROMBUIDLINGQUEUE)){
			
			this.removeUnitFromBuildingQueue(parameters.buildingNo, parameters.queueNo);
			
		}else if(methodName.equals(ATTACKUNITFROMTOWER)){
			
			this.attackUnitFromTower(parameters.towerNo, parameters.unitNo);
			
		}else if(methodName.equals(REQUESTEDUNITINFORMATION)){
			
			this.requestedUnitInformation(parameters.unitNo);
			
		}else if(methodName.equals(REQUESTEDBUILDINGINFORMATION)){
			
			this.requestedBuildingInformation(parameters.buildingNo);
			
		}else if(methodName.equals(GETMAPINFORMATION)){
			
			this.getMapInformation(parameters.mapNo);
			
		}else if(methodName.equals(UPDATEGRAPHICALFRAMEPOS)){
			
			this.updateGraphicalFramePos(parameters.playerNo, parameters.frameX, parameters.frameY);
			
		}else if(methodName.equals(DESTROYBUILDING)){
			
			this.destroyBuilding(parameters.buildingNo);
		
		}else if(methodName.equals(NEWVIEWMAP)){
			
			this.newViewMap(parameters.mapNo, parameters.player);
		
		}else if(methodName.equals(SAVEGAME)){
			
			this.saveGame(parameters.filename,parameters.player,parameters.noOfPlayers);
			
		}else if(methodName.equals(ADDMESSAGETOCHAT)){
			
			this.addMessageToChat(parameters.message);
			
		}else if(methodName.equals(ALLIANCE)){
			
			this.alliance(parameters.player1,parameters.player2);
			
		}else if(methodName.equals(ATWAR)){
			
			this.atWar(parameters.player1, parameters.player2);
		
		}else if(methodName.equals(ADDWORKERTOSITE)){
			
			this.addWorkerToSite(parameters.buildingNo, parameters.unitNos);
		
		}else if(methodName.equals(TOWERATTACKUNIT)){
			
			this.towerAttackUnit(parameters.unitNo,parameters.buildingNo);
		
		}else if(methodName.equals(BUILDWALLS)){
			
			this.buildWalls(parameters.wallCenter,parameters.wallSize,parameters.unitNos , parameters.map,
					parameters.playerNo);
		
		}else if(methodName.equals(ADDAI)){
			
			this.addAI(parameters.AINum, parameters.AIName, parameters.Seed);
		}
		
	}
			

	public void towerAttackUnit(int unitNo, int buildingNo) {
		// TODO Auto-generated method stub
		context.battles.addTowerUnitBattle(context.units.getUnits(unitNo), 
				(Tower)context.buildings.getBuilding(buildingNo));
	}

	@Override
	public void moveUnit(int unitNo, int targetX, int targetY, int targetMap,boolean follow) {
		// TODO Auto-generated method stub
		//add a path to move to the unit 
		//System.out.println(targetX + " " + targetY + " MoveTO MethodCallup");
		int unitFollow = context.units.getFollow(unitNo);
		if(unitFollow != -1 && !follow){
			
			context.units.unfollow(unitNo);
			context.units.cancelFollow(unitFollow,unitNo);
		}
		
		if(targetX >= 0 && targetY >= 0){
			context.units.addPathToUnit(unitNo, 
					new MapRouteFinder( context.units.getUnitPlayer(unitNo),context.maps
					).getPath((int) context.units.getMoveUnitX(unitNo),(int) context.units.getMoveUnitY(unitNo)
							,targetX, targetY,context.units.getUnitMap(unitNo),targetMap),false);
		}

	}
	
	
	public void moveUnit(int unitNo, int targetX, int targetY, int targetMap,int ignoreUnit){
		// TODO Auto-generated method stub
		//add a path to move to the unit 
		context.units.addPathToUnit(unitNo, 
				new MapRouteFinder(context.units.getUnitPlayer(unitNo),context.maps,ignoreUnit
				).getPath((int) context.units.getMoveUnitX(unitNo),(int) context.units.getMoveUnitY(unitNo)
						,targetX, targetY,context.units.getUnitMap(unitNo),targetMap),true);
		

	}
	
	@Override
	public void groupFollow(int[] unitNo, int unitFollow) {
		// TODO Auto-generated method stub
		
		for(int u = 0; u < unitNo.length; u++){
			
			context.units.setFollow(unitNo[u], unitFollow);
			this.moveUnit(unitNo[u], (int) context.units.getUnitX(unitFollow), (int) context.units.getUnitY(unitFollow),
					(int) context.units.getUnitMap(unitFollow), unitFollow);
		}
		
	}
	
	@Override
	public void followUnit(int unitNo, int unitFollow) {
		// TODO Auto-generated method stub
//		new CollisionMap(context.buildings,context.units,
//				context.maps.getMap(context.units.getUnitMap(unitFollow)),
//				context.units.getUnitMap(unitFollow))
		context.units.setFollow(unitNo,unitFollow);
		int[] target = context.units.getUnits(unitFollow).getFreeSpace(context.units.getUnitX(unitNo),
				context.units.getUnitY(unitNo));
		
		//System.out.println(GameEngineCollisionMap.getTile(target[0], target[1], 
				//context.units.getUnitMap(unitFollow)) + " MethodCallup FollowUnit");
		//System.out.println(target[0] + " " + target[1] + " " + context.units.getUnits(unitNo).getName()
			//	+ " MethodCallup followUnit");
		this.moveUnit(unitNo, target[0], target[1],
				context.units.getUnitMap(unitFollow),true);
		
	}
	

	@Override
	public void setWayPoints(int unitNo, int[] targetX, int[] targetY, int[] targetMap) {
		// TODO Auto-generated method stub
		
		System.out.println("Set Way points MethodCallup");
		ArrayList<int[]> totalPath = new ArrayList<int[]>();
		
		//get the x points in the way points path in an array
		int[] pointsX = new int[targetX.length+1];
		
		pointsX[0] = (int) context.units.getUnitX(unitNo);
		
		for(int i = 1; i < targetX.length+1; i++){
			
			pointsX[i] = targetX[i-1];
		}
		
		//get the y points in the way points path in an array
		int[] pointsY = new int[targetY.length+1];
		
		pointsY[0] = (int) context.units.getUnitY(unitNo);
		
		for(int i = 1; i < targetY.length+1; i++){
			
			pointsY[i] = targetY[i-1];
		}
		
		//get the maps to travel to in an array 
		int[] mapsNo = new int[targetMap.length+1];
		
		mapsNo[0] = context.units.getUnitMap(unitNo);
		
		for(int i = 1; i < mapsNo.length; i++){
			
			mapsNo[i] = targetMap[i-1];
		}

		System.out.println("mapsNos " + mapsNo.length + " setWayPoint MethodCallup");
		//combine paths for each path
		for(int t = 0; t < mapsNo.length-1; t++){
		
			ArrayList<int[]> next = new MapRouteFinder(context.units.getUnitPlayer(unitNo),
					context.maps).getPath(
					pointsX[t],pointsY[t],
					pointsX[t+1],pointsY[t+1],mapsNo[t],mapsNo[t+1]);
			
			next.remove(next.size()-1);
			
			totalPath.addAll(next);
				
		}
		
		
		//System.out.println("setWayPoints Methodcallup before");
		for(int p = 0; p < pointsX.length; p++){
			
			System.out.println(pointsX[p] + " " + pointsY[p] + " " + mapsNo[p]);
		}
		//System.out.println("setWayPoints Methodcallup after");
		
		//add the path to the unit 
		context.units.addPathToUnit(unitNo, totalPath,false);
	}
	
	@Override
	public void groupMovement(int[] unitNo, int targetX, int targetY, int targetMap) {
		// TODO Auto-generated method stub
		
		//int unitRowSize = (int) Math.sqrt((double) unitNo.length);
		//int row = 0;
		
		//ArrayList<int[]> orgPath = new MapRouteFinder(units, buildings,maps
		//		).getPath((int) units.getUnitX(unitNo[0]), (int) units.getUnitY(unitNo[0]),
		//				targetX, targetY, units.getUnitMap(unitNo[0]), targetMap);
		//
	//	for(int u = 0; u < unitNo.length; u++){
			
			//if(row == unitRowSize){
				
			//	row = 0;
			//}
			
		//	units.addPathToUnit(unitNo[u], new FormationMovement(maps,units,buildings
				//	).getPath(orgPath, row));
			
			//row++;
		//}
		
		for(int u = 0; u < unitNo.length; u++){
			
			moveUnit(unitNo[u], targetX, targetY, targetMap,false);
		}
		
		context.units.setUnitGroupSpeed(unitNo,context.units.getSmallestSpeed(unitNo));
	}
	
	@Override
	public void groupWayPointsMovement(int[] unitNo, int[] targetX,
			int[] targetY, int[] targetMap) {
		// TODO Auto-generated method stub
		
		for(int u = 0; u < unitNo.length; u++){
			
			this.setWayPoints(unitNo[u], targetX, targetY, targetMap);
		}
		
		context.units.setUnitGroupSpeed(unitNo, context.units.getSmallestSpeed(unitNo));
		
	}

	

	@Override
	public void attackUnit(int unitNo, int targetUnit) {
		// TODO Auto-generated method stub
		this.followUnit(unitNo, targetUnit);
		context.battles.addBattle(unitNo, targetUnit);
	}
	
	@Override
	public void attackGroup(int[] unitNo, int targetUnit) {
		// TODO Auto-generated method stub
		
		for(int u = 0; u < unitNo.length; u++){
			
			this.followUnit(unitNo[u], targetUnit);
			context.battles.addBattle(unitNo[u], targetUnit);
		}
	}

	@Override
	public void attackBuilding(int[] unitNos, int buildingNo) {
		// TODO Auto-generated method stub
		Building toAttack = context.buildings.getBuilding(buildingNo);
		
		HashMap<Integer,int[]> taken = new HashMap<Integer,int[]>();
//		CollisionMap map = new CollisionMap(context.buildings,context.units,
//				context.maps.getMap(toAttack.getMap()),toAttack.getMap());
		for(int u = 0; u < unitNos.length; u++){
			
			int[] pos = toAttack.getFreeSpace(
					(int)context.units.getUnitX(unitNos[u]),
					(int)context.units.getUnitY(unitNos[u]), taken);

			taken.put(Point.GetUniqueNo(pos), pos);
			
			this.moveUnit(unitNos[u],pos[0] , pos[1], toAttack.getMap(),false);
			if(toAttack instanceof Tower){
				context.battles.addTowerUnitBattle(context.units.getUnits(unitNos[u]), (Tower)toAttack);
			}else{
				context.buildingAttackList.add(context.units.getUnits(unitNos[u]), toAttack);
			}
		}
		
	}
	
	public void buildBuilding(int x, int y,int map,int player, String buildingType,int[] unitNos,int angle){
		
		int buildingNo = context.buildings.addReservation();
		Building newBuilding = GameGraphics.Building.GetBuildingClass(buildingType,
				buildingNo);

		newBuilding.setPlayer(player);
		newBuilding.setMap(map);
		newBuilding.setPos(x, y);
		
		if(buildingType == Names.WALL){
			
			((Wall)newBuilding).setRotationFromCenter((float)angle);
		}
		
		context.players.addPlayerResource(-newBuilding.FoodNeeded(), -newBuilding.GoldNeeded(), player);
		
		ArrayList<Worker> workers = new ArrayList<Worker>();
		
		for(int u = 0; u < unitNos.length; u++){
			
			//hack
			//if(context.units.getUnits(unitNos[u]).isWorker()){
			workers.add((Worker)context.units.getUnits(unitNos[u]));
			//}
		}
		
		context.sites.addSite(newBuilding,workers);
		BuildingSite site = context.sites.getLastSite();
		
		HashMap<Integer,int[]> unitTargets = new HashMap<Integer,int[]>();
//				new CollisionMap(context.buildings,
//						context.units,context.maps.getMap(newBuilding.getMap()),
//						newBuilding.getMap())
		for(int u = 0; u < unitNos.length; u++){
			int[] pos = site.getFreeSpace(
							(int) context.units.getUnitX(unitNos[u]), 
							(int) context.units.getUnitY(unitNos[u]),unitTargets);
			if(pos[0] != -1){
				unitTargets.put(Point.GetUniqueNo(pos), pos);
				this.moveUnit(unitNos[u], pos[0], pos[1], newBuilding.getMap(),false);
				((Worker) context.units.getUnits(unitNos[u])).build(newBuilding.getBuildingNo());
			}else{
				System.out.println("unit can't build MethodCallup");
				site.removeWorker(unitNos[u]);
			}
			
		} 
	}

	@Override
	public void buildBuilding(int x, int y,int map,int player, String buildingType,int[] unitNos) {
		// TODO Auto-generated method stub
		buildBuilding(x, y, map, player, buildingType, unitNos,0);
	}

	@Override
	public void addUnitToBuildingQueue(int buildingNo, String unitType,int player) {
		// TODO Auto-generated method stub
		Units.Unit unitDes = Units.Unit.GetUnit(unitType);
		//System.out.println("AddUnitToBuildingQueue");
		context.players.addPlayerResource(-unitDes.foodNeeded(), -unitDes.goldNeeded(), player);
		context.buildings.addUnitToBuildingQueue(buildingNo, unitType);
	}

	@Override
	public void removeUnitFromBuildingQueue(int buildingNo, int queueNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attackUnitFromTower(int towerNo, int unitNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestedUnitInformation(int unitNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestedBuildingInformation(int buildingNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMapInformation(int mapNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGraphicalFramePos(int playerNo, int frameX, int frameY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyBuilding(int buildingNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newViewMap(int mapNo,int player) {
		// TODO Auto-generated method stub
		
		context.players.setPlayerViewedMap(player, mapNo);
	}

	@Override
	public void saveGame(String filename,int playerNo,int noOfPlayers) {
		// TODO Auto-generated method stub
		try {
			context.saveGame.save(context, filename,
					playerNo,noOfPlayers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addMessageToChat(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alliance(int player1, int player2) {
		// TODO Auto-generated method stub
		context.players.setAllied(player1, player2);
	}

	@Override
	public void atWar(int player1, int player2) {
		// TODO Auto-generated method stub
		context.players.setAtWar(player1, player2);
	}

	@Override
	public void addWorkerToSite(int buildingNo, int[] unitNos) {
		// TODO Auto-generated method stub
		
		HashMap<Integer,int[]> unitTargets = new HashMap<Integer,int[]>();	

//		new CollisionMap(context.buildings,
//				context.units,context.maps.getMap(newBuilding.getMap()),newBuilding.getMap()),
		Building newBuilding = context.sites.getBuilding(buildingNo);
		if(newBuilding != null){
			for(int u = 0; u < unitNos.length; u++){
				
				
				int[] pos = newBuilding.getFreeSpace(
						(int) context.units.getUnitX(unitNos[u]), 
								(int) context.units.getUnitY(unitNos[u]),unitTargets);
				unitTargets.put(Point.GetUniqueNo(pos), pos);
				this.moveUnit(unitNos[u], pos[0],
						pos[1], newBuilding.getMap(),false);
				Worker worker = (Worker) context.units.getUnits(unitNos[u]);
				worker.build(buildingNo);
				context.sites.addWorker(worker);
			}
		}else{
			
			for(int w = 0; w < unitNos.length; w++){
				
				((Worker)context.units.getUnits(w)).stopBuild();
				((Worker)context.units.getUnits(w)).cancelMovement();
			}
		}
	}

	@Override
	public void buildWalls(Point center,int size, int[] workers, int mapNo, int playerNumber) {
		// TODO Auto-generated method stub
		WallCreator creator = new WallCreator(center,size,GameEngineCollisionMap.toArray(mapNo));
		
		ArrayList<int[]> path = creator.getWallRoute();
		//path.add(0,new int[]{pts.get(0).x,pts.get(0).y});
		int currentAngle = 0;
		
		for(int p = 0; p < path.size(); p++){
			
			int temp;
			boolean isCorner = false;
			if((temp = creator.isCorner(path.get(p)[0], path.get(p)[1])) != -1){
				
				currentAngle = temp;
				isCorner = true;
			}
//			if(currentPoint < pts.size() && path.get(p)[0] == pts.get(currentPoint).x
//					&& path.get(p)[1] == pts.get(currentPoint).y){
//				
//				buildBuilding(path.get(p)[0], path.get(p)[1],
//						mapNo, playerNumber, Names.WALLTOWER, workers);
//				currentPoint++;
//			
//			}else{
			
			String type;
			
			if(isCorner){
				
				type = Names.WALLTOWER;
				
			}else{
				
				type = Names.WALL;
			}
				
			buildBuilding(path.get(p)[0], path.get(p)[1],
					mapNo, playerNumber, type, new int[]{},currentAngle);
			//}
		}
	}

	@Override
	public void addAI(int AINum, String AIName,long Seed) {
		// TODO Auto-generated method stub
		context.ais.newInitialAI(AIName, AINum,Seed);
	}

}
