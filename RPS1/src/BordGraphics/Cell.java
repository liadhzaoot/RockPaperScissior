package BordGraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.file.DirectoryIteratorException;
import java.util.LinkedList;
import java.util.Random;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.CellEditorListener;

import Logic.BoardLogic;
import Logic.BoardLogic.cellStatus;

public class Cell extends JPanel implements MouseListener,  ActionListener, MouseMotionListener
{
	private int _i;
	private int _j;
	private Img _imgBackground; 
	private Img _mouseMotion;
	private boolean _isPosibleCell;
	private Img _imgSoldier;
	//public static enum cellStatus{RedRock, BlueRock,RedPaper,BluePaper,RedScissors,BlueScissors,Empty,Flag,Hole,EmptySoldier};
	//private cellStatus _cellStatus;
	private int _x,tempX;
	private int _y,tempY;
	private boolean _arrowFlag = false,_setRedFlag = false;
	private static int  count = 0;
	private int tCount = 0;
	private int _action = 0;
	private LinkedList<CellClickListener> _listeners;
	Timer _t;
	public static enum ArrowDirections{Straight,Left,Right,Back};
	ArrowDirections _direction;
	private boolean b;
	Img _StraightArrowImg,_LeftArrowImg,_RightArrowImg,_BackArrowImg,_StraightArrowImgEnemy,_LeftArrowImgEnemy,_BackArrowImgEnemy,_RightArrowImgEnemy;
	int  n;
	public Cell(int i, int j,BoardPanel bp) 
	{
		_StraightArrowImg = new Img ("ArrowIMG\\StraightArrow.png",0,0,BoardPanel.width, BoardPanel.hight);
		_LeftArrowImg = new Img ("ArrowIMG\\leftArrow.png",0,0,BoardPanel.width, BoardPanel.hight);
		_BackArrowImg = new Img ("ArrowIMG\\backArrow.png",0,0,BoardPanel.width, BoardPanel.hight);
		_RightArrowImg = new Img ("ArrowIMG\\rightArrow.png",0,0,BoardPanel.width, BoardPanel.hight);
		_StraightArrowImgEnemy = new Img("ArrowIMG\\frontArrow.png",0,0,BoardPanel.width, BoardPanel.hight);
		_LeftArrowImgEnemy = new Img("ArrowIMG\\leftBlueArrow.png",0,0,BoardPanel.width, BoardPanel.hight);
		_BackArrowImgEnemy = new Img("ArrowIMG\\backBlueArrow.png",0,0,BoardPanel.width, BoardPanel.hight);
		_RightArrowImgEnemy = new Img("ArrowIMG\\rightBlueArrow.png",0,0,BoardPanel.width, BoardPanel.hight);
		//_mouseMotion = new Img("MouseMoutionIMG\\flag.png",0,0,0,0);
		_t = new Timer(100, this);
		tempX = _x;
		tempY = _y;
		setBackground(Color.black);
		setOpaque(false);
		_i = i;
		_j = j;
		// Random on _soldierType
		//_imgSoldier = imgSoldier;
		_x=j*BoardPanel.width+BoardPanel.startX; 
		_y=i*BoardPanel.hight;
		//setSize();
		addMouseListener(this);
		addMouseMotionListener(this);
		setBounds(_x, _y,BoardPanel.width, BoardPanel.hight);
		//System.out.println(i + ", " + j + "x: " + _x + ", y: " + _y);
		count++;
		if(count%2 == 0)
		{
			_imgBackground = new Img("images\\BrightCell.png", 0, 0, BoardPanel.width, BoardPanel.hight);

			//_frame.add(arg0)(_board[i][j]);
		}
		else 
		{				
			_imgBackground = new Img("images\\darkCell.png", 0, 0, BoardPanel.width, BoardPanel.hight);
			//_board[i][j] = new Cell(i,j,img,true,null,null,  Cell.cellStatus.Empty,i*hight, j*width,false);
		}

		if(i == 0 || i == BoardPanel.gameH+1 || j == 0 || j== BoardPanel.gameW+1 )
		{
			_imgBackground = null;
			setOpaque(false);
		}
		FirstSetSoldier();
		_listeners = new LinkedList<CellClickListener>();
		addListener(bp);
		//System.out.println("adad");
		repaint();
		//System.out.println("adad");
	}
	public ArrowDirections getDirection() {
		return _direction;
	}
	public void setDirection(ArrowDirections direction) {
		this._direction = direction;
	}
	public boolean isArrow() {
		return _arrowFlag;
	}
	public void setArrow(boolean arrow) {
		this._arrowFlag = arrow;
	}
	public int getX() {
		return _x;
	}
	public void setX(int x) {
		this._x = x;
	}
	public int getY() {
		return _y;
	}
	public void setY(int y) {
		this._y = y;
	}
	//	public cellStatus getCellStatus() {
	//		return _cellStatus;
	//	}
	//	public void setCellStatus(cellStatus cellStatus) {
	//		_cellStatus = cellStatus;
	//	}
	public int getRow() {
		return _i;
	}
	public void setRow(int row) {
		_i = row;
	}
	public int getCol() {
		return _j;
	}
	public void setCol(int col) {
		_j = col;
	}
	public Img getImgBackground() {
		return _imgBackground;
	}
	public void setImgBackground(Img imgBackground) {
		_imgBackground = imgBackground;
	}
	public boolean isPosibleCell() {
		return _isPosibleCell;
	}
	public void setPosibleCell(boolean isPosibleCell) {
		_isPosibleCell = isPosibleCell;
	}
	public Img getImgSoldier() {
		return _imgSoldier;
	}
	public void setImgSoldier(Img imgSoldier) {
		_imgSoldier = imgSoldier;
	}
	public void drawSoldier(Graphics g)
	{
		_imgSoldier.drawImg(g);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		//System.out.println("adssad");
		// TODO Auto-generated method stub
		super.paintComponent(g);
		tempY+=10;
		tempX+=10;
		//System.out.println("liad");
		if(_imgBackground != null)
		{
			_imgBackground.drawImg(g);
			//g.fillRect(0, 0, BoardPanel.width, BoardPanel.hight);
			//System.out.println("asdasd");
		}
		if(_imgSoldier != null)
			_imgSoldier.drawImg(g);
		//_mouseMotion.drawImg(g);
		//				if(tCount<20 && b);
		//				_imgSoldier.setImgCords(tempX, tempY);
		//				if(tCount >20)
		//				{
		//					_t.stop();
		//					tCount = 0;
		//					tempX = _x;
		//					tempY = _y;
		//					b = false;
		//				}
		if(_arrowFlag)
		{
			//System.out.println("asdasd");
			if(_direction == ArrowDirections.Straight)
			{
				_StraightArrowImg.drawImg(g);
			}
			if(_direction == ArrowDirections.Back)
			{
				_BackArrowImg.drawImg(g);
			}
			if(_direction == ArrowDirections.Left)
			{
				_LeftArrowImg.drawImg(g);
			}
			if(_direction == ArrowDirections.Right)
			{
				_RightArrowImg.drawImg(g);
			}

			if(_direction == ArrowDirections.Straight &&
					(BoardLogic.getInstance().getStatus(_i, _j) == BoardLogic.cellStatus.RedPaper || 
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.RedRock ||
					BoardLogic.getInstance().getStatus(_i, _j) == BoardLogic.cellStatus.RedScissors ||
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.RedFlag ||
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.Hole))
			{
				_StraightArrowImgEnemy.drawImg(g);
			}
			if(_direction == ArrowDirections.Back &&
					(BoardLogic.getInstance().getStatus(_i, _j) == BoardLogic.cellStatus.RedPaper || 
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.RedRock ||
					BoardLogic.getInstance().getStatus(_i, _j) == BoardLogic.cellStatus.RedScissors ||
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.RedFlag ||
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.Hole))
			{
				_BackArrowImgEnemy.drawImg(g);
			}
			if(_direction == ArrowDirections.Left &&
					(BoardLogic.getInstance().getStatus(_i, _j) == BoardLogic.cellStatus.RedPaper || 
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.RedRock ||
					BoardLogic.getInstance().getStatus(_i, _j) == BoardLogic.cellStatus.RedScissors ||
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.RedFlag ||
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.Hole))
			{
				_LeftArrowImgEnemy.drawImg(g);
			}
			if(_direction == ArrowDirections.Right &&
					(BoardLogic.getInstance().getStatus(_i, _j) == BoardLogic.cellStatus.RedPaper || 
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.RedRock ||
					BoardLogic.getInstance().getStatus(_i, _j) == BoardLogic.cellStatus.RedScissors ||
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.RedFlag ||
					BoardLogic.getInstance().getStatus(_i, _j) ==BoardLogic.cellStatus.Hole))
			{
				_RightArrowImgEnemy.drawImg(g);
			}
			//System.out.println("arrows : \n i = "+_i +" j = "+_j+""+_direction);

		}
	}
	public void addListener(CellClickListener toAdd) 
	{ 
		_listeners.add(toAdd);
	}
	public void FirstSetSoldier()
	{
		boolean flag = true;
		Img soldier;
		Random rand = new Random();
		int  n1 = rand.nextInt(3)+0;
		if (_i > 0&& _i < BoardPanel.gameH+1 && _j>0&&_j<BoardPanel.gameW+1)
		{
			//setCellStatus(cellStatus.Empty);
			BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.Empty);
			BoardLogic.getInstance().setStatusComBoard(_i, _j, BoardLogic.cellStatus.Empty);
			BoardLogic.getInstance().setStatusUserBoard(_i, _j, BoardLogic.cellStatus.Empty);
		}
		if( _i>=5 && _i<BoardPanel.gameH+1&& _j >=1 && _j<BoardPanel.gameW+1)
		{
			soldier = new Img("images\\emptyBluePlayer.png", 0, 0,  BoardPanel.width, BoardPanel.hight);
			setImgSoldier(soldier);
			//setCellStatus(Cell.cellStatus.EmptySoldier);
			BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.EmptySoldier);
			BoardLogic.getInstance().setStatusComBoard(_i, _j, BoardLogic.cellStatus.EmptySoldier);
		}
		/**
		 * enemy
		 */
		if(_i>=1 && _i<3 && _j>=1 &&_j< BoardPanel.gameW+1)
		{
			n = rand.nextInt(3)+0;
			if(n == 0)
			{
				soldier = new Img("images\\EnemySoldier.png", 0, 0,  BoardPanel.width, BoardPanel.hight);
				//setCellStatus(Cell.cellStatus.RedRock);
				BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedRock);
				//_board[i][j].setCellStatus(Cell.cellStatus.Red);
				/**
				 * set the second matrix (com matrix)
				 */
				BoardLogic.getInstance().setStatusComBoard(_i, _j, BoardLogic.cellStatus.RedRock);
				setImgSoldier(soldier);
			}
			else if(n==1)
			{
				soldier = new Img("images\\EnemySoldier.png", 0,0,  BoardPanel.width, BoardPanel.hight);
				//setCellStatus(Cell.cellStatus.RedPaper);
				BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedPaper);
				BoardLogic.getInstance().setStatusComBoard(_i, _j, BoardLogic.cellStatus.RedPaper);
				//_board[i][j].setCellStatus(Cell.cellStatus.Red);
				setImgSoldier(soldier);
			}
			else if(n == 2)
			{
				soldier = new Img("images\\EnemySoldier.png", 0, 0,  BoardPanel.width, BoardPanel.hight);
				//setCellStatus(Cell.cellStatus.RedScissors);
				BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedScissors);
				BoardLogic.getInstance().setStatusComBoard(_i, _j, BoardLogic.cellStatus.RedScissors);
				//_board[i][j].setCellStatus(Cell.cellStatus.Red);
				setImgSoldier(soldier);
			}
			System.out.print("  "+BoardLogic.getInstance().getStatus(_i, _j).toString());
			if(_j==7)
			{
				System.out.println("");
			}
			BoardLogic.getInstance().setStatusUserBoard(_i, _j, BoardLogic.cellStatus.EmptySoldier);
		}

		//





		//				  	if(n1 == 0 &&  BoardPanel._RedRockCount<4)
		//						{
		//							soldier = new Img("images\\EnemySoldier.png", 0, 0,  BoardPanel.width, BoardPanel.hight);
		//							//setCellStatus(BoardLogic.cellStatus.BlueRock);
		//							BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedRock);
		//							//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
		//							setImgSoldier(soldier);
		//							BoardPanel._RedRockCount++;
		//							//BoardLogic.getInstance().setStatus(_i, _j, getCellStatus());
		//						}
		//						else if(n1==1 &&  BoardPanel._RedPaperCount < 4)
		//						{
		//							soldier = new Img("images\\EnemySoldier.png", 0, 0, BoardPanel.width, BoardPanel.hight);
		//							//setCellStatus(Cell.cellStatus.BluePaper);
		//							BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedPaper);
		//							//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
		//							setImgSoldier(soldier);
		//							BoardPanel._RedPaperCount++;
		//						}
		//						else if(n1 == 2 &&  BoardPanel._RedScissorsCount< 4)
		//						{
		//							soldier = new Img("images\\EnemySoldier.png", 0, 0, BoardPanel.width, BoardPanel.hight);
		//							//setCellStatus(Cell.cellStatus.BlueScissors);
		//							BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedScissors);
		//							//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
		//							setImgSoldier(soldier);
		//							BoardPanel._RedScissorsCount++;
		//						}
		//		
		//						/**
		//						 *  
		//						 */
		//						else if((n1 == 0 && BoardPanel._RedRockCount == 4) ||(n1==1 &&BoardPanel._RedPaperCount == 4 )||(n1==2 && BoardPanel._RedScissorsCount == 4))
		//						{
		//							while(flag)
		//							{
		//								System.out.println("in while");
		//								n1 = rand.nextInt(3)+0;
		//								if(n1 == 0 &&  BoardPanel._RedRockCount<4)
		//								{
		//									soldier = new Img("images\\EnemySoldier.png", 0, 0,  BoardPanel.width, BoardPanel.hight);
		//								//	setCellStatus(Cell.cellStatus.BlueRock);
		//									BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedRock);
		//									//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
		//									setImgSoldier(soldier);
		//									BoardPanel._RedRockCount++;
		//									flag = false;
		//								}
		//								else if(n1==1 &&  BoardPanel._RedPaperCount < 4)
		//								{
		//									soldier = new Img("images\\EnemySoldier.png", 0, 0, BoardPanel.width, BoardPanel.hight);
		//									//setCellStatus(Cell.cellStatus.BluePaper);
		//									BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedPaper);
		//									//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
		//									setImgSoldier(soldier);
		//									BoardPanel._RedPaperCount++;
		//									flag = false;
		//								}
		//								else if(n1 == 2 &&  BoardPanel._RedScissorsCount< 4)
		//								{
		//									soldier = new Img("images\\EnemySoldier.png", 0, 0, BoardPanel.width, BoardPanel.hight);
		//			//						setCellStatus(Cell.cellStatus.BlueScissors);
		//									BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedScissors);
		//									//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
		//									setImgSoldier(soldier);
		//									BoardPanel._RedScissorsCount++;
		//									flag = false;
		//								}
		//							}
		//						}
	}




	//			soldier = new Img("images\\EnemySoldier.png", 0, 0,  BoardPanel.width, BoardPanel.hight);
	//			//setCellStatus(BoardLogic.cellStatus.BlueRock);
	//			BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedRock);
	//			BoardLogic.getInstance().setStatusComBoard(_i, _j, BoardLogic.cellStatus.RedRock);
	//			//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
	//			setImgSoldier(soldier);
	//			BoardPanel._RedRockCount++;

	//BoardLogic.getInstance().setStatus(_i, _j, getCellStatus());



	/**
	 * set flag Comp
	 */
	public void SetFlagComp()
	{
		//	setCellStatus(cellStatus.Flag);
		BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.RedFlag);
		BoardLogic.getInstance().setStatusComBoard(_i, _j, BoardLogic.cellStatus.RedFlag);
		//setImgSoldier(new Img ("images\\flag_char.png",0,0, BoardPanel.width, BoardPanel.hight));
		//repaint();
	}
	public void SetHoleComp()
	{
		//setCellStatus(cellStatus.Hole);
		BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.Hole);
		BoardLogic.getInstance().setStatusComBoard(_i, _j, BoardLogic.cellStatus.Hole);
		setImgSoldier(new Img ("images\\hole.png",0,0, BoardPanel.width, BoardPanel.hight));
		repaint();
	}
	/*
	/**
	 * Set Flag Player
	 */
	public void SetFlagPlayer()
	{
		//	setCellStatus(cellStatus.Flag);
		BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.BlueFlag);
		BoardLogic.getInstance().setStatusUserBoard(_i, _j, BoardLogic.cellStatus.BlueFlag);
		setImgSoldier(new Img ("images\\flag_char.png",0,0, BoardPanel.width, BoardPanel.hight));
		repaint();
	}
	/**
	 * Set Hole Player
	 */
	public void SetHolePlayer()
	{
		//setCellStatus(cellStatus.Hole);
		BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.Hole);
		BoardLogic.getInstance().setStatusUserBoard(_i, _j, BoardLogic.cellStatus.Hole);
		setImgSoldier(new Img ("images\\hole.png",0,0, BoardPanel.width, BoardPanel.hight));
		repaint();
	}
	/**
	 * set weapons
	 */
	public void SetWeapons()
	{
		Img soldier;
		Random rand = new Random();
		boolean flag = true;
		if (_i > 0&& _i < BoardPanel.gameH+1 && _j>0&&_j<BoardPanel.gameW+1)
		{
			//setCellStatus(cellStatus.Empty);
			BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.Empty);
		}
		n = rand.nextInt(3)+0;
		if( _i>=5 && _i<BoardPanel.gameH+1&& _j >=1 && _j<BoardPanel.gameW+1)
		{
			n = rand.nextInt(3)+0;
			//System.out.println("rock = "+ BoardPanel._BlueRockCount+" paper = "+BoardPanel._BluePaperCount+"scissors = "+ BoardPanel._BlueScissorsCount +"\n");
			if(n == 0 &&  BoardPanel._BlueRockCount<4)
			{
				soldier = new Img("images\\RockSoldier.png", 0, 0,  BoardPanel.width, BoardPanel.hight);
				//setCellStatus(BoardLogic.cellStatus.BlueRock);
				BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.BlueRock);
				BoardLogic.getInstance().setStatusUserBoard(_i, _j, BoardLogic.cellStatus.BlueRock);
				//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
				setImgSoldier(soldier);
				BoardPanel._BlueRockCount++;
				//BoardLogic.getInstance().setStatus(_i, _j, getCellStatus());
			}
			else if(n==1 &&  BoardPanel._BluePaperCount < 4)
			{
				soldier = new Img("images\\PaperSoldier.png", 0, 0, BoardPanel.width, BoardPanel.hight);
				//setCellStatus(Cell.cellStatus.BluePaper);
				BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.BluePaper);
				BoardLogic.getInstance().setStatusUserBoard(_i, _j, BoardLogic.cellStatus.BluePaper);
				//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
				setImgSoldier(soldier);
				BoardPanel._BluePaperCount++;
			}
			else if(n == 2 &&  BoardPanel._BlueScissorsCount< 4)
			{
				soldier = new Img("images\\ScissorsSoldier.png", 0, 0, BoardPanel.width, BoardPanel.hight);
				//setCellStatus(Cell.cellStatus.BlueScissors);
				BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.BlueScissors);
				BoardLogic.getInstance().setStatusUserBoard(_i, _j, BoardLogic.cellStatus.BlueScissors);
				//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
				setImgSoldier(soldier);
				BoardPanel._BlueScissorsCount++;
			}
			/**
			 *  
			 */
			else if((n == 0 && BoardPanel._BlueRockCount == 4) ||(n==1 &&BoardPanel._BluePaperCount == 4 )||(n==2 && BoardPanel._BlueScissorsCount == 4))
			{
				while(flag)
				{
					n = rand.nextInt(3)+0;
					if(n == 0 &&  BoardPanel._BlueRockCount<4)
					{
						soldier = new Img("images\\RockSoldier.png", 0, 0,  BoardPanel.width, BoardPanel.hight);
						//	setCellStatus(Cell.cellStatus.BlueRock);
						BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.BlueRock);
						BoardLogic.getInstance().setStatusUserBoard(_i, _j, BoardLogic.cellStatus.BlueRock);
						//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
						setImgSoldier(soldier);
						BoardPanel._BlueRockCount++;
						flag = false;
					}
					else if(n==1 &&  BoardPanel._BluePaperCount < 4)
					{
						soldier = new Img("images\\PaperSoldier.png", 0, 0, BoardPanel.width, BoardPanel.hight);
						//setCellStatus(Cell.cellStatus.BluePaper);
						BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.BluePaper);
						BoardLogic.getInstance().setStatusUserBoard(_i, _j, BoardLogic.cellStatus.BluePaper);
						//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
						setImgSoldier(soldier);
						BoardPanel._BluePaperCount++;
						flag = false;
					}
					else if(n == 2 &&  BoardPanel._BlueScissorsCount< 4)
					{
						soldier = new Img("images\\ScissorsSoldier.png", 0, 0, BoardPanel.width, BoardPanel.hight);
						//						setCellStatus(Cell.cellStatus.BlueScissors);
						BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.BlueScissors);
						BoardLogic.getInstance().setStatusUserBoard(_i, _j, BoardLogic.cellStatus.BlueScissors);
						//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
						setImgSoldier(soldier);
						BoardPanel._BlueScissorsCount++;
						flag = false;
					}
				}
			}
		}


		//			soldier = new Img("images\\ScissorsSoldier.png", 0, 0, BoardPanel.width, BoardPanel.hight);
		//			//setCellStatus(Cell.cellStatus.BlueScissors);
		//			BoardLogic.getInstance().setStatus(_i, _j, BoardLogic.cellStatus.BlueScissors);
		//			//_board[i][j].setCellStatus(Cell.cellStatus.Blue);
		//			setImgSoldier(soldier);
		//			BoardPanel._BlueScissorsCount++;
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		//		_t.start();
		//		b= true;


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
		for(int i = 0; i<_listeners.size();i++)
		{
			_listeners.get(i).CellClickListener(_i,_j);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		//		tCount++;
		//		repaint();
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		// TODO Auto-generated method stub

		//_mouseMotion.setImgCords(e.getX()+_i*BoardPanel.width, e.getY()+_j*BoardPanel.hight);
		//repaint();
	}

}
