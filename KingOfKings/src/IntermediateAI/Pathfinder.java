package IntermediateAI;

import java.util.ArrayList;
import java.util.HashMap;

import Util.Point;

public class Pathfinder {
	
	private HashMap<Integer,Node> closedList;
	private HashMap<Integer,Node> openList;
	private Node smallestNode;
	private ArrayList<Node> path;
	private int[][] map;
	private int targetX;
	private int targetY;
	private boolean foundRoute;
	private int playerNo;
	
	
	public Pathfinder(int[][] map,int playerNo){
		
		closedList = new HashMap<Integer,Node>();
		openList = new HashMap<Integer,Node>();
		path = new ArrayList<Node>();
		this.map = map;
	}
	
	/*
	 * converts an path of nodes to a path of ints 
	 */
	private ArrayList<int[]> nodeListToIntList(ArrayList<Node> path){
		
		ArrayList<int[]> pathInt = new ArrayList<int[]>();
		
		for(int i = 0; i < path.size(); i++){
			
			Node node = path.get(i);
			
			pathInt.add(new int[]{node.getX(),node.getY()});
		}
		
		return pathInt;
		
	}
	
	
	
	/*
	 * follows the parents of a node to get a path
	 */
	private void createPath(Node node){
		
		path.clear();
		
		while(node.getG() != 0){
			
			path.add(node);
			
			node = node.getParent();
			
		}
		
		path.add(node);
		
		
	}
	
	/*
	 * is the node on the list
	 */
	private boolean onList(Node node,ArrayList<Node> list){
		
		for(int i = 0; i < list.size(); i++){
			
			if(list.get(i).getX() == node.getX() 
					&& list.get(i).getY() == node.getY()){
				
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * is the node on the list, if so then return it's index
	 */
	private int onListIndex(Node node,ArrayList<Node> list){
		
		for(int i = 0; i < list.size(); i++){
			
			if(list.get(i).getX() == node.getX() 
					&& list.get(i).getY() == node.getY()){
				
				return i;
			}
		}
		
		return -1;
	}
	
	/*
	 * for debugging
	 */
	private void printList(Node start,ArrayList<Node> list){
		

		for(int j = 0; j < map.length; j++){
			for(int i = 0; i < map[j].length; i++){
				
				Node node = new Node(i,j);
				
				if(map[j][i] != 0){
					
					System.out.print(map[j][i]);
					
				}else if(onList(node,list)){
					
					if(targetX == i && targetY == j){
						
						System.out.print('T');
						
					}else if(start.getX() == i && start.getY() == j){
						
						System.out.print('S');
						
					}else{
						System.out.print('*');
					}
				}else{
					
					if(targetX == i && targetY == j){
						
						System.out.print('T');
						
					}else{
						System.out.print('.');
					}
					
				}
				
			}
			System.out.println();
		}
	}
	
	/*
	 * gets the node with the smallest F score 
	 */
	public Node getSmallestNode(){
		
//		int index = 0;
//		int lowest = Integer.MAX_VALUE;
//		
//		//if there is no route
//		if(openList.size() == 0){
//			
//			return null;
//		}
		
//		for(int i = 0; i < openList.size(); i++){
//
//			if(openList.get(i).getF() < lowest){
//				
//				index = i;
//				lowest = openList.get(i).getF();
//			}
//			
//		}
		
//		for(Node node : openList.values()){
//			
//			if(node.getF() < lowest){
//				
//				index = node.getIndex();
//				lowest = node.getF();
//			}
//		}
//		
//		return openList.get(index);
		Node ret = smallestNode;
		smallestNode = null;
		return ret;
		
	}
	
	/*
	 * gets a path from (startX,startY) to (targetX,targetY)
	 */
	public ArrayList<int[]> getPath(int startX, int startY, int targetX, int targetY){
		
//		System.out.println("start pathfinder");
//		for(int y = 0; y < map.length; y++){
//			for(int x = 0; x < map[y].length; x++){
//				
//				System.out.print(map[y][x]);
//			}
//			
//			System.out.println();
//		}
//		System.out.println("end pathfinder");
		smallestNode = null;
		if((startX == targetX && startY == targetY)
				|| (map[targetX][targetY] != 0 && map[targetX][targetY] != 4)){
		
			ArrayList<int[]> ret = new ArrayList<int[]>();
			ret.add(new int[]{startX,startY});
			
			return ret;
		}
		
		Node start = new Node(startX, startY);
		start.calculateH(targetX, targetY);
		
		closedList.clear();
		openList.clear();
		
		this.targetX = targetX;
		this.targetY = targetY;
		
		//add the start node
		openList.put(start.getIndex(), start);
		//openList.add(start);
		path.add(getSmallestNode());
		//closedList.add(start);
		closedList.put(start.getIndex(), start);
		//openList.remove(start);
		openList.remove(start.getIndex());
		
		Node node = start;
		

		while(true){
			
			//System.out.println("looping list Pathfinder");
			//adds the nodes around the current node to openlist 
			addNodes(node);
			//adds current node to the openlist 
			//openList.remove(node);
			openList.remove(node.getIndex());
			//gets the node with the smallest F score 
			node = getSmallestNode();
			//adds the new current node to the closedList
			//closedList.add(node);
			if(node != null){
				closedList.put(node.getIndex(), node);
			}
				
			//if there is not route 
			if(node == null){
				
				foundRoute = false;
				return new ArrayList<int[]>();
			
			//keep looking for a node in till the target node has been added to the closed list
			//}else if(closedList.get(closedList.size()-1).getX() == targetX 
			//		&& closedList.get(closedList.size()-1).getY() == targetY){
			}else if(closedList.containsKey(Point.GetUniqueNo(new int[]{targetX,targetY}))){
				
				foundRoute = true;
				break;
				
			} 

			
		}
		
		//if there is a route, find it 
		//if(foundRoute){
			createPath(closedList.get(Point.GetUniqueNo(new int[]{targetX,targetY})));
		//}
		
		//return path as list on int[] 
		return nodeListToIntList(path);				
	}
	
	public boolean foundRoute(){
		
		return foundRoute;
	}
	
	
	private void addNodes(Node start){
		
		Node currentNode;
		int[] loopControl = new int[]{0,1,0,-1,1,0,-1,0,1,1,-1,-1,1,-1,-1,1};

		
		//loops though all the nodes around the start node 
		for(int i = 0; i < 16; i+= 2){
			
			currentNode = new Node(start.getX() + loopControl[i],start.getY() + loopControl[i+1],start);
			
			currentNode.calculateG();
			currentNode.calculateH(targetX, targetY);
			//walkable
			if(currentNode.getY() >= map[0].length || currentNode.getX() >= map.length || 
					currentNode.getX() < 0 ||currentNode.getY() < 0 
					|| (map[currentNode.getX()][currentNode.getY()] != 0
							&& map[currentNode.getX()][currentNode.getY()] != 4
								 && !isOurWall(currentNode.getX(),currentNode.getY()))){
				
				//closedList.add(currentNode);
				closedList.put(currentNode.getIndex(), currentNode);
				continue;
			
		    //is the node on the closed list 
			}else if(closedList.containsKey(currentNode.getIndex())){

				continue;
				
			}else{
				//if the node is already on the openList 
				if(openList.containsKey(currentNode.getIndex())){
					
					//if the currentNode has a lower F score
					if(currentNode.getF() < openList.get(currentNode.getIndex()).getF()){
						
						//if so then change the parent
						openList.get(currentNode.getIndex()).changeParent(currentNode.getParent());
						
					}
				//else just add it to the openList
				}else{
					
					//openList.add(currentNode);
					openList.put(currentNode.getIndex(), currentNode);
				}
				
				isSmallestNode(currentNode);
			}
			
			
		}
		
	}
	
	private void isSmallestNode(Node node){
		
		if(smallestNode == null || node.getF() < smallestNode.getF()){
			
			smallestNode = node;
		}
	}

	private boolean isOurWall(int x, int y) {
		// TODO Auto-generated method stub
		
		String num = new Integer(map[x][y]).toString();
		
		return num.charAt(0) == '8' && 
				num.length() > 1 && num.charAt(1) == new Integer(playerNo).toString().charAt(0);
	}
	
//	public static boolean isOnList(int x, int y,ArrayList<int[]> list){
//		
//		for(int l = 0; l < list.size(); l++){
//			
//			if(list.get(l)[0] == x && list.get(l)[1] == y){
//				
//				return true;
//			}
//		}
//		
//		return false;
//	}
//	
//	public static void main(String[] args) {
//		
//		int[][] map = new int[][]{{0,0,0,0,0,0,1,0,0,0,1,1,0,0,1,0,0,0,1,1},
//								   {1,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,1,0},
//								   {0,0,0,0,1,0,0,0,0,1,0,0,1,0,1,0,0,0,0,0},
//								   {0,1,0,0,0,1,0,1,0,0,0,0,0,1,0,0,1,0,0,0},
//								   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
//		
//		Pathfinder pf = new Pathfinder(map);
//		ArrayList<int[]> list = pf.getPath(0, 0, map.length-1, map[0].length-1);
//		
//		System.out.println(list.size() + " pathfinder");
//		for(int x = 0; x < map.length; x++){
//			for(int y = 0; y < map[0].length; y++){
//				
//				if(isOnList(x,y,list)){
//					System.out.print("X ");
//				}else{
//					System.out.print(map[x][y] + " ");
//				}
//			}
//			System.out.println();
//		}
//		
//	}
	

}
