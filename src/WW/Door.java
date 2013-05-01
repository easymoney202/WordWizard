package WW;

import WW.Dungeon.Direction;

/**
 * Door class represents a door
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */
public class Door extends Interactable {
	// directions go 0-1-2-3 for N-E-S-W
	Direction room1dir; // direction of starting room -- I.E. door in north wall
	Direction room2dir; // opposite of room1dir. Usually
	Room room1;
	Room room2;
	
	public Door(){
		super("Images/door.bmp");
	}
	
	public Door(Room r1, Room r2) {
		this();
		room1 = r1;
		room2 = r2;
	}

	@Override
	public void interact(Entity e) {
		System.out.println("You got hit by the door cuz I said so!");
		// just screwing with health
		e.beAttacked(10);
	}
}
