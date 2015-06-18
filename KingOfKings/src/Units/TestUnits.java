package Units;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestUnits {

	LightChariot lc;
	HeavyChariot rc;
	Slave sl;
	Servant sv;
	BatteringRam br;
	Battle battle;
	
	
	@Test
	public void test() {
		
		lc = new LightChariot();
		rc = new HeavyChariot();
		sl = new Slave();
		sv = new Servant();
		br = new BatteringRam();
		
		rc.setPos(0,100);
		battle = new Battle(br,rc,0);
		
		//System.out.println(cp.getHealth() + " " + rc.getHealth());
		battle.similuateHit();
		//System.out.println(cp.getHealth() + " " + rc.getHealth());
		battle.similuateHit();
		//System.out.println(cp.getHealth() + " " + rc.getHealth());
		battle.similuateHit();
		//System.out.println(cp.getHealth() + " " + rc.getHealth());
		battle.similuateHit();
		//System.out.println(cp.getHealth() + " " + rc.getHealth());
		battle.similuateHit();
		//System.out.println(cp.getHealth() + " " + rc.getHealth());
		battle.similuateHit();
		//System.out.println(cp.getHealth() + " " + rc.getHealth());
		
		testStatsLc();
		testStatsRc();
		testStatsKn();
		testStatsSl();
		testStatsSv();
		/*testStatsPs();
		testStatsTsm();
		testStatsMsm();
		testStatsLb();
		testStatsCb();*/
		//testStatsCp();
		testStatsBr();
		
		
	}
	
	//tests that stats are correct
	@Test
	public void testStatsLc(){
		
		assert(lc.getMaxHealth() == 125);
		assert(lc.getAttack() == 4);
		assert(lc.getDefence() == 1);
		assert(lc.getSpeed() == 15);
		assert(lc.getHealth() == lc.getMaxHealth());
		
	}
	
	@Test
	public void testStatsRc(){
		
		assert(rc.getMaxHealth() == 150);
		assert(rc.getAttack() == 6);
		assert(rc.getDefence() == 3);
		assert(rc.getSpeed() == 10);
		
	}
	
	@Test
	public void testStatsKn(){
		
		assert(rc.getMaxHealth() == 200);
		assert(rc.getAttack() == 10);
		assert(rc.getDefence() == 10);
		assert(rc.getSpeed() == 7);
		assert(rc.getName().equals("Unit:Cavalry:Knight"));
		assert(rc.getBiasAttack("Archer") == 13);
		
	}
	
	@Test
	public void testStatsSl(){
		
		
		assert(sl.getSpeed() == 6);
		
	}
	
	@Test
	public void testStatsSv(){
		
		assert(sv.getMaxHealth() == 50);
		assert(sv.getProductivity() == 10);
		assert(sv.getAttack() == 3);
		assert(sv.getDefence() == 1);
		assert(sv.getSpeed() == 8);
		
	}
	
	/*
	@Test
	public void testStatsPs(){
		
		assert(ps.getMaxHealth() == 50);
		assert(ps.getAttack() == 2);
		assert(ps.getDefence() == 5);
		assert(ps.getSpeed() == 8);
		assert(ps.getProductivity() == 1);
		
	}
	
	
	@Test
	public void testStatsTsm(){
		
		assert(tsm.getMaxHealth() == 125);
		assert(tsm.getAttack() == 4);
		assert(tsm.getDefence() == 5);
		assert(tsm.getSpeed() == 4);
		
	}
	
	@Test
	public void testStatsMsm(){
		
		assert(msm.getMaxHealth() == 150);
		assert(msm.getAttack() == 7);
		assert(msm.getDefence() == 7);
		assert(msm.getSpeed() == 2);
		
	}
	
	@Test
	public void testStatsLb(){
		
		assert(lb.getMaxHealth() == 100);
		assert(lb.getAttack() == 2);
		assert(lb.getDefence() == 2);
		assert(lb.getSpeed() == 3);
		assert(lb.getRange() == 5);
		
	}
	
	@Test
	public void testStatsCb(){
		
		assert(cb.getMaxHealth() == 100);
		assert(cb.getAttack() == 4);
		assert(cb.getDefence() == 1);
		assert(cb.getSpeed() == 2);
		assert(cb.getRange() == 3);
		
	}
	
	/*@Test
	public void testStatsCp(){
		
		assert(cp.getMaxHealth() == 50);
		assert(cp.getAttack() == 75);
		assert(cp.getDefence() == 2);
		assert(cp.getSpeed() == 2);
		assert(cp.getRange() == 11);
		
	}*/
	
	@Test
	public void testStatsBr(){
		
		assert(br.getMaxHealth() == 75);
		assert(br.getAttack() == 125);
		assert(br.getDefence() == 1);
		assert(br.getSpeed() == 1);
		assert(br.getRange() == 1);
		
	}
	
	

}
