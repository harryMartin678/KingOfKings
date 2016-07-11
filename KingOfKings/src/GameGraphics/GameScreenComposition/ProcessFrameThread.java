package GameGraphics.GameScreenComposition;

import java.util.ArrayList;

import GameClient.ClientMessages;
import GameClient.ParseText;
import GameGraphics.Building;
import GameGraphics.ChariotUnit;
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
				cmsg.requestFrame();
				/*try {
					Thread.sleep(2);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				
				//System.out.println("DO FRAME");
				
				if(cmsg.getFrameMessage().equals("START_FRAME")){
					
					ArrayList<String> msgs = new ArrayList<String>();
					
					while(true){
						
						String msg = "";
						
						if((msg = cmsg.getFrameMessage()) != "null" && !msg.equals("null")){
							
							if(msg.equals("END_FRAME")){
								
								boolean building = false;
								boolean unit = false;
								
								for(int m = 0; m < msgs.size(); m++){
									
									if(msgs.get(m).equals("buildinglist")){
										
										building = true;
										break;
									
									}else if(msgs.get(m).equals("unitlist")){
										
										unit = true;
									}
								}
								
								if(unit && building){
									
									try{
										processFrame(msgs,0);
									}catch(Exception e){
										
										//catch the intermediate exceptions here 
										e.printStackTrace();
									}
								}
								
								//cmsg.addMessage("SEND_FRAME");
								break;
							}
								
							msgs.add(msg);
						}
						
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				long delay = 50 - (System.currentTimeMillis() - time);
				
				if(delay < 5){
					
					delay = 5;
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
	
	private void processFrame(ArrayList<String> msgs, int index){
		
		String msg;
		boolean mapChangedFrame = false;
		
		buildings.clearAllQueues();
		
//		for(int i = 0; i < msgs.size(); i++){
//			
//			System.out.println(msgs.get(i));
//		}
		
		if(msgs.get(0).equals("START_FRAME")){
			
			index++;
		}
		int viewedMap;
		//if viewed map has not made it over then skip it 
		if(msgs.get(index).length() < 3){
			//bug here
			viewedMap = new Integer(msgs.get(index)).intValue();
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
		
		if(!msgs.get(index).equals("unitlist")){
		
			ArrayList<String> mapInfo = new ParseText(msgs.get(index)).getNumbers();
			
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
		
		//units.clear();
		
		ArrayList<Unit> TempUnits = new ArrayList<Unit>();
		
		while(!(msg = msgs.get(m)).equals("buildinglist")){
			
			if(msg.equals("unitlist")){
				
				m++;
				continue;
			}
			
			ParseText parsed = new ParseText(msg);
			ArrayList<String> numbers = parsed.getNumbers();
			String unitName = parsed.getUnitName();

			
			if(unitName.equals("die")){
				
				units.removeByUnitNo(new Integer(numbers.get(0)).intValue());
				m++;
				continue;
			}
			
			if(parsed.getUnitType().equals("chariot")){
				
				ChariotUnit chariot = new ChariotUnit(new Float(numbers.get(1)).floatValue(),
						new Float(numbers.get(2)).floatValue()
						,unitName,
						new Integer(numbers.get(3)).intValue(),new Integer(numbers.get(0)).intValue());
				TempUnits.add(chariot);
				//units.add(chariot);
			
			}else{

				
				Unit unit = new Unit(new Float(numbers.get(1)).floatValue(),
						new Float(numbers.get(2)).floatValue()
						,unitName,
						new Integer(numbers.get(3)).intValue(),new Integer(numbers.get(0)).intValue());
				
				
				if(new Integer(numbers.get(4)).intValue() == 1){
					
					unit.moving();
				
				}
				
				if(new Integer(numbers.get(6)).intValue() == 1){
					
					unit.setFiring();
					unit.setAttack(new Integer(numbers.get(7)).intValue());
					
				}
				

				unit.setAngle(new Integer(numbers.get(5)).intValue());
				
				//if(unit.getUnitNo() == 5){
					
					//System.out.println(unit.getUnitNo() + " " + unit.getX()
						//	+ " " + unit.getY());
				//}
				//units.add(unit);
				TempUnits.add(unit);
			}
				
			m++;
		}
		
		
		units.setUnits(TempUnits,mapChangedFrame);
		
		units.end();
		
		//System.out.println(units.size() + " unitSize");

		buildings.begin();
		
		//System.out.println("CLEAR PROCESSFRAMETHREAD");
		ArrayList<Building> buildingsTemp = new ArrayList<Building>();
		//System.out.println("buildingSize " + buildings.size() + " ProcessFrameThread start");
		while(!(msg = msgs.get(m)).equals("sites")){
		
			msg = msgs.get(m);
			
			if(msg.equals("buildinglist")){
				
				m++;
				continue;
			}
			//System.out.println(msg);
			ParseText parsed = new ParseText(msg);
			ArrayList<String> numbers = parsed.getNumbers();
			String building = parsed.getUnitName();
			
			//System.out.println(new Integer(numbers.get(3)).intValue() + " player ProcesssFrameThread");
			buildingsTemp.add(new Building(new Float(numbers.get(1)).floatValue(),
					new Float(numbers.get(2)).floatValue(),building,
					new Integer(numbers.get(0)).intValue(),new Integer(numbers.get(3)).intValue()));
			buildingsTemp.get(buildingsTemp.size()-1).SetSite(false);
			buildingsTemp.get(buildingsTemp.size()-1).setAttack(new Integer(numbers.get(4)).intValue(),
					new Integer(numbers.get(5)).intValue(),
					new Integer(numbers.get(6)).intValue());
			m++;
		}
		
		
		
		while(!msgs.get(m).equals("buildingqueue")){
		//for(int b = m; b < msgs.size(); b++){
			
			if(msgs.get(m).equals("sites")){
				
				m++;
				continue;
			}
			
			ParseText parsed = new ParseText(msgs.get(m));
			ArrayList<String> numbers = parsed.getNumbers();
			String building = parsed.getUnitName();
			
			buildingsTemp.add(new Building(new Float(numbers.get(1)).intValue(),
					new Float(numbers.get(2)).intValue(),building,new Float(numbers.get(0)).intValue(),
					new Integer(numbers.get(3)).intValue()));
			buildingsTemp.get(buildingsTemp.size()-1).SetSite(true);
			
			m++;
		}
		
		m++;
		buildings.setBuildings(buildingsTemp);
		
		
		while(!(m >= msgs.size() || msgs.get(m).equals("resource"))){
			
			if(msgs.get(m).equals("buildingqueue") || msgs.get(m).equals("")){
				
				m++;
				continue;
			}
			//System.out.println(msgs.get(m) + "|||| building Queue");
			String[] queueInfo = msgs.get(m).split(" ");
		
			Building building = buildings.getBuildingByBuildingNo(new Integer(queueInfo[0]).intValue());
			
			if(building != null){
				building.clearUnitQueue();
				
				buildings.clearSelectedBuildingQueue(new Integer(queueInfo[0]).intValue());
				
				building.setProgress(new Integer(queueInfo[1]).intValue());
				
				//System.out.println(building.getSize() + " before");
				for(int u = 2; u < queueInfo.length; u++){
					//System.out.println(queueInfo.length + " queueInfo");
					buildings.addUnitToBuildingQueue(new Integer(queueInfo[0]).intValue(), queueInfo[u]);
					//buildings.get(new Integer(queueInfo[0]).intValue()).addUnitQueue(queueInfo[u]);
				}
				//System.out.println(building.getSize() + " after");
			}
			
			m++;
			
		}
		
		m++;
		
		if(m < msgs.size()){
			
			String[] resource = msgs.get(m).split(" ");
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
	
	
	public void load(ArrayList<String> load){
		
		processFrame(load,2);
		//getFrame.start();
		
	}
	
	public void start(){
		
		getFrame.start();
	}

}
