/**
 * NPC Class represents NPCs on the map
 * 
 * @author Team DMZ
 * @version Final
 */
package WW;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class NPC extends Interactable {

	Words book; // If we decide that some NPCs can offer words to contribute
	String firstline; // First line the NPC rattles off
	String repeatline; // Line that the NPC repeats
	String thirdline;
	boolean donefirst;
	boolean donesecond; //
	
	Image groundImage;

	public NPC(String fl, String rl) {
		super("Images/NPC_Oldman.png");
		book = new Words();
		firstline = fl;
		repeatline = rl;
		donefirst = false;
		
		// Need to render ground below the NPC
		try
		{
			File grndImg = new File("Images/Floor.png");
			groundImage = ImageIO.read(grndImg);
		}
		catch (Exception ex)
		{
			
		}
	}

	public NPC(String fl, Words w) {
		super("Images/NPC_Oldman.png");
		book = w;
		firstline = fl;

		repeatline = "For the word: ";
		repeatline += (book.getWord());
		repeatline += (" Synonyms are: ");
		for (String s : book.getAllSyns()) {
			repeatline += (s);
			repeatline += (" ");
		}

		thirdline = ("Antonyms of ");
		thirdline += book.getWord();
		thirdline += " are: ";
		for (String a : book.getAllAnts()) {
			repeatline += (a);
			repeatline += (" ");
		}

		donefirst = false;
		donesecond = false;
	}

	public String getLine() {
		if (!donefirst) {
			donefirst = true;
			return firstline;
		} else if (donesecond && thirdline != null) {
			donesecond = false;
			return thirdline;
		} else {
			donesecond = true;
			return repeatline;
		}
	}

	@Override
	public void interact(Entity e) {
		WordWizard.Instance.GetExploreScene().SetStatusMsg(getLine());
	}
	
	public void render(Graphics g, Integer locx, Integer locy) {
		Graphics2D gr = (Graphics2D)g;
		gr.drawImage(groundImage, locx, locy, null);
		gr.drawImage(objectImage, locx , locy, null);
	}
}
