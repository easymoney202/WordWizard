package WW;
/**
 * NPC Class will contain the data for various NPCs.  They aren't very smart
 * @author Team DMZ
 * @version Beta
 */

public class NPC {

	String name; //NPC's name
	String firstline; //First line NPC tells you
	String repeatline; //The line an NPC repeats ad nauseum
	String eventline; //If there's a special event, NPC says this
	int npctype; //Some NPCs are more equal than others.
	boolean talkedto; //If NPC has been talked to before
	boolean event; //If NPC has an event line to say
	/**
	 * Constructor for the NPC class 
	 * @param n			String -- NPC's name
	 * @param fl		String -- NPC's first line
	 * @param rl		String -- NPC's repeated line
	 * @param t			int	   -- If anything special about NPC
	 */
	public NPC(String n, String fl, String rl, int t) {
		name = n;
		firstline = fl;
		repeatline = rl;
		npctype = t;
		talkedto = false;
	}
	/**
	 * Getter for the NPC's name
	 * @return			String -- NPC's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Getter for NPC's line
	 * @return			String -- the NPC's next line of dialogue
	 */
	public String getLine() {
		if(!talkedto)
			return firstline;
		else if(event)
			return eventline;
		else
			return repeatline;
	}
	
	/**
	 * Getter for the NPC's type, possible consequences
	 * @return			int -- the NPC's type
	 */
	public int getNPCType() {
		return npctype;
	}
	
	/**
	 * Setter for an event line for an NPC.  Also trips event flag back and forth
	 * @param e			String -- the event line for the NPC
	 */
	public void setEventLine(String e) {
		eventline = e;
		if(!event)
			event = true;
		else
			event = false;
	}
	
}
