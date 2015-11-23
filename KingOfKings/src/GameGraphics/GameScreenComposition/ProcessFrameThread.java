package GameGraphics.GameScreenComposition;

import java.util.ArrayList;

import GameClient.ClientMessages;
import GameClient.ParseText;
import GameGraphics.Building;
import GameGraphics.ChariotUnit;
import GameGraphics.Unit;

public class ProcessFrameThread {
	
	private ClientMessages cmsg;
	private IComFrameProcessMap map;
	private IComFrameProcessDisplay display;
	private IComGameEngineFrameProcess engine;
	private IComUnitListFrameProcess units;
	private IComBuildingListFrameProcess buildings;
	
	public ProcessFrameThread(ClientMessages cmsg,IComFrameProcessMap map,
			IComFrameProcessDisplay display,IComGameEngineFrameProcess engine,
			IComUnitListFrameProcess units,IComBuildingListFrameProcess buildings){
		
		this.cmsg = cmsg;
		this.map = map;
		this.display = display;
		this.engine = engine;
		this.units = units;
		this.buildings = buildings;
	}
	

	
	Thread getFrame = new Thread(new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			cmsg.addMessage("SEND_FRAME");
			
			while(true){

				
				long time = System.currentTimeMillis();
				
				/*try {
					Thread.sleep(2);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				
				//System.out.println("DO FRAME");
				
				if(cmsg.getMessage().equals("START_FRAME")){
					
					ArrayList<String> msgs = new ArrayList<String>();
					
					while(true){
						
						String msg = "";
						
						if((msg = cmsg.getMessage()) != null && !msg.equals("null")){
							
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
										//e.printStackTrace();
									}
								}
								
								cmsg.addMessage("SEND_FRAME");
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
		
		//for(int i = 0; i < msgs.size(); i++){
			
			//System.out.println(msgs.get(i));
		//}
		
		if(msgs.get(0).equals("START_FRAME")){
			
			index++;
		}
		
		
		//bug here
		int viewedMap = new Integer(msgs.get(index)).intValue();

		
		if(map.getViewedMap() != viewedMap){
			
			map.setLastMapFrames(display.getFrameX(),display.getFrameY());
			display.setFrameX(map.getLastMapFramesX());
			display.setFrameY(map.getLastMapFrameY());
		}
		
		map.setViewedMap(viewedMap);
		
		index++;
		
		int m = index+2;
		
		if(!msgs.get(index).equals("unitlist")){
		
			ArrayList<String> mapInfo = new ParseText(msgs.get(index)).getNumbers();
			
			for(int in = 0; in < mapInfo.size(); in+=2){
				
				if(new Integer(mapInfo.get(in)).intValue() == viewedMap){
					
					engine.setMyPlayerNumber(new Integer(mapInfo.get(in+1)).intValue());
				}
				
				map.setMapPlayer(new Integer(mapInfo.get(in)).intValue(),
						new Integer(mapInfo.get(in+1)).intValue());
			}
		
		}
		
		units.begin();
		
		units.clear();
		
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
				units.add(chariot);
			
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
					
				}
				

				unit.setAngle(new Integer(numbers.get(5)).intValue());
				
				//if(unit.getUnitNo() == 5){
					
					//System.out.println(unit.getUnitNo() + " " + unit.getX()
						//	+ " " + unit.getY());
				//}
				units.add(unit);
			}
				
			m++;
		}
		
		units.end();
		
		//System.out.println(units.size() + " unitSize");

		buildings.begin();
		
		buildings.clear();
		
		for(int b = m+1; b < msgs.size(); b++){
		
			msg = msgs.get(b);
			
			if(msg.equals("buildinglist")){
				
				continue;
			}
			//System.out.println(msg);
			ParseText parsed = new ParseText(msg);
			ArrayList<String> numbers = parsed.getNumbers();
			String building = parsed.getUnitName();
			
			
			buildings.add(new Building(new Float(numbers.get(1)).floatValue(),
					new Float(numbers.get(2)).floatValue(),building,
					new Integer(numbers.get(0)).intValue()));
			
		}

		buildings.end();
	}
	
	public void start(ArrayList<String> load){
		
		processFrame(load,1);
		getFrame.start();
	}

}
