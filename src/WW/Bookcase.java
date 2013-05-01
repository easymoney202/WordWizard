package WW;

/**
 * Bookcase that stores books with words
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */
public class Bookcase extends Interactable {

	Words book;
	
	public Bookcase() {
		super("Images/Shelf.png");
		book = new Words();
	}

	@Override
	public void interact(Entity e){
		WordWizard.Instance.GetExploreScene().ShowBook("Magical Words",getBookString());
		System.out.println("Hey this is a bookcase!");
	}
	
	public Bookcase(Words w) {
		super("Images/Shelf.png");
		book = w;
		System.out.println(book.getWord());
	}
	
	public void setBook(Words w) {
		book = w;
	}
	
	public Words getBook() {
		return book;
	}
	
	public String getBookString() {
		if(book == null || book.getWord() == null)
			return "Status: The bookshelf does not contain any magic manuscripts.";
		String status = "Word:--";
		status += (book.getWord());
		status += ("-- --Synonyms:--  ");
		for(String s : book.getAllSyns()) {
			status += (s);
			status += ("--  ");
		}
		status += ("--Antonyms:--  ");
		for(String a : book.getAllAnts()) {
			status += (a);
			status += ("--  ");
		}
		WordWizard.Instance.GetExploreScene().SetStatusMsg("This is a bookcase!");
		return status;
	}
}
