package WW;
import java.awt.*;

/**
 * Room class will make note of anything a room contains, such as an enemy, book, or item
 * @author Zac, Matt, Diego
 * @version Alpha
 *
 */
public class Room {

    Integer roomid;
    Enemy anenemy;
    Boolean m_init = false;
	Integer player_startX, player_startY;
	
	RoomObject tiles[][];
    
    public static Integer TILE_SIZE = 32;	// Tile size in pixels
	public static Integer MAP_X_SIZE = 12;	// X Grid size
	public static Integer MAP_Y_SIZE = 12;	// Y Grid size
	public static Integer OFFSET_X = 50;	// Offset for screen X
	public static Integer OFFSET_Y = 40;	// Offset for screen Y

    /**
     * Constructor with Boolean flags for room contents
     * @param id			integer -- room id
     * @param enemy			enemy
     */
    public Room(Integer id, Enemy en) {
        roomid = id;
        anenemy = en;
        tiles = new RoomObject[][] {
    			{new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()},
    			{new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Wall(), new Ground(), new Ground(), new Bookcase(), new Bookcase(), new Ground(), new Wall()},
    			{new Wall(), new Ground(), new Spawn(), new Ground(), new Ground(), new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Wall()},
    			{new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Wall()},
    			{new Wall(), new Ground(), new Ground(), new Wall(), new Wall(), new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Wall()},
    			{new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Bookcase(), new Wall()},
    			{new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Bookcase(), new Wall()},
    			{new Wall(), new Ground(), new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Bookcase(), new Wall()},
    			{new Wall(), new Ground(), new Wall(), new Wall(), new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Wall()},
    			{new Wall(), new Ground(), new Ground(), new Ground(), new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Wall()},
    			{new Wall(), new Bookcase(), new Bookcase(), new Bookcase(), new Wall(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Ground(), new Wall()},
    			{new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()}
    	};
        
		// Get extra info from tile array like player starting position
		for (Integer i = 0; i < MAP_X_SIZE; i++)
		{
			for (Integer j = 0; j < MAP_Y_SIZE; j++)
			{
				if (tiles[j][i] instanceof Spawn)
				{
					player_startX = i;
					player_startY = j;
				}
			}
		}
		m_init = true;
    }
    /**
     * Getter for the room's Enemy
     * @return		Enemy -- the room's enemy.
     */
    public Enemy roomEnemy() {
        return anenemy;
    }
    /**
     * Getter for the room's ID number
     * @return		Integer -- the room's ID
     */
    public Integer getRoomID() {
        return roomid;
    }
    
    /**
	* Returns the beginning X pos for the player
	*/
	public Integer GetPlayerStartX()
	{
		System.out.println("Player start X requested: " + player_startX);
		return player_startX;
	}
	
	/**
	* Returns the beginning Y pos for the player
	*/ 
	public Integer GetPlayerStartY()
	{
		System.out.println("Player start Y requested: " + player_startY);
		return player_startY;
	}
	/**
	 * Paints the tilemap
	 * @param g
	 */
	public void paint(Graphics g)
	{
		if (m_init)
		{
			Graphics2D g2d = (Graphics2D) g;
			for (Integer i = 0; i < MAP_X_SIZE; i++)
			{
				for (Integer j = 0; j < MAP_Y_SIZE; j++)
				{
					tiles[j][i].render(g2d, OFFSET_X + i * TILE_SIZE, OFFSET_Y + j * TILE_SIZE);
				}
			}
		}
	}
}
