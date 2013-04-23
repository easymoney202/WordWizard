package WW;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

public class WordWizard extends JFrame
{
	boolean m_running = true;
	Room m_currentRoom;
	
	public static WordWizard Instance = null;
	
    // Constructor
    public WordWizard()
    {
        add(new GamePanel());
        setTitle("Word Wizard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        m_currentRoom = new Room(0, 0, false, false);
        
        Instance = this;
        
        GameLoop();
    }
    
    public void paint(Graphics g)
    {
    	m_currentRoom.paint(g);
    }
    
    public void GameLoop()
    {
    	while (m_running)
    	{
    		
    	}
    }
    // Main
    public static void main (String[] args)
    {
        new WordWizard();
    }
}

