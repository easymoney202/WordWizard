package WW;

import WW.Dungeon.Direction;

/**
 * Door class represents a door
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */
public class Door extends RoomObject {
	Integer room1; // "starting" room's id
	Integer room2; // "ending" room's id
	// directions go 0-1-2-3 for N-E-S-W
	Direction room1dir; // direction of starting room -- I.E. door in north wall
	Direction room2dir; // opposite of room1dir. Usually
	String r1sub; // Description of room 1
	String r2sub; // Description of room 2

	public Door(){
		super("Images/door.bmp");
	}
	
	public Door(Integer r1, Integer r2, Direction r1dir, Direction r2dir) {
		this();
		room1 = r1;
		room2 = r2;
		room1dir = r1dir;
		room2dir = r2dir;
	}

	public Integer getOtherSide(Integer start) {
		if (start == room1)
			return room2;
		else if (start == room2)
			return room1;
		else
			return 0;
	}

	public Direction getDoorDir(Integer start) {
		if (start == room1)
			return room1dir;
		else if (start == room2)
			return room2dir;
		else
			return null;
	}

}
