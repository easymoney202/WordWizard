package WW;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**This will be the main class for the game
 * 
 * @author Zac, Matt, Diego
 * @version Alpha
 *
 */
public class GameMain {

    WordList thelist;
    Player hero;
    Dungeon maze;
    /**
     * Basic constructor.  Creates a new wordlist and runs its init method
     */
    public GameMain() {
        thelist = new WordList();
        thelist.init();
        hero = new Player();
        maze = new Dungeon();
        maze.sampleDungeon();
    }
    /**
     * dungeonStorm is the main program for traversing the dungeon.  It allows for moves and processes rooms
     * No returns or parameters.
     */
    public void dungeonStorm() {

        boolean isdone = false; //boolean for if the game is still running (I.E. player has not quit)
        boolean hasmoved = false; //boolean for if the player has moved from the current room
        Scanner scan = new Scanner(System.in);
        while(!isdone) { //While the player has not yet quit,

            processRoom(); //process the room

            while(!hasmoved) { //While the player hasn't moved, prompt them to move
                System.out.println("Travel to another room?");
                System.out.println("0 goes North, 1 goes East, 2 goes South, and 3 goes West  (-1 quits)");
                int dir = scan.nextInt();
                if(dir == -1) //check if player desires to quit
                    isdone = true;
                else if(getMaze().travel(dir)) { //if successful move, you can leave the loop
                    hasmoved = true;
                }//end else if
                else {
                    System.out.println("Can't do that, chief");
                }//end else
            }//end while !hasmoved
            hasmoved = false;
        }//end while !isdone

    }//end dungeonStorm
    /**
     * processRoom determines what's going on in a room before the player has a chance to interact with it
     * It also contains the call to battleTime to fight an enemy.
     */
    public void processRoom() {
        System.out.print("You have entered the room.  The number ");
        System.out.print(maze.getCurrentRoom());
        System.out.println(" is written on one of the walls");

        //Get objects for this room and the room's enemy
        Room thisroom = maze.getRoom(maze.getCurrentRoom());	
        Enemy roomenemy = thisroom.roomEnemy();
        //If an enemy is there, fight it.
        if(roomenemy!=null) {
            System.out.println("You are not alone in this room...");
            System.out.println("A figure notices you, and charges!");
            boolean success = battleTime(hero, roomenemy, 1);
        } //end if roomenemy
        System.out.println("Alone in the room, you take a look around");
        /*
         * This would be where you mention chests or books
         */
        //After this, check for which doors exist that lead out of the room
        ArrayList<Door> roomdoors = thisroom.getDoors();
        if(roomdoors.size() > 0) {
            for(Door d : roomdoors) {
                System.out.print("You notice a door on the ");
                int dir = d.getDoorDir(thisroom.getRoomID());
                if(dir == 0)
                    System.out.print("North");
                else if(dir == 1)
                    System.out.print("East");
                else if(dir == 2)
                    System.out.print("South");
                else if(dir == 3)
                    System.out.print("West");
                System.out.print(" Wall.  It has ");
                System.out.print(d.getOtherSide(thisroom.getRoomID()));
                System.out.println(" written on it");
            }
        }

    }
    /**
     * Method runs through a "combat" against a specified enemy type
     * 
     * @param game		GainMain object -- Instance of the game being used
     * @param entype	integer -- The type of enemy encountered
     * @param type		integer -- The type of question being asked (temp)
     * @return			boolean, for the end of the fight being reached
     */
    public boolean battleTime(Player thehero, Enemy theenemy, int type) {

        boolean enemdead = false;				//for enemy (and player) death
        int numQs = theenemy.enemyWords();		//Number of answers the enemy offers 
        String enemyname = theenemy.enemyName();//enemy's name

        System.out.print("You are fighting a ");
        System.out.println(enemyname);

        //This fight won't stop until someone falls -- there is a check for player death after the loop
        while(!enemdead) {
            //Next block sets up an ArrayList of the possible answers
            ArrayList<String> prob = thelist.getProblem(numQs, type);  //returns ArrayList with first entry as word, second correct answer
            ArrayList<String> gamelist = new ArrayList<String>();			//A randomized list
            String word = prob.remove(0); //remove the word in question from the list
            String answer = prob.get(0); //and note which answer is the correct one

            Random rand = new Random();
            //randomizes the list of answers.  No fun if it's always the first one
            while(!prob.isEmpty()) {
                gamelist.add(prob.remove(rand.nextInt(prob.size())));
            } // end while !prob.isEmpty
            System.out.print("For Word: ");
            System.out.println(word);
            System.out.print("Find a ");
            if(type==1)
                System.out.println("Synonym");
            else
                System.out.println("Antonym");
            //Print out the choices
            System.out.println(gamelist.toString());
            //Accept player input
            Scanner s = new Scanner(System.in);
            int ansindex = 0;
            while(ansindex < 1 || ansindex > numQs) {
                System.out.print("Enter 1-");
                System.out.print(numQs);
                System.out.println(":");
                ansindex = s.nextInt();
            } //end while ansindex

            //Check if the answer is right and deal damage to enemy if so
            if(gamelist.get(ansindex-1).equals(answer)) {
                System.out.print("Correct! You dealt the ");
                System.out.print(theenemy.enemyName());
                System.out.print(" ");
                int playerdmg = thehero.playerStrike();
                System.out.print(playerdmg);
                System.out.println(" damage!");
                enemdead = theenemy.dmgEnemy(playerdmg);
            } // end if gamelist

            //or deal damage to player if it is not
            else {
                System.out.print("Incorrect! You took ");
                int endmg = theenemy.enemyStrike();
                System.out.print(endmg);
                System.out.println(" damage, ouch!");
                if(thehero.dmgPlayer(endmg) <= 0)
                    enemdead = true;
            } //end else
        }//end while !enemdead
        //check for player death
        if(thehero.GetHealth()<=0){
            System.out.println("But the future refused to change...");
        }
        //otherwise, give the player a pat on the back
        else {
            System.out.print("And the mighty ");
            System.out.print(enemyname);
            System.out.println(" is vanquished!  I bet you feel proud!");
            theenemy = null;
        }
        return enemdead; //return the end of the fight
    }
    /**
     * Getter for the Dungeon object
     * @return		Dungeon -- the game board, so to speak
     */
    public Dungeon getMaze() {
        return maze;
    }

    public static void main(String args[]) {

        GameMain game = new GameMain();
        game.dungeonStorm();
        //Random rnd = new Random();
        //game.getMaze().travel(3);
        //game.processRoom();

        //int qtype = 1; //this may go the way of the dodo at some point
        //Enemy myenemy = new Enemy(rnd.nextInt(3));
        //game.battleTime(game.hero, myenemy, qtype);

    }

}
