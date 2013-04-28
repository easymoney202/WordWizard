package WW;
import java.util.*;
/** Words object.  Contains word, and list of synonyms and antonyms
 *  Kind of a wreck in terms of coding, but my Java is rusty
 * @author Team DMZ
 * @version Beta
 *
 */
public class Words {

    String theword; //the word involved
    ArrayList<String> syns; //synonyms of the word
    ArrayList<String> ants; //antonyms of the word

    public Words(){
    	theword = null;
    	syns = new ArrayList<String>();
    	ants = new ArrayList<String>();
    }
    
    /** Constructor for Words.  Requires word, synonyms, antonyms
     * 
     * @param tw		String for the word involved
     * @param s			ArrayList of Strings for synonyms
     * @param a			ArrayList of Strings for antonyms
     */
    public Words(String tw, ArrayList<String> s, ArrayList<String> a) {
        theword = tw;
        syns = new ArrayList<String>(s); //it took me far too long to remember I had to do this
        ants = new ArrayList<String>(a);
    }
    /** Getter for the word
     * 
     * @return			String for the word in the Words object
     */
    public String getWord() {
        return theword;
    }
    /** Getter for a random synonym
     * 
     * @return			String with a random synonym of the word
     */
    public String getRandomSyn() {
        Random rnd = new Random();
        Integer z = rnd.nextInt(syns.size());
        return syns.get(z);
    }
    /** Getter for random antonym
     * 
     * @return			String with a random antonym of word
     */
    public String getRandomAnt() {
        Random rnd = new Random();
        Integer z = rnd.nextInt(ants.size());
        return ants.get(z);
    }
    /** Getter for all synonyms
     * 
     * @return			ArrayList<String> of all synonyms
     */
    public ArrayList<String> getAllSyns() {
        return syns;
    }
    /** Getter for all antonyms
     * 
     * @return 			ArrayList<String> of all antonyms
     */
    public ArrayList<String> getAllAnts() {
        return ants;
    }
}