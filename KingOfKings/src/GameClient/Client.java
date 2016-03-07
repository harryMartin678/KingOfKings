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
	
	public Client() throws UnknownHostException, IOException{
		
		Socket client = new Socket("127.0.0.1",9999);
		
		lock = new Semaphore(1);
		
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		
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
			new Client();
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
