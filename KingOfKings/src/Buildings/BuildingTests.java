package Buildings;

import static org.junit.Assert.*;

import org.junit.Test;

public class BuildingTests {
	
	Castle ct;
	BallistaTower bt;
	TowerBattle tb;

	@Test
	public void test() {
		
		ct = new Castle(0);
		bt = new BallistaTower(0);
		tb = new TowerBattle(ct,bt);
		
	}
	
	@Test
	public void TowerVsTower(){
		
		ct = new Castle(0);
		bt = new BallistaTower(0);
		tb = new TowerBattle(ct,bt);
		
		assert(ct.getHitpoints() == 3700);
		ct.setPos(0, 0);
		bt.setPos(1, 1);
		System.out.println(ct.getHitpoints() + " " + bt.getHitpoints());
		tb.similuateHitTowers();
		System.out.println(ct.getHitpoints() + " " + bt.getHitpoints());
		tb.similuateHitTowers();
		System.out.println(ct.getHitpoints() + " " + bt.getHitpoints());
	}
	
	@Test
	public void TowerVsUnit(){
		
//		ct = new Castle(0);
//		Swordsman ts = new Swordsman();
//		ts.setPos(1, 1);
//		tb = new TowerBattle(ts,ct);
//		
//		assert(ct.getHitpoints() == 3700);
//		ct.setPos(0, 0);
//		System.out.println(ct.getHitpoints() + " " + ts.getHealth());
//		tb.similuateHit();
//		System.out.println(ct.getHitpoints() + " " + ts.getHealth());
//		tb.similuateHit();
//		System.out.println(ct.getHitpoints() + " " + ts.getHealth());
	}
	
	@Test
	public void BuildingDestruction(){
		
//		RoyalPalace rp = new RoyalPalace(0);
//		Swordsman ts = new Swordsman();
//		BuildingDestruction bd = new BuildingDestruction(rp,ts);
//		rp.setPos(0, 0);
//		ts.setPos(1, 0);
//		
//		System.out.println(rp.getHitpoints());
//		bd.simulateHit();
//		System.out.println(rp.getHitpoints());
	}
	
	

}
