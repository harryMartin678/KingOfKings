package GameClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	public Client() throws UnknownHostException, IOException{
		
		Socket client = new Socket("127.0.0.1",9999);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		
		String start = in.readLine();
		System.out.println(start);
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

}
