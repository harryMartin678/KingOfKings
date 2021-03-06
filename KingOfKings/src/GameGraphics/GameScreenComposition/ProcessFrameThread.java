package GameGraphics.GameScreenComposition;

import java.util.ArrayList;

import AI.AIHandler;
import Buildings.Names;
import GameClient.ClientMessages;
import GameClient.ParseText;
import GameGraphics.Building;
import GameGraphics.Unit;

public class ProcessFrameThread {
	
	private ClientWrapper cmsg;
	private IComFrameProcessMap map;
	private IComFrameProcessDisplay display;
	private IComGameEngineFrameProcess engine;
	private IComUnitListFrameProcess units;
	private IComBuildingListFrameProcess buildings;
	private IComMouseFrameProcess mouse;
	private boolean mapBeenSet;
	
	public ProcessFrameThread(ClientWrapper cmsg,IComFrameProcessMap map,
			IComFrameProcessDisplay display,IComGameEngineFrameProcess engine,
			IComUnitListFrameProcess units,IComBuildingListFrameProcess buildings,
			IComMouseFrameProcess mouse){
		
		this.cmsg = cmsg;
		this.map = map;
		this.display = display;
		this.engine = engine;
		this.units = units;
		this.buildings = buildings;
		this.mouse = mouse;
	}
	

	
	Thread getFrame = new Thread(new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			//cmsg.addMessage("SEND_FRAME");
			
			while(true){

				
				long time = System.currentTimeMillis();
				long time2 = 0;
				String[] frame = cmsg.requestFrame().split("\n");
				
				processFrame(frame, 0);
				
				//System.out.println("Frame Processor: " + (System.currentTimeMillis() - time));
				long delay = 100 - (System.currentTimeMillis() - time);
				
				if(delay < 10){
					
					delay = 10;
				}
				
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	});
	
	private void processFrame(String[] msgs, int index){
		
		String msg;
		boolean mapChangedFrame = false;
		
		buildings.clearAllQueues();
		
//		for(int i = 0; i < msgs.size(); i++){
//			
//			System.out.println(msgs.get(i));
//		}
		
		if(msgs[0].equals("START_FRAME")){
			
			index++;
		}
		int viewedMap;
		//if viewed map has not made it over then skip it 
		if(msgs[index].length() < 3){
			//bug here
			viewedMap = new Integer(msgs[index]).intValue();
			int lastViewedMap = map.getViewedMap();
			
			if(lastViewedMap != viewedMap || !mapBeenSet){
				
				if(mapBeenSet){
					map.setLastMapFrames(display.getFrameX(),display.getFrameY());
				}
				
				map.setViewedMap(viewedMap);
				mapChangedFrame = true;
				
//				if(lastViewedMap != viewedMap){
//					
				display.setFrameX(map.getLastMapFramesX());
				display.setFrameY(map.getLastMapFrameY());
				//}
					
				mapBeenSet = true;
			}
			
			
			
			index++;
		}else{
			
			viewedMap = engine.getPlayerNumber();
		}
		
		
		
		int m = index+2;
		
		if(!msgs[index].equals("unitlist")){
		
			ArrayList<String> mapInfo = new ParseText(msgs[index]).getNumbers();
			
			for(int in = 0; in < mapInfo.size(); in+=2){
				
//				if(new Integer(mapInfo.get(in)).intValue() == viewedMap){
//					
//					engine.setMyPlayerNumber(new Integer(mapInfo.get(in+1)).intValue());
//				}
//				System.out.println(new Integer(mapInfo.get(in)).intValue() + " map " +
//						new Integer(mapInfo.get(in+1)).intValue() + " player ProcesssFrameThread");
				map.setMapPlayer(new Integer(mapInfo.get(in)).intValue(),
						new Integer(mapInfo.get(in+1)).intValue());
			}
		
		}
		
		units.begin();
		
		//Unit.ClearAnimateUnits();
		//units.clear();
		
		ArrayList<Unit> TempUnits = new ArrayList<Unit>();
		
		while(!(msg = msgs[m]).equals("buildinglist")){
			
			if(msg.equals("unitlist")){
				
				m++;
				continue;
			}
			
			ParseText parsed = new ParseText(msg);
			ArrayList<String> numbers = parsed.getNumbers();
			String unitName = parsed.getUnitName();

			
			if(unitName.equals("die")){
				
				//Unit.removeUnit(units.getUnitByUnitNo(new Integer(numbers.get(0)).intValue()));
				System.out.println("die " + new Integer(numbers.get(0)).intValue() + " ProcessFrameThread");
				//System.out.println(units.getUnitListSize() + " PROCESSFRAMETHREAD BEFORE");
				//units.removeByUnitNo(new Integer(numbers.get(0)).intValue());
				//System.out.println(units.getUnitListSize() + " PROCESSFRAMETHREAD AFTER");
				units.killByUnitNo(new Integer(numbers.get(0)).intValue());
				m++;
				continue;
			}
			
//			if(parsed.getUnitType().equals("chariot")){
//				
//				ChariotUnit chariot = new ChariotUnit(new Float(numbers.get(1)).floatValue(),
//						new Float(numbers.get(2)).floatValue()
//						,unitName,
//						new Integer(numbers.get(3)).intValue(),new Integer(numbers.get(0)).intValue());
//				TempUnits.add(chariot);
//				//units.add(chariot);
//			
//			}else{

				
				Unit unit = new Unit(new Float(numbers.get(1)).floatValue(),
						new Float(numbers.get(2)).floatValue()
						,unitName,
						new Integer(numbers.get(3)).intValue(),new Integer(numbers.get(0)).intValue());
				//System.out.println(new Integer(numbers.get(0)).intValue() + " " + 
						//	new Integer(numbers.get(3)).intValue() 
							//+ " ProcessFrameThread");
				
				
				if(new Integer(numbers.get(4)).intValue() == 1){
					
					unit.setDelayedMoving();
				
				}
				
//				if(numbers.size() < 7){
//					
//					System.out.println(msg + " ProcessFrameThread");
//				}
				
				if(new Integer(numbers.get(6)).intValue() == 1){
					
					unit.setDelayedFiring();
					unit.setAttack(new Integer(numbers.get(7)).intValue());
					
				}
				

				unit.setAngle(new Integer(numbers.get(5)).intValue());
				
				//if(unit.getUnitNo() == 5){
					
					//System.out.println(unit.getUnitNo() + " " + unit.getX()
						//	+ " " + unit.getY());
				//}
				//units.add(unit);
				TempUnits.add(unit);
			
			//}
				
			m++;
		}
		
		units.setUnits(TempUnits,mapChangedFrame);
		
		units.end();
		
		//System.out.println(units.size() + " unitSize");

		buildings.begin();
		
		//System.out.println("CLEAR PROCESSFRAMETHREAD");
		ArrayList<Building> buildingsTemp = new ArrayList<Building>();
		//System.out.println("buildingSize " + buildings.size() + " ProcessFrameThread start");
		while(!(msg = msgs[m]).equals("sites")){
		
			msg = msgs[m];
			
			if(msg.equals("buildinglist")){
				
				m++;
				continue;
			}
			//System.out.println(msg + "|building ProcessFrameThread");
			ParseText parsed = new ParseText(msg);
			ArrayList<String> numbers = parsed.getNumbers();
			String building = parsed.getUnitName();
			
			//System.out.println(msg + " " + building + " ProcessFrameThread");
			
			if(building.equals("collapse")){
				
				buildings.collapseByBuildingNo(new Integer(numbers.get(0)).intValue());
				
			}else{
			
				//System.out.println(new Integer(numbers.get(3)).intValue() + " player ProcesssFrameThread");
				buildingsTemp.add(new Building(new Float(numbers.get(1)).floatValue(),
						new Float(numbers.get(2)).floatValue(),building,
						new Integer(numbers.get(0)).intValue(),new Integer(numbers.get(3)).intValue()));
				buildingsTemp.get(buildingsTemp.size()-1).SetSite(false);
				buildingsTemp.get(buildingsTemp.size()-1).setAttack(new Integer(numbers.get(4)).intValue(),
						new Integer(numbers.get(5)).intValue(),
						new Integer(numbers.get(6)).intValue());
				if(building.equals(Names.WALL)){
					buildingsTemp.get(buildingsTemp.size()-1).setAngle(new Float(numbers.get(7)).floatValue());
				}
			}
			m++;
		}
		
		
		
		while(!msgs[m].equals("buildingqueue")){
		//for(int b = m; b < msgs.size(); b++){
			
			if(msgs[m].equals("sites")){
				
				m++;
				continue;
			}
			
			ParseText parsed = new ParseText(msgs[m]);
			ArrayList<String> numbers = parsed.getNumbers();
			String building = parsed.getUnitName();
			buildingsTemp.add(new Building(new Float(numbers.get(1)).intValue(),
					new Float(numbers.get(2)).intValue(),building,new Float(numbers.get(0)).intValue(),
					new Integer(numbers.get(3)).intValue()));
			buildingsTemp.get(buildingsTemp.size()-1).SetSite(true);
			
			m++;
		}
		
		m++;
		buildings.setBuildings(buildingsTemp,mapChangedFrame);
		
		
		while(!(m >= msgs.length || msgs[m].equals("resource"))){
			
			if(msgs[m].equals("buildingqueue") || msgs[m].equals("")){
				
				m++;
				continue;
			}
			//System.out.println(msgs.get(m) + "|||| building Queue ProcessFrameThread");
			String[] queueInfo = msgs[m].split(" ");
			Building building = buildings.getBuildingByBuildingNo(new Integer(queueInfo[0]).intValue());
			
			if(building != null){
				building.clearUnitQueue();
				
				buildings.clearSelectedBuildingQueue(new Integer(queueInfo[0]).intValue());
				
				building.setProgress(new Integer(queueInfo[1]).intValue());
				
				//System.out.println(building.getSize() + " before");
				for(int u = 2; u < queueInfo.length; u++){

					buildings.addUnitToBuildingQueue(new Integer(queueInfo[0]).intValue(), queueInfo[u]);
					//buildings.get(new Integer(queueInfo[0]).intValue()).addUnitQueue(queueInfo[u]);
				}
				//System.out.println(building.getSize() + " after");
			}
			
			m++;
			
		}
		
		m++;
		
		if(m < msgs.length){
			
			String[] resource = msgs[m].split(" ");
			display.setResources(new Integer(resource[1]).intValue(), new Integer(resource[2]).intValue());
			mouse.setResources(new Integer(resource[1]).intValue(), new Integer(resource[2]).intValue());

		}
		
//		for(int b = 0; b < buildings.size(); b++){
//			
//			System.out.println(buildings.get(b).getBuildingNo() + " " 
//					+ buildings.get(b).getName() + " " + buildings.get(b).isSite());
//		}
		//System.out.println("FULL PROCESSFRAMETHREAD");
		//System.out.println("buildingSize " + buildings.size() + " ProcessFrameThread end");
		buildings.end();
	}
	
	
	public void load(String[] load){
		
		processFrame(load,2);
		//getFrame.start();
		
	}
	
	public void start(){
		
		getFrame.start();
	}

}
