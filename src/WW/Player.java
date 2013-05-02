package WW;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Player represents the protagonist, and contains his stats and such
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */
public class Player extends Entity {
	// Experience points and max experience points for next level
	public int exp;
	public int maxExp;
	public int maxHealth;

	public File BattleImgFile;
	public Image BattleImg;

	/**
	 * Constructor with default stats loaded
	 */

	public Player(Room myroom) {
		super("Images/Player.png", myroom);
		health = 100;
		maxHealth = 100;
		level = 1;
		dmgmin = 15;
		dmgmax = 25;
		exp = 0;
		maxExp = 100;
		m_moved = false;

		try {
			BattleImgFile = new File("Images/Player2.png");
			BattleImg = ImageIO.read(BattleImgFile);
		} catch (Exception ex) {
			System.out.println("Problem loading Player2.png.");
		}
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

		if (m_y + y > Room.MAP_Y_SIZE - 1 || m_x + x > Room.MAP_X_SIZE - 1)
			return;
		if (myroom.tiles[m_y + y][m_x + x] instanceof Ground
				|| myroom.tiles[m_y + y][m_x + x] instanceof Spawn) {
			m_x += x;
			m_y += y;
			System.out.println("New player position: " + m_x + ", " + m_y);
			WordWizard.Instance.GetExploreScene().ResetStatusMsg();
		}
	}

	/**
	 * Interacts with the world
	 */
	public void Interact() {
		if (!isDead()) {
			RoomObject rightObject = null;
			if (m_x + 1 < Room.MAP_X_SIZE) {
				rightObject = myroom.tiles[m_y][m_x + 1];
			}
			RoomObject leftObject = null;
			if (m_x - 1 >= 0) {
				leftObject = myroom.tiles[m_y][m_x - 1];
			}
			RoomObject upObject = null;
			if (m_y - 1 >= 0) {
				upObject = myroom.tiles[m_y - 1][m_x];
			}
			RoomObject downObject = null;
			if (m_y + 1 < Room.MAP_Y_SIZE) {
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
			}
		} else {
			System.out.println("You are dead bro");
		}
	}

	/**
	 * Maintain input for player object This is for explore mode
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
	 * Sets new attack power, adds the level Increases max health and resets
	 * health of player
	 */
	public void LevelUp() {
		level++;
		dmgmin = 1 + (int) (dmgmin * 1.15);
		dmgmax = 1 + (int) (dmgmax * 1.20);
		maxHealth = (int) (maxHealth * 1.15);
		health = maxHealth;
		System.out.println("Leveled Up: Health: " + maxHealth + ", Dmg Min: "
				+ dmgmin + ", Dmg Max: " + dmgmax);
	}

	/**
	 * Adds experience to the player
	 * 
	 * @param xp
	 *            Experience to be added
	 */
	public void AddEXP(int xp) {
		int extraExp = 0;
		exp += xp;

		// Level Up
		if (exp >= maxExp) {
			// Reset exp
			extraExp = exp - maxExp;
			exp = 0;
			// Adjust exp needed for next level
			maxExp = (int) (maxExp * 1.5);
			LevelUp();
			// Add the other EXP and make sure it is processed for leveling
			AddEXP(extraExp);
		}
	}

	/**
	 * Needed for correct movement This is for explore mode
	 * 
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		if (m_moved = true)
			m_moved = false;
	}
}
