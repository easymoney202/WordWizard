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
	
	private long m_timeUntilBattle;
	
	private Image m_background;
	/**
	 * Constructor
	 * @param active
	 */
	public ExploreScene(Player player, Room currentRoom, Dungeon dungeon, WordList wordList) 
	{
		super();
		m_player = player;
		m_room = currentRoom;
		m_dungeon = dungeon;
		m_statusMsg = "Status: None";
		m_wordList = wordList;
		// Generate a new timeUntilBattle
		Random rand = new Random();
		m_timeUntilBattle = System.currentTimeMillis() + ((rand.nextInt(BATTLE_TIME_RAND) + 1) * BATTLE_TIME_MIN);
		try
		{
			File imageFile = new File("Images/explore_bg.png");
			m_background = ImageIO.read(imageFile);
		}
		catch (Exception ex)
		{
			System.out.println("Failed to load Explore background image");
		}
	}
	
	/**
	 * Resets the time for battle
	 */
	public void ResetBattleTime()
	{
		Random rand = new Random();
		m_timeUntilBattle = System.currentTimeMillis() + ((rand.nextInt(BATTLE_TIME_RAND) + 1) * BATTLE_TIME_MIN);
		System.out.println("Time until next battle: " + m_timeUntilBattle);
	}
	
	/**
	 * Draws a simple HUD for the explore scene
	 * @param g
	 */
	public void DrawHUD(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font("ARIAL", Font.BOLD, 17);
		g2.setFont(font);
		g2.setColor(Color.RED);
		if (m_player != null) {
			g2.drawString("Health: " + m_player.getHealth() + "/" + m_player.maxHealth, 490, 40);
			g2.setColor(Color.BLACK);
			g2.drawString("Level: " + m_player.GetLevel(), 490, 60);
			g2.setColor(Color.WHITE);
			g2.drawString("Exp: " + m_player.exp + "/" + m_player.maxExp, 490, 80);
			g2.drawString(m_statusMsg, 20, 450);
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
		if (System.currentTimeMillis() > m_timeUntilBattle)
		{
			// Generate a level for the enemies
			Random random = new Random();
			int enemyLevel = m_player.level + random.nextInt(2);
			System.out.println("Battle Time!");
			WordWizard.Instance.StartBattleScene(new Enemy("Images/player.bmp", m_room, enemyLevel), 1);
		}
	}
	
	/**
	 * Obtains input to the scene
	 */
	public void KeyPressed(KeyEvent e)
	{
		// Pass input to player
		m_player.keyPressed(e);
		
		// Testing leveling
		//if (e.getKeyCode() == KeyEvent.VK_SPACE)
		//	m_player.LevelUp();
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
