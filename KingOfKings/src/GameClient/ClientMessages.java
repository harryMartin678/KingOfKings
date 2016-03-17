package GameClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import GameServer.IGotToTurn;

import GameServer.Server;

public class ClientMessages implements IGotToTurn{
	
	private ArrayList<String> inputs;
	private ArrayList<String> frame;
	private Client client;
	private boolean everyoneReady;
	private boolean gameStarted;
	
	public ClientMessages(){
		
		inputs = new ArrayList<String>();
		frame = new ArrayList<String>();
		gameStarted = false;
		try {
		
			client = new Client();
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startGame();
		
	}
	
	public void startGame(){
		
		Thread handleInput = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					
					try {
	
						String msg = client.getMessage();
						if(msg != null){
							inputs.add(msg);
						}
						
//						if(inputs.get(inputs.size()-1).equals("AllREADY")){
//			
//							System.out.println("GOT MESSAGE");
//						}
	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		});
		
		handleInput.start();
	}
	
	public void addMessage(String message){
		
		try {
			client.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getFrameMessage(){
		
		client.start();
		if(frame.size() == 0){
			client.end();
			return "null";
		}else{
			
			String in = frame.get(0);
			frame.remove(0);
			client.end();
			return in;
		}
	}
	
	public void putBackMessage(String msg){
		
		inputs.add(msg);
	}
	
	public String getMessage(){
		
		if(inputs.size() == 0){
			
			return "null";
		
		}else{
			
			try{
				String in = inputs.get(0);
				inputs.remove(0);
				return in;
			}catch(IndexOutOfBoundsException e){
				
				return "null";
			}
			
		}
		
	}
	
	public void addToInput(String inpt){
		
		String[] lines = inpt.split("\n");
		
		for(int l = 0; l < lines.length; l++){
			
			frame.add(lines[l]);
		}
	}
	
	public static void main(String[] args) {
		
		new ClientMessages();
	}

	@Override
	public void ready() {
		// TODO Auto-generated method stub
		everyoneReady = false;
		try {
			client.sendMessage("READY");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void everyoneIsReady(){
		
		everyoneReady = true;
	}

	@Override
	public boolean isEveryoneReady() {
		// TODO Auto-generated method stub
		return everyoneReady;
	}
	
	public void gameHasStarted(){
		
		gameStarted = true;
	}

	@Override
	public boolean gameStarted() {
		// TODO Auto-generated method stub
		return gameStarted;
	}

}
