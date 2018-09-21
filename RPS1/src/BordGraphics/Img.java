package BordGraphics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Img
{
	private Image _image;
	private int x, y, width, height;
	/**
	 * constractor
	 * @param path
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Img(String path, int x, int y, int width, int height)
	{
		_image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage(); 
		
		setImgCords(x, y);
		setImgSize(width, height);
	}
	/**
	 * draw IMG
	 * @param g
	 */
	public void drawImg(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
        		g2d.drawImage(_image, x, y, width, height, null);
	}
	/**
	 * set the image location
	 * @param x
	 * @param y
	 */
	public void setImgCords(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	/**
	 * set image size
	 * @param width
	 * @param height
	 */
	public void setImgSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	/**
	 * set image
	 * @param image
	 */
	public void setImg(Image image)
	{
		_image = image;
	}
}


