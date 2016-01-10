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

public class Server {
	
	private ArrayList<BufferedWriter> writers;
	private ArrayList<BufferedReader> readers;
	
	private ArrayList<Integer> playersEntered;
	private boolean start;
	
	/*
	 * Runs a game server
	 */
	public Server() throws IOException{
		
		final ServerSocket server = new ServerSocket(9999);
		start = true;
		
		playersEntered = new ArrayList<Integer>();
		
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
						
						playersEntered.add(readers.size());
						
						if(start){
							try {
								readers.add(new BufferedReader(new InputStreamReader(socket.getInputStream())));
								writers.add(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
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
		
		System.out.println(player + " " + msg + " start");
		writers.get(player).write(msg + "\n");
		writers.get(player).flush();
		System.out.println(player + " " + msg + " end");
		
	}
	
	public String getMessage(int player) throws IOException{
		
		return readers.get(player).readLine();
	}
	
	public boolean messageReady(int player) throws IOException{
		
		return readers.get(player).ready();
	}
	
	public int noOfPlayers(){
		
		return readers.size();
	}
	
	public static void main(String[] args) {
		
		try {
			new Server();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
