package GameCommunicationServer;

import java.io.IOException;
import java.util.ArrayList;

import GameServer.Server;

public class Communication {
	
	private Server server;
	private boolean enterGame;
	private ArrayList<Integer> players;
	private ArrayList<String> playerNames;
	private int playersEntered;
	private boolean playerLobbyPhase;
	
	public Communication() throws IOException{
		
		server = new Server();
		players = new ArrayList<Integer>();
		playerNames= new ArrayList<String>();
		playerLobbyPhase = false;
		waitForPlayerNames();
		acceptPlayer();
		enterGame();
		
	}
	
	public void waitForPlayerNames(){
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				while(true){
					
					for(int p = 0; p < players.size(); p++){
						
						try{
							if(server.messageReady(players.get(p))){
								String msg;
							
								msg = server.getMessage(players.get(p));
								
								if(msg.substring(0, 4).equals("name")){
									
									playerNames.add(msg.substring(5));
									
									String playerList = "";
									
									for(int s = 0; s < players.size(); s++){
										
										playerList += playerNames.get(s) + " ";
									}
									
									for(int pn = 0; pn < players.size(); pn++){
										server.sendMessage(players.get(pn), playerList);
									}
									
								}else if(msg.equals("ENTERGAME")){
									
									playersEntered++;
									
									if(playersEntered == players.size() && playersEntered != 0){
										
										System.out.println("ENTER GAME");
										break;
									}
								
								}
								
							}
							
							
								
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							
					}
					
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		}).start();
	}
	
	
	//accept players
	public void acceptPlayer() throws IOException{
		
		while(true){
			
			int player = server.getPlayersEntered();
			
			if(player != -1){
				
				System.out.println(player);
//				for(int p = 0; p < players.size(); p++){
//					server.sendMessage(players.get(p), "plet " + player);
//				}
//				
				players.add(player);
				
				
			}
			
			
			
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//enter game
	public void enterGame(){
		
		
	}
	//get user commands 
	
	//send user commands to players who didn't send that user command 
	
	public static void main(String[] args) throws IOException {
		
		new Communication();
	}

}
