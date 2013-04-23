package WW;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;

public class GamePanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    public GamePanel()
    {
    	// Test
    }
    
    public void paint(Graphics g)
    {
    	super.paint(g);
    	
    	Graphics2D g2 = (Graphics2D) g;
    	
    	RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
    									       RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    	
    	g2.setRenderingHints(rh);
    	
    	Dimension size = getSize();
    	double w = size.getWidth();
    	double h = size.getHeight();
    	
    	Ellipse2D e = new Ellipse2D.Double(0, 0, 80, 130);
    	g2.setStroke(new BasicStroke(1));
    	g2.setColor(Color.gray);
    	
    	for (double deg = 0; deg < 360; deg += 5) {
            AffineTransform at =
                AffineTransform.getTranslateInstance(w / 2, h / 2);
            at.rotate(Math.toRadians(deg));
            g2.draw(at.createTransformedShape(e));
          }
    	
    	WordWizard.Instance.paint(g);
    
    }
}

