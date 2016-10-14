package GameServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
	
	private ArrayList<BufferedWriter> writers;
	private ArrayList<BufferedReader> readers;
	
	private ArrayList<Integer> playersEntered;
	private HashMap<Integer,Integer> playerNoToIndex;
	private boolean start;
	
	/*
	 * Runs a game server
	 */
	public Server(final String gameName, final int NoOfPlayers) throws IOException{
		
		final boolean load = gameName != null;
		
		final ServerSocket server = new ServerSocket(9999);
		start = true;
		
		playersEntered = new ArrayList<Integer>();
		playerNoToIndex = new HashMap<Integer, Integer>();
		
		writers = new ArrayList<BufferedWriter>();
		readers = new ArrayList<BufferedReader>();
		
		//server.setSoTimeout(1000);
		
		Thread serverThread = new Thread(new Runnable(){
		
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//accepts clients 
					while(true){
			
							
						Socket socket = null;
						try {
							socket = server.accept();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						System.out.println("accept");
						
						if(start){
							try {
								
								int playerIndex = readers.size();
								boolean reject = false;
								
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(socket.getInputStream()));
								BufferedWriter writer = new BufferedWriter(
										new OutputStreamWriter(socket.getOutputStream()));
								
								
								if(load){
									
									String entry = reader.readLine();
									
									String[] gamePass = entry.split(" ");
									
									playerIndex = new Integer(gamePass[1]).intValue();
									
									if(!(playerIndex < NoOfPlayers && gameName.equals(gamePass[0]))){
										
//										writers.get(writers.size()-1).write("INVALID\n");
//										writers.get(writers.size()-1).flush();
										reject = true;
										
										//playersEntered.remove(playersEntered.size()-1);
									}
	
								}
								
								if(reject){
									
									reader.close();
									writer.close();
									socket.close();
									
								}else{
									
									playersEntered.add(playerIndex);
									readers.add(reader);
									writers.add(writer);
									playerNoToIndex.put(playerIndex, readers.size()-1);
								}
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
		});
		
		serverThread.start();
	}
	
	
	public void endStart(){
		
		start = false;
	}
	
	public int getPlayersEntered(){
		
		if(playersEntered.size() == 0){
			
			return -1;
		}
		
		int pe = playersEntered.get(0);
		playersEntered.remove(0);
		
		return pe;
	}
	
	public void sendMessage(int player, String msg) throws IOException{
		
		//System.out.println(player + " " + msg + " start");
		writers.get(playerNoToIndex.get(player)).write(msg + "\n");
		writers.get(playerNoToIndex.get(player)).flush();
		//System.out.println(player + " " + msg + " end");
		
	}
	
	public String getMessage(int player) throws IOException{
		
		return readers.get(playerNoToIndex.get(player)).readLine();
	}
	
	public boolean messageReady(int player) throws IOException{
		
		if(player >= readers.size()){
			
			return false;
		}
		
		return readers.get(playerNoToIndex.get(player)).ready();
	}
	
	public int noOfPlayers(){
		
		return readers.size();
	}
	
//	public static void main(String[] args) {
//		
//		try {
//			new Server();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
