package WW;

import java.awt.*;

public class Tilemap {
	const int tile_size = 32;
	// Simple room for now
	int m_tileArray[][] = {
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1}
	};
	
	Tilemap()
	{
		
	}
	
	public void paint(Graphics g)
	{
		int offset = 20;
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < m_tileArray.length; i++)
		{
			for (int j = 0; j < m_tileArray[0].length; i++)
			{
				// Floor
				if (m_tileArray[j][i] == 1)
				{
					g2d.setColor(Color.gray);
					g2d.drawRect(offset + i * tile_size, offset + j * tile_size, tile_size, tile_size);
				
				}
			}
		}
	}
}
