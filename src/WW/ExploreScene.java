package WW;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Defines all the logic for the Explore scene
 * @author Diego, Matt & Zac
 */
public class ExploreScene extends GameScene
{
	private static int BATTLE_TIME_MIN = 10000;
	private static int BATTLE_TIME_RAND = 3;
	private Player m_player;
	private Dungeon m_dungeon;
	private Room m_room;
	private String m_statusMsg;
	private WordList m_wordList;
	private boolean m_onBattle;
	private int m_curpage;
	
	private long m_timeUntilBattle;
	private long m_timeDelay;
	
	private Image m_background;
	private Image m_bookImg;
	private String m_bookString[];
	private String m_bookPage2[];
	private String m_bookTitle;
	private boolean m_showBook;
	private boolean m_inMenu;
	private boolean m_showDict;
	/**
	 * Constructor
	 * @param active
	 */
	public ExploreScene(Player player, Room currentRoom, Dungeon dungeon, WordList wordList) 
	{
		super();
		m_timeDelay = 0;
		m_curpage = 0;
		m_inMenu = false;
		m_player = player;
		m_room = currentRoom;
		m_onBattle = false;
		m_showBook = false;
		m_showDict = false;
		m_dungeon = dungeon;
		m_statusMsg = "Status: None";
		m_bookString = new String[24];
		m_bookPage2 = new String[24];
		m_bookTitle = "Dictionary";
		m_wordList = wordList;
		// Generate a new timeUntilBattle
		Random rand = new Random();
		m_timeUntilBattle = System.currentTimeMillis() + ((rand.nextInt(BATTLE_TIME_RAND) + 1) * BATTLE_TIME_MIN);
		try
		{
			File imageFile = new File("Images/explore_bg.png");
			m_background = ImageIO.read(imageFile);
			imageFile = new File("Images/book.png");
			m_bookImg = ImageIO.read(imageFile);
		}
		catch (Exception ex)
		{
			System.out.println("Failed to load Explore background image or Book image");
		}
	}
	
	public void addToPlayerBook(Words w) {
		m_player.getWordList().addToKnown(w);
	}
	
	/**
	 * Shows the book to the player
	 * @param message
	 */
	public void ShowBook(String title, String message)
	{
		m_inMenu = true;
		m_timeDelay = m_timeUntilBattle - System.currentTimeMillis();
		m_showBook = true;
		m_bookString = message.split("--");
		m_bookTitle = title;
	}
	
	/**
	 * Closes the book
	 */
	public void CloseBook()
	{
		m_timeUntilBattle = System.currentTimeMillis() + m_timeDelay;
		m_timeDelay = 0;
		m_showBook = false;
		m_inMenu = false;
	}
	
	public void OpenDict(String title, String message, String message2) {
		m_inMenu = true;
		m_timeDelay = m_timeUntilBattle - System.currentTimeMillis();
		m_showDict = true;
		m_bookString = message.split("--");
		m_bookPage2 = message2.split("--");
		m_bookTitle = title;
	}
	
	public void CloseDict() {
		m_timeUntilBattle = System.currentTimeMillis() + m_timeDelay;
		m_timeDelay = 0;
		m_curpage = 0;
		m_showDict = false;
		m_inMenu = false;
	}
	
    public void ReadDict() {
    	//Make sure we don't get an array out of bounds error
    	if(m_curpage+1 < m_player.getWordList().getKnown().size())
    		WordWizard.Instance.GetExploreScene().OpenDict("Known Words, page " + (m_curpage/2 +1), 
    			new Bookcase(m_player.getWordList().getKnown().get(m_curpage)).getBookString(),
    			new Bookcase(m_player.getWordList().getKnown().get(m_curpage+1)).getBookString());
    	else
    		WordWizard.Instance.GetExploreScene().OpenDict("Known Words, page " + (m_curpage/2 +1), 
        			new Bookcase(m_player.getWordList().getKnown().get(m_curpage)).getBookString(),
        			" ");
    }
	
	/**
	 * Resets the time for battle
	 */
	public void ResetBattleTime()
	{
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		long addTime = ((rand.nextInt(BATTLE_TIME_RAND) + 1) * BATTLE_TIME_MIN);
		m_timeUntilBattle = System.currentTimeMillis() + addTime;
		System.out.println("Time until next battle: " + m_timeUntilBattle);
		m_onBattle = false;
	}
	
	/**
	 * Draws a simple HUD for the explore scene
	 * @param g
	 */
	public void DrawHUD(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font("ARIAL", Font.BOLD, 17);
		g2.setFont(font);
		
		if (m_showBook == false && m_showDict == false)
		{
			// Show the HUD
			g2.setColor(Color.RED);
			if (m_player != null) {
				g2.drawString("Health: " + m_player.getHealth() + "/" + m_player.maxHealth, 490, 40);
				g2.setColor(Color.BLACK);
				g2.drawString("Level: " + m_player.GetLevel(), 490, 60);
				g2.setColor(Color.WHITE);
				g2.drawString("Exp: " + m_player.exp + "/" + m_player.maxExp, 490, 80);
				g2.drawString(m_statusMsg, 20, 450);
				font = new Font("ARIAL", Font.BOLD, 15);
				g2.drawString("Press 'E' to open", 485, 130);
				g2.drawString("the Dictionary", 485, 149);
			}
		}
		else if (m_showDict)
		{
			g.drawImage(m_bookImg, 0, 0, null);
			g2.setColor(Color.BLACK);
			g2.drawString(m_bookTitle, 50, 70);
			for (int i = 0; i < m_bookString.length; i++)
				g2.drawString(m_bookString[i], 50, 130 + i * 22);
			for (int j = 0; j< m_bookPage2.length; j++)
				g2.drawString(m_bookPage2[j], 380, 130 + j * 22);
			g2.setColor(Color.WHITE);
			g2.drawString("Press 'Q' To close Dictionary.", 20, 470);
			g2.drawString("Press 'N' to go to next page.", 320, 470);
		} 
		else
		{
			// Show book
			g.drawImage(m_bookImg, 0, 0, null);
			g2.setColor(Color.BLACK);
			g2.drawString(m_bookTitle, 50, 90);
			for (int i = 0; i < m_bookString.length; i++)
				g2.drawString(m_bookString[i], 30, 150 + i * 22);
			g2.setColor(Color.WHITE);
			g2.drawString("Press 'Q' To close the book.", 20, 465);
		}
	}
	
	/**
	 * Paint method for the Explore Scene
	 */
	public void paint(Graphics g)
	{
		g.drawImage(m_background, 0, 0, null);
		
		if (m_room != null)
		{
			m_room.paint(g);
		}
		
		if (m_player != null)
		{
			m_player.render(g, m_player.m_x * Room.TILE_SIZE + Room.OFFSET_X, m_player.m_y * Room.TILE_SIZE + Room.OFFSET_Y);
		}
		
		DrawHUD(g);
	}
	
	/**
	 * Sets a status message in the HUD
	 * 
	 * @param msg
	 */
	public void SetStatusMsg(String msg) {
		m_statusMsg = msg;
	}

	/**
	 * Resets the status msg
	 */
	public void ResetStatusMsg() {
		m_statusMsg = "Status: None";
	}
	
	/**
	 * Update method
	 */
	public void Update()
	{
		// Check if we are ready for a battle
		if (m_onBattle == false && System.currentTimeMillis() > m_timeUntilBattle && m_inMenu == false)
		{
			System.out.println ("Sys: " + System.currentTimeMillis() + ", Time: " + m_timeUntilBattle);
			// Generate a level for the enemies
			Random random = new Random();
			int enemyLevel = m_player.level + random.nextInt(2);
			System.out.println("Battle Time!");
			m_onBattle = true;
			WordWizard.Instance.StartBattleScene(new Enemy("Images/Player.png", m_room, enemyLevel), 1);
		}
	}
	
	/**
	 * Obtains input to the scene
	 */
	public void KeyPressed(KeyEvent e)
	{
		
		if (e.getKeyCode() == KeyEvent.VK_E && !m_showDict)
			ReadDict();
		
		else if (e.getKeyCode() == KeyEvent.VK_N) {
			if(m_showDict) {
				if(m_curpage+2 < m_player.getWordList().getKnown().size())
					m_curpage = m_curpage + 2;
				else
					m_curpage = 0;
			}
			ReadDict();
		}
		// Pass input to player only when not looking at a book
		else if (m_showBook == false && !m_showDict)
			m_player.keyPressed(e);
		

		
		// Testing leveling
		if (e.getKeyCode() == KeyEvent.VK_Q){
			if(m_showBook)
				CloseBook();
			if(m_showDict)
				CloseDict();
		}
	}
	/**
	 * Obtains input to the scene
	 */
	public void KeyReleased(KeyEvent e)
	{
		// Pass input to player
		m_player.keyReleased(e);
	}
}
