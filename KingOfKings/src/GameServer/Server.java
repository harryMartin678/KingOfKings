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

public class Server {
	
	/*
	 * Runs a game server
	 */
	public Server() throws IOException{
		
		ServerSocket server = new ServerSocket(9999);
		
		server.setSoTimeout(1000);
		
		//accepts clients 
		while(true){
			try{
				
				final Socket socket = server.accept();
				
				//processes a client 
				Thread client = new Thread(new Runnable(){
	
					@Override
					public void run() {
						// TODO Auto-generated method stub
						InetAddress ia = socket.getInetAddress();
						
						if(ia != null){
							
							try {
								BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
								
								out.write("Start Game \n");
								out.flush();
								
								while(true){
									
									try {
										Thread.sleep(200);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}catch(IOException e){
								
								
							}
						}
					}
					
					
				});
				
				client.start();
			
			}catch(SocketTimeoutException e){
			
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
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
