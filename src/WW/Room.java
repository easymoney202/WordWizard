package WW;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Room class will make note of anything a room contains, such as an enemy,
 * book, or item
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 */
public class Room {

	protected Integer roomid;
	protected Enemy anenemy;
	protected Boolean m_init = false;
	protected Integer player_startX, player_startY;
	protected ArrayList<Words> roomwords;
	protected ArrayList<Bookcase> bookcaselist;
	protected NPC oldman;
	protected int num_bookcases;

	protected boolean hasdoorN = false;
	protected boolean hasdoorS = false;
	protected boolean hasdoorE = false;
	protected boolean hasdoorW = false;

	public boolean hasDoorN() {
		return hasdoorN;
	}

	public boolean hasDoorS() {
		return hasdoorS;
	}

	public boolean hasDoorE() {
		return hasdoorE;
	}

	public boolean hasDoorW() {
		return hasdoorW;
	}

	protected RoomObject tiles[][];

	public RoomObject[][] getTiles() {
		return tiles;
	}

	public static Integer TILE_SIZE = 32; // Tile size in pixels
	public static Integer MAP_X_SIZE = 12; // X Grid size
	public static Integer MAP_Y_SIZE = 12; // Y Grid size
	public static Integer OFFSET_X = 50; // Offset for screen X
	public static Integer OFFSET_Y = 40; // Offset for screen Y

	/**
	 * Constructor with Boolean flags for room contents
	 * 
	 * @param id
	 *            integer -- room id
	 * @param enemy
	 *            enemy
	 */
	public Room(Integer id, Enemy en, ArrayList<Words> rw, int num_bc) {
		roomid = id;
		anenemy = en;
		tiles = new RoomObject[MAP_X_SIZE + 1][MAP_Y_SIZE + 1];
		roomwords = rw;
		num_bookcases = 8;
		bookcaselist = new ArrayList<Bookcase>();
		oldman = new NPC("Oh, you must be the hero!", "Please, defeat the Wizard and save us all!");
		for (int i = 0; i < num_bookcases; i++) {
			if (i < rw.size()) // if there's a word to assign to the bookcase,
								// do so
				bookcaselist.add(new Bookcase(rw.get(i)));
			else
				// otherwise just add a bookcase
				bookcaselist.add(new Bookcase());
		}

		/*tiles = new RoomObject[][] {
				{ new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Door(), new Wall(),
						new Wall(), new Wall(), new Wall(), new Wall() },
				{ new Wall(), oldman, new Ground(), new Ground(), new Ground(), new Wall(), new Ground(), new Ground(),
						bookcaselist.get(0), bookcaselist.get(1), new Ground(), new Wall() },
				{ new Wall(), new Ground(), new Spawn(), new Ground(), new Ground(), new Wall(), new Ground(),
						new Ground(), new Ground(), new Ground(), new Ground(), new Wall() },
				{ new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Wall(), new Ground(),
						new Ground(), new Ground(), new Ground(), new Ground(), new Wall() },
				{ new Wall(), new Ground(), new Ground(), new Wall(), new Wall(), new Wall(), new Ground(),
						new Ground(), new Ground(), new Ground(), new Ground(), new Wall() },
				{ new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(),
						new Ground(), new Ground(), new Ground(), bookcaselist.get(2), new Wall() },
				{ new Door(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(),
						new Ground(), new Ground(), new Ground(), bookcaselist.get(3), new Wall() },
				{ new Wall(), new Ground(), new Wall(), new Ground(), new Ground(), new Ground(), new Ground(),
						new Ground(), new Ground(), new Ground(), bookcaselist.get(4), new Wall() },
				{ new Wall(), new Ground(), new Wall(), new Wall(), new Wall(), new Ground(), new Ground(),
						new Ground(), new Ground(), new Ground(), new Ground(), new Wall() },
				{ new Wall(), new Ground(), new Ground(), new Ground(), new Wall(), new Ground(), new Ground(),
						new Ground(), new Ground(), new Ground(), new Ground(), new Door() },
				{ new Wall(), bookcaselist.get(5), bookcaselist.get(6), bookcaselist.get(7), new Wall(), new Ground(),
						new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Wall() },
				{ new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(),
						new Wall(), new Wall(), new Wall(), new Wall() } };
		 */
		tiles = generate();

		// Get extra info from tile array like player starting position
		for (Integer i = 0; i < MAP_X_SIZE; i++) {
			for (Integer j = 0; j < MAP_Y_SIZE; j++) {
				if (tiles[j][i] instanceof Spawn) {
					player_startX = i;
					player_startY = j;
				}
			}
		}
		m_init = true;
	}

	/**
	 * Getter for the room's Enemy
	 * 
	 * @return Enemy -- the room's enemy.
	 */
	public Enemy roomEnemy() {
		return anenemy;
	}

	/**
	 * Getter for the room's ID number
	 * 
	 * @return Integer -- the room's ID
	 */
	public Integer getRoomID() {
		return roomid;
	}

	public ArrayList<Words> getRoomWords() {
		return roomwords;
	}

	/**
	 * Returns the beginning X pos for the player
	 */
	public Integer GetPlayerStartX() {
		System.out.println("Player start X requested: " + player_startX);
		return player_startX;
	}

	/**
	 * Returns the beginning Y pos for the player
	 */
	public Integer GetPlayerStartY() {
		System.out.println("Player start Y requested: " + player_startY);
		return player_startY;
	}

	/**
	 * Paints the tilemap
	 * 
	 * @param g
	 */
	public void paint(Graphics g) {
		if (m_init) {
			Graphics2D g2d = (Graphics2D) g;
			for (Integer i = 0; i < MAP_X_SIZE; i++) {
				for (Integer j = 0; j < MAP_Y_SIZE; j++) {
					tiles[j][i].render(g2d, OFFSET_X + i * TILE_SIZE, OFFSET_Y + j * TILE_SIZE);
				}
			}
		}
	}

	public boolean canAddDoor(Direction dir) {
		boolean trytoadd = false;
		switch (dir) {
		case NORTH:
			trytoadd = !hasdoorN;
			break;
		case SOUTH:
			trytoadd = !hasdoorS;
			break;
		case EAST:
			trytoadd = !hasdoorE;
			break;
		case WEST:
			trytoadd = !hasdoorW;
			break;
		default:
			break;
		}
		return trytoadd;
	}

	public boolean addDoor(Room roomto, Direction dir) {
		boolean trytoadd = true;

		if (!canAddDoor(dir)) {
			return false;
		}

		if (dir == Direction.EAST) {
			for (int x = 1; x < MAP_X_SIZE - 1; x++) {
				Integer surroundingwalls = checkSurroundings(x, 0, getTiles(), Wall.class.toString());
				Integer surroundingbooks = checkSurroundings(x, 0, getTiles(), Bookcase.class.toString());
				Integer surroundingnpcs = checkSurroundings(x, 0, getTiles(), NPC.class.toString());
				Integer surroundingdoors = checkSurroundings(x, 0, getTiles(), Door.class.toString());

				if (surroundingwalls < 3 && surroundingbooks == 0 && surroundingnpcs == 0 && surroundingdoors == 0) {
					getTiles()[x][0] = new Door(this, roomto);
					hasdoorE = true;
					break;
				}
			}
		} else if (dir == Direction.WEST) {
			for (int x = MAP_X_SIZE - 2; x > 1; x--) {
				Integer surroundingwalls = checkSurroundings(x, MAP_Y_SIZE - 1, getTiles(), Wall.class.toString());
				Integer surroundingbooks = checkSurroundings(x, MAP_Y_SIZE - 1, getTiles(), Bookcase.class.toString());
				Integer surroundingnpcs = checkSurroundings(x, MAP_Y_SIZE - 1, getTiles(), NPC.class.toString());
				Integer surroundingdoors = checkSurroundings(x, MAP_Y_SIZE - 1, getTiles(), Door.class.toString());

				if (surroundingwalls < 3 && surroundingbooks == 0 && surroundingnpcs == 0 && surroundingdoors == 0) {
					getTiles()[x][MAP_Y_SIZE - 1] = new Door(this, roomto);
					hasdoorW = true;
					break;
				}
			}
		} else if (dir == Direction.NORTH) {
			for (int y = 1; y < MAP_Y_SIZE - 1; y++) {
				Integer surroundingwalls = checkSurroundings(0, y, getTiles(), Wall.class.toString());
				Integer surroundingbooks = checkSurroundings(0, y, getTiles(), Bookcase.class.toString());
				Integer surroundingnpcs = checkSurroundings(0, y, getTiles(), NPC.class.toString());
				Integer surroundingdoors = checkSurroundings(0, y, getTiles(), Door.class.toString());

				if (surroundingwalls < 3 && surroundingbooks == 0 && surroundingnpcs == 0 && surroundingdoors == 0) {
					getTiles()[0][y] = new Door(this, roomto);
					hasdoorN = true;
					break;
				}
			}
		} else if (dir == Direction.SOUTH) {
			for (int y = MAP_Y_SIZE - 2; y > 1; y--) {
				Integer surroundingwalls = checkSurroundings(MAP_X_SIZE - 1, y, getTiles(), Wall.class.toString());
				Integer surroundingbooks = checkSurroundings(MAP_X_SIZE - 1, y, getTiles(), Bookcase.class.toString());
				Integer surroundingnpcs = checkSurroundings(MAP_X_SIZE - 1, y, getTiles(), NPC.class.toString());
				Integer surroundingdoors = checkSurroundings(MAP_X_SIZE - 1, y, getTiles(), Door.class.toString());

				if (surroundingwalls < 3 && surroundingbooks == 0 && surroundingnpcs == 0 && surroundingdoors == 0) {
					getTiles()[MAP_X_SIZE - 1][y] = new Door(this, roomto);
					hasdoorS = true;
					break;
				}
			}
		}

		return true;
	}

	public RoomObject[][] generate() {
		Integer randomChanceWall = 8;
		Integer randomChanceBook = 6;
		Integer randomChanceNPC = 4;
		RoomObject mytiles[][] = new RoomObject[MAP_X_SIZE][MAP_Y_SIZE];
		for (int x = 0; x < MAP_X_SIZE; x++) {
			for (int y = 0; y < MAP_Y_SIZE; y++) {
				mytiles[x][y] = new Ground();
			}
		}
		boolean npc = (0 == new Random().nextInt(randomChanceNPC));
		for (int x = 0; x < MAP_X_SIZE; x++) {
			for (int y = 0; y < MAP_Y_SIZE; y++) {
				Integer numadjbooks = checkSurroundings(x, y, mytiles, Bookcase.class.toString());
				Integer numadjwalls = checkSurroundings(x, y, mytiles, Wall.class.toString());

				boolean bookcase = (0 == new Random().nextInt(randomChanceBook));
				boolean wall = (0 == new Random().nextInt(randomChanceWall));
				if ((x == MAP_X_SIZE - 1 || x == 0) || (y == MAP_Y_SIZE - 1 || y == 0)) {
					mytiles[x][y] = new Wall();
				} else if (npc && numadjbooks == 0 && numadjwalls < 3) {
					mytiles[x][y] = new NPC("Venture onward young adventurer!", "You must make haste!");
					npc = false;
				} else if (bookcase && bookcaselist.size() > 0 && numadjbooks < 1) {
					Bookcase bk = bookcaselist.remove(new Random().nextInt(bookcaselist.size()));
					mytiles[x][y] = bk;
				} else if (wall && numadjwalls < 2) {
					mytiles[x][y] = new Wall();
				} else {
					mytiles[x][y] = new Ground();
				}
			}
		}

		boolean spawncreated = false;
		while (!spawncreated) {
			Integer randomx = new Random().nextInt(MAP_X_SIZE - 1) + 1;
			Integer randomy = new Random().nextInt(MAP_Y_SIZE - 1) + 1;
			if (mytiles[randomx][randomy].getClass().toString().equals(Ground.class.toString())) {
				mytiles[randomx][randomy] = new Spawn();
				spawncreated = true;
			}
		}

		return mytiles;
	}

	// See if something around this area is of the classname specified
	public Integer checkSurroundings(int x, int y, RoomObject[][] mytiles, String classname) {
		Integer height = mytiles[0].length - 1;
		Integer width = mytiles.length - 1;
		Integer retvalue = 0;
		if ((y - 1 >= 0) && mytiles[x][y - 1].getClass().toString().equals(classname))
			retvalue++;
		if ((y + 1 <= height) && mytiles[x][y + 1].getClass().toString().equals(classname))
			retvalue++;
		if (x + 1 <= width && mytiles[x + 1][y].getClass().toString().equals(classname))
			retvalue++;
		if (x + 1 <= width && y + 1 <= height && mytiles[x + 1][y + 1].getClass().toString().equals(classname))
			retvalue++;
		if (x + 1 <= width && y - 1 >= 0 && mytiles[x + 1][y - 1].getClass().toString().equals(classname))
			retvalue++;
		if (x - 1 >= 0 && mytiles[x - 1][y].getClass().toString().equals(classname))
			retvalue++;
		if (x - 1 >= 0 && y - 1 >= 0 && mytiles[x - 1][y - 1].getClass().toString().equals(classname))
			retvalue++;
		if (x - 1 >= 0 && y + 1 <= height && mytiles[x - 1][y + 1].getClass().toString().equals(classname))
			retvalue++;
		return retvalue;
	}

	public enum Direction {
		NORTH, EAST, SOUTH, WEST;
		public static Direction fromInteger(Integer x) {
			switch (x) {
			case 0:
				return NORTH;
			case 1:
				return EAST;
			case 2:
				return SOUTH;
			case 3:
				return WEST;
			default:
				return null;
			}
		}

		public static Direction getOpposite(Direction dir) {
			switch (dir) {
			case NORTH:
				return SOUTH;
			case EAST:
				return WEST;
			case WEST:
				return EAST;
			case SOUTH:
				return NORTH;
			default:
				return null;
			}
		}

		public static Direction random() {
			Integer num = new Random().nextInt(4);
			return fromInteger(num);
		}
	}
}
