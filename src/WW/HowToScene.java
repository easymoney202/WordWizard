package WW;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.imageio.ImageIO;

import WW.WordWizard.GAME_STATE;

public class HowToScene extends GameScene{
	
	private Image m_img1;
	private Image m_img2;
	private Image m_img3;
	private Image m_img4;
	private Image m_img5;
	
	private int m_page = 1;
	
	/**
	 *  Constructor
	 */
	public HowToScene()
	{
		super();
		try
		{
			File file = new File("Images/HowToPlay1.png");
			m_img1 = ImageIO.read(file);
			file = new File("Images/HowToPlay2.png");
			m_img2 = ImageIO.read(file);
			file = new File("Images/HowToPlay3.png");
			m_img3 = ImageIO.read(file);
			file = new File("Images/HowToPlay4.png");
			m_img4 = ImageIO.read(file);
			file = new File("Images/HowToPlay5.png");
			m_img5 = ImageIO.read(file);
		}
		catch (Exception ex)
		{
			System.out.println("Could not load How To Play scenes.");
		}
	}
	
	/**
	 * Paint method
	 */
	public void paint(Graphics g) {
		switch(m_page)
		{
		case 1:
			g.drawImage(m_img1, 0, 0, null);
			break;
		case 2:
			g.drawImage(m_img2, 0, 0, null);
			break;
		case 3:
			g.drawImage(m_img3, 0, 0, null);
			break;
		case 4:
			g.drawImage(m_img4, 0, 0, null);
			break;
		case 5:
			g.drawImage(m_img5, 0, 0, null);
			break;
		default:
			// Shouldn't happen
			break;
		}
	}

	/**
	 * Obtains input to the scene
	 */
	public void KeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_N:
			m_page++;
			if (m_page >= 6)
				m_page = 1;
			break;
		case KeyEvent.VK_Q:
			WordWizard.Instance.SetGameState(GAME_STATE.Menu);
			break;
		default:
			break;
		}
	}

	/**
	 * Obtains input to the scene
	 */
	public void KeyReleased(KeyEvent e) {

	}
}
