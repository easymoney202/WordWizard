package WW;
import java.util.ArrayList;

/**
 * Book class holds a Words object representing the book.
 * It can be "read," which returns the word/synonyms/antonyms, and adds to the WordList
 * @author Team DMZ
 * @version Beta
 *
 */
public class Book {

	Words contents;
	
	public Book(String word, ArrayList<String> syns, ArrayList<String> ants) {
		contents = new Words(word, syns, ants);
	}
	
	public ArrayList<ArrayList<String>> readBook(WordList wl) {
		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
		ArrayList<String> theword = new ArrayList<String>();
		theword.add(contents.getWord());
	
		wl.addToKnown(contents.getWord(), contents.getAllSyns(), contents.getAllAnts());
		ret.add(theword);
		ret.add(contents.getAllSyns());
		ret.add(contents.getAllAnts());
		return ret;
	}
	
}
