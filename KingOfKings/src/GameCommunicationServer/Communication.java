package GameCommunicationServer;

import java.io.IOException;
import java.util.ArrayList;

import GameServer.Server;

public class Communication {
	
	private Server server;
	private boolean enterGame;
	private ArrayList<Integer> players;
	private int playersEntered;
	private boolean playerLobbyPhase;
	
	public Communication() throws IOException{
		
		server = new Server();
		players = new ArrayList<Integer>();
		playerLobbyPhase = false;
		acceptPlayer();
		enterGame();
		
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
				
				String playerList = "";
				
				for(int s = 0; s < players.size(); s++){
					
					playerList += players.get(s) + " ";
				}
				
				for(int p = 0; p < players.size(); p++){
					server.sendMessage(p, playerList);
				}
			}
			
			for(int p = 0; p < players.size(); p++){
				
				if(server.messageReady(p)){
					
					String msg = server.getMessage(p);
					
					if(msg.equals("ENTERGAME")){
						playersEntered++;
					}
					
					if(playersEntered == players.size() && playersEntered != 0){
						
						break;
					}
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
	//enter game
	public void enterGame(){
		
		
	}
	//get user commands 
	
	//send user commands to players who didn't send that user command 
	
	public static void main(String[] args) throws IOException {
		
		new Communication();
	}

}
