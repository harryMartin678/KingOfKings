package AI;

import java.util.ArrayList;
import java.util.HashMap;

import Buildings.BuildingList;
import GameClient.ClientMessages;
import GameGraphics.Unit;
import GameGraphics.GameScreenComposition.MapComp;
import GameServer.UserCommandList;
import Map.MapList;
import Player.PlayerList;
import Units.UnitList;

public class AIHandler {

	private ArrayList<IAI> ais;
	private HashMap<Integer,Integer> aiNumToIndex;
	private AIVision vision;
	private MapList maps;
	private PlayerList players;
	
	public AIHandler(UnitList units,BuildingList buildings,
			MapList maps,PlayerList players){
		
		ais = new ArrayList<IAI>();
		vision = new AIVision(units,buildings);
		aiNumToIndex = new HashMap<Integer, Integer>();
		this.maps = maps;
		this.players = players;
	}
	
	public void newInitialAI(String AIName,int AINum,long Seed){
		
		ais.add(new InitialAI(AIName,AINum,vision,Seed));
		aiNumToIndex.put(AINum, ais.size()-1);
	}

	public void startAIs() {
		// TODO Auto-generated method stub
		for(int a = 0; a < ais.size(); a++){
			
			ais.get(a).StartAI();
		}
	}
	
	public boolean areAIs(){
		
		return ais.size() > 0;
	}

	public void updateAIs(UnitList units) {
		// TODO Auto-generated method stub
		//System.out.println("START UDPDATE AIHandler");
		for(int m = 0; m < maps.getSize(); m++){
			
			if(aiNumToIndex.containsKey(maps.getPlayer(m))){
				
				//System.out.println(m + " " + maps.getPlayer(m) + " AIHandler");
				ais.get(aiNumToIndex.get(maps.getPlayer(m))).UpdateForMap(m,
						players.getPlayersFood(maps.getPlayer(m)),players.getPlayersGold(maps.getPlayer(m)));
			}
		}
		
		//System.out.println("END UDPDATE AIHandler");
	}

	public void doAICommands(UserCommandList commands,int communicationTurnNo) {
		// TODO Auto-generated method stub
		for(int a = 0; a < ais.size(); a++){
			for(int c = 0; c < ais.get(a).getNoOfCommands(); c++){
				
				AICommand comm = ais.get(a).collectCommand();
				commands.addNextTurn(comm.command, comm.parameters, communicationTurnNo);
			}
		}
	}
}
