package BordGraphics;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Logic.BoardLogic;

public class TieScreen extends JPanel implements MouseListener
{
	private Img _FightTieScreen;
	JFrame _frame = new JFrame("Tie");
	///private BoardLogic _bl;
	private int _blueI,_blueJ,_redI,_redJ;
	private BoardPanel _myBP;
	
	public TieScreen(int blueI,int blueJ,int redI,int redJ, BoardPanel myBP)
	{
		_FightTieScreen = new Img("images\\FightTieScreen.png", 0, 0, 816, 786);

		addMouseListener(this);
		//_boardGame.setBounds(0,0,1800,2000);
		//_bl = bl;
		_blueI = blueI;
		_blueJ = blueJ;
		_redI = redI;
		_redJ = redJ;
		_myBP = myBP;
	}
	public void tie(BoardLogic bl)
	{
		
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		// TODO Auto-generated method stub
		super.paintComponent(g);
		_FightTieScreen.drawImg(g);

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
	public void open()
	{
		repaint();

		_frame.add(this);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setSize(816,786);
		_frame.setVisible(true);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("x = "+e.getX()+"y = "+e.getY());
		Random rand= new Random();
		int num;
		num = rand.nextInt(3)+0;
		if(num==0)
		{
			BoardLogic.getInstance().setStatus(_redI,_redJ,BoardLogic.cellStatus.RedScissors);
		}
		if(num==1)
		{
			BoardLogic.getInstance().setStatus(_redI,_redJ,BoardLogic.cellStatus.RedRock);
		}
		
		if(num == 2)
		{
			BoardLogic.getInstance().setStatus(_redI,_redJ,BoardLogic.cellStatus.RedPaper);
		}
		/**
		 * scissors
		 */
		//System.out.println(BoardLogic.getInstance().getStatus(_redI, _redJ).toString());
		if(e.getX()<750 && e.getX()>540
				&&e.getY()<469&& e.getY()>218)
		{
			BoardLogic.getInstance().setStatus(_blueI, _blueJ,  BoardLogic.cellStatus.BlueScissors);
		}
		/**
		 * rock
		 */
		if(e.getX()<470 && e.getX()>299
				&&e.getY()<470&& e.getY()>222)
		{
			BoardLogic.getInstance().setStatus(_blueI, _blueJ,  BoardLogic.cellStatus.BlueRock);
		}
		/**
		 * paper
		 */
		if(e.getX()<242 && e.getX()>60
				&&e.getY()<470&& e.getY()>222)
		{
			BoardLogic.getInstance().setStatus(_blueI, _blueJ,  BoardLogic.cellStatus.BluePaper);
			//_frame.dispatchEvent(new WindowEvent(_frame, WindowEvent.WINDOW_CLOSING));
		}
		//System.out.println("blue i = "+_blueI+"_blue J = "+_blueJ);
		//System.out.println("RED I = "+_redI+"RED J = "+_redJ);
		//System.out.println(BoardLogic.getInstance().getStatus(_blueI, _blueJ).toString()+ "  " + BoardLogic.getInstance().getStatus(_redI , _redJ).toString());
		//_myBP.StopThread();
		

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public int getBlueI() {
		return _blueI;
	}
	public void setBlueI(int blueI) {
		this._blueI = blueI;
	}
	public int getBlueJ() {
		return _blueJ;
	}
	public void setBlueJ(int blueJ) {
		this._blueJ = blueJ;
	}
	public int getRedI() {
		return _redI;
	}
	public void setRedI(int redI) {
		this._redI = redI;
	}
	public int getRedJ() {
		return _redJ;
	}
	public void setRedJ(int redJ) {
		this._redJ = redJ;
	}
	public void visibleFalse() {
		// TODO Auto-generated method stub
		_frame.setVisible(false);
	}


}




