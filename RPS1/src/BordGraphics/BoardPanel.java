package BordGraphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.channels.NetworkChannel;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.Timer;
import Logic.BoardLogic;
import Logic.BoardLogic.cellStatus;
import Logic.BoardLogic.fightResult;
public class BoardPanel extends JPanel implements CellClickListener, MouseMotionListener,ActionListener,MouseListener,Runnable,StopThreadListener
{
	private JButton _button;
	private Cell[][] _board;
	static int width = 120,hight = 120;
	static int gameW = 7, gameH = 6, startX = 300,startY = 0;
	//BoardGame g = new BoardGame();
	IJ indexSoldierClicked,ijHelperRedSoldier,ijHelperBlueSoldier;
	Vector<IJ> _arrowIJ;
	private Vector<IJ> vec;
	private boolean _isTie = true,_flagHelper = false, _continue = true,_wait = false;
	private Timer _timer;
	private int _i,_j;
	//private SoldierType directions;
	Thread t ;
	Img BackgroundBoard,leftGraphic,rightGraphic,background,_mouseMotion,_setFlagMasseg,_letStartButton,_changeButton,_holeMassege ;
	/**
	 * num of start clicks///////// action: what the action need to be done in paintC
	 */
	private fightResult _fightResultHelper ;
	public static int _numOfStartClicked = 0,_action = 0;
	private TieScreen _tieScreen;
	private Win _winScreen;
	public static boolean _startFlag = false,_isUserTurn = true,neutrelizeTrade = true;
	public static int _BlueRockCount = 0, _BluePaperCount = 0, _BlueScissorsCount = 0,_RedRockCount = 0, _RedPaperCount = 0, _RedScissorsCount = 0;
	//======================================================================================================================
	public BoardPanel() 
	{
		Random r= new Random();
		int n;
		//_mouseMotion = new Img("MouseMoutionIMG\\flag.png",0,0,120,120);
		//		_button = new JButton("Change Weapons");
		//		_button.addActionListener(this);
		//		add(_button,BorderLayout.CENTER);
		//		setVisible(true);
		//super();
		//setBackground(Color.BLACK);
		//repaint();
		t = new Thread(this);
		setLayout(null);
		setOpaque(false);
		int count =0;
		_board = new Cell[gameH+2][gameW+2];
		Img img;
		_tieScreen = new TieScreen(0,0,0,0,this);
		_winScreen = new Win (_isUserTurn);
		//_frame.setBackground(Color.BLACK);
		_arrowIJ = new Vector<IJ>();
		indexSoldierClicked = new IJ(0, 0);
		ijHelperRedSoldier = new IJ(0, 0);
		ijHelperBlueSoldier = new IJ(0, 0);
		//setLayout(new GridLayout(6, 7));
		/**
		 * creat the frame board
		 */
		//		BackgroundBoard = new Img("images\\board.png",width-50+startX, hight-40, (gameW+1)*width-25, (gameH+1)*hight -30) ;
		//		leftGraphic =  new Img("images\\leftGraphic.png",startX-100,hight, 200, (gameH+1)*hight -30) ;
		//		rightGraphic = new Img("images\\rightGraphic.png",(gameW+1)*width+40+startX,hight, 180, (gameH+1)*hight -30);
		//		background = new Img("images\\background.png",0,0,1800,2000);
		//_setFlagMasseg = new Img("Massage\\enterAFlag.png",350,300,1000,100);
		_letStartButton = new Img("Massage\\letsPlay.png",1500,400,256,114);
		_changeButton = new Img ("Massage\\sweach weapons.png",1500,200,256,114);
		_setFlagMasseg = new Img("Massage\\enterAFlag.png",450,0,750,100);
		_holeMassege = new Img ("Massage\\holeMassege.png",500,0,600,100);
		/**
		 * creat cells(created +2 rows and +2 cols because i dont want exaptions)
		 */
		for (int i = 0; i < gameH+2; i++)
		{
			for (int j = 0; j < gameW+2; j++)
			{
				_board[i][j] = new Cell(i,j,this);
				add(_board[i][j]);
				/**
				 * the help
				 */
				//				if(i == 0 || i == gameH+1 || j == 0 || j == gameW+1 )
				//				{
				//					_board[i][j].setSoldierType(null);
				//				}

			}
		}
		n = r.nextInt(8);
		_board[1][n].SetFlagComp();
		_timer = new Timer(10000,this );
		//_startFlag = true;
		//repaint();
		addMouseMotionListener(this);
		//setBounds(0, startY,BoardPanel.width* (gameH+1), BoardPanel.hight* (gameH+1));
		//g.add(this);
		//	addMouseMotionListener(this);
		addMouseListener(this);
		//	FirstSetSoldier();
		//		_frame.add(this);
		//		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		_frame.setSize(1800,2000);
		//		_frame.setVisible(true);
		t.start();
	}
	//======================================================================================================================
	//	public int getNumOfStartClicked() {
	//		return _numOfStartClicked;
	//	}
	//======================================================================================================================
	/**
	 * firstDraw Panel Game
	 */
	public void firstDrawPanel()
	{
		for (int i = 0; i < gameH+2; i++)
		{
			for (int j = 0; j < gameW+2; j++)
			{
				_board[i][j] = new Cell(i,j,this);
				add(_board[i][j]);
			}
		}
	}
	/**
	 * draw
	 */
	protected void paintComponent(Graphics g)
	{
		// TODO Auto-generated method stub

		//System.out.println("paint");
		/**
		 * paint the board
		 */
		super.paintComponent(g);
		/**
		 * paint the board squar
		 */
		if(_action == 0)
		{
			//		background.drawImg(g);
			//		leftGraphic.drawImg(g);
			//		BackgroundBoard.drawImg(g);
			//		rightGraphic.drawImg(g);
			//_action++;
		}
		if(_mouseMotion != null)
		{
			//_mouseMotion.drawImg(g);
		}
		if(_numOfStartClicked==0)
			_setFlagMasseg.drawImg(g);
		if(_numOfStartClicked==1)
		{
			_setFlagMasseg.setImg(null);
			_setFlagMasseg.setImgSize(0, 0);
			_setFlagMasseg.drawImg(g);
			_holeMassege.drawImg(g);
		}
		if(_numOfStartClicked>1)
		{
			_changeButton.drawImg(g);
			_letStartButton.drawImg(g);
		}


	}
	@Override
	public void CellClickListener(int i, int j)
	{
		// TODO Auto-generated method stub

		if(_numOfStartClicked == 0 && i>0 &&i<7&&j>0&&j<8 )
		{

			_board[i][j].SetFlagPlayer();
			_numOfStartClicked++;
			repaint();
		}
		else if(_numOfStartClicked == 1&& i>0 &&i<7&&j>0&&j<8)
		{

			_board[i][j].SetHolePlayer();

			_holeMassege.setImg(null);
			_holeMassege.setImgSize(0, 0);
			repaint();
			_numOfStartClicked++;
			for (int i2 = 5; i2 <= gameH; i2++)
			{
				for (int j2 = 1; j2 <= gameW; j2++)
				{
					if(BoardLogic.getInstance().getStatus(i2, j2) != BoardLogic.cellStatus.BlueFlag &&
							BoardLogic.getInstance().getStatus(i2, j2)!= BoardLogic.cellStatus.Hole)
						_board[i2][j2].SetWeapons();
					_board[i2][j2].repaint();

					//					if(_board[i2][j2].getCellStatus() != Cell.cellStatus.Flag &&_board[i2][j2].getCellStatus() != Cell.cellStatus.Hole)
					//						_board[i2][j2].SetWeapons();
					//					_board[i2][j2].repaint();
				}
			}
		}
		//else if(_board[i][j].isArrow()&&_startFlag)
		else if(_isUserTurn)
		{
			if(BoardLogic.getInstance().isLigalClick(i, j,_isUserTurn)&& _startFlag)
			{

				if(BoardLogic.getInstance().isLigalClickMove(i, j,indexSoldierClicked.getI(),indexSoldierClicked.getJ()))
				{
					//_continue = false;
					//System.out.println("last i = "+indexSoldierClicked.getI()+" last j"+indexSoldierClicked.getJ());
					MoveSoldier(i,j);
					//_isUserTurn = false;
				}
			}
			else if(_startFlag)
			{
				//System.out.println("else");
				removeArrowFromVertor();
				clickedSoldierMakeArrow(i, j);
				indexSoldierClicked.setI(i);
				indexSoldierClicked.setJ(j);
				//System.out.println(_arrowIJ.toString());
				_flagHelper = true;
			}
		}
		//		if(_wait)
		//		{
		//		while(!_continue)
		//		{
		//			System.err.println("yyyyyyyyy");
		//		}
		//		}
		//		if(!neutrelizeTrade)
		//		{
		//		try {
		//			this.wait();
		//		} catch (InterruptedException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		}
		//	
		//		//wait();
		//		System.err.println("_isUserTurn         "+_isUserTurn);
		if(!_isUserTurn && _continue)
		{
			_wait = false;
			//System.out.println("red soldier move");
			redSoldierMove();
			//_isUserTurn = true;
			
		}
		//System.err.println("liad");
//		for (int k = 1; k < gameH +2; k++)
//		{
//			for (int k2 = 1; k2 < gameW + 2; k2++) 
//			{
//				if(k2 == gameW+1 )
//					System.out.println("");
//				System.out.print(BoardLogic.getInstance().getStatusComBoard(k, k2)+ " ");
//
//
//			}
//		}
		//		
		//System.err.println("_isUserTurn         "+_isUserTurn);
	}
	/**
	 * Change Weapon For All Soldier
	 */
	public void ChangeWeaponForAllSoldier()
	{
		//System.out.println("yeaaaaaa");
		_BlueRockCount = 0;
		_BluePaperCount = 0;
		_BlueScissorsCount = 0;
		for (int i2 = 5; i2 <= gameH; i2++)
		{
			for (int j2 = 1; j2 <= gameW; j2++)
			{
				//if(_board[i2][j2].getCellStatus() != Cell.cellStatus.Flag &&_board[i2][j2].getCellStatus() != Cell.cellStatus.Hole)
				if(BoardLogic.getInstance().getStatus(i2, j2) != BoardLogic.cellStatus.BlueFlag &&
						BoardLogic.getInstance().getStatus(i2, j2)!= BoardLogic.cellStatus.Hole)
					_board[i2][j2].SetWeapons();
				_board[i2][j2].repaint();
			}
		}
	}
	//======================================================================================================================
	/**
	 * if clicked soldier open arrows
	 * @param i
	 * @param j
	 */
	public void clickedSoldierMakeArrow(int i,int j)
	{

		//		if(_board[i][j].getCellStatus() == Cell.cellStatus.BluePaper ||
		//				_board[i][j].getCellStatus() == Cell.cellStatus.BlueRock ||
		//				_board[i][j].getCellStatus() == Cell.cellStatus.BlueScissors)
		if(BoardLogic.getInstance().getStatus(i, j) == BoardLogic.cellStatus.BluePaper ||
				BoardLogic.getInstance().getStatus(i, j) == BoardLogic.cellStatus.BlueRock ||
				BoardLogic.getInstance().getStatus(i, j) == BoardLogic.cellStatus.BlueScissors)
		{
			//			if (_board[i-1][j].getCellStatus() == Cell.cellStatus.Empty )
			if(BoardLogic.getInstance().getStatus(i-1, j) == BoardLogic.cellStatus.Empty)
			{
				_board[i-1][j].setArrow(true);
				_arrowIJ.addElement(new IJ(i-1,j));
				_board[i-1][j].setDirection(Cell.ArrowDirections.Straight);
				_board[i-1][j].repaint();
			}
			//	if (_board[i+1][j].getCellStatus() == Cell.cellStatus.Empty)
			if(BoardLogic.getInstance().getStatus(i+1, j) == BoardLogic.cellStatus.Empty)
			{
				_board[i+1][j].setArrow(true);
				_arrowIJ.addElement(new IJ(i+1,j));
				_board[i+1][j].setDirection(Cell.ArrowDirections.Back);
				_board[i+1][j].repaint();
			}
			//	if (_board[i][j-1].getCellStatus() == Cell.cellStatus.Empty)
			if(BoardLogic.getInstance().getStatus(i, j-1) == BoardLogic.cellStatus.Empty)
			{
				_board[i][j-1].setArrow(true);
				_arrowIJ.addElement(new IJ(i,j-1));
				_board[i][j-1].setDirection(Cell.ArrowDirections.Left);
				//System.out.println("the exaption is i = "+i+" j = "+j);
				_board[i][j-1].repaint();
			}
			//	if (_board[i][j+1].getCellStatus() == Cell.cellStatus.Empty)
			if(BoardLogic.getInstance().getStatus(i, j+1) == BoardLogic.cellStatus.Empty)
			{
				_board[i][j+1].setArrow(true);
				_arrowIJ.addElement(new IJ(i,j+1));
				_board[i][j+1].setDirection(Cell.ArrowDirections.Right);
				//	System.out.println("the exaption is i = "+i+" j = "+j);
				_board[i][j+1].repaint();
			}
			/**
			 * enemy
			 */
			if(BoardLogic.getInstance().getStatus(i-1, j) == BoardLogic.cellStatus.RedPaper ||
					BoardLogic.getInstance().getStatus(i-1, j) == BoardLogic.cellStatus.RedRock ||
					BoardLogic.getInstance().getStatus(i-1, j) ==  BoardLogic.cellStatus.RedScissors||
					BoardLogic.getInstance().getStatus(i-1, j) ==  BoardLogic.cellStatus.RedFlag)
			{
				_board[i-1][j].setArrow(true);
				_arrowIJ.addElement(new IJ(i-1,j));
				_board[i-1][j].setDirection(Cell.ArrowDirections.Straight);
				_board[i-1][j].repaint();
			}
			//	if (_board[i+1][j].getCellStatus() == Cell.cellStatus.Empty)
			if(BoardLogic.getInstance().getStatus(i+1, j) == BoardLogic.cellStatus.RedPaper ||
					BoardLogic.getInstance().getStatus(i+1, j) == BoardLogic.cellStatus.RedRock ||
					BoardLogic.getInstance().getStatus(i+1, j) ==  BoardLogic.cellStatus.RedScissors||
					BoardLogic.getInstance().getStatus(i-1, j) ==  BoardLogic.cellStatus.RedFlag)
			{
				_board[i+1][j].setArrow(true);
				_arrowIJ.addElement(new IJ(i+1,j));
				_board[i+1][j].setDirection(Cell.ArrowDirections.Back);
				_board[i+1][j].repaint();
			}
			//	if (_board[i][j-1].getCellStatus() == Cell.cellStatus.Empty)
			if(BoardLogic.getInstance().getStatus(i, j-1) == BoardLogic.cellStatus.RedPaper ||
					BoardLogic.getInstance().getStatus(i, j-1) == BoardLogic.cellStatus.RedRock ||
					BoardLogic.getInstance().getStatus(i, j-1) ==  BoardLogic.cellStatus.RedScissors||
					BoardLogic.getInstance().getStatus(i-1, j) ==  BoardLogic.cellStatus.RedFlag)
			{
				_board[i][j-1].setArrow(true);
				_arrowIJ.addElement(new IJ(i,j-1));
				_board[i][j-1].setDirection(Cell.ArrowDirections.Left);
				//System.out.println("the exaption is i = "+i+" j = "+j);
				_board[i][j-1].repaint();
			}
			//	if (_board[i][j+1].getCellStatus() == Cell.cellStatus.Empty)
			if(BoardLogic.getInstance().getStatus(i, j+1) == BoardLogic.cellStatus.RedPaper ||
					BoardLogic.getInstance().getStatus(i, j+1) == BoardLogic.cellStatus.RedRock ||
					BoardLogic.getInstance().getStatus(i, j+1) ==  BoardLogic.cellStatus.RedScissors||
					BoardLogic.getInstance().getStatus(i-1, j) ==  BoardLogic.cellStatus.RedFlag)
			{
				_board[i][j+1].setArrow(true);
				_arrowIJ.addElement(new IJ(i,j+1));
				_board[i][j+1].setDirection(Cell.ArrowDirections.Right);
				//	System.out.println("the exaption is i = "+i+" j = "+j);
				_board[i][j+1].repaint();
			}
		}
		//System.out.println(_arrowIJ.toString());
		//repaint();
	}
	//======================================================================================================================
	/**
	 * red soldier move
	 */
	public void redSoldierMove()
	{
		boolean b = false;
		Img curSoldier = null;
		BoardLogic.cellStatus soldier = null;
		IJ compNextMoveArr[] = BoardLogic.getInstance().comNextMove();
		//System.out.println("last ij = "+BoardLogic.getInstance().getStatus(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ()).toString());
		//System.out.println("new ij = "+ BoardLogic.getInstance().getStatus(compNextMoveArr[1].getI(),compNextMoveArr[1].getJ()).toString());
		if(compNextMoveArr!=null)
		{
			if(BoardLogic.getInstance().getStatus(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ())== BoardLogic.cellStatus.RedPaper)
			{
				curSoldier = new Img("images\\EnemySoldier.png", 0, 0, width,hight);
				//	soldier = Cell.cellStatus.BlueRock;
				soldier = BoardLogic.cellStatus.RedPaper;
				//cellStatus = Cell.cellStatus.Red;
			}
			//else if(_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].getCellStatus() == Cell.cellStatus.BluePaper)
			else if(BoardLogic.getInstance().getStatus(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ())== BoardLogic.cellStatus.RedRock)
			{
				curSoldier =  new Img("images\\EnemySoldier.png",0,0, width,hight);
				//soldier = Cell.cellStatus.BluePaper;
				soldier = BoardLogic.cellStatus.RedRock;
				//cellStatus = Cell.cellStatus.Red;
			}
			//else if(_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].getCellStatus() == Cell.cellStatus.BlueScissors)
			else if(BoardLogic.getInstance().getStatus(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ())== BoardLogic.cellStatus.RedScissors)
			{
				curSoldier= new Img("images\\EnemySoldier.png", 0, 0, width,hight);
				//soldier = Cell.cellStatus.BlueScissors;
				soldier = BoardLogic.cellStatus.RedScissors;
				//cellStatus = Cell.cellStatus.Red;
			}
			/**
			 * CHECK LIGAL MOVE
			 */
			if(BoardLogic.getInstance().isLigalClick(compNextMoveArr[1].getI(), compNextMoveArr[1].getJ(),_isUserTurn) && 
					BoardLogic.getInstance().isLigalClickMove(compNextMoveArr[0].getI(), compNextMoveArr[0].getJ(),compNextMoveArr[1].getI(), compNextMoveArr[1].getJ()))
			{
				//System.out.println("_isUserTURn RED MOVE ::: "+ _isUserTurn);
				if(BoardLogic.getInstance().isWin)
				{
					System.out.println("you LOSE !!!!!!!!!!!!!!!!!!");
					_winScreen.setUserWin(_isUserTurn);
					_winScreen.open();
					b=true;
				}
				else if(BoardLogic.getInstance().getStatus(compNextMoveArr[1].getI(),compNextMoveArr[1].getJ())==cellStatus.Hole)
				{
					BoardLogic.getInstance().setStatus(compNextMoveArr[0].getI(), compNextMoveArr[0].getJ() ,cellStatus.Empty);
					BoardLogic.getInstance().setStatusComBoard(compNextMoveArr[0].getI(), compNextMoveArr[0].getJ(),cellStatus.Empty);
					BoardLogic.getInstance().setStatusUserBoard(compNextMoveArr[0].getI(), compNextMoveArr[0].getJ(),cellStatus.Empty);
					_board[compNextMoveArr[0].getI()][compNextMoveArr[0].getJ()].setImgSoldier(null);
					_board[compNextMoveArr[0].getI()][compNextMoveArr[0].getJ()].repaint();
					//BoardLogic.getInstance().setStatus(firstI,firstJ ,cellStatus.Empty);
					BoardLogic.getInstance().setStatusComBoard(compNextMoveArr[1].getI(),compNextMoveArr[1].getJ(),cellStatus.Hole);
					//BoardLogic.getInstance().setStatusUserBoard(firstI, firstJ,cellStatus.Empty);
				}
				else if(BoardLogic.getInstance().getStatus(compNextMoveArr[1].getI(),compNextMoveArr[1].getJ())==BoardLogic.cellStatus.Empty)
				{
					_board[compNextMoveArr[1].getI()][compNextMoveArr[1].getJ()].setImgSoldier(_board[compNextMoveArr[0].getI()][compNextMoveArr[0].getJ()].getImgSoldier());
					//_board[i][j].setCellStatus(soldier);
					//System.out.println("the soldier is"+soldier);
					BoardLogic.getInstance().setStatus(compNextMoveArr[1].getI(), compNextMoveArr[1].getJ(), soldier);
					BoardLogic.getInstance().setStatusComBoard(compNextMoveArr[1].getI(), compNextMoveArr[1].getJ(), soldier);
					if(BoardLogic.getInstance().getStatusUserBoard(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ()) != BoardLogic.cellStatus.EmptySoldier)
						BoardLogic.getInstance().setStatusUserBoard(compNextMoveArr[1].getI(), compNextMoveArr[1].getJ(),soldier);
					else
						BoardLogic.getInstance().setStatusUserBoard(compNextMoveArr[1].getI(), compNextMoveArr[1].getJ(),BoardLogic.cellStatus.EmptySoldier);

					_board[compNextMoveArr[1].getI()][compNextMoveArr[1].getJ()].repaint();
					//_board[i][j].setCellStatus(cellStatus);
					_board[compNextMoveArr[0].getI()][compNextMoveArr[0].getJ()].setImgSoldier(null);
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
					BoardLogic.getInstance().setStatus(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ() ,BoardLogic.cellStatus.Empty);
					BoardLogic.getInstance().setStatusComBoard(compNextMoveArr[0].getI(), compNextMoveArr[0].getJ(),BoardLogic.cellStatus.Empty);
					BoardLogic.getInstance().setStatusUserBoard(compNextMoveArr[0].getI(), compNextMoveArr[0].getJ(),BoardLogic.cellStatus.Empty);
					_board[compNextMoveArr[0].getI()][compNextMoveArr[0].getJ()].repaint();
					_board[compNextMoveArr[1].getI()][compNextMoveArr[1].getJ()].repaint();
					b = true;
					_isUserTurn = true;
				}
				if(!b)
				{

					//System.out.println("i: "+i+ "j:"+ j+"last i:" + indexSoldierClicked.getI()+"last j:"+indexSoldierClicked.getJ());
					/**
					 * 
					 */
					_fightResultHelper = BoardLogic.getInstance().fight(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ(),compNextMoveArr[1].getI(), compNextMoveArr[1].getJ());
					/**
					 * blue win
					 */
					if(_fightResultHelper==fightResult.BlueWin)
					{



						_board[compNextMoveArr[0].getI()][compNextMoveArr[0].getJ()].setImgSoldier(null);
						//System.out.println("last i:" + indexSoldierClicked.getI()+"last j:"+indexSoldierClicked.getJ());
						BoardLogic.getInstance().setStatus(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ() ,BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusComBoard(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ(),BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusUserBoard(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ(),BoardLogic.cellStatus.Empty);

						BoardLogic.getInstance().setStatusComBoard(compNextMoveArr[1].getI(),compNextMoveArr[1].getJ(),BoardLogic.getInstance().getStatus(compNextMoveArr[1].getI(),compNextMoveArr[1].getJ()));

						_board[compNextMoveArr[0].getI()][compNextMoveArr[0].getJ()].repaint();
						//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
						_board[compNextMoveArr[1].getI()][compNextMoveArr[1].getJ()].setImgSoldier(getImgAfterFight(compNextMoveArr[1].getI(),compNextMoveArr[1].getJ()));
						_board[compNextMoveArr[1].getI()][compNextMoveArr[1].getJ()].repaint();


						//System.out.println("asdsada");
						//				_board[_i][_j].setImgSoldier(null);/////////////////////////i,i
						//				//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
						//				//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
						//				_board[_i][_j].setImgSoldier(getImgAfterFight(indexSoldierClicked.getI(),indexSoldierClicked.getJ()));
						//				BoardLogic.getInstance().setStatus(_i,_j ,BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ()));
						//
						//				BoardLogic.getInstance().setStatusComBoard(i, j,BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ()));
						//				_board[_i][_j].repaint();
						//_isTie = false;
						_isUserTurn = true;
					}
					/**
					 * red Win
					 */
					else if(_fightResultHelper==fightResult.RedWin)
					{
						//				_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
						//				System.out.println("last i:" + indexSoldierClicked.getI()+"last j:"+indexSoldierClicked.getJ());
						//				BoardLogic.getInstance().setStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ() ,BoardLogic.cellStatus.Empty);
						//				BoardLogic.getInstance().setStatusComBoard(indexSoldierClicked.getI(),indexSoldierClicked.getJ(),BoardLogic.cellStatus.Empty);
						//
						//				_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].repaint();
						//				//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
						//				_board[_i][_j].setImgSoldier(getImgAfterFight(_i,_j));
						//				_board[_i][_j].repaint();
						//				//_isTie = false;


						_board[compNextMoveArr[0].getI()][compNextMoveArr[0].getJ()].setImgSoldier(null);
						//_board[compNextMoveArr[1].getI()][compNextMoveArr[1].getJ()].setImgSoldier(null);/////////////////////////i,i
						//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
						//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
						_board[compNextMoveArr[1].getI()][compNextMoveArr[1].getJ()].setImgSoldier(getImgAfterFight(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ()));
						BoardLogic.getInstance().setStatus(compNextMoveArr[1].getI(),compNextMoveArr[1].getJ() ,BoardLogic.getInstance().getStatus(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ()));
						BoardLogic.getInstance().setStatusComBoard(compNextMoveArr[1].getI(),compNextMoveArr[1].getJ(),BoardLogic.getInstance().getStatus(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ()));
						BoardLogic.getInstance().setStatusUserBoard(compNextMoveArr[1].getI(),compNextMoveArr[1].getJ(),BoardLogic.getInstance().getStatus(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ()));

						BoardLogic.getInstance().setStatus(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ() ,BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusUserBoard(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ(),BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusComBoard(compNextMoveArr[0].getI(),compNextMoveArr[0].getJ(),BoardLogic.cellStatus.Empty);
						_board[compNextMoveArr[1].getI()][compNextMoveArr[1].getJ()].repaint();
						_board[compNextMoveArr[0].getI()][compNextMoveArr[0].getJ()].repaint();
						_isUserTurn = true;
					}
					else if(_fightResultHelper==fightResult.Tie)
					{
						ijHelperRedSoldier.setI(compNextMoveArr[0].getI());
						ijHelperRedSoldier.setJ(compNextMoveArr[0].getJ());
						ijHelperBlueSoldier.setI(compNextMoveArr[1].getI());
						ijHelperBlueSoldier.setJ(compNextMoveArr[1].getJ());
						_tieScreen.open();
						//_timer.start();
						//				try {
						//					t.join();
						//				} catch (InterruptedException e){
						//					// TODO Auto-generated catch block
						//					e.printStackTrace();
						//				}
						//t.start();
						neutrelizeTrade = false;
						b=true;
						//System.out.println(BoardLogic.getInstance().getStatus(i, j).toString()+ "  " + BoardLogic.getInstance().getStatus(indexSoldierClicked.getI() , indexSoldierClicked.getJ()).toString());
						//					for (int k = 0; k < BoardLogic.length; k++) {
						//						System.out.print("  "+BoardLogic.getInstance().getStatus(k, _k).toString());
						//						if(_j==7)
						//						{
						//							System.out.println("");
						//						}
						//					}

						//	helpMove(i,j);
						//
						//System.out.println("blue i = "+indexSoldierClicked.getI()+"_blue J = "+indexSoldierClicked.getJ());
						//System.out.println("RED I = "+i+"RED J = "+j);
						//System.out.println(BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(), indexSoldierClicked.getJ()).toString()+ "  " + BoardLogic.getInstance().getStatus(i , j).toString());


					}

				}
			}
		}

		for (int k = 1; k < gameH +2; k++)
		{
			for (int k2 = 1; k2 < gameW + 2; k2++) 
			{
				if(k2 == gameW+1 )
					System.out.println("");
				System.out.print(BoardLogic.getInstance().getStatusComBoard(k, k2)+ " ");


			}
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		for (int k = 1; k < gameH +2; k++)
		{
			for (int k2 = 1; k2 < gameW + 2; k2++) 
			{
				if(k2 == gameW+1 )
					System.out.println("");
				System.out.print(BoardLogic.getInstance().getStatusUserBoard(k, k2)+ " ");


			}
		}

	}
	/**move blue soldier
	 * get cur i
	 * cur j
	 * indexSoldierClicked
	 * Move Soldier
	 * @param i
	 * @param j
	 * @param indexSoldierClicked
	 */
	public void MoveSoldier(int i,int j)
	{ 
		boolean b = false;
		/**
		 * current soldier
		 */
		_i = i;
		_j = j;
		Img curSoldier = null;
		//	SoldierType soldier = null;
		BoardLogic.cellStatus soldier = null;
		//		//if(_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].getCellStatus() == Cell.cellStatus.BlueRock)
		if(BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ())== BoardLogic.cellStatus.BlueRock)
		{
			curSoldier = new Img("images\\RockSoldier.png", 0, 0, width,hight);
			//	soldier = Cell.cellStatus.BlueRock;
			soldier = BoardLogic.cellStatus.BlueRock;
			//cellStatus = Cell.cellStatus.Red;
		}
		//else if(_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].getCellStatus() == Cell.cellStatus.BluePaper)
		else if(BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ())== BoardLogic.cellStatus.BluePaper)
		{
			curSoldier =  new Img("images\\PaperSoldier.png",0,0, width,hight);
			//soldier = Cell.cellStatus.BluePaper;
			soldier = BoardLogic.cellStatus.BluePaper;
			//cellStatus = Cell.cellStatus.Red;
		}
		//else if(_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].getCellStatus() == Cell.cellStatus.BlueScissors)
		else if(BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ())== BoardLogic.cellStatus.BlueScissors)
		{
			curSoldier= new Img("images\\ScissorsSoldier.png", 0, 0, width,hight);
			//soldier = Cell.cellStatus.BlueScissors;
			soldier = BoardLogic.cellStatus.BlueScissors;
			//cellStatus = Cell.cellStatus.Red;
		}
		//if (_board[i][j].isArrow())
		if(BoardLogic.getInstance().isLigalClick(i, j,_isUserTurn) && BoardLogic.getInstance().isLigalClickMove(i, j,indexSoldierClicked.getI(),indexSoldierClicked.getJ()))
		{
			System.err.println("isWin"+BoardLogic.getInstance().isWin);
			if(BoardLogic.getInstance().isWin)
			{
				System.out.println("you W  I  N !!!!!!!!!!!!!!!!!!");
				_winScreen.setUserWin(_isUserTurn);
				_winScreen.open();
				b=true;
			}
			else if(BoardLogic.getInstance().getStatus(i,j)==cellStatus.Hole)
			{
				BoardLogic.getInstance().setStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ() ,cellStatus.Empty);
				BoardLogic.getInstance().setStatusComBoard(indexSoldierClicked.getI(), indexSoldierClicked.getJ(),cellStatus.Empty);
				BoardLogic.getInstance().setStatusUserBoard(indexSoldierClicked.getI(), indexSoldierClicked.getJ(),cellStatus.Empty);
				_board[_i][_j].setImgSoldier(null);
				_board[_i][_j].repaint();
				//BoardLogic.getInstance().setStatus(firstI,firstJ ,cellStatus.Empty);
				//BoardLogic.getInstance().setStatusComBoard(secondI, secondJ,cellStatus.Hole);
				BoardLogic.getInstance().setStatusUserBoard(i, j,cellStatus.Hole);
			}
			else if(BoardLogic.getInstance().getStatus(i, j)==BoardLogic.cellStatus.Empty)
			{
				_board[i][j].setImgSoldier(_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].getImgSoldier());
				//_board[i][j].setCellStatus(soldier);
				BoardLogic.getInstance().setStatus(i, j, soldier);
				BoardLogic.getInstance().setStatusUserBoard(i, j,soldier);
				_board[i][j].repaint();
				if(BoardLogic.getInstance().getStatusComBoard(indexSoldierClicked.getI(),indexSoldierClicked.getJ()) != BoardLogic.cellStatus.EmptySoldier)
					BoardLogic.getInstance().setStatusComBoard(i, j,soldier);
				else
					BoardLogic.getInstance().setStatusComBoard(i, j,BoardLogic.cellStatus.EmptySoldier);
				//_board[i][j].setCellStatus(cellStatus);
				_continue =true;
				_isUserTurn = false;
			}
			else if(BoardLogic.getInstance().isLigalClickMove(i, j,indexSoldierClicked.getI(),indexSoldierClicked.getJ()))
			{
				/**
				 * blue win
				 */
				//System.out.println("i: "+i+ "j:"+ j+"last i:" + indexSoldierClicked.getI()+"last j:"+indexSoldierClicked.getJ());
				_fightResultHelper = BoardLogic.getInstance().fight(_i, _j,indexSoldierClicked.getI(),indexSoldierClicked.getJ());
				if(_fightResultHelper==fightResult.BlueWin)
				{
					//System.out.println("asdsada");
					_board[_i][_j].setImgSoldier(null);/////////////////////////i,i
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
					_board[_i][_j].setImgSoldier(getImgAfterFight(indexSoldierClicked.getI(),indexSoldierClicked.getJ()));
					BoardLogic.getInstance().setStatus(_i,_j ,BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ()));

					BoardLogic.getInstance().setStatusComBoard(_i, _j,BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ()));
					BoardLogic.getInstance().setStatusUserBoard(_i,_j ,BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ()));
					_board[_i][_j].repaint();
					//_isTie = false;
					_isUserTurn = false;
				}
				/**
				 * red Win
				 */
				else if(_fightResultHelper==fightResult.RedWin)
				{
					_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
					//System.out.println("last i:" + indexSoldierClicked.getI()+"last j:"+indexSoldierClicked.getJ());
					BoardLogic.getInstance().setStatusUserBoard(_i,_j ,BoardLogic.getInstance().getStatus(_i,_j));
					BoardLogic.getInstance().setStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ() ,BoardLogic.cellStatus.Empty);
					BoardLogic.getInstance().setStatusComBoard(indexSoldierClicked.getI(),indexSoldierClicked.getJ(),BoardLogic.cellStatus.Empty);
					BoardLogic.getInstance().setStatusUserBoard(indexSoldierClicked.getI(),indexSoldierClicked.getJ() ,BoardLogic.cellStatus.Empty);

					_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].repaint();
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
					_board[_i][_j].setImgSoldier(getImgAfterFight(_i,_j));
					_board[_i][_j].repaint();
					//_isTie = false;
					_isUserTurn = false;
				}
				else if(_fightResultHelper==fightResult.Tie)
				{
					ijHelperBlueSoldier.setI(indexSoldierClicked.getI());
					ijHelperBlueSoldier.setJ(indexSoldierClicked.getJ());
					ijHelperRedSoldier.setI(_i);
					ijHelperRedSoldier.setJ(_j);
					_tieScreen.open();
					//_timer.start();
					//t.start();
					//					try {
					//
					//						t.join();
					//					} catch (InterruptedException e){
					//						// TODO Auto-generated catch block
					//						e.printStackTrace();
					//					}
					//t.start();
					//System.out.println("neutrelizeTrade: "+neutrelizeTrade);
					neutrelizeTrade = false;
					//System.out.println("neutrelizeTrade: "+neutrelizeTrade);
					b=true;
					//System.out.println(BoardLogic.getInstance().getStatus(i, j).toString()+ "  " + BoardLogic.getInstance().getStatus(indexSoldierClicked.getI() , indexSoldierClicked.getJ()).toString());
					//					for (int k = 0; k < BoardLogic.length; k++) {
					//						System.out.print("  "+BoardLogic.getInstance().getStatus(k, _k).toString());
					//						if(_j==

					//7)
					//						{
					//							System.out.println("");
					//						}
					//					}

					//	helpMove(i,j);
					//
					//System.out.println("blue i = "+indexSoldierClicked.getI()+"_blue J = "+indexSoldierClicked.getJ());
					//System.out.println("RED I = "+i+"RED J = "+j);
					//System.out.println(BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(), indexSoldierClicked.getJ()).toString()+ "  " + BoardLogic.getInstance().getStatus(i , j).toString());


				}
			}
			//);
			if(!b)
			{
				//_board[_arrowIJ.get(0).getI()][_arrowIJ.get(0).getJ()].setArrow(false);
				_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
				//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
				//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
				BoardLogic.getInstance().setStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ() ,BoardLogic.cellStatus.Empty);
				BoardLogic.getInstance().setStatusComBoard(indexSoldierClicked.getI(), indexSoldierClicked.getJ(), BoardLogic.cellStatus.Empty);
				BoardLogic.getInstance().setStatusUserBoard(indexSoldierClicked.getI(), indexSoldierClicked.getJ(), BoardLogic.cellStatus.Empty);
				_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].repaint();
				_board[_i][_j].repaint();
				indexSoldierClicked.setI(-1);
				indexSoldierClicked.setJ(-1);
			}
			//			for (int k = 1; k < gameH+1; k++)
			//			{
			//				for (int k2 = 1; k2 < gameW+1; k2++) 
			//				{
			//					if(k2== 7)
			//						System.out.println(" ");
			//					else
			//						System.out.print("  "+BoardLogic.getInstance().getStatus(k, k2).toString());
			//				}
			//			}
		}

		removeArrowFromVertor();
	}
/**
 * get Img After Fight
 * 
 * @param i
 * @param j
 * @return
 */
	public Img getImgAfterFight(int i,int j)
	{
		/**
		 * blue
		 */
		if(BoardLogic.getInstance().getStatus(i,j) == BoardLogic.cellStatus.BluePaper)
		{
			return new Img("AfterFightImage\\fightPeper.png",0,0, width,hight);
		}
		if(BoardLogic.getInstance().getStatus(i,j) == BoardLogic.cellStatus.BlueRock)
		{
			return new Img("AfterFightImage\\fightRock.png",0,0, width,hight);
		}
		if(BoardLogic.getInstance().getStatus(i,j) == BoardLogic.cellStatus.BlueScissors)
		{
			return new Img("AfterFightImage\\fightScissor.png",0,0, width,hight);
		}
		/**
		 * red
		 */
		if(BoardLogic.getInstance().getStatus(i,j) == BoardLogic.cellStatus.RedPaper)
		{
			return new Img("AfterFightImage\\redFightPaper.png",0,0, width,hight);
		}
		if(BoardLogic.getInstance().getStatus(i,j) == BoardLogic.cellStatus.RedRock)
		{
			return new Img("AfterFightImage\\redFightRock.png",0,0, width,hight);
		}
		if(BoardLogic.getInstance().getStatus(i,j) == BoardLogic.cellStatus.RedScissors)
		{
			return new Img("AfterFightImage\\redFightScissor.png",0,0, width,hight);
		}
		return null;

	}
	//======================================================================================================================
	/**
	 * remove Arrow From Vertor and change to false
	 */
	public void removeArrowFromVertor()
	{
		for(int k =0;k<_arrowIJ.size();k++)
		{
			_board[_arrowIJ.get(k).getI()][_arrowIJ.get(k).getJ()].setArrow(false);
			_board[_arrowIJ.get(k).getI()][_arrowIJ.get(k).getJ()].repaint();
			//System.out.println(_arrowIJ.toString());
		}
		_arrowIJ.removeAllElements();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//		System.out.println("x =   "+e.getX()+"  y =   "+e.getY());
		//		_mouseMotion.setImgCords(e.getX(), e.getY()); 
		//		repaint();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

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
	/**
	 * check if click on the bottons 
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("x:    "+e.getX()+"y:   "+e.getY());
		if(!_startFlag)
		{
			if(e.getX()<1750 && e.getX()>1500
					&&e.getY()<300&& e.getY()>200)
			{
				///System.out.println("omo");
				this.ChangeWeaponForAllSoldier();
			}
			if(e.getX()<1750 && e.getX()>1500
					&&e.getY()<510&& e.getY()>412)
			{
				_changeButton.setImg(null);
				_changeButton.setImgSize(0, 0);
				_letStartButton.setImg(null);
				_letStartButton.setImgSize(0, 0);
				repaint();
				_startFlag = true;
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * runnable(check tie fight)
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println("run !!");   _i_j = red
		//System.out.println(_isTie);
		//	System.out.println(" "+_i+" "+_j+" "+BoardLogic.getInstance().getStatus(_i, _j) );
		//	System.out.println(" "+indexSoldierClicked.getI()+" "+indexSoldierClicked.getJ()+" "+BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(), indexSoldierClicked.getJ()) );
		//int n = 1;
		//System.out.println(BoardLogic.getInstance().fight(_i, _j,indexSoldierClicked.getI(),indexSoldierClicked.getJ()).toString());
		//System.out.println(neutrelizeTrade);
		while(_isTie)
		{
			//System.out.println(neutrelizeTrade);

			if(neutrelizeTrade == false)
			{
				//System.out.println(neutrelizeTrade);
				//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

				//System.out.println("_isUserTurn   "+_isUserTurn);
				_wait = true;
				/**
				 * blue win
				 */
				_continue = false;
				_fightResultHelper = BoardLogic.getInstance().fight(ijHelperRedSoldier.getI(), ijHelperRedSoldier.getJ(),ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ());
				if(_fightResultHelper==fightResult.BlueWin)
				{
					//System.out.println("asdsada");
					//				_board[_i][_j].setImgSoldier(null);/////////////////////////i,i
					//				_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
					//				//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
					//				_board[_i][_j].setImgSoldier(getImgAfterFight(_i,_j));
					//				BoardLogic.getInstance().setStatus(_i,_j ,BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ()));
					//				_board[_i][_j].repaint();
					//				_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].repaint();
					//				_isTie = false;
					//break;
					//System.out.println("asdsada");
					/////////////////////////i,i
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
					//BoardLogic.getInstance().setStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ() ,BoardLogic.cellStatus.Empty);
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
					/**
					 * is user turn
					 */
					if(_isUserTurn)
					{
						//_board[ijHelperRedSoldier.getI()][ijHelperRedSoldier.getJ()].setImgSoldier(null);
						_board[ijHelperRedSoldier.getI()][ijHelperRedSoldier.getJ()].setImgSoldier(getImgAfterFight(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ()));
						_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].setImgSoldier(null);
						BoardLogic.getInstance().setStatus(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ() ,BoardLogic.getInstance().getStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ()));
						BoardLogic.getInstance().setStatusUserBoard(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ() ,BoardLogic.getInstance().getStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ()));
						BoardLogic.getInstance().setStatusComBoard(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ() ,BoardLogic.getInstance().getStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ()));


						BoardLogic.getInstance().setStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusComBoard(ijHelperBlueSoldier.getI(), ijHelperBlueSoldier.getJ(), BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusUserBoard(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.cellStatus.Empty);

						_board[ijHelperRedSoldier.getI()][ijHelperRedSoldier.getJ()].repaint();

						//					_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].setImgSoldier(null);
						//					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
						//					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
						//					BoardLogic.getInstance().setStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.cellStatus.Empty);
						//					BoardLogic.getInstance().setStatusComBoard(ijHelperBlueSoldier.getI(), ijHelperBlueSoldier.getJ(), BoardLogic.cellStatus.Empty);
						//					_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].repaint();
						//_isTie = false;
						_isUserTurn = false;
						_continue = true;
						redSoldierMove();
					}
					else
					{
						_board[ijHelperRedSoldier.getI()][ijHelperRedSoldier.getJ()].setImgSoldier(null);
						_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].setImgSoldier(getImgAfterFight(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ()));
						//BoardLogic.getInstance().setStatus(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ() ,BoardLogic.getInstance().getStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ()));
						BoardLogic.getInstance().setStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.getInstance().getStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ()));
						BoardLogic.getInstance().setStatusComBoard(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.getInstance().getStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ()));
						BoardLogic.getInstance().setStatusUserBoard(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.getInstance().getStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ()));


						BoardLogic.getInstance().setStatus(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ() ,BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusComBoard(ijHelperRedSoldier.getI(), ijHelperRedSoldier.getJ(), BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusUserBoard(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ() ,BoardLogic.cellStatus.Empty);

						_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].repaint();
						_board[ijHelperRedSoldier.getI()][ijHelperRedSoldier.getJ()].repaint();
						//_isTie = false;
						//System.err.println("yardennnnnnnnnnnnnnnnnnnnnnnn");
						_isUserTurn = true;
					}
					neutrelizeTrade = true;
					_tieScreen.visibleFalse();
					//notify();
				}
				/**
				 * red Win
				 */
				else if(_fightResultHelper==fightResult.RedWin)
				{
					//				_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
					//				//BoardLogic.getInstance().setStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ() ,BoardLogic.cellStatus.Empty);
					//				_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].repaint();
					//				//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
					//				_board[_i][_j].setImgSoldier(getImgAfterFight(_i,_j));
					//				//BoardLogic.getInstance().setStatus(_i,_j ,BoardLogic.getInstance().getStatus(indexSoldierClicked.getI(),indexSoldierClicked.getJ()));
					//				_board[_i][_j].repaint();
					/**
					 * is user turn
					 */
					//System.out.println("yarden");
					if(_isUserTurn)
					{
						//System.out.println("yarden");
						BoardLogic.getInstance().setStatusUserBoard(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ() ,BoardLogic.getInstance().getStatus(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ()));
						_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].setImgSoldier(null);
						BoardLogic.getInstance().setStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusComBoard(ijHelperBlueSoldier.getI(), ijHelperBlueSoldier.getJ(), BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusUserBoard(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.cellStatus.Empty);

						_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].repaint();
						//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
						_board[ijHelperRedSoldier.getI()][ijHelperRedSoldier.getJ()].setImgSoldier(getImgAfterFight(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ()));
						_board[ijHelperRedSoldier.getI()][ijHelperRedSoldier.getJ()].repaint();
						_isUserTurn = false;
						_continue = true;
						redSoldierMove();
					}
					else
					{
						//System.out.println("yardeniiiiiiiiiiii");
						_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].setImgSoldier(getImgAfterFight(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ()));
						_board[ijHelperRedSoldier.getI()][ijHelperRedSoldier.getJ()].setImgSoldier(null);


						BoardLogic.getInstance().setStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.getInstance().getStatus(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ()));
						BoardLogic.getInstance().setStatusComBoard(ijHelperBlueSoldier.getI(), ijHelperBlueSoldier.getJ(), BoardLogic.getInstance().getStatus(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ()));
						BoardLogic.getInstance().setStatusUserBoard(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.getInstance().getStatus(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ()));


						BoardLogic.getInstance().setStatus(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ() ,BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusComBoard(ijHelperRedSoldier.getI(), ijHelperRedSoldier.getJ(), BoardLogic.cellStatus.Empty);
						BoardLogic.getInstance().setStatusUserBoard(ijHelperRedSoldier.getI(),ijHelperRedSoldier.getJ() ,BoardLogic.cellStatus.Empty);

						_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].repaint();
						//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);

						_board[ijHelperRedSoldier.getI()][ijHelperRedSoldier.getJ()].repaint();
						//System.out.println(" red status : \n i: "+ijHelperRedSoldier.getI()+" \n j:"+ijHelperRedSoldier.getJ()+" status:  "+	BoardLogic.getInstance().getStatus(ijHelperRedSoldier.getI(), ijHelperRedSoldier.getJ()));
						//	System.out.println(" blue status : "+ijHelperBlueSoldier.getI()+" \n j:"+ijHelperBlueSoldier.getJ()+" status:  "+	BoardLogic.getInstance().getStatus(ijHelperBlueSoldier.getI(), ijHelperBlueSoldier.getJ()));
						_isUserTurn = true;
					}
					neutrelizeTrade = true;
					_tieScreen.visibleFalse();
					//notify();
					//_isTie = false;
					//	break;
				}
				else if(_fightResultHelper==fightResult.Tie)
				{
					//System.out.println("i:"+_i+"j:"+_j);
					//System.out.println("**** i: "+indexSoldierClicked.getI()+" ***j:  "+indexSoldierClicked.getJ());
					_tieScreen.setRedI(ijHelperRedSoldier.getI());
					_tieScreen.setRedJ(ijHelperRedSoldier.getJ());
					_tieScreen.setBlueI(ijHelperBlueSoldier.getI());
					_tieScreen.setBlueJ(ijHelperBlueSoldier.getJ());

				}

				//System.out.println("while is running");
				//				if(!_isUserTurn)
				//					{
				//				_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].setImgSoldier(null);
				//				//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
				//				//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
				//				BoardLogic.getInstance().setStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.cellStatus.Empty);
				//				BoardLogic.getInstance().setStatusComBoard(ijHelperBlueSoldier.getI(), ijHelperBlueSoldier.getJ(), BoardLogic.cellStatus.Empty);
				//				_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].repaint();
				//					}
				//				else
				//				{
				//					_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].setImgSoldier(null);
				//					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
				//					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
				//					BoardLogic.getInstance().setStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.cellStatus.Empty);
				//					BoardLogic.getInstance().setStatusComBoard(ijHelperBlueSoldier.getI(), ijHelperBlueSoldier.getJ(), BoardLogic.cellStatus.Empty);
				//					_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].repaint();
				//				}
			}
			//if(n==0) {
			//System.out.println(_isTie);
			//_board[_arrowIJ.get(0).getI()][_arrowIJ.get(0).getJ()].setArrow(false);
			//			_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].setImgSoldier(null);
			//			//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
			//			//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
			//			BoardLogic.getInstance().setStatus(ijHelperBlueSoldier.getI(),ijHelperBlueSoldier.getJ() ,BoardLogic.cellStatus.Empty);
			//			BoardLogic.getInstance().setStatusComBoard(ijHelperBlueSoldier.getI(), ijHelperBlueSoldier.getJ(), BoardLogic.cellStatus.Empty);
			/**
			 * timtum
			 */
			//	System.out.println(_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].getImgSoldier().toString());
			_board[ijHelperBlueSoldier.getI()][ijHelperBlueSoldier.getJ()].repaint();
		}
		//System.out.println(_isTie);
		//_isTie = true;
		//System.out.println(BoardLogic.getInstance().fight(_i, _j,indexSoldierClicked.getI(),indexSoldierClicked.getJ()).toString());
		//t = null;
		//_tieScreen.visibleFalse();
		//n =1;
		//}


	}

	//@SuppressWarnings("deprecation")
	@Override
	public void StopThread() {
		// TODO Auto-generated method stub

		t.interrupt();

	}
}
