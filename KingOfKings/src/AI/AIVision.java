package AI;

public class AIVision {

	private int AINum;
	
	public enum Type{
		
		UNIT,
		BUILDING,
		SITE
	}
	
	public AIVision(int AINum){
		
		this.AINum = AINum;
	}
	
	
	
	public class AIObj{
		
		private int id;
		private int x; 
		private int y;
		private boolean isBusy;
		private Type type;
		
		
		public AIObj(int id,int x, int y,Type type){
			
			this.id = id;
			this.x = x; 
			this.y = y;
			this.type = type;
		}
		
		public void busy(){
			
			isBusy = true;
		}
		
		public void idle(){
			
			isBusy = false;
		}
		
		public boolean isBusy(){
			
			return isBusy;
		}
		
		public int getX(){
			
			return x;
			
		}
		
		public int getY(){
			
			return y;
		}
		
		public int getID(){
			
			return id;
		}
		
		public Type getType(){
			
			return type;
		}

	}
}
