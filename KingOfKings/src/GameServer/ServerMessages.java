package GameServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import GameClient.ParseText;

public class ServerMessages {
	
	private int playerNo;
	private int noOfPlayer;
	//private Server server;
	private boolean start;
	private GameEngine engine;
	private int thisPlayer;
	
	
	public ServerMessages(int thisPlayer,int noOfPlayers,IGotToTurn beacon){
		
		playerNo = 0;
		this.thisPlayer = thisPlayer;
		this.noOfPlayer = noOfPlayers;
		start = true;
		engine = new GameEngine("PlayTest1",noOfPlayer,beacon);
		
	}
	
	public void parseClientMessage(String inpt){
		
		int communicationTurn = -1;
		if(!inpt.equals("READY")){
			System.out.println(inpt + " clientMessage");
		}
		if(inpt.charAt(0) == 'c'){
			
			String[] message = inpt.substring(2).split(Pattern.quote("|"));
			inpt = message[1];
			
			communicationTurn = new Integer(message[0]).intValue();
		}
		
		if(inpt.substring(0, 4).equals("utat")){
			
			engine.parseMoveUnit(inpt.substring(5),communicationTurn);
		
		}
		else if(inpt.substring(0,4).equals("vwmp")){
			
			engine.setNewViewMap(inpt.substring(5),communicationTurn);
		
		}else if(inpt.substring(0,4).equals("utwp")){
			
			engine.parseWayPoints(inpt.substring(5),communicationTurn);
		
		}else if(inpt.substring(0,4).equals("gtat")){
			
			engine.parseGroupMovement(inpt.substring(5),communicationTurn);
		
		}else if(inpt.substring(0,4).equals("utfl")){
			
			engine.parseFollowMovement(inpt,communicationTurn);
		
		}else if(inpt.substring(0,4).equals("utak")){
			
			engine.parseAttack(inpt,communicationTurn);
		
		}else if(inpt.substring(0,4).equals("gtwp")){
			
			engine.parseGroupWayMovement(inpt.substring(5),communicationTurn);
		
		}else if(inpt.substring(0,4).equals("gtfl")){
			
			engine.parseGroupFollowMovement(inpt.substring(5),communicationTurn);
		
		}else if(inpt.substring(0,4).equals("gtak")){
			
			engine.parseGroupAttack(inpt.substring(5),communicationTurn);
		
		}else if(inpt.substring(0, 2).equals("bb")){
			
			engine.parseBuildings(inpt.substring(3),communicationTurn);
		
		}else if(inpt.substring(0,3).equals("auq")){
			
			engine.parseAddUnitToQueue(inpt.substring(3),communicationTurn);
		
		}else if(inpt.substring(0,4).equals("atbl")){
			
			engine.parseAttackBuilding(inpt.substring(5), communicationTurn);
		
		}else if(inpt.substring(0,4).equals("wnbb")){
			
			engine.parseAddWorkerToSite(inpt.substring(5),communicationTurn);
		
		}else if(inpt.substring(0,4).equals("taku")){
			
			engine.parseTowerAttackUnit(inpt.substring(5),communicationTurn);
		}
	}
	
	public String sendFrame(){
		
		if(start){
			
			start = false;
			return firstFrame();
		}
			
		String output = "";
		
		int viewedMap = engine.getPlayerViewedMap(thisPlayer);
			
		output += "START_FRAME\n";
		output += viewedMap +"\n";
		output += engine.getWhoOwnsWhatMap() + "\n";
		output += "unitlist\n";
		output += engine.getUnitsOnMap(viewedMap);
		output += "buildinglist\n";
		output += engine.getBuildingOnMap(viewedMap);
		output += "buildingqueue\n";
		output += engine.getUnitQueues(thisPlayer);
		output += "resource\n";
		output += engine.getPlayerResource(thisPlayer);
		output += "END_FRAME\n";
		//addMessage(player,output);
		if(engine.getUnitQueues(thisPlayer).length() > 0){
			System.out.println(output + " ServerMessage");
		}
		return output;
		
	}
	
	public String firstFrame(){
		
		String output = "";
		
		int map = engine.getMapOwnedMap(thisPlayer);
		
		output += thisPlayer + "\n";
		output += engine.getGameName() + "\n";
		output += map + "\n";
		output += engine.getWhoOwnsWhatMap() + "\n";
		
		//for(int m = 0; m < engine.getNumberOfMapRows(map); m++){
			
			//output += engine.getMapRow(map, m) + "\n";
	//	}
		
		output += "unitlist\n";
		output += engine.getUnitsOnMap(map);
		output += "buildinglist\n";
		output += engine.getBuildingOnMap(map);
		output += "buildingqueue\n";
		output += "END_LOAD\n";
		
		return output;
	}
	
	public int getNextCommunicationTurn(){
		
		return engine.getCommunicationTurnNo();
	}
	
	public void endStart(){
		
		start = false;
	}
	
//	public void addMessage(int player, String message){
//		
//		try {
//			server.sendMessage(player, message);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	public class PlayerMsg{
		
		private int player;
		private String msg;
		
		public PlayerMsg(int player, String msg){
			
			this.player = player;
			this.msg = msg;
		}
		
		public int getPlayer(){
			
			return player;
		}
		
		public String getMsg(){
			
			return msg;
		}
	}

	
//	public static void main(String[] args) {
//		
//		new ServerMessages();
//	}

}
