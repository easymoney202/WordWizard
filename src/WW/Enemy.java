package WW;

import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Enemy class will contain information about the four enemies you can face:
 * Goblin (Easy, low damage and few choices) Apprentice (Medium, low damage but
 * more choices) Ghoul (Medium, high damage, few choices) Word Wizard(Hard,
 * medium damage, but many choices)
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 * 
 */

public class Enemy extends Entity {
	
	// For random enemy name generation
	private static String _adj[] = {"Mighty", "Hateful", "Supreme", "Brutal", "Wise", "Evil", "Benighted", "Corrupted", "Legendary",
									"Powerful", "Murderer", "Undead", "Demonic", "Diabolic", "Hollow", "Master", "Mystic"};
	private static String _enType[] = {"Apprentice", "Wizard", "Ghoul", "Slime", "Goblin", "Dragon", "Elf"};

	
	/**
	 * Generates a random enemy with a random name of a specific level
	 * @param imageloc
	 * @param room
	 */
	public Enemy(String imageloc, Room room, int _lvl) {
		// TODO: Change the image depending on the _enType selected
		super(imageloc, room);
		
		level = _lvl;
		Random rand = new Random();
		// Create a Special Enemy or a Normal Enemy
		int odds = rand.nextInt(10);
		// Select type of enemy
		int enType_id = rand.nextInt(_enType.length);
		if (odds > 7)
		{
			// Special Enemies - Mini bosses
			// Add more level to the enemy
			level += rand.nextInt(2) + 1;
			int adj_id = rand.nextInt(_adj.length);
			int adj2_id = rand.nextInt(_adj.length);
			// Make sure same adjective is not used twice
			if (adj2_id == adj_id)
			{
				if (adj2_id >= _adj.length - 1)
					adj2_id = 0;
				else
					adj2_id++;
			}
			
			name = _adj[adj_id] + " " + _adj[adj2_id] + " " + _enType[enType_id];
		}
		else
		{
			// Generate an enemy with name like: Adjective Type
			int adj_id = rand.nextInt(_adj.length);
			name = _adj[adj_id] + " " + _enType[enType_id];
		}
		
		try
		{
			String imgPath = "Images/" + _enType[enType_id] + ".png";
			System.out.println(imgPath);
			objectImageFile = new File(imgPath);
			objectImage = ImageIO.read(objectImageFile);
		}
		catch (Exception ex)
		{
			System.out.println("Could not find enemy image file");
		}
		
		
		health = 15 * level + rand.nextInt((int)(level*1.2));
		dmgmin = (int)(6 * _lvl * 0.8);
		dmgmax = (int)(17 * _lvl * 0.7);
	}
	
	/**
	 * Generates an enemy with a specific name, level, image, etc
	 * @param imageloc
	 * @param room
	 * @param _name
	 * @param _lvl
	 * @param _health
	 * @param _dmin
	 * @param _dmax
	 */
	public Enemy(String imageloc, Room room, String _name, int _lvl, int _health, int _dmin, int _dmax){
		super(imageloc,room);
		name = _name;
		level = _lvl;
		health = _health;
		dmgmin = _dmin;
		dmgmax = _dmax;
	}
	
	Integer words;

	/**
	 * Determines how much damage enemy does on incorrect answer
	 * 
	 * @return integer -- damage enemy deals to player
	 */
	public Integer enemyStrike() {
		Random rand = new Random();
		return rand.nextInt(dmgmax - dmgmin) + dmgmin;
	}

	/**
	 * Getter for the number of answers an enemy has
	 * 
	 * @return integer -- number of answers enemy asks
	 */
	public Integer enemyWords() {
		return words;
	}

	/**
	 * Updates enemy's health with given damage, and returns if dead
	 * 
	 * @param damage
	 *            integer -- amount of damage enemy took
	 * @return Boolean -- whether enemy is dead or not
	 */
	public Boolean dmgEnemy(Integer damage) {
		health = health - damage;
		if (health > 0)
			return false;
		else
			return true;
	}

	/**
	 * Returns the enemy's name as a string
	 * 
	 * @return String -- enemy's name
	 */
	public String enemyName() {
		return name;
	}

	public static Enemy randomEnemy() {
		// TODO Auto-generated method stub
		return null;
	}

}
