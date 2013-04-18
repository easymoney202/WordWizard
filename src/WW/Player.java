package WW;
import java.util.Random;

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

    /**
     * Constructor with default stats loaded
     */
    public Player() {
        health = 100;
        dmgmin = 15;
        dmgmax = 25;
    } //end Player
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
    public int playerHealth(){
        return health;
    } //end playerHealth
    /**
     * Updates player's health after taking damage and returns new hp
     * @param dmg		integer -- amount of damage player took
     * @return			integer -- updated player hp
     */
    public int dmgPlayer(int dmg){
        health = health - dmg;
        return health;
    } //end dmgPlayer

}
