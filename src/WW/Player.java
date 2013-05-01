package WW;

import java.awt.event.KeyEvent;

/**
 * Player represents the protagonist, and contains his stats and such
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */
public class Player extends Entity {

	/**
	 * Constructor with default stats loaded
	 */

	public Player(Room myroom) {
		super("Images/player.bmp", myroom);
		health = 100;
		level = 1;
		dmgmin = 15;
		dmgmax = 25;
		m_moved = false;
	}

	/**
	 * Moves the player by an offset This deals with collisions with the level
	 */
	public void Move(Integer x, Integer y) {
		System.out.println("Trying to move the player: " + x + ", " + y);
		// Only move if no walls are in the falling tile
		System.out.println("Current player position: " + m_x + ", " + m_y);
		if (m_y + y < 0 || m_x + x < 0)
			return;

		if (m_y + y > Room.MAP_Y_SIZE || m_x + y > Room.MAP_X_SIZE) {
			return;
		} else if (isDead()) {
			System.out.println("Cannot move because you are dead!");
			return;
		}
		if (myroom.tiles[m_y + y][m_x + x] instanceof Ground
				|| myroom.tiles[m_y + y][m_x + x] instanceof Spawn) {
			m_x += x;
			m_y += y;
			System.out.println("New player position: " + m_x + ", " + m_y);
			WordWizard.Instance.ResetStatusMsg();
		}
	}

	/**
	 * Interacts with the world
	 */
	public void Interact() {
		if (!isDead()) {
			RoomObject rightObject = null;
			if(m_x+1 < Room.MAP_X_SIZE){
			rightObject = myroom.tiles[m_y][m_x + 1];
			}
			RoomObject leftObject = null;
			if(m_x - 1 >= 0){
			leftObject = myroom.tiles[m_y][m_x - 1];
			}
			RoomObject upObject = null;
			if(m_y - 1 >= 0){
			upObject = myroom.tiles[m_y - 1][m_x];
			}
			RoomObject downObject = null;
			if(m_y + 1 < Room.MAP_Y_SIZE){
			downObject = myroom.tiles[m_y + 1][m_x];
			}
			// Interact with objects
			if (rightObject instanceof Interactable
					|| leftObject instanceof Interactable
					|| upObject instanceof Interactable
					|| downObject instanceof Interactable) {
				if (rightObject instanceof Interactable) {
					System.out.println("Interacting with rightobject");
					((Interactable) rightObject).interact(this);
				} else if (leftObject instanceof Interactable) {
					System.out.println("Interacting with leftobject");
					((Interactable) leftObject).interact(this);
				} else if (upObject instanceof Interactable) {
					System.out.println("Interacting with upobject");
					((Interactable) upObject).interact(this);
				} else if (downObject instanceof Interactable) {
					System.out.println("Interacting with downobject");
					((Interactable) downObject).interact(this);
				} else {
					System.out.println("LOL I can't interact with anything");
				}
				WordWizard.Instance
						.SetStatusMsg("Status: The bookshelf does not contain any magic manuscripts.");
			}
		} else {
			System.out.println("You are dead bro");
		}
	}

	/**
	 * Maintain input for player object
	 * 
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		System.out.println("Got into player's key pressed mechanism");
		Integer key = e.getKeyCode();

		if (m_moved == false) {
			switch (key) {
			case KeyEvent.VK_LEFT:
				m_moved = true;
				Move(-1, 0);
				break;
			case KeyEvent.VK_RIGHT:
				m_moved = true;
				Move(1, 0);
				break;
			case KeyEvent.VK_DOWN:
				m_moved = true;
				Move(0, 1);
				break;
			case KeyEvent.VK_UP:
				m_moved = true;
				Move(0, -1);
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
	 * 
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		if (m_moved = true)
			m_moved = false;
	}

}
