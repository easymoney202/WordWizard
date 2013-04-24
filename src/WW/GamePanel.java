package WW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;

public class GamePanel extends JPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;
    public static GamePanel Instance;
    private static boolean m_initialized = false;

    public GamePanel()
    {
    	addKeyListener(new InputAdapter());
    	setFocusable(true);
    	setBackground(Color.BLACK);
    	setDoubleBuffered(true);
    	// Test
    	if (m_initialized == false)
    	{
    		Instance = this;
    		m_initialized = true;
    	}
    }
    
    public void actionPerformed(ActionEvent e){
    	repaint();
    }
    
    public void paint(Graphics g)
    {
    	super.paint(g);
    	g.clearRect(0, 0, WordWizard.WINDOW_WIDTH, WordWizard.WINDOW_HEIGHT);
    	
    	Graphics2D g2 = (Graphics2D) g;
    	
    	RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
    									       RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    	
    	g2.setRenderingHints(rh);
    	
    	if (WordWizard.Instance != null)
    		WordWizard.Instance.paint(g);
    	
    	g.dispose();
    }
    
    /**
     * Passes input to the corresponding classes
     * @author diegopinatem
     */
    private class InputAdapter extends KeyAdapter
    {
    	public void keyPressed(KeyEvent e)
    	{
    		WordWizard.Instance.GetPlayer().keyPressed(e);
    	}
    	
    	public void keyReleased(KeyEvent e)
    	{
    		WordWizard.Instance.GetPlayer().keyReleased(e);
    	}
    }
}

