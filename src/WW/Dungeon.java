package WW;
import java.util.ArrayList;
import java.util.Random;

/**
 * Dungeon will hold a number of Room objects to form the full dungeon
 * @author Zac, Matt, Diego
 * @version Alpha
 *
 */
public class Dungeon {

    ArrayList<Room> maze;
    ArrayList<Door> doors;
    int numrooms;
    int currentroom;
    /**
     * Basic constructor for Dungeon.  Adds a starter room with nothing in it (including enemy)
     */
    public Dungeon() {
        maze = new ArrayList<Room>();
        doors = new ArrayList<Door>();
        numrooms = 0;
        //This adds a starter room
        maze.add(new Room(numrooms, 0, false, false));
        currentroom = 0; //start in this room
        numrooms++;
    }
    /**
     * Getter for the id of the current room
     * @return		int -- the current room's ID
     */
    public int getCurrentRoom() {
        return currentroom;
    }
    /**
     * Getter for a Room object with given ID number
     * @param id	int -- the desired room's ID
     * @return		Room - the room with the given ID
     */
    public Room getRoom(int id) {
        return maze.get(id);
    }
    /**
     * Adds a room to the maze, with a 1/4 chance of Goblin, Apprentice, Ghoul, or No Enemy
     */
    public void addRoom() {

        Random rand = new Random();
        //Note that this is hard-coded as 4, will need to change if we add new enemies
        int enemy = rand.nextInt(4); //note: 0 means no enemy, boss will not show up
        //not implemented yet, so these stay out
        boolean item = false;
        boolean book = false;

        maze.add(new Room(numrooms, enemy, item, book));
        numrooms++;
    }
    /**
     * Adds a door to the list of doors included in Maze, and to the Room objects it borders
     * @param room1			int -- id of the first room
     * @param room2			int -- id of the second room
     * @param room1dir		int -- which wall in the first room the door is in (1234 -> NESW)
     * @param room2dir		int -- which wall in the second room the door is in (1234 -> NESW)
     */
    public void addDoor(int room1, int room2, int room1dir, int room2dir){
        Door d = new Door(room1, room2, room1dir, room2dir);
        doors.add(d);
        maze.get(room1).addDoors(d);
        maze.get(room2).addDoors(d);
    }
    /**
     * Attempts to move the current room to one that borders the current room via a door
     * @param direction		int -- the direction travel is being attempted in (1234 -> NESW)
     * @return				boolean for whether it was successful or not
     */
    public boolean travel(int direction){
        boolean success = false;
        ArrayList<Door> temp = maze.get(currentroom).getDoors();
        int i = 0;
        int curroomid = maze.get(currentroom).getRoomID();
        while(i<temp.size() && success == false) {
            if(temp.get(i).getDoorDir(curroomid) == direction) {
                success = true;
                currentroom = temp.get(i).getOtherSide(curroomid);
            }
            i++;
        }
        return success;
    }
    /**
     * Creates a sample dungeon, layout of:
     * 
     *      7 - 4 - 2
     *      |   |   |
     * 10 - 8 - 5   1 - 0
     *      |   |   |
     *      9 - 6 - 3
     * 
     */
    public void sampleDungeon(){
        int NORTH = 0;
        int EAST = 1;
        int SOUTH = 2;
        int WEST = 3;

        for(int i=0; i<10; i++)
            addRoom();

        addDoor(0, 1, WEST, EAST);
        addDoor(1, 2, NORTH, SOUTH);
        addDoor(1, 3, SOUTH, NORTH);
        addDoor(2, 4, WEST, EAST);
        addDoor(3, 6, WEST, EAST);
        addDoor(4, 5, SOUTH, NORTH);
        addDoor(5, 6, SOUTH, NORTH);
        addDoor(5, 8, WEST, EAST);
        addDoor(4, 7, WEST, EAST);
        addDoor(6, 9, WEST, EAST);
        addDoor(7, 8, SOUTH, NORTH);
        addDoor(8, 9, SOUTH, NORTH);
        addDoor(8, 10, WEST, EAST);

    }

}
