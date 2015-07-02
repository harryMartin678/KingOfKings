package GameServer;

import java.io.IOException;
import java.util.ArrayList;

public class ServerMessages {
	
	private ArrayList<PlayerMsg> inputs;
	private int playerNo;
	private Server server;
	private boolean start;
	private GameEngine engine;
	private Thread sendFrames;
	
	public ServerMessages(){
		
		inputs = new ArrayList<PlayerMsg>();
		
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
								inputs.add(new PlayerMsg(new Integer(inpt.charAt(0)).intValue(),
										inpt.substring(1)));
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
		
		sendFrames = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				while(true){
					
					sendFrame();
					
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		});
	}
	
	public void sendFrame(){
		
		for(int p = 0; p < playerNo; p++){
			
			String output = "";
			
			int viewedMap = engine.getPlayerViewedMap(p);
			
			output += "START_FRAME\n";
			output += engine.getWhoOwnsWhatMap() + "\n";
			output += "unitlist\n";
			output += engine.getUnitsOnMap(viewedMap);
			output += "buildinglist\n";
			output += engine.getBuildingOnMap(viewedMap);
			output += "END_FRAME\n";
			addMessage(p,output);
		}
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
			output += "END_LOAD\n";
			
			addMessage(p,output);
		}
		
		this.sendFrames.start();
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
