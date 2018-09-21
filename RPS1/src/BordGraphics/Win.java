package BordGraphics;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Win extends JPanel
{
	private Img _winScreen;
	private Img _loseScreen;
	JFrame _frame = new JFrame("winScreen");
	///private BoardLogic _bl;
	private boolean userWin = false;
	public Win(boolean userWin) {
		_winScreen = new Img("images\\youWin.jpg", 0, 0, 816, 786);
		_loseScreen = new Img("images\\youLose.jpg", 0, 0, 816, 786);
		this.userWin = userWin;
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if(userWin)
		{
			_winScreen.drawImg(g);
		}
		else if(!userWin)
		{
			_loseScreen.drawImg(g);
		}
	}
	public void open()
	{
		repaint();

		_frame.add(this);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setSize(816,786);
		_frame.setVisible(true);
	}
	public boolean isUserWin() {
		return userWin;
	}
	public void setUserWin(boolean userWin) {
		this.userWin = userWin;
	}
	
}
