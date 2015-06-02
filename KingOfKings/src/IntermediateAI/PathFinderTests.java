package IntermediateAI;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PathFinderTests {


	public void test() {
		
		test1();
		test2();
		test3();
		test4();
		test5();
		test6();
		test7();
	}
	
	private int[][] convert(int[][] map){
		
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[x].length; y++){
				
				map[x][y] -= '.';
			}
		}
		
		return map;
	}
	
	@Test
	public void test1(){
		
		int[][] map = new int[][]{{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'}};
		map = convert(map);
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(1,5, 1, 0);
		
		assert(pf.foundRoute());
		assert(path.size() == 6);
	}
	
	@Test
	public void test2(){
		
		int[][] map = new int[][]{{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'}};
		map = convert(map);
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(0,0, 5, 5);
		//for(int i = 0; i < path.size(); i++){
			
			//System.out.println((path.get(i)[0]) + (path.get(i)[1]));
		//}
		assert(pf.foundRoute());
		assert(path.size() == 6);
	}
	
	@Test
	public void test3(){
		
		int[][] map = new int[][]{{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'}};
		map = convert(map);
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(5,5, 0, 0);
		
		assert(pf.foundRoute());
		assert(path.size() == 6);
	}
	
	@Test
	public void test4(){
		
		int[][] map = new int[][]{{'.','.','.','.','.','.'},
					{'.','.','.','.','#','#'},
					{'.','.','.','.','#','.'},
					{'.','.','#','.','.','.'},
					{'.','.','#','.','#','#'},
					{'.','.','#','.','.','.'}};
		map = convert(map);
		
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(5,5, 5, 0);
		
		assert(pf.foundRoute());
		assert(path.size() == 8);
	}
	
	@Test
	public void test5(){
		
		int[][] map = new int[][]{{'.','.','.','.','.','.'},
				{'.','.','.','.','#','#'},
				{'.','.','.','.','#','.'},
				{'.','.','.','.','#','.'},
				{'.','.','.','.','#','.'},
				{'.','.','.','.','.','.'}};
		map = convert(map);
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(5,5, 5, 0);
		
		assert(pf.foundRoute());
		assert(path.size() == 8);
	}
	
	@Test
	public void test6(){
		
		int[][] map = new int[][]{{'.','.','.','.','#','#'},
				{'.','.','.','#','#','.'},
				{'.','.','#','#','.','.'},
				{'.','#','#','.','.','.'},
				{'.','#','.','#','#','.'},
				{'.','.','.','#','.','.'}};
		map = convert(map);
		
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(0,0, 5, 5);
		
		assert(pf.foundRoute());
		assert(path.size() == 11);
	}
	
	@Test
	public void test7(){
		
		int[][] map = new int[200][200];
		
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				
				map[i][j] = '.';
			}
			
		}
		map = convert(map);
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(0,0, 199, 199);
		
		assert(pf.foundRoute());
		assert(path.size() == 200);
	}
	


}
