package IntermediateAI;

import java.util.ArrayList;

public class Pathfinder {
	
	private ArrayList<Node> closedList;
	private ArrayList<Node> openList;
	private ArrayList<Node> path;
	private int[][] map;
	private int targetX;
	private int targetY;
	private boolean foundRoute;
	
	
	public Pathfinder(int[][] map){
		
		closedList = new ArrayList<Node>();
		openList = new ArrayList<Node>();
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
		
		int index = 0;
		int lowest = Integer.MAX_VALUE;
		
		//if there is no route
		if(openList.size() == 0){
			
			return null;
		}
		
		for(int i = 0; i < openList.size(); i++){

			if(openList.get(i).getF() < lowest){
				
				index = i;
				lowest = openList.get(i).getF();
			}
			
		}
		
		return openList.get(index);
		
	}
	
	/*
	 * gets a path from (startX,startY) to (targetX,targetY)
	 */
	public ArrayList<int[]> getPath(int startX, int startY, int targetX, int targetY){
		
		
		if(startX == targetX && startY == targetY){
		
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
		openList.add(start);
		path.add(getSmallestNode());
		closedList.add(start);
		openList.remove(start);
		
		Node node = start;
		

		while(true){
			
			//adds the nodes around the current node to openlist 
			addNodes(node);
			
			//adds current node to the openlist 
			openList.remove(node);
			//gets the node with the smallest F score 
			node = getSmallestNode();
			//adds the new current node to the closedList
			closedList.add(node);
				
			//if there is not route 
			if(node == null){
				
				foundRoute = false;
				break;
			
			//keep looking for a node in till the target node has been added to the closed list
			}else if(closedList.get(closedList.size()-1).getX() == targetX 
					&& closedList.get(closedList.size()-1).getY() == targetY){
				
				foundRoute = true;
				break;
				
			} 

			
		}
		
		//if there is a route, find it 
		if(foundRoute){
			createPath(closedList.get(closedList.size()-1));
		}
		
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
			if(currentNode.getY() >= map.length || currentNode.getX() >= map[0].length || 
					currentNode.getX() < 0 ||currentNode.getY() < 0 
					|| map[currentNode.getY()][currentNode.getX()] != 0){
				
				closedList.add(currentNode);
				continue;
			
		    //is the node on the closed list 
			}else if(onList(currentNode,closedList)){

				continue;
			}else{
				
				int index; 

				//if the node is already on the openList 
				if((index = onListIndex(currentNode,openList)) != -1){
					
					//if the currentNode has a lower F score
					if(currentNode.getF() < openList.get(index).getF()){
						
						//if so then change the parent
						openList.get(index).changeParent(currentNode.getParent());
					}
				//else just add it to the openList
				}else{
					
					openList.add(currentNode);
				}
			}
			
			
		}
		
	}
	

}
