package GameGraphics.GameScreenComposition;

import GameClient.ClientMessages;
import GameServer.ServerMessages;

public class ClientWrapper {
	
	private ClientMessages cmsg;
	private ServerMessages server;
	
	public ClientWrapper(ClientMessages cmsg,int playerNo,int noOfPlayers){
		
		this.cmsg = cmsg;
		server = new ServerMessages(playerNo,noOfPlayers,cmsg);
		listenToOtherPlayers();
	}
	
	public void requestFrame(){
		
		cmsg.addToInput(server.sendFrame());
	}
	
	public String getFrameMessage(){
		
		return cmsg.getFrameMessage();
	}
	
	public void GameStarted(){
		
		cmsg.gameHasStarted();
	}

	public void addMessage(String message) {
		// TODO Auto-generated method stub
		server.parseClientMessage(message);
		cmsg.addMessage("c " + server.getNextCommunicationTurn() + "|" + message);
	}
	
	public void listenToOtherPlayers(){
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					
					String msg;
					
					if((msg = cmsg.getMessage()) != "null" && msg != null){
						
						if(msg.equals("ALLREADY")){
							
							cmsg.putBackMessage(msg);
			
						}else if(msg.equals("ALLREADYCOM")){
							
							cmsg.everyoneIsReady();
							
						}else{
							
							server.parseClientMessage(msg);
						}
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}).start();
	}

}
