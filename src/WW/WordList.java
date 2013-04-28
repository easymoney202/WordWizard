package WW;

import java.util.*;

/**
 * WordList holds a list of Words objects and can run basic basic WordWizard
 * 
 * @author Team DMZ
 * @version Beta
 * 
 */
public class WordList {

	/** Two lists of words, known and encountered */
	List<String> encwords;
	List<Words> knownwords;

	/** Basic constructor */
	public WordList() {
		encwords = new ArrayList<String>();
		knownwords = new ArrayList<Words>();
	}

	/**
	 * addToList takes a word, synonyms and antonyms and populates Words object
	 * Also adds all of those synonyms and antonyms to the encountered list
	 * 
	 * @param word
	 *            the word used
	 * @param syns
	 *            ArrayList of synonyms
	 * @param ants
	 *            ArrayList of antonyms
	 */
	public void addToKnown(String word, ArrayList<String> syns,
			ArrayList<String> ants) {
		Words temp = new Words(word, syns, ants)
		if(!knownwords.contains(temp)) {
			knownwords.add(temp);
			for (String s : syns)
				addToEnc(s);
			for (String a : ants)
				addToEnc(a);
		}
	}
	
	public void addToKnown(Words w) {
		if(!knownwords.contains(w)) {
			knownwords.add(w);
			for (String s : w.getAllSyns())
				addToEnc(s);
			for (String a : w.getAllAnts())
				addToEnc(a);
		}
	}

	/**
	 * addToEnc adds a word to the list of "encountered" words -- those seen,
	 * but not known.
	 * 
	 * @param word
	 *            String for word to be added
	 */
	public void addToEnc(String word) {
		if (!encwords.contains(word))
			encwords.add(word);
	}

	/**
	 * getRandomWord pulls a random word from the encountered words and returns
	 * it
	 * 
	 * @return String representing random word from encwords
	 */
	public String getRandomEncWord() {
		Random rnd = new Random();
		int z = rnd.nextInt(encwords.size());
		return encwords.get(z);
	}

	/**
	 * getProblem returns numQs+1 words in an ArrayList, the first being the
	 * word to be matched, the second the correct answer, and the rest incorrect
	 * answers
	 * 
	 * @param numQs
	 *            int for how many total answers desired
	 * @param type
	 *            int for whether synonyms (if ==1) or antonyms
	 * @return ArrayList<String> with word, correct answer, then incorrect
	 *         answers
	 */
	public ArrayList<String> getProblem(int numQs, int type) {
		Random rnd = new Random();
		// Get a random number for the index of a word in thelist
		int z = rnd.nextInt(knownwords.size());
		String word = knownwords.get(z).getWord();
		ArrayList<String> matchset; // all synonyms/antonyms to provide
									// comparison when randoms are chosen
		ArrayList<String> qset = new ArrayList<String>(); // Make an array to
															// return
		String answer; // String for the correct answer
		if (type == 1) { // Ask for a synonym
			answer = knownwords.get(z).getRandomSyn();
			matchset = knownwords.get(z).getAllSyns();
		} // end if
		else { // ask for an antonym
			answer = knownwords.get(z).getRandomAnt();
			matchset = knownwords.get(z).getAllAnts();
		} // end else
		String temp;
		if(type == 1)
			temp = ("Find Synonym: " + word);
		else
			temp = ("Find Antonym: " + word);
		qset.add(temp); // add word to the beginning of the list
		qset.add(answer); // add answer right afterwards
		int i = 0;
		/* Get a random word, compare it to all correct answers */
		while (i < numQs - 1) {
			String wrong = getRandomEncWord();
			if (!matchset.contains(wrong) && !wrong.equals(word) && !qset.contains(wrong)) {
				qset.add(wrong);
				i++;
			} // end if
		} // end while
		return qset;
	}

	/**
	 * For now, assuming that every synonym of a synonym is a synonym of that
	 * synonym, and antonyms too. It then makes a Words object for each word
	 * involved in each list given
	 * 
	 * @param syns
	 *            ArrayList of words that are all synonyms of each other
	 * @param ants
	 *            ArrayList of antonyms that are all antonyms of each synonym,
	 *            and therefore all synonyms of each other
	 */
	public void cascadeAdd(ArrayList<String> syns, ArrayList<String> ants) {
		ArrayList<String> temparr1 = syns; // local copy of synonyms array
		ArrayList<String> temparr2 = ants; // local copy of antonyms array
		String tempstr; // temporary variable for a single string
		/*
		 * Take a word from the synonyms array, then add it to thelist with all
		 * of its synonyms and antonymsthen add it back to the temp list, and
		 * repeat for the next entry.
		 */
		for (int i = 0; i < syns.size(); i++) {
			tempstr = temparr1.remove(0);
			addToKnown(tempstr, temparr1, temparr2);
			temparr1.add(tempstr);
			// addToEnc(tempstr);
		} // end for
		/*
		 * Do the same as above, just with the antonyms. Only this time, the
		 * antonyms are synonyms of each other so temparr2 is used for synonyms
		 * and temparr1 for antonyms
		 */
		for (int i = 0; i < ants.size(); i++) {
			tempstr = temparr2.remove(0);
			addToKnown(tempstr, temparr2, temparr1);
			temparr2.add(tempstr);
			// addToEnc(tempstr);
		} // end for
	}

	public List<Words> getKnown() {
		return knownwords;
	}
	/**
	 * This is a placeholder for dumping all of the syns/ants Just general
	 * initialization of the array with a few predetermined words.
	 */
	public void init() {
		// Add stuff to the list
		ArrayList<String> temp1 = new ArrayList<String>();
		ArrayList<String> temp2 = new ArrayList<String>();
		temp1.add("Abhor");
		temp1.add("Loathe");
		temp1.add("Scorn");
		temp2.add("Admire");
		temp2.add("Cherish");
		temp2.add("Approve");
		cascadeAdd(temp1, temp2);
		temp1.clear();
		temp2.clear();
		temp1.add("Hamper");
		temp1.add("Obligate");
		temp1.add("Restrict");
		temp2.add("Allow");
		temp2.add("Permit");
		cascadeAdd(temp1, temp2);
		temp1.clear();
		temp2.clear();
	}
}