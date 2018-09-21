package BordGraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardGame extends JPanel implements MouseListener
{
	private BoardPanel _bordPanel;
	private OpenningScreen _op;
	private Img _BackgroundBoard,_leftGraphic,_rightGraphic,_background,_mouseMotion,_setFlagMasseg,_letStartButton,_changeButton;
	static int width = 120,hight = 120;
	static int gameW = 7, gameH = 6, startX = 300,startY = 0;
	JFrame _frame = new JFrame("My Frame");
	private boolean _flag = false;
	private LinkedList<ButtonListiner> _listeners; 
	public BoardGame()
	{
		_mouseMotion = new Img("MouseMoutionIMG\\flag.png",0,0,0,0);
		/**
		 * creat the frame board
		 */
		setLayout(null);
		_BackgroundBoard = new Img("images\\board.png",width-50+startX, hight-40, (gameW+1)*width-25, (gameH+1)*hight -30) ;
		_leftGraphic =  new Img("images\\leftGraphic.png",startX-300,hight-50, 400, (gameH+1)*hight -30) ;
		_rightGraphic = new Img("images\\rightGraphic.png",(gameW+1)*width+40+startX,hight-50, 400, (gameH+1)*hight -30);
		_background = new Img("images\\background.png",0,0,1800,2000);
//		_setFlagMasseg = new Img("Massage\\enterAFlag.png",450,0,750,100);
//		_letStartButton = new Img("Massage\\letsPlay.png",1500,400,256,114);
//		_changeButton = new Img ("Massage\\sweach weapons.png",1500,200,256,114);
		repaint();
		//	addMouseMotionListener(this);
		addMouseListener(this);
		_bordPanel = new BoardPanel();
		add(_bordPanel);
		_bordPanel.setBounds(0,0,1800,2000);
		_listeners = new LinkedList<ButtonListiner>();
		addListener(_op);
		_frame.add(this);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setSize(1800,2000);
		_frame.setVisible(true);

	}
	@Override
	protected void paintComponent(Graphics g)
	{
		//super.paintComponent(g);
		// TODO Auto-generated method stub
		super.paintComponent(g);
		//if(!_flag)
		//{
		_background.drawImg(g);
		_leftGraphic.drawImg(g);
		_BackgroundBoard.drawImg(g);
		_rightGraphic.drawImg(g);
		_mouseMotion.drawImg(g);
		//_setFlagMasseg.drawImg(g);
		//_letStartButton.drawImg(g);
		//_changeButton.drawImg(g);
		_flag = true;
		//}

		//		else
		//		{
		//			_mouseMotion.drawImg(g);
		//		}
	}

	//	@Override
	//	public void mouseMoved(MouseEvent e) {
	//		// TODO Auto-generated method stub
	//		//System.out.println("x =   "+e.getX()+"  y =   "+e.getY());
	//		//_mouseMotion = new Img("MouseMoutionIMG\\flag.png",e.getX(),e.getY(),width,hight);
	//		//repaint();
	//	}
	public void addListener(ButtonListiner toAdd) 
	{ 
		_listeners.add(toAdd);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("x:    "+e.getX()+"y:   "+e.getY());
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
