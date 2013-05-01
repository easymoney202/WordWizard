/**
 * NPC Class represents NPCs on the map
 * 
 * @author Team DMZ
 * @version Final
 */
package WW;

public class NPC extends Interactable {

	Words book; // If we decide that some NPCs can offer words to contribute
	String firstline; // First line the NPC rattles off
	String repeatline; // Line that the NPC repeats
	String thirdline;
	boolean donefirst;
	boolean donesecond; //

	public NPC(String fl, String rl) {
		super("Images/oldman.bmp");
		book = new Words();
		firstline = fl;
		repeatline = rl;
		donefirst = false;
	}

	public NPC(String fl, Words w) {
		super("Images/oldman.bmp");
		book = w;
		firstline = fl;

		repeatline = "For the word: ";
		repeatline += (book.getWord());
		repeatline += (" Synonyms are: ");
		for (String s : book.getAllSyns()) {
			repeatline += (s);
			repeatline += (" ");
		}

		thirdline = ("Antonyms of ");
		thirdline += book.getWord();
		thirdline += " are: ";
		for (String a : book.getAllAnts()) {
			repeatline += (a);
			repeatline += (" ");
		}

		donefirst = false;
		donesecond = false;
	}

	public String getLine() {
		if (!donefirst) {
			donefirst = true;
			return firstline;
		} else if (donesecond && thirdline != null) {
			donesecond = false;
			return thirdline;
		} else {
			donesecond = true;
			return repeatline;
		}
	}

	@Override
	public void interact(Entity e) {
		WordWizard.Instance.GetExploreScene().SetStatusMsg(getLine());
	}
}
