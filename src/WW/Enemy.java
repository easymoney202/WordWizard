package WW;

import java.util.Random;

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
	private static String _adj[] = {"Mighty", "Hateful", "Supreme", "Brutal", "Wise", "Evil", "Benighted", "Corrupted"};
	private static String _enType[] = {"Apprentice", "Wizard", "Ghoul", "Slime", "Goblin", "Dragon", "Elf"};

	
	/**
	 * Generates a random enemy with a random name of a specific level
	 * @param imageloc
	 * @param room
	 */
	public Enemy(String imageloc, Room room, int _lvl) {
		// TODO: Change the image depending on the _enType selected
		super(imageloc, room);
		
		Random rand = new Random();
		int adj_id = rand.nextInt(_adj.length);
		int enType_id = rand.nextInt(_enType.length);
		name = _adj[adj_id] + " " + _enType[enType_id];
		
		level = _lvl;
		health = 30 * _lvl;
		dmgmin = (int)(10 * _lvl * 0.8);
		dmgmax = (int)(30 * _lvl * 0.7);
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
	 * Constructor will assign stats based on four templates
	 * 
	 * @param entype
	 *            EnemyType -- the type of enemy encountered
	 */
	/*	public Enemy() {
	else if (entype == EnemyType.GOBLIN) { // Goblin
			name = "Goblin";
			health = 30;
			dmgmin = 10;
			dmgmax = 20;
			words = 3;
		} else if (entype == EnemyType.APPRENTICE) { // Apprentice
			name = "Wizard Apprentice";
			health = 50;
			dmgmin = 10;
			dmgmax = 25;
			words = 5;
		} else if (entype == EnemyType.GHOUL) { // Ghoul
			name = "Ghoul";
			health = 80;
			dmgmin = 25;
			dmgmax = 50;
			words = 4;
		} else if (entype == EnemyType.WORD_WIZARD) { // Word Wizard
			name = "Word Wizard";
			health = 200;
			dmgmin = 20;
			dmgmax = 30;
			words = 6;
		} else {
			name = "WEIRDO?";
			health = 1;
			dmgmin = 1;
			dmgmax = 1;
			words = 2;
		}
		*/
		// health banks
		/*
		 * gobhealth = 30; apphealth = 50; ghohealth = 80; wizhealth = 200;
		 * 
		 * //damage banks, with mins and maxes gobdmgmin = 10; gobdmgmax = 20;
		 * appdmgmin = 10; appdmgmax = 25; ghodmgmin = 25; ghodmgmax = 50;
		 * wizdmgmin = 20; wizdmgmax = 30;
		 * 
		 * //word option banks gobwords = 3; appwords = 5; ghowords = 4;
		 * wizwords = 6;
		 
	}
*/
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
