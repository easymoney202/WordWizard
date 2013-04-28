package WW;

/**
 * Bookcase that stores books with words
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */
public class Bookcase extends RoomObject {

	Words book;
	
	public Bookcase() {
		super("Images/shelf.bmp");
		book = new Words();
	}
	public Bookcase(Words w) {
		super("Images/shelf.bmp");
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
		String status = "Word: ";
		status += (book.getWord());
		status += ("-- Synonyms: ");
		for(String s : book.getAllSyns()) {
			status += (s);
			status += (" ");
		}
		status += ("-- Antonyms: ");
		for(String a : book.getAllAnts()) {
			status += (a);
			status += (" ");
		}
		return status;
	}
	
}
