package WW;

import java.util.ArrayList;

public class Dictionary {

	WordList starter;
	ArrayList<Words> level1;
	ArrayList<Words> level2;
	ArrayList<Words> level3;

	public Dictionary() {
		starter = new WordList();
		level1 = new ArrayList<Words>();
		level2 = new ArrayList<Words>();
		level3 = new ArrayList<Words>();
		init();
	}
	
	private void init() {
		initStarter();
		initLevel1();
		initLevel2();
		initLevel3();		
	}
	
	public WordList getStarter() {
		return starter;
	}
	
	public ArrayList<Words> getLevelWords(int level){
		switch(level) {
			case 1: return level1;
			case 2: return level2;
			case 3: return level3;
			default: return null;
		}
	}
	
	private void initStarter() {
		ArrayList<String> temp1 = new ArrayList<String>();
		ArrayList<String> temp2 = new ArrayList<String>();
		temp1.add("Abhor"); temp1.add("Loathe"); temp1.add("Scorn");
		temp2.add("Admire"); temp2.add("Cherish"); temp2.add("Approve");
		starter.cascadeAdd(temp1, temp2);
		temp1.clear(); temp2.clear();
		temp1.add("Hamper"); temp1.add("Obligate"); temp1.add("Restrict");
		temp2.add("Allow"); temp2.add("Permit");
		starter.cascadeAdd(temp1, temp2);
		temp1.clear(); temp2.clear();
	}
	
	private void initLevel1() {
		WordList tempwords = new WordList();
		ArrayList<String> temp1 = new ArrayList<String>();
		ArrayList<String> temp2 = new ArrayList<String>();
		temp1.add("Abjure"); temp1.add("Recant"); temp1.add("Disavow");
		temp2.add("Confirm"); temp2.add("Emphasize"); temp2.add("Recapitulate");
		tempwords.cascadeAdd(temp1,  temp2);
		temp1.clear(); temp2.clear();
		temp1.add("Absolve"); temp1.add("Pardon"); temp1.add("Liberate");
		temp2.add("Charge"); temp2.add("Ostracize"); temp2.add("Chastise");
		tempwords.cascadeAdd(temp1, temp2);
		temp1.clear(); temp2.clear();
		temp1.add("Confound"); temp1.add("Flabbergast"); temp1.add("Perplex");
		temp2.add("Clarify"); temp2.add("Explain"); temp2.add("Enlighten");
		tempwords.cascadeAdd(temp1,  temp2);
		temp1.clear(); temp2.clear();
		temp1.add("Postulate"); temp1.add("Suppose"); temp1.add("Speculate");
		temp2.add("Measure"); temp2.add("Calculate"); temp2.add("Know");
		tempwords.cascadeAdd(temp1, temp2);
		temp1.clear(); temp2.clear();
		temp1.add("Onerous"); temp1.add("Arduous"); temp1.add("Burdensome");
		temp2.add("Trivial"); temp2.add("Simplistic"); temp2.add("Easy");
		tempwords.cascadeAdd(temp1, temp2);
		
		level1 = (ArrayList<Words>) tempwords.getKnown();
	}
	
	private void initLevel2() {
		
	}
	
	private void initLevel3() {
		
	}
		/* 
		 * 
		 * Deleterious (Harmful, ) ()
		 * Tractable (Obedient, Dutiful) ()
		 * Evacuate (vacate, empty, abandon) ()
		 * Lithe (Supple, Flexible) (Stiff)
		 * Ignomious (Shameful) ()
		 * Aloof (Distant, Detatched) ()
		 * Precarious (Unstable, Risky) ()
		 * Lofty (Snooty, Arrogant, Haughty) ()
		 * Deprecate (Criticize, Denounce) ()
		 * Impartial (Unbiased) ()
		 * 
		 * 
		 */	
	
}
