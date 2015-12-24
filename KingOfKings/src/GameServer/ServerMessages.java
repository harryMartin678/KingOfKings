package GameServer;

import java.io.IOException;
import java.util.ArrayList;

import GameClient.ParseText;

public class ServerMessages {
	
	private int playerNo;
	private Server server;
	private boolean start;
	private GameEngine engine;
	
	public ServerMessages(){
		
		playerNo = 0;
		
		start = true;
		
		try {
			System.out.println("success");
			server = new Server();
					
		} catch (IOException e) {
		// TODO Auto-generated catch block
					
			e.printStackTrace();
		}
	
		
		
		Thread handleInput = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				while(true){
					
					for(int p = 0; p < server.noOfPlayers(); p++){
						
						try {
							if(server.messageReady(p)){
								String inpt = server.getMessage(p);
								
								if(!inpt.equals("SEND_FRAME")){
									System.out.println(inpt);
								}
								
								if(inpt.substring(0, 4).equals("utat")){
									
									engine.parseMoveUnit(inpt.substring(5));
								
								}else if(inpt.equals("SEND_FRAME")){
									
									sendFrame(p);
								
								}else if(inpt.substring(0,4).equals("vwmp")){
									
									engine.setNewViewMap(new Integer(inpt.substring(5)).intValue()
											,p);
								
								}else if(inpt.substring(0,4).equals("utwp")){
									
									engine.parseWayPoints(inpt.substring(5),p);
								
								}else if(inpt.substring(0,4).equals("gtat")){
									
									engine.parseGroupMovement(inpt.substring(5));
								
								}else if(inpt.substring(0,4).equals("utfl")){
									
									engine.parseFollowMovement(inpt);
								
								}else if(inpt.substring(0,4).equals("utak")){
									
									engine.parseAttack(inpt);
								
								}else if(inpt.substring(0,4).equals("gtwp")){
									
									engine.parseGroupWayMovement(inpt.substring(5));
								
								}else if(inpt.substring(0,4).equals("gtfl")){
									
									engine.parseGroupFollowMovement(inpt.substring(5));
								
								}else if(inpt.substring(0,4).equals("gtak")){
									
									engine.parseGroupAttack(inpt.substring(5));
								
								}else if(inpt.substring(0, 2).equals("bb")){
									
									engine.parseBuildings(inpt.substring(2),p);
								
								}else if(inpt.substring(0,3).equals("auq")){
									
									engine.parseAddUnitToQueue(inpt.substring(3),p);
								}
								
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		});
		
		handleInput.start();
		
		Thread handleAcceptance = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				while(true){
					
					if(!start){
						
						//stub
						startGame("Game1",playerNo);
						break;
					}
					
					int player = server.getPlayersEntered();
					//System.out.println(player + " player");
					
					if(player != -1){
					
						
						playerNo++;
						//give information about lobby
						start = false;
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		});
		
		handleAcceptance.start();
		
		
	}
	
	public void sendFrame(int player){
			
		String output = "";
		
		int viewedMap = engine.getPlayerViewedMap(player);
			
		output += "START_FRAME\n";
		output += viewedMap +"\n";
		output += engine.getWhoOwnsWhatMap() + "\n";
		output += "unitlist\n";
		output += engine.getUnitsOnMap(viewedMap);
		output += "buildinglist\n";
		output += engine.getBuildingOnMap(viewedMap);
		output += "buildingqueue\n";
		output += engine.getUnitQueues(player);
		output += "END_FRAME\n";
		addMessage(player,output);
		
	}
	
	public void startGame(String game, int noOfPlayers){
		
		System.out.println("Start game");
		engine = new GameEngine(game,noOfPlayers);
		
		for(int p = 0; p < playerNo; p++){
			
			String output = "";
			
			int map = engine.getMapOwnedMap(p);
			
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
			
			addMessage(p,output);
		}

	}
	
	public void endStart(){
		
		start = false;
	}
	
	public void addMessage(int player, String message){
		
		try {
			server.sendMessage(player, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
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
	
	public static void main(String[] args) {
		
		new ServerMessages();
	}

}
