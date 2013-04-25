package WW;
/**
 * Door class represents a door
 * @author Team DMZ
 * @version Beta
 *
 */
public class Door {
	int room1; //"starting" room's id
	int room2; //"ending" room's id
	//directions go 0-1-2-3 for N-E-S-W
	int room1dir; //direction of starting room -- I.E. door in north wall
	int room2dir; //opposite of room1dir.  Usually
	String r1sub; //Description of room 1
	String r2sub; //Description of room 2
	
	public Door(int r1, int r2, int r1dir, int r2dir) {
		room1 = r1;
		room2 = r2;
		room1dir = r1dir;
		room2dir = r2dir;
	}
	
	public int getOtherSide(int start) {
		if(start==room1)
			return room2;
		else if(start==room2)
			return room1;
		else return 0;
	}
	
	public int getDoorDir(int start) {
		if(start==room1)
			return room1dir;
		else if(start==room2)
			return room2dir;
		else return -1;
	}

	
}
