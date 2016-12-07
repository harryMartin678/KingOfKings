package GameServer;

import java.util.ArrayList;

public class UserCommandList {

	private ArrayList<MethodCallup> userCommands;
	private GameEngineContext context;
	private ArrayList<ArrayList<MethodCallup>> nextCommands;
	
	public UserCommandList(GameEngineContext context){
	
		userCommands = new ArrayList<MethodCallup>();
		nextCommands = new ArrayList<ArrayList<MethodCallup>>();
		for(int n = 0; n < 5; n++){
			
			nextCommands.add(new ArrayList<MethodCallup>());
		}
		this.context = context;
	}
	
	public void add(String methodName,MethodParameter parameters,int communicationTurnNo){


		userCommands.add(new MethodCallup(context,methodName,
						parameters,communicationTurnNo+2));
//		for(int c = 0; c < userCommands.size(); c++){
//			
//			if(userCommands.get(c).getCommunicationTurnNo() > communicationTurnNo){
//				
//				userCommands.add(c,new MethodCallup(context,methodName,
//						parameters,communicationTurnNo+2));
//			}
//		}
		
		//userCommands.add(new MethodCallup(context,methodName,parameters));
	}
	
	public void addNextTurn(String methodName,MethodParameter parameters,int communicationTurnNo){
		
		userCommands.add(new MethodCallup(context,methodName,
				parameters,communicationTurnNo+1));
	}
	
	private void clearNextArray(){
		
		for(int c = 0; c < nextCommands.size(); c++){
			
			nextCommands.get(c).clear();
		}
	}
	
	public void setUpCallMethods(int communicationTurn){
		
//		System.out.println("user command call " + userCommands.size());
//		for(int uc = 0; uc < userCommands.size(); uc++){
//			
//			System.out.println(userCommands.get(uc).getMethodCallup());
//		}
//		
//		while(userCommands.size() > 0 &&
//				userCommands.get(0).getCommunicationTurnNo() != communicationTurn){
//			//System.out.println("user command " + userCommands.get(0).getMethodCallup());
//			userCommands.get(0).call();
//			userCommands.remove(0);
//		}
		
		clearNextArray();
		int c = 0;
		for(int u = 0; u < userCommands.size(); u++){
			
			if(userCommands.get(u).getCommunicationTurnNo() != communicationTurn){
				
				nextCommands.get(c).add(userCommands.get(u));
				userCommands.remove(u);
				u--;
				c++;
				if(c == nextCommands.size()){
					
					c = 0;
				}
				
			}else{
				
				break;
			}
		}
		
		
	}
	
	public void callMethods(int beat){
		
		for(int m = 0; m < nextCommands.get(beat).size(); m++){
			
			nextCommands.get(beat).get(m).call();
		}
	}
}
