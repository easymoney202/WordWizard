package WW;

import javax.swing.JFrame;
import java.awt.*;
import java.util.ArrayList;

public class WordWizard extends JFrame {
	private static final long serialVersionUID = 2635031231518739273L;
	public static Boolean m_running = true;
	public static GamePanel m_gamePanel;
	public static Room m_currentRoom;
	public static Player m_player;
	public static Dungeon m_dungeon;
	public static Dictionary m_dictionary;
	public static WordWizard Instance = null;
	public static Integer WINDOW_WIDTH = 640;
	public static Integer WINDOW_HEIGHT = 500;
	public static Integer NUM_ROOMS = 10;
	
	ExploreScene 	m_exploreScene;
	MenuScene 		m_menuScene;
	BattleScene 	m_battleScene;
	HowToScene		m_howToScene;
	
	WordList		m_wordList;
	
	boolean 		m_seenHowTo = false;
	
	public enum GAME_STATE
	{
		Menu,
		HowTo,
		Explore,
		Battle
	};
	
	public static GAME_STATE m_gameState = GAME_STATE.Menu;

	/**
	 * Word wizard constructor
	 */
	public WordWizard() {
		Instance = this;
		m_dictionary = new Dictionary();
		m_dungeon = new Dungeon(m_dictionary.getLevelWords(1));
		m_gamePanel = new GamePanel();
		add(m_gamePanel);
		setTitle("Word Wizard");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		WordList tempWL = new WordList();
		
		for(Words w2 : m_dictionary.getStarter().getKnown()) {
			tempWL.addToKnown(w2);
		}
		
		m_wordList = m_dictionary.getStarter();

		m_currentRoom = m_dungeon.generate(NUM_ROOMS);
		
		ArrayList<Words> newwords = m_currentRoom.getRoomWords();
		if(newwords != null) {		
			for(Words w : newwords) {
				m_wordList.addToKnown(w);
			}
		}
		m_player = new Player(m_currentRoom);
		m_player.setWordList(tempWL);
		m_player.SetPosition(m_dungeon.getCurrentRoom().GetPlayerStartX(),
				m_dungeon.getCurrentRoom().GetPlayerStartY());
		m_player.name = "Young Wizard";
		
		m_menuScene = new MenuScene();
		m_howToScene = new HowToScene();
		m_exploreScene = new ExploreScene(m_player, m_currentRoom, m_dungeon, m_wordList);

		System.out.println(Instance);
		
		GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		
		//StartBattleScene(new Enemy("Images/Player.png", m_currentRoom, 1), 0);

		GameLoop();
	}

	/**
	 * Paints the game. This gets called from the GamePanel paint method
	 */
	public void paint(Graphics g) {
		switch (m_gameState)
		{
		case Menu:
			if (m_menuScene != null)
				m_menuScene.paint(g);
			break;
		case Explore:
			if (m_exploreScene != null)
				m_exploreScene.paint(g);
			break;
		case Battle:
			if (m_battleScene != null)
				m_battleScene.paint(g);
			break;
		case HowTo:
			if (m_howToScene != null)
				m_howToScene.paint(g);
			break;
		default:
			// Shouldn't happen
			break;
		}
	}

	/**
	 * Gets the current room object
	 * 
	 * @return Current room object
	 */
	public Room GetCurrentRoom() {
		return m_currentRoom;
	}

	/**
	 * Getter for player object
	 * 
	 * @return The current player object
	 */
	public Player GetPlayer() {
		return m_player;
	}

	/**
	 * Main game loop
	 */
	public void GameLoop() {
		while (m_running) {
			switch (m_gameState)
			{
			case Menu:
				if (m_menuScene != null)
					m_menuScene.Update();
				break;
			case Explore:
				if (m_exploreScene != null)
					m_exploreScene.Update();
				break;
			case Battle:
				if (m_battleScene != null)
					m_battleScene.Update();
				break;
			case HowTo:
				if (m_howToScene != null)
					m_howToScene.Update();
			default:
				break;
			}
			
			m_gamePanel.repaint();
		}
	}
	
	/**
	 * Returns the explore scene
	 * @return
	 */
	public ExploreScene GetExploreScene()
	{
		return m_exploreScene;
	}
	
	/**
	 * Gets the current GameScene being displayed
	 * @return
	 */
	public GameScene GetCurrentScene()
	{
		switch (m_gameState)
		{
		case Menu:
			if (m_menuScene != null)
				return m_menuScene;
			break;
		case Explore:
			if (m_exploreScene != null)
				return m_exploreScene;
			break;
		case Battle:
			if (m_battleScene != null)
				return m_battleScene;
			break;
		case HowTo:
			if (m_howToScene != null)
				return m_howToScene;
			break;
		default:
			return null;
		}
		
		return null;
	}
	
	/**
	 * Sets the game state of the game
	 * @param state
	 */
	public void SetGameState(GAME_STATE state)
	{
		m_gameState = state;
		if (state == GAME_STATE.Explore)
		{
			m_exploreScene.ResetBattleTime();
		}
	}
	
	/**
	 * This will be used to create a battle from the explore scene
	 * by passing the arguments of the room and what monster dwells
	 * in it
	 * @param params
	 */
	public void StartBattleScene(Enemy enemy, int type)
	{
		// Create the new battle scene object
		m_battleScene = new BattleScene(m_player, enemy, m_wordList, type);
		m_gameState = GAME_STATE.Battle;
	}

	/**
	 * Program start
	 * @param args
	 */
	public static void main(String[] args) {
		new WordWizard();
	}
}
