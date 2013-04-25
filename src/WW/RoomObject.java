package WW;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class RoomObject {

	protected Image objectImage;
	protected File objectImageFile;

	public RoomObject() {
	}

	public RoomObject(String fileloc) {
		objectImageFile = new File(fileloc);
		try {
			objectImage = ImageIO.read(objectImageFile);
		} catch (IOException e) {
			System.out.println("Error opening image file: " + fileloc);
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g, Integer locx, Integer locy) {
		Graphics2D gr = (Graphics2D)g;
		gr.drawImage(objectImage, locx , locy, null);
	}

}
