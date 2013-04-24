package WW;
import java.util.Random;
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

/**
 * Player represents the protagonist, and contains his stats and such
 * @author Zac, Matt, Diego
 * @version Alpha
 *
 */
public class Player {

    int health; //player's current health
    int dmgmin; //player's initial damage
    int dmgmax; //player's max damage
    int level;  //player's level
    
    Image m_image;
    
    int m_x;
    int m_y;
    
    boolean m_moved;

    /**
     * Constructor with default stats loaded
     */
    public Player() {
        health = 100;
        level = 1;
        dmgmin = 15;
        dmgmax = 25;
        m_moved = false;
        try
        {
        	File playerImg = new File("Images/Player.bmp");
        	m_image = ImageIO.read(playerImg);
        }
        catch (Exception ex)
        {
        	System.out.println("Problem loading player's image.");
        	System.out.println(ex.getMessage());
        }
    }
    /**
     * Sets the position of the player in the room
     */
    public void SetPosition(int x, int y)
    {
    	m_x = x;
    	m_y = y;
    }
    /**
     * Moves the player by an offset
     * This deals with collisions with the level
     */
    public void Move(int x, int y)
    {
    	System.out.println("Trying to move the player: " + x + ", " + y);
    	int map[][] = WordWizard.Instance.GetCurrentRoom().GetTilemap().GetTileArray();
    	// Only move if no walls are in the falling tile
    	System.out.println("Current player position: " + m_x + ", " + m_y);
    	if (m_y + y < 0 || m_x + x < 0)
    		return;
    	if (m_y + y > Tilemap.MAP_Y_SIZE || m_x + y > Tilemap.MAP_X_SIZE)
    		return;
    	if (map[m_y + y][m_x + x] == 0 || map[m_y + y][m_x + x] == 9)
    	{
    		m_x += x;
    		m_y += y;
    		System.out.println("New player position: " + m_x + ", " + m_y);
    		WordWizard.Instance.ResetStatusMsg();
    	}
    }
    /**
     * Interacts with the world
     */
    public void Interact()
    {
    	int map[][] = WordWizard.Instance.GetCurrentRoom().GetTilemap().GetTileArray();
    	// Interact with shelves
    	if (map[m_y][m_x + 1] == 2 || map[m_y][m_x -1] == 2 || map[m_y+1][m_x] == 2 || map[m_y-1][m_x] == 2)
    	{
    		WordWizard.Instance.SetStatusMsg("Status: The bookshelf does not contain any magic manuscripts.");
    	}
    }
    /**
     * Maintain input for player object
     * @param e
     */
    public void keyPressed(KeyEvent e)
    {
    	System.out.println("Got into player's key pressed mechanism");
    	int key = e.getKeyCode();
    	
    	if (m_moved == false)
    	{
    		switch(key)
    		{
    		case KeyEvent.VK_LEFT:
    			m_moved = true;
    			Move(-1,0);
    			break;
    		case KeyEvent.VK_RIGHT:
    			m_moved = true;
    			Move(1,0);
    			break;
    		case KeyEvent.VK_DOWN:
    			m_moved = true;
    			Move(0,1);
    			break;
    		case KeyEvent.VK_UP:
    			m_moved = true;
    			Move(0,-1);
    			break;
    		case KeyEvent.VK_SPACE:
    			Interact();
    			break;
    		default:
    			break;
    		}
    	}
    }
    /**
     * Needed for correct movement
     * @param e
     */
    public void keyReleased(KeyEvent e)
    {
    	if (m_moved = true)
    		m_moved = false;
    }
    /**
     * Determines amount of damage player deals on a strike
     * @return		integer -- amount of damage player deals
     */
    public int playerStrike(){
        Random rand = new Random();
        return rand.nextInt(dmgmax-dmgmin)+dmgmin;
    } //end playerStrike
    /**
     * Getter for current player health
     * @return		integer -- player's current hp
     */
    public int GetHealth(){
        return health;
    } //end playerHealth
    /**
     * Returns the level of the player
     * @return
     */
    public int GetLevel()
    {
    	return level;
    }
    /**
     * Updates player's health after taking damage and returns new hp
     * @param dmg		integer -- amount of damage player took
     * @return			integer -- updated player hp
     */
    public int dmgPlayer(int dmg){
        health = health - dmg;
        return health;
    } //end dmgPlayer
    /**
     * Paints the player
     * @param g Graphics object
     */
    public void paint(Graphics g)
    {
    	Graphics2D g2 = (Graphics2D)g;
    	
    	if (m_image != null)
    		g2.drawImage(m_image, m_x * Tilemap.TILE_SIZE + Tilemap.OFFSET_X, m_y * Tilemap.TILE_SIZE + Tilemap.OFFSET_Y, null);
    }
}
