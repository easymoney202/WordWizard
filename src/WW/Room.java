package WW;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

/**
 * Room class will make note of anything a room contains, such as an enemy, book, or item
 * @author Zac, Matt, Diego
 * @version Alpha
 *
 */
public class Room {

    int roomid;
    Enemy anenemy;
    ArrayList<Door> doorlist;
    Tilemap roomMap;
    //Item anitem;
    //Book abook;


    /**
     * Constructor with boolean flags for room contents
     * @param id			integer -- room id
     * @param hasenemy		boolean -- whether room contains enemy
     * @param hasitem		boolean -- whether room contains item
     * @param hasbook		boolean -- whether room contains book
     */
    public Room(int id, int entype, boolean hasitem, boolean hasbook) {
        roomid = id;
        doorlist = new ArrayList<Door>();
        if(entype != 0)
            anenemy = new Enemy(entype);
        
        roomMap = new Tilemap();
    }
    /**
     * Gets the tilemap object
     * @return roomMap tilemap object
     */
    public Tilemap GetTilemap()
    {
    	return roomMap;
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
     * @return		int -- the room's ID
     */
    public int getRoomID() {
        return roomid;
    }
    /**
     * Adds a door object to the doorlist ArrayList of the Room
     * @param d		Door to be added
     */
    public void addDoors(Door d){
        doorlist.add(d);
    }
    /**
     * Getter for the list of doors of the room
     * @return		ArrayList<Door> for all doors leading out of the room
     */
    public ArrayList<Door> getDoors(){
        return doorlist;
    }
    
    /**
     * Paints the room to the game screen
     * @param		Graphics screen to be painted
     */
    public void paint(Graphics g)
    {
    	roomMap.paint(g);
    }
}
