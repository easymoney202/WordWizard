package WW;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import WW.WordWizard.GAME_STATE;

/**
 * Scene for battles
 */
public class BattleScene extends GameScene {
	private int EXP_GAIN = 100;
	
	private Player m_player;
	private Enemy m_enemy;
	private WordList m_wordList;
	private int m_type;
	
	// Enemy words and battle
	private String m_enemyWord;
	private String m_answer;
	private ArrayList<String> m_problem;
	private ArrayList<String> m_gameList;
	
	// Used to create a timer for each enemy attack
	private long m_finalTime;
	private long m_timeLength;
	private int m_secondsLeft;
	
	private int m_selection;
	
	private int m_numQs;
	private boolean m_enemyDead;
	
	private Image m_background;
	
	/**
	 * Constructor
	 * @param active
	 */
	public BattleScene(Player player, Enemy enemy, WordList wordList, int type) {
		super();
		m_player = player;
		m_enemy = enemy;
		m_wordList = wordList;
		m_type = type;
		m_numQs = 3;
		m_selection = 0;
		
		
		// Set time depending on enemy level
		m_timeLength = 60000 / (int)((m_enemy.level * 0.3) + 1);
		// Sets the attacking word
		SetNewWord();
		UpdateTime();
		
		try
		{
			File imageFile = new File("Images/battle_bg.png");
			m_background = ImageIO.read(imageFile);
		}
		catch (Exception ex)
		{
			System.out.println("Failed to load Battle background image");
		}
	}
	/**
	 * Makes the enemy attack with a new word
	 */
	public void SetNewWord()
	{
		m_finalTime = System.currentTimeMillis() + m_timeLength;
		m_problem = m_wordList.getProblem(m_numQs, 0);
		m_gameList = new ArrayList<String>();
		m_enemyWord = m_problem.remove(0);
		m_answer = m_problem.get(0);
		
		Random rand = new Random();
		
		// Randomize the options
		while (!m_problem.isEmpty())
		{
			m_gameList.add(m_problem.remove(rand.nextInt(m_problem.size())));
		}
		
	}
	/**
	 * Checks the health points of each entity
	 * then tells if the battle is done or keep going
	 */
	public void CheckBattleStatus()
	{
		if (m_player.getHealth() <= 0)
		{
			// Lost
			// Game Over
			System.exit(0);
			// TODO: Go to game over scene
		}
		
		if (m_enemy.getHealth() <= 0)
		{
			// Player won!
			// Compute some experience gain based on enemy level
			int expGain = (int)(((float)m_enemy.level) * EXP_GAIN);
			m_player.AddEXP(expGain);
			// Go back to explore mode
			WordWizard.Instance.SetGameState(GAME_STATE.Explore);
		}
	}
	/**
	 * Checks if the current selection is correct or wrong
	 *  and act accordingly
	 */
	public void CheckAnswer()
	{
		if (m_answer.compareTo(m_gameList.get(m_selection)) == 0)
		{
			// Correct answer!
			int damage = m_player.attack();
			m_enemy.dmgEnemy(damage);
		}
		else
		{
			// Wrong answer!
			int damage = m_enemy.enemyStrike();
			m_player.beAttacked(damage);
		}
		
		CheckBattleStatus();
		
		SetNewWord();
	}
	/**
	 * Updates the time for the word attack in which
	 * the player needs to answer
	 */
	public void UpdateTime()
	{
		long curTime = System.currentTimeMillis();
		m_secondsLeft = (int)((m_finalTime - curTime)/1000);
		
		if (m_secondsLeft < 0)
		{
			// Damage the player since it took too long to answer
			int damage = m_enemy.enemyStrike();
			m_player.beAttacked(damage);
			SetNewWord();
		}
	}
	/**
	 * Update method
	 */
	public void Update()
	{
		UpdateTime();
	}
	/**
	 * Draws the menus, words, buttons, etc
	 * @param g
	 */
	public void DrawHUD(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font("ARIAL", Font.BOLD, 18);
		g2.setFont(font);
		if (m_player != null) {
			g2.setColor(Color.WHITE);
			g2.drawString("Player: " + m_player.name, 40, 40);
			g2.setColor(Color.RED);
			g2.drawString("Health: " + m_player.getHealth() + "/" + m_player.maxHealth, 40, 60);
			g2.setColor(Color.WHITE);
			g2.drawString("Level: " + m_player.level, 40, 80);
			
		}
		g2.setColor(Color.RED);
		g2.drawString("Enemy: ", 300, 40);
		g2.setColor(Color.WHITE);
		g2.drawString(m_enemy.name, 369, 40);
		g2.drawString("Level: " + m_enemy.level, 300, 60);
		g2.setColor(Color.RED);
		g2.drawString("Enemy Health: " + m_enemy.getHealth(), 300, 80);
		
		g2.setColor(Color.WHITE);
		font = new Font("ARIAL", Font.BOLD, 22);
		g2.setFont(font);
		// Draw the words
		g2.drawString("Enemy Attack: " + m_enemyWord, 120, 370);
		// Draw the options
		for (int i = 0; i < m_gameList.size(); i++)
		{
			if (m_selection == i)
				g2.drawString("> " + m_gameList.get(i), 120, 400 + i * 30);
			else
				g2.drawString("   " + m_gameList.get(i), 120, 400 + i * 30);
		}
		
		// Draw the time
		font = new Font("ARIAL", Font.BOLD, 22);
		g2.setFont(font);
		g2.setColor(Color.BLACK);
		g2.drawString("Time: " + m_secondsLeft, 500, 440);
	}
	/**
	 * Paint method for the Battle Scene
	 */
	public void paint(Graphics g)
	{
		g.drawImage(m_background, 0, 0, null);
		DrawHUD(g);
	}
	/**
	 * Obtains input to the scene
	 */
	public void KeyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			if (m_selection > 0)
				m_selection--;
			break;
		case KeyEvent.VK_DOWN:
			if (m_selection < m_numQs - 1)
				m_selection++;
			break;
		case KeyEvent.VK_SPACE:
			CheckAnswer();
			break;
		default:
			break;
		}
	}
	/**
	 * Obtains input to the scene
	 */
	public void KeyReleased(KeyEvent e)
	{
		
	}
}
