package WW;
import java.util.*;

/** WordList holds a list of Words objects and can run basic basic WordWizard
 *
 * @author Zac, Matt, Diego
 * @version Alpha
 *
 */
public class WordList {

    /** thelist is the master list of Words */
    List<Words> thelist;

    /** Basic constructor */
    public WordList() {
        thelist = new ArrayList<Words>();
    }
    /** addToList takes a word, synonyms and antonyms and populates Words object
     * 
     * @param word		the word used
     * @param syns		ArrayList of synonyms
     * @param ants		ArrayList of antonyms
     */
    public void addToList(String word, ArrayList<String> syns, ArrayList<String> ants) {
        thelist.add(new Words(word, syns, ants));	
    }
    /** getRandomWord pulls a random word from thelist and returns it
     * 
     * @return			String representing random word from thelist
     */
    public String getRandomWord() {
        Random rnd = new Random();
        Integer z = rnd.nextInt(thelist.size());
        return thelist.get(z).getWord();
    }
    /** getProblem returns numQs+1 words in an ArrayList, the first being the word to be matched,
     *  the second the correct answer, and the rest incorrect answers
     * 
     * @param numQs		Integer for how many total answers desired
     * @param type		Integer for whether synonyms (if ==1) or antonyms
     * @return			ArrayList<String> with word, correct answer, then incorrect answers
     */
    public ArrayList<String> getProblem(Integer numQs, Integer type) {
        Random rnd = new Random();
        //Get a random number for the index of a word in thelist
        Integer z = rnd.nextInt(thelist.size());
        String word = thelist.get(z).getWord();
        ArrayList<String> matchset; //all synonyms/antonyms to provide comparison when randoms are chosen
        ArrayList<String> qset = new ArrayList<String>();  //Make an array to return
        String answer;  //String for the correct answer
        if(type==1) {  //Ask for a synonym
            answer = thelist.get(z).getRandomSyn();
            matchset = thelist.get(z).getAllSyns();
        } //end if
        else { //ask for an antonym
            answer = thelist.get(z).getRandomAnt();
            matchset = thelist.get(z).getAllAnts();
        } //end else
        qset.add(word); //add word to the beginning of the list
        qset.add(answer); //add answer right afterwards
        Integer i = 0;
        /* Get a random word, compare it to all correct answers */
        while(i<numQs-1) {
            String wrong = getRandomWord(); 
            if(!matchset.contains(wrong)&&!wrong.equals(word)) {
                qset.add(wrong);
                i++;
            } //end if			
        } //end while
        return qset;
    }
    /** For now, assuming that every synonym of a synonym is a synonym of that synonym, and antonyms too.
     * It then makes a Words object for each word involved in each list given
     * 
     * @param syns		ArrayList of words that are all synonyms of each other
     * @param ants		ArrayList of antonyms that are all antonyms of each synonym, and therefore all synonyms of each other
     */
    public void cascadeAdd(ArrayList<String> syns, ArrayList<String> ants) {
        ArrayList<String> temparr1 = syns; //local copy of synonyms array
        ArrayList<String> temparr2 = ants; //local copy of antonyms array
        String tempstr; //temporary variable for a single string
        /*Take a word from the synonyms array, then add it to thelist with all of its synonyms and antonyms
         *then add it back to the temp list, and repeat for the next entry. 
         */
        for(Integer i=0; i<syns.size(); i++) {
            tempstr = temparr1.remove(0);
            addToList(tempstr, temparr1, temparr2);
            temparr1.add(tempstr);
        } //end for
        /* Do the same as above, just with the antonyms.  Only this time, the antonyms are synonyms of each other
         * so temparr2 is used for synonyms and temparr1 for antonyms 
         */
        for(Integer i=0; i<ants.size(); i++) {
            tempstr = temparr2.remove(0);
            addToList(tempstr, temparr2, temparr1);
            temparr2.add(tempstr);
        } //end for
    }
    /**This is a placeholder for dumping all of the syns/ants
     * Just general initialization of the array with a few predetermined words.
     */
    public void init() {
        //Add stuff to the list
        ArrayList<String> temp1 = new ArrayList<String>();
        ArrayList<String> temp2 = new ArrayList<String>();
        temp1.add("Abhor"); temp1.add("Loathe"); temp1.add("Scorn");
        temp2.add("Admire"); temp2.add("Cherish"); temp2.add("Approve");
        cascadeAdd(temp1, temp2);
        temp1.clear(); temp2.clear();
        temp1.add("Hamper"); temp1.add("Obligate"); temp1.add("Restrict");
        temp2.add("Allow"); temp2.add("Permit");
        cascadeAdd(temp1, temp2);
        temp1.clear(); temp2.clear();		

        /*Abhor
          + Loathe, Scorn, Despise
          - Admire, Approve, Cherish
          Hamper
          + Obligate, Restrict, Confine
          - Allow, Permit, Allow
          Placid
          + Collected,  Halcyon, Serene
          - Agitated, Excited, Wild
          Abrasive
          + Rough, Caustic, Nasty
          - Pleasant, Soothing, Placid
          Nuance
          + Graduation, Shading
          - <None>
          Bolster
          + Assist, brace, buttress
          - hinder, hamper, obstruct
          Abrogate
          + abolish, dissolve, nullify
          - establish, institute, ratify
          */	
    }
    /**Main program for current testing
     * 
     * @param args
     */
    public static void main(String args[]) {
        WordList mainlist = new WordList();
        mainlist.init();

        ArrayList<String> prob = mainlist.getProblem(4, 1); //four questions, synonyms

        for(String s: prob)
            System.out.println(s);

    }

}
