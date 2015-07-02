package GameClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import GameServer.Server;

public class ClientMessages{
	
	private ArrayList<String> outputs;
	private ArrayList<String> inputs;
	private Client client;
	
	public ClientMessages(){
		
		outputs = new ArrayList<String>();
		inputs = new ArrayList<String>();
		try {
		
			client = new Client();
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread handleInput = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					
					try {
	
						inputs.add(client.getMessage());
	
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
		
		Thread handleOutput = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(outputs.size() > 0){
					
					try {

						client.sendMessage(outputs.get(0));
				
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputs.remove(0);
				}
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		
		});
		
		handleOutput.start();
	}
	
	public void addMessage(String message){
		
		outputs.add(message);
	}
	
	public String getMessage(){
		
		if(inputs.size() == 0){
			
			return "null";
		
		}else{
			
			String in = inputs.get(0);
			inputs.remove(0);
			return in;
		}
		
	}
	
	public static void main(String[] args) {
		
		new ClientMessages();
	}

}
