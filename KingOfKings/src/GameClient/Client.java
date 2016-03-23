package GameClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

public class Client {
	
	private BufferedReader in;
	private BufferedWriter out;
	private Semaphore lock;
	
	public Client(String ip) throws UnknownHostException, IOException{
		
		assert(ValidIP(ip));
		Socket client = new Socket(ip,9999);
		lock = new Semaphore(1);
		
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		
	}
	
	private boolean ValidIP(String ip){
		
		String[] parts = ip.split(".");
		
		if(parts.length != 4){
			
			return false;
		}
		
		for(int p = 0; p < parts.length; p++){
			
			boolean isNumber = TryParseInt(parts[p]);
			
			if(!isNumber){
				
				return false;
			}
		}
		
		return true;
	}
	
	public boolean TryParseInt(String someText) {
		   try {
		      return (Integer.parseInt(someText) >= 0 && Integer.parseInt(someText) <=255);
		   } catch (NumberFormatException ex) {
		      return false;
		   }
		}
	
	public void start(){
		
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void end(){
		
		lock.release();
	}
	
	public void postMessage(String msg) throws IOException{
		
		out.write(msg);
		out.flush();
	}
	
	public static void main(String[] args) {
		
		try {
			new Client("127.0.0.1");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMessage() throws IOException {
		// TODO Auto-generated method stub
		start();
		if(in.ready()){
			
			String msg = in.readLine();
			end();
			return msg;
		}else{
			end();
			return null;
		}
		
		
	}
	
	public void sendMessage(String msg) throws IOException{
		
		out.write(msg + "\n");
		out.flush();
	}

}
