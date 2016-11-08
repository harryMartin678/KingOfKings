package AI;

import java.util.ArrayList;

import GameClient.ClientMessages;

public class AIHandler {

	private ArrayList<IAI> ais;
	
	public AIHandler(){
		
		ais = new ArrayList<IAI>();
	}
	
	public void newInitialAI(String AIName,int AINum,ClientMessages cmsg){
		
		ais.add(new InitialAI(AIName,AINum,cmsg));
	}

	public void startAIs() {
		// TODO Auto-generated method stub
		for(int a = 0; a < ais.size(); a++){
			
			ais.get(a).StartAI();
		}
	}
}
