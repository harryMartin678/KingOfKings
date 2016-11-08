package AI;

import GameClient.ClientMessages;

public class InitialAI extends IAI {

	
	public InitialAI(String AIName,int AINum,ClientMessages cmsg){
		
		super(AIName,AINum,cmsg);
		
	}
	
	public void StartAI(){
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					
					
					
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		}).start();
	}
}
