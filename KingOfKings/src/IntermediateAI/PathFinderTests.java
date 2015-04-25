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
	
	@Test
	public void test1(){
		
		char[][] map = new char[][]{{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'}};
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(1,5, 1, 0);
		
		assert(pf.foundRoute());
		assert(path.size() == 6);
	}
	
	@Test
	public void test2(){
		
		char[][] map = new char[][]{{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'}};
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(0,0, 5, 5);
		
		assert(pf.foundRoute());
		assert(path.size() == 6);
	}
	
	@Test
	public void test3(){
		
		char[][] map = new char[][]{{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'}};
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(5,5, 0, 0);
		
		assert(pf.foundRoute());
		assert(path.size() == 6);
	}
	
	@Test
	public void test4(){
		
			char[][] map = new char[][]{{'.','.','.','.','.','.'},
					{'.','.','.','.','#','#'},
					{'.','.','.','.','#','.'},
					{'.','.','#','.','.','.'},
					{'.','.','#','.','#','#'},
					{'.','.','#','.','.','.'}};
		
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(5,5, 5, 0);
		
		assert(pf.foundRoute());
		assert(path.size() == 8);
	}
	
	@Test
	public void test5(){
		
		char[][] map = new char[][]{{'.','.','.','.','.','.'},
				{'.','.','.','.','#','#'},
				{'.','.','.','.','#','.'},
				{'.','.','.','.','#','.'},
				{'.','.','.','.','#','.'},
				{'.','.','.','.','.','.'}};
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(5,5, 5, 0);
		
		assert(pf.foundRoute());
		assert(path.size() == 8);
	}
	
	@Test
	public void test6(){
		
		char[][] map = new char[][]{{'.','.','.','.','#','#'},
				{'.','.','.','#','#','.'},
				{'.','.','#','#','.','.'},
				{'.','#','#','.','.','.'},
				{'.','#','.','#','#','.'},
				{'.','.','.','#','.','.'}};
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(0,0, 5, 5);
		
		assert(pf.foundRoute());
		assert(path.size() == 11);
	}
	
	@Test
	public void test7(){
		
		char[][] map = new char[200][200];
		
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				
				map[i][j] = '.';
			}
			
		}
		Pathfinder pf = new Pathfinder(map);
		ArrayList<int[]> path = pf.getPath(0,0, 199, 199);
		
		assert(pf.foundRoute());
		assert(path.size() == 200);
	}
	


}
