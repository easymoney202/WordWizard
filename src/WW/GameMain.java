package WW;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This will be the main class for the game
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
	 * Basic constructor. Creates a new wordlist and runs its init method
	 */
	public GameMain() {
		thelist = new WordList();
		thelist.init();
		maze = new Dungeon();
		Room currentroom = maze.generate(8);
		hero = new Player(currentroom);
	}

	/**
	 * dungeonStorm is the main program for traversing the dungeon. It allows
	 * for moves and processes rooms No returns or parameters.
	 */
	public void dungeonStorm() {

		Boolean isdone = false; // Boolean for if the game is still running
								// (I.E. player has not quit)
		Boolean hasmoved = false; // Boolean for if the player has moved from
									// the current room
		Scanner scan = new Scanner(System.in);
		while (!isdone) { // While the player has not yet quit,

			processRoom(); // process the room

			while (!hasmoved) { // While the player hasn't moved, prompt them to
								// move
				System.out.println("Travel to another room?");
				System.out
						.println("0 goes North, 1 goes East, 2 goes South, and 3 goes West  (-1 quits)");
				Integer dir = scan.nextInt();
				if (dir == -1 || dir == null) {// check if player desires to quit
					isdone = true;
					break;
				}/*
				else if (getMaze().travel(Direction.fromInteger(dir))) { // if successful move, you can
													// leave the loop
					hasmoved = true;
				}// end else if
				else {
					System.out.println("Can't do that, chief");
				}// end else
				*/
			}// end while !hasmoved
			hasmoved = false;
		}// end while !isdone
		if(isdone){
			System.out.println("BYE");
			System.exit(0);
		}
		scan.close();
	}// end dungeonStorm

	/**
	 * processRoom determines what's going on in a room before the player has a
	 * chance to interact with it It also contains the call to battleTime to
	 * fight an enemy.
	 */
	public void processRoom() {
		System.out.print("You have entered the room.  The number ");
		System.out.print(maze.getCurrentRoom());
		System.out.println(" is written on one of the walls");
/*
		// Get objects for this room and the room's enemy
		Room thisroom = maze.getRoom(maze.getCurrentRoom());
		Enemy roomenemy = thisroom.roomEnemy();
		// If an enemy is there, fight it.
		if (roomenemy != null) {
			System.out.println("You are not alone in this room...");
			System.out.println("A figure notices you, and charges!");
			Boolean success = battleTime(hero, roomenemy, 1);
		} // end if roomenemy
		*/
		System.out.println("Alone in the room, you take a look around");
		/*
		 * This would be where you mention chests or books
		 */
		// After this, check for which doors exist that lead out of the room
		/*
		ArrayList<Door> roomdoors = thisroom.getDoors();
		if (roomdoors.size() > 0) {
			for (Door d : roomdoors) {
				System.out.print("You notice a door on the ");
				Direction dir = d.getDoorDir(thisroom.getRoomID());
				System.out.print(dir);
				System.out.print(" Wall.  It has ");
				System.out.print(d.getOtherSide(thisroom.getRoomID()));
				System.out.println(" written on it");
			}
		}
*/
	}

	/**
	 * Method runs through a "combat" against a specified enemy type
	 * 
	 * @param game
	 *            GainMain object -- Instance of the game being used
	 * @param entype
	 *            integer -- The type of enemy encountered
	 * @param type
	 *            integer -- The type of question being asked (temp)
	 * @return Boolean, for the end of the fight being reached
	 */
	public Boolean battleTime(Player thehero, Enemy theenemy, Integer type) {

		Boolean enemdead = false; // for enemy (and player) death
		Integer numQs = theenemy.enemyWords(); // Number of answers the enemy offers
		String enemyname = theenemy.enemyName();// enemy's name

		System.out.print("You are fighting a ");
		System.out.println(enemyname);

		// This fight won't stop until someone falls -- there is a check for
		// player death after the loop
		while (!enemdead) {
			// Next block sets up an ArrayList of the possible answers
			ArrayList<String> prob = thelist.getProblem(numQs, type); // returns
																		// ArrayList
																		// with
																		// first
																		// entry
																		// as
																		// word,
																		// second
																		// correct
																		// answer
			ArrayList<String> gamelist = new ArrayList<String>(); // A
																	// randomized
																	// list
			String word = prob.remove(0); // remove the word in question from
											// the list
			String answer = prob.get(0); // and note which answer is the correct
											// one

			Random rand = new Random();
			// randomizes the list of answers. No fun if it's always the first
			// one
			while (!prob.isEmpty()) {
				gamelist.add(prob.remove(rand.nextInt(prob.size())));
			} // end while !prob.isEmpty
			System.out.print("For Word: ");
			System.out.println(word);
			System.out.print("Find a ");
			if (type == 1)
				System.out.println("Synonym");
			else
				System.out.println("Antonym");
			// Print out the choices
			System.out.println(gamelist.toString());
			// Accept player input
			Scanner s = new Scanner(System.in);
			Integer ansindex = 0;
			while (ansindex < 1 || ansindex > numQs) {
				System.out.print("Enter 1-");
				System.out.print(numQs);
				System.out.println(":");
				ansindex = s.nextInt();
			} // end while ansindex
			s.close();
			// Check if the answer is right and deal damage to enemy if so
			if (gamelist.get(ansindex - 1).equals(answer)) {
				System.out.print("Correct! You dealt the ");
				System.out.print(theenemy.enemyName());
				System.out.print(" ");
				Integer playerdmg = thehero.attack();
				System.out.print(playerdmg);
				System.out.println(" damage!");
				enemdead = theenemy.dmgEnemy(playerdmg);
			} // end if gamelist

			// or deal damage to player if it is not
			else {
				System.out.print("Incorrect! You took ");
				Integer endmg = theenemy.enemyStrike();
				System.out.print(endmg);
				System.out.println(" damage, ouch!");
				if (thehero.beAttacked(endmg) <= 0)
					enemdead = true;
			} // end else
		}// end while !enemdead
			// check for player death
		if (thehero.getHealth() <= 0) {
			System.out.println("But the future refused to change...");
		}
		// otherwise, give the player a pat on the back
		else {
			System.out.print("And the mighty ");
			System.out.print(enemyname);
			System.out.println(" is vanquished!  I bet you feel proud!");
			theenemy = null;
		}
		return enemdead; // return the end of the fight
	}

	/**
	 * Getter for the Dungeon object
	 * 
	 * @return Dungeon -- the game board, so to speak
	 */
	public Dungeon getMaze() {
		return maze;
	}

	public static void main(String args[]) {

		GameMain game = new GameMain();
		game.dungeonStorm();
		// Random rnd = new Random();
		// game.getMaze().travel(3);
		// game.processRoom();

		// Integer qtype = 1; //this may go the way of the dodo at some point
		// Enemy myenemy = new Enemy(rnd.nextInt(3));
		// game.battleTime(game.hero, myenemy, qtype);

	}

}
