package WW;

import java.awt.Image;
import java.util.Random;

public class Entity extends RoomObject {

	protected Integer health; // player's current health
	protected Integer dmgmin; // player's initial damage
	protected Integer dmgmax; // player's max damage
	protected Integer level; // player's level
	
	protected String name;

	protected Image m_image;

	protected Integer m_x;
	protected Integer m_y;

	protected Boolean m_moved;

	protected Room myroom;
	
	protected Boolean dead = false;

	public Entity(String imageloc, Room myroom) {
		super(imageloc);
		this.myroom = myroom;
	}

	public void SetPosition(Integer x, Integer y) {
		m_x = x;
		m_y = y;
	}

	/**
	 * Determines amount of damage player deals on a strike
	 * 
	 * @return integer -- amount of damage player deals
	 */
	public Integer attack() {
		Random rand = new Random();
		return rand.nextInt(dmgmax - dmgmin) + dmgmin;
	} // end playerStrike

	public Integer getHealth() {
		return health;
	}

	/**
	 * Returns the level of the player
	 * 
	 * @return
	 */
	public Integer GetLevel() {
		return level;
	}
	
	public void die(){
		dead = true;
		this.setImageFile("Images/player_dead.bmp");
	}
	public Integer beAttacked(Integer dmg) {
		health = health - dmg;
		if(health <= 0){
			die();
		}
		return health;
	} // end dmgPlayer
	public Boolean isDead(){
		return dead;
	}
}
