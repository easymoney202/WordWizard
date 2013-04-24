package WW;

import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class Tilemap {
	// Level constants
	public static int TILE_SIZE = 32;	// Tile size in pixels
	public static int MAP_X_SIZE = 12;	// X Grid size
	public static int MAP_Y_SIZE = 12;	// Y Grid size
	public static int OFFSET_X = 50;	// Offset for screen X
	public static int OFFSET_Y = 40;	// Offset for screen Y
	
	boolean m_init = false;
	int player_startX, player_startY;
	// Simple room for now
	int m_tileArray[][] = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 1, 0, 0, 2, 2, 0, 1},
			{1, 0, 9, 0, 0, 1, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 1},
			{1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
			{1, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
	
	// Images for the game
	Image m_groundImg;
	Image m_wallImg;
	Image m_shelfImg;
	
	Tilemap()
	{
		try
		{
			System.out.println("Loading ground.");
			File groundFile = new File("Images/ground.bmp");
			m_groundImg = ImageIO.read(groundFile);
			System.out.println("Loading wall.");
			File wallFile = new File("Images/wall.bmp");
			m_wallImg = ImageIO.read(wallFile);
			File shelfFile = new File("Images/shelf.bmp");
			m_shelfImg = ImageIO.read(shelfFile);
			m_init = true;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println("Exception!");
		}
		
		// Get extra info from tile array like player starting position
		for (int i = 0; i < MAP_X_SIZE; i++)
		{
			for (int j = 0; j < MAP_Y_SIZE; j++)
			{
				if (m_tileArray[j][i] == 9)
				{
					player_startX = i;
					player_startY = j;
				}
			}
		}
	}
	
	public int[][] GetTileArray()
	{
		return m_tileArray;
	}
	/**
	* Returns the beginning X pos for the player
	*/
	public int GetPlayerStartX()
	{
		System.out.println("Player start X requested: " + player_startX);
		return player_startX;
	}
	
	/**
	* Returns the beginning Y pos for the player
	*/ 
	public int GetPlayerStartY()
	{
		System.out.println("Player start Y requested: " + player_startY);
		return player_startY;
	}
	/**
	 * Paints the tilemap
	 * @param g
	 */
	public void paint(Graphics g)
	{
		if (m_init)
		{
			int offset = 50;
			Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < MAP_X_SIZE; i++)
			{
				for (int j = 0; j < MAP_Y_SIZE; j++)
				{
					// Walls
					if (m_tileArray[j][i] == 1)
					{
						g2d.drawImage(m_wallImg, OFFSET_X + i * TILE_SIZE, OFFSET_Y + j * TILE_SIZE, null);
					}
					// Floor
					if (m_tileArray[j][i] == 0 || m_tileArray[j][i] == 9)
					{
						g2d.drawImage(m_groundImg, OFFSET_X + i * TILE_SIZE, OFFSET_Y + j * TILE_SIZE, null);
					}
					// Shelf
					if (m_tileArray[j][i] == 2)
					{
						g2d.drawImage(m_shelfImg, OFFSET_X + i * TILE_SIZE, OFFSET_Y + j * TILE_SIZE, null);
					}
				}
			}
		}
	}
}
