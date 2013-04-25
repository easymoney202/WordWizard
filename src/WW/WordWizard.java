package WW;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;

public class WordWizard extends JFrame
{
	boolean m_running = true;
	GamePanel m_gamePanel;
	Room m_currentRoom;
	Player m_player;
	String m_statusMsg;
	
	public static WordWizard Instance = null;
	public static int WINDOW_WIDTH = 640;
	public static int WINDOW_HEIGHT = 480;
	
    /**
     * Word wizard constructor
     */
    public WordWizard()
    {
    	Instance = this;
    	
    	m_gamePanel = new GamePanel();
        add(m_gamePanel);
        setTitle("Word Wizard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        m_currentRoom = new Room(0, 0, false);
        m_player = new Player();
        m_player.SetPosition(m_currentRoom.GetTilemap().GetPlayerStartX(), m_currentRoom.GetTilemap().GetPlayerStartY());
        m_statusMsg = "Status: None";
        
        System.out.println(Instance);
        
        GameLoop();
    }
    /**
     * Draws the heads up display of the game
     * @param g
     */
    public void DrawHUD(Graphics g)
    {
    	Graphics2D g2 = (Graphics2D)g;
    	
    	Font font = new Font("ARIAL", Font.PLAIN, 15);
    	g2.setFont(font);
    	g2.setColor(Color.BLACK);
    	g2.drawString("Word Wizard", 20, 20);
    	
    	g2.drawString("Health: " + m_player.GetHealth(), 500, 20);
    	g2.drawString("Level: " + m_player.GetLevel(), 500, 40);
    	g2.drawString(m_statusMsg, 20, 450);
    }
    /**
     * Paints the game.
     * This gets called from the GamePanel paint method
     */
    public void paint(Graphics g)
    {
    	GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    	
    	if (m_currentRoom != null)
    	{
    		m_currentRoom.paint(g);
    	}
    	
    	m_player.paint(g);

    	DrawHUD(g);
    }
    /**
     * Gets the current room object
     * @return Current room object
     */
    public Room GetCurrentRoom()
    {
    	return m_currentRoom;
    }
    /**
     * Getter for player object
     * @return The current player object
     */
    public Player GetPlayer()
    {
    	return m_player;
    }
    /**
     * Main game loop
     */
    public void GameLoop()
    {
    	while (m_running)
    	{
    		m_gamePanel.repaint();
    	}
    }
    /**
     * Sets a status message in the HUD
     * @param msg
     */
    public void SetStatusMsg(String msg)
    {
    	m_statusMsg = msg;
    }
    /**
     * Resets the status msg
     */
    public void ResetStatusMsg()
    {
    	m_statusMsg = "Status: None";
    }
    /**
     * Program start
     * @param args
     */
    public static void main (String[] args)
    {
        new WordWizard();
    }
}

