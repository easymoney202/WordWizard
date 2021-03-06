package WW;

import java.util.ArrayList;
import java.util.Random;

import WW.Room.Direction;

/**
 * Dungeon will hold a number of Room objects to form the full dungeon
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */
public class Dungeon {

	ArrayList<Room> rooms;
	ArrayList<Words> dungeonwords; //These are words that the dungeon will teach
	ArrayList<Words> remainingwords; //These are unallocated dungeon words
	Integer numrooms;
	Integer currentroomid;

	/**
	 * Basic constructor for Dungeon.
	 */
	public Dungeon() {
		rooms = new ArrayList<Room>();
		dungeonwords = new ArrayList<Words>();
		remainingwords = new ArrayList<Words>();
		numrooms = 0;
	}
	
	public Dungeon(ArrayList<Words> words) {
		rooms = new ArrayList<Room>();
		numrooms = 0;
		dungeonwords = words;
		remainingwords = words;
	}

	/**
	 * Getter for the id of the current room
	 * 
	 * @return Integer -- the current room's ID
	 */
	public Integer getCurrentRoomId() {
		return currentroomid;
	}
	
	public Room generate(Integer numofrooms) {
		for (int a = 0; a < numofrooms; a++) {
			addRoom();
		}
		currentroomid = getRoom(0).getRoomID();
		linkrooms();
		return getRoom(0);
	}

	/**
	 * Getter for the current room
	 * 
	 * @return Room -- the current room
	 */
	public Room getCurrentRoom() {
		return getRoom(getCurrentRoomId());
	}
	
	public void linkrooms(){
		//                   [ ]
		//               [ ] [ ] [ ]
		//               [ ] [ ] [ ] [ ]
		//                   [ ]     [ ]
		//                   [*]
		//rooms.get(0).addDoor(rooms.get(1), Direction.EAST);
		//rooms.get(1).addDoor(rooms.get(0), Direction.WEST);
		for(int a = 0; a < rooms.size() - 1; a++){
			Direction dir1 = Direction.random();
			Direction dir2 = Direction.getOpposite(dir1);
			if(rooms.get(a).canAddDoor(dir1) && rooms.get(a+1).canAddDoor(dir2)){
				rooms.get(a).addDoor(rooms.get(a+1), dir1);
				rooms.get(a+1).addDoor(rooms.get(a), dir2);
			}
		}
	}

	/**
	 * Getter for a Room object with given ID number
	 * 
	 * @param id
	 *            Integer -- the desired room's ID
	 * @return Room - the room with the given ID
	 */
	public Room getRoom(Integer id) {
		return rooms.get(id);
	}
	
	/**
	 * Adds a room to the maze, with a 50/50 chance of an enemy being present.
	 * If an enemy is present, it has an equal chance to be any of the enemies
	 */
	public void addRoom() {

		int num_bookcases = 8;
		Random rand = new Random();
		Boolean isEnemy = (rand.nextInt(2) == 0);
		Enemy enemy = null;
		if (isEnemy) {
			enemy = Enemy.randomEnemy();
		}
		// show up
		// not implemented yet, so these stay out
		
		/*
		Boolean item = false;
		Boolean book = false;
		*/
		ArrayList<Words> temp = new ArrayList<Words>();
		for(int i = 0; i<num_bookcases; i++) {
			if(remainingwords.size()!= 0)
				temp.add(remainingwords.remove(rand.nextInt(remainingwords.size())));
		}
		rooms.add(new Room(numrooms, enemy, temp, num_bookcases));
		numrooms++;
	}

}
