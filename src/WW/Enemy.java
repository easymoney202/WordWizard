package WW;
import java.util.Random;


/**
 * Enemy class will contain information about the four enemies you can face:
 * Goblin (Easy, low damage and few choices)
 * Apprentice (Medium, low damage but more choices)
 * Ghoul (Medium, high damage, few choices)
 * Word Wizard(Hard, medium damage, but many choices)
 * 
 * @author Team DMZ
 * @version Beta
 *
 */
public class Enemy {

    String name;
    int health;
    int dmgmin;
    int dmgmax;
    int words;

    /**
     * Constructor will assign stats based on four templates
     * @param entype	integer -- the type of enemy encountered
     */
    public Enemy(int entype) {

        if(entype == 1) { //Goblin
            name = "Goblin";
            health = 30;
            dmgmin = 10;
            dmgmax = 20;
            words = 3;
        }
        else if(entype == 2) { //Apprentice
            name = "Wizard Apprentice";
            health = 50;
            dmgmin = 10;
            dmgmax = 25;
            words = 5;
        }
        else if(entype == 3) { //Ghoul
            name = "Ghoul";
            health = 80;
            dmgmin = 25;
            dmgmax = 50;
            words = 4;
        }
        else if(entype == 4){ //Word Wizard
            name = "Word Wizard";
            health = 200;
            dmgmin = 20;
            dmgmax = 30;
            words = 6;
        }
        else {
            health = 1;
            dmgmin = 1;
            dmgmax = 1;
            words = 2;
        }
        //health banks
        /*gobhealth = 30;	apphealth = 50;	ghohealth = 80;	wizhealth = 200;

        //damage banks, with mins and maxes
        gobdmgmin = 10; gobdmgmax = 20;
        appdmgmin = 10;	appdmgmax = 25;
        ghodmgmin = 25;	ghodmgmax = 50;
        wizdmgmin = 20;	wizdmgmax = 30;

        //word option banks
        gobwords = 3;	appwords = 5;
        ghowords = 4;	wizwords = 6; */	
    }
    /**
     * Determines how much damage enemy does on incorrect answer
     * @return		integer -- damage enemy deals to player
     */
    public int enemyStrike() {
        Random rand = new Random();
        return rand.nextInt(dmgmax-dmgmin)+dmgmin;
    }
    /**
     * Getter for the number of answers an enemy has
     * @return		integer -- number of answers enemy asks
     */
    public int enemyWords() {
        return words;
    }
    /**
     * Updates enemy's health with given damage, and returns if dead
     * @param damage	integer -- amount of damage enemy took
     * @return			boolean -- whether enemy is dead or not
     */
    public boolean dmgEnemy(int damage) {
        health = health - damage;
        if(health>0)
            return false;
        else 
            return true;
    }
    /**
     * Returns the enemy's name as a string
     * @return		String -- enemy's name
     */
    public String enemyName() {
        return name;
    }
    
    public int getHealth() {
    	return health;
    }


}
