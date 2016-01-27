package GameServer;

import java.util.ArrayList;

public class UserCommandList {

	private ArrayList<MethodCallup> userCommands;
	private GameEngineContext context;
	
	public UserCommandList(GameEngineContext context){
		
		userCommands = new ArrayList<MethodCallup>();
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
	
	public void callMethods(int communicationTurn){
		
//		System.out.println("user command call " + userCommands.size());
//		for(int uc = 0; uc < userCommands.size(); uc++){
//			
//			System.out.println(userCommands.get(uc).getMethodCallup());
//		}
//		
		while(userCommands.size() > 0 &&
				userCommands.get(0).getCommunicationTurnNo() != communicationTurn){
			//System.out.println("user command " + userCommands.get(0).getMethodCallup());
			userCommands.get(0).call();
			userCommands.remove(0);
		}
	}
}
