package WW;

/**
 * Door class represents a door
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */
public class Door extends Interactable {
	// directions go 0-1-2-3 for N-E-S-W
	Room room1;
	Room room2;
	
	public Door(){
		super("Images/Door.png");
	}
	
	public Door(Room r1, Room r2) {
		this();
		room1 = r1;
		room2 = r2;
		
	}
	
	
	@Override
	public void interact(Entity e) {
		WordWizard.Instance.GetExploreScene().SetStatusMsg("This is a door.");
		if(e.getRoom().equals(room1)){
			e.travel(room2);
		} else if(e.getRoom().equals(room2)){
			e.travel(room1);		}
	}
}
