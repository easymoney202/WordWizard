package WW;

/**
 * Bookcase that stores books with words
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */
public class Bookcase extends Interactable {

	public Bookcase() {
		super("Images/shelf.bmp");
	}

	@Override
	public void interact(Entity e){
		System.out.println("Hey this is a bookcase!");
	}
	
	
}
