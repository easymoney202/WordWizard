package WW;

/**
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */
public abstract class Interactable extends RoomObject {

	public Interactable(String imageloc) {
		super(imageloc);
	}

	public abstract void interact(Entity e);
	
	
}
