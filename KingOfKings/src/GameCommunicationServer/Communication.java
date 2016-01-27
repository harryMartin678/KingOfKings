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
	private int playersReady;
	private int syncUser;
	
	public Communication() throws IOException{
		
		server = new Server();
		players = new ArrayList<Integer>();
		playerNames= new ArrayList<String>();
		enterGame = false;
		playersReady = 0;
		syncUser = 0;
		waitForPlayerNames();
		acceptPlayer();
		enterGame();
		
	}
	
	public void waitForPlayerNames(){
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				mainLoop:
				while(true){
					
					for(int p = 0; p < players.size(); p++){
						
						try{
							if(server.messageReady(players.get(p))){
								String msg;
							
								msg = server.getMessage(players.get(p));
								
								if(msg.substring(0, 4).equals("name")){
									
									playerNames.add(msg.substring(5));
									
									String playerList = "";
									
									for(int s = 0; s < playerNames.size(); s++){
										
										playerList += playerNames.get(s) + " ";
									}
									
									for(int pn = 0; pn < players.size(); pn++){
										
										server.sendMessage(players.get(pn), playerList);
										
									}
									
								}else if(msg.equals("ENTERGAME")){
									
									playersEntered++;
									
									if(playersEntered == players.size() && playersEntered != 0){
										
										enterGame = true;
										break mainLoop;
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
			
			if(enterGame){
				
				break;
			}
			
			if(player != -1){
				
				//System.out.println(player);
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
	public void enterGame() throws IOException{
		
		for(int p = 0; p < players.size(); p++){
			server.sendMessage(p, "StartGame");
		}
		
		final Thread gameThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					
					long time = System.currentTimeMillis();
					
					//System.out.println("game thread");
					
					for(int rp = 0; rp < players.size(); rp++){
						
						try {
							if(server.messageReady(rp)){
								
								String msg = server.getMessage(rp);
								
								if(msg.equals("READY")){
									
									//System.out.println("ready player com");
									syncUser++;
									
									if(syncUser == players.size()){
										
										syncUser = 0;
										for(int sp = 0; sp < players.size(); sp++){
											server.sendMessage(sp, "ALLREADYCOM");
										}
									}
								}
								
								for(int sp = 0; sp < players.size(); sp++){
									
									if(sp == rp){
										
										continue;
									}
									
									server.sendMessage(sp, msg);
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					
					
					long waitTime = 35 - (System.currentTimeMillis() - time);
					
					if(waitTime < 2){
						
						waitTime = 2;
					}
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		});
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					
					for(int p = 0; p < players.size(); p++){
						
						try {
							
							if(server.messageReady(p)){
								
								String msg = server.getMessage(p).substring(4);
								
								if(msg.equals("READY")){
									
									playersReady++;
								}
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					//System.out.println(playersReady + " " + playersEntered + " playerCompare");
					if(playersReady == playersEntered){
						
						
						for(int p = 0; p < players.size(); p++){
							
							try {
								//System.out.println(players.get(p) + " players");
								server.sendMessage(p, "ALLREADY");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						gameThread.start();
						break;
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
	
	//get user commands 
	
	//send user commands to players who didn't send that user command 
	
	public static void main(String[] args) throws IOException {
		
		new Communication();
	}

}
