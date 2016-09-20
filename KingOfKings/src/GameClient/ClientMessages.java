package GameClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import GameServer.IGotToTurn;

import GameServer.Server;

public class ClientMessages implements IGotToTurn{
	
	private ArrayList<String> inputs;
	private ArrayList<String> frame;
	private Client client;
	private boolean everyoneReady;
	private boolean gameStarted;
	private ArrayList<String> messages;
	private Semaphore lock;
	
	public ClientMessages(){
		
		lock = new Semaphore(2);
		messages = new ArrayList<String>();
		
	}
	
	public void startClient(String ip){
		
		inputs = new ArrayList<String>();
		frame = new ArrayList<String>();
		gameStarted = false;
		try {
		
			client = new Client(ip);
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage() + " ClientMessage");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage() + " ClientMessage");
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
						
						try {
							lock.acquire();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	
						String msg = client.getMessage();
						if(msg != null){
							
							inputs.add(msg);

						}
						
						try {
							
							for(int m = 0; m < messages.size(); m++){
								
								client.sendMessage(messages.get(m));
							}
							
							messages.clear();
							
							lock.release();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
			lock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		messages.add(message);
		lock.release();
	}
	
	//To implement: only one thread should be able to remove or add
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
		client.start();
		try{
			inputs.add(msg);
		}catch(ArrayIndexOutOfBoundsException e){
			
			client.end();
			putBackMessage(msg);
		}
		client.end();
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
