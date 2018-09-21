package Logic;

import java.awt.List;
import java.awt.Point;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import BordGraphics.IJ;
import BordGraphics.Img;
import Logic.BoardLogic.fightResult;

public class BoardLogic
{
	private final static BoardLogic INSTANCE = new BoardLogic();
	public static enum cellStatus{RedRock, BlueRock,RedPaper,BluePaper,RedScissors,BlueScissors,Empty,BlueFlag,Hole,EmptySoldier,RedFlag};
	public static enum fightResult{RedWin,BlueWin,Tie};
	private fightResult _fightResult,_fightResultHelper;
	private cellStatus _cellStatus;
	private cellStatus [][] _mat,_helpMat;
	private int _gameH = 10,_gameW = 9;
	private LinkedList<IJ> _listIJOptimelRedSoldierMove,_listIJOptimelRedSoldierNextMove;
	private LinkedList<saveMoves>_listSaveMoves;
	private cellStatus [][] _comSeeBord,_helpComSeeBoard;
	private cellStatus [][] _userSeeBord,_helpUserSeeBoard;
	private final int H = 6;
	private final int W = 7;
	public boolean isWin = false , isHole = false,_isTie = false;
	private final long Infinity=50000000; // ציון 
	private int HeuristicSize = 14;
	private Point [][]movementCordMat;
	public static int _maxDepth = 2;
	int movementIndex=0;

	private BoardLogic()
	{
		_mat=new cellStatus[_gameH][_gameW];
		_comSeeBord = new cellStatus[_gameH][_gameW];
		_userSeeBord = new cellStatus[_gameH][_gameW];
		_helpComSeeBoard = new cellStatus[_gameH][_gameW];
		_helpMat = new cellStatus[_gameH][_gameW];
		_helpUserSeeBoard = new cellStatus[_gameH][_gameW];
		_listIJOptimelRedSoldierMove = new LinkedList<>();
		_listIJOptimelRedSoldierNextMove = new LinkedList<>();
		_listSaveMoves = new LinkedList<>();
		movementCordMat=new Point[100][2];
		buildMovementMat();
		for(int i=0;i<movementIndex;i++)
		{
			for(int j=0;j<2;j++)
			{
				System.out.print(movementCordMat[i][j].y+","+movementCordMat[i][j].x+":");
			}
			System.out.println();
		}
		System.out.println("max "+movementIndex);

	}
	private void buildMovementMat()
	{
		getLines();
		getCols();
	}

	private void getLines()
	{
		int offsetY,offsetX,i,j;
		for(i=0;i<H*(W-2+1);i++)
		{
			offsetY=i/(W-2+1);
			offsetX=i%(W-2+1);
			for(j=0;j<2;j++)
			{
				movementCordMat[movementIndex][j]=new Point(offsetX+j+1,offsetY+1);

			}
			movementIndex++;
			for(j=0;j<2;j++)
			{
				movementCordMat[movementIndex][j]=new Point(offsetX+j+1,offsetY+1);

			}
		}
	}
	private void getCols()
	{
		int offsetY,offsetX,i,j;
		for(i=0;i<(H-2+1)*W;i++)
		{
			offsetY=i/W;
			offsetX=i%W;
			for(j=0;j<2;j++)
			{
				movementCordMat[movementIndex][j]=new Point(offsetX+1,offsetY+j+1);
			}
			movementIndex++;
		}
	}
	public static BoardLogic getInstance() {
		return INSTANCE;
	}
	public void setStatus(int i,int j,cellStatus cellStatus)
	{
		_mat[i][j] = cellStatus;
	}
	public cellStatus getStatus(int i,int j)
	{
		return _mat[i][j];
	}
	public void setStatusComBoard(int i,int j,cellStatus cellStatus)
	{
		_comSeeBord[i][j] = cellStatus;
	}
	public cellStatus getStatusComBoard(int i,int j)
	{
		return _comSeeBord[i][j];
	}
	public void setStatusUserBoard(int i,int j,cellStatus cellStatus)
	{
		_userSeeBord[i][j] = cellStatus;
	}
	public cellStatus getStatusUserBoard(int i,int j)
	{
		return _userSeeBord[i][j];
	}
	public boolean isLigaFirstlClick(int i,int j,boolean isBlueTurn)
	{

		//		if((_mat[i][j] == _cellStatus.Hole && isBlueTurn && i<4) ||
		//				(_mat[i][j] == _cellStatus.Hole && !isBlueTurn && i>4)	)
		//		{
		//			return true;
		//		}
		/**
		 * computer turn
		 */
		if(_mat[i][j] == cellStatus.RedPaper &&  !isBlueTurn)
		{
			return true;
		}
		if(_mat[i][j] == _cellStatus.RedRock &&  !isBlueTurn)
		{
			return true;
		}
		if(_mat[i][j] == _cellStatus.RedScissors && !isBlueTurn)
		{
			return true;
		}
		/**
		 *  user turn
		 */
		if(_mat[i][j] == cellStatus.BluePaper &&  isBlueTurn)
		{
			return true;
		}
		if(_mat[i][j] == _cellStatus.BlueRock &&  isBlueTurn)
		{
			return true;
		}
		if(_mat[i][j] == _cellStatus.BlueScissors && isBlueTurn)
		{
			return true;
		}
		return false;
	}
	/**
	 *  check if the click is legal
	 * @param i
	 * @param j
	 * @param isBlueTurn
	 * @return
	 */
	public boolean isLigalClick(int i,int j,boolean isBlueTurn)
	{
		if(_mat[i][j] == _cellStatus.BlueFlag && !isBlueTurn)
		{
			isWin = true;
			return true;
		}
		if(_mat[i][j] == _cellStatus.RedFlag && isBlueTurn)
		{
			System.err.println("yardeni tabilisi");
			isWin = true;
			return true;
		}
		if(_mat[i][j] == _cellStatus.Empty)
		{
			return true;
		}
		//		if((_mat[i][j] == _cellStatus.Hole && isBlueTurn && i<4) ||
		//				(_mat[i][j] == _cellStatus.Hole && !isBlueTurn && i>4)	)
		//		{
		//			return true;
		//		}
		/**
		 * user turn
		 */
		if(_mat[i][j] == cellStatus.RedPaper &&  isBlueTurn)
		{
			return true;
		}
		if(_mat[i][j] == _cellStatus.RedRock &&  isBlueTurn)
		{
			return true;
		}
		if(_mat[i][j] == _cellStatus.RedScissors &&  isBlueTurn)
		{
			return true;
		}
		/**
		 *  compute turn
		 */
		if(_mat[i][j] == cellStatus.BluePaper &&  !isBlueTurn)
		{
			return true;
		}
		if(_mat[i][j] == _cellStatus.BlueRock &&  !isBlueTurn)
		{
			return true;
		}
		if(_mat[i][j] == _cellStatus.BlueScissors &&  !isBlueTurn)
		{
			return true;
		}
		return false;
	}
	/**
	 * add to list the IJ that the soldier can move
	 * @param i
	 * @param j
	 */
	public void addListCellsRedSoldierCanMove1(int i,int j)
	{
		if(_mat[i-1][j] == _cellStatus.Empty ||_comSeeBord[i-1][j] == _cellStatus.EmptySoldier)
		{
			_listIJOptimelRedSoldierNextMove.add(new IJ(i-1,j));
		}
		if(_mat[i][j-1] ==  _cellStatus.Empty || _comSeeBord[i][j-1] == _cellStatus.EmptySoldier)
		{
			_listIJOptimelRedSoldierNextMove.add(new IJ(i,j-1));
		}
		if(_mat[i+1][j] ==  _cellStatus.Empty || _comSeeBord[i+1][j] == _cellStatus.EmptySoldier)
		{
			_listIJOptimelRedSoldierNextMove.add(new IJ(i+1,j));
		}
		if(_mat[i][j+1] == _cellStatus.Empty|| _comSeeBord[i][j+1] == _cellStatus.EmptySoldier)
		{
			_listIJOptimelRedSoldierNextMove.add(new IJ(i,j+1));
		}
	}
	public void addListCellsRedSoldierCanMove(int i,int j)
	{
		if(_mat[i-1][j] == _cellStatus.Empty )
		{
			_listIJOptimelRedSoldierNextMove.add(new IJ(i-1,j));
		}
		if(_mat[i][j-1] ==  _cellStatus.Empty )
		{
			_listIJOptimelRedSoldierNextMove.add(new IJ(i,j-1));
		}
		if(_mat[i+1][j] ==  _cellStatus.Empty)
		{
			_listIJOptimelRedSoldierNextMove.add(new IJ(i+1,j));
		}
		if(_mat[i][j+1] == _cellStatus.Empty)
		{
			_listIJOptimelRedSoldierNextMove.add(new IJ(i,j+1));
		}
	}
	public boolean checkIfRedSoldierCanMove1(int i,int j)
	{

		if(_comSeeBord[i-1][j] == _cellStatus.Empty || _comSeeBord[i-1][j] == _cellStatus.EmptySoldier)
		{
			return true;
		}
		if(_comSeeBord[i][j-1] ==  _cellStatus.Empty|| _comSeeBord[i][j-1] == _cellStatus.EmptySoldier)
		{
			return true;
		}
		if(_comSeeBord[i+1][j] ==  _cellStatus.Empty|| _comSeeBord[i+1][j] == _cellStatus.EmptySoldier)
		{
			return true;
		}
		if(_comSeeBord[i][j+1] == _cellStatus.Empty|| _comSeeBord[i][j+1] == _cellStatus.EmptySoldier)
		{
			return true;
		}
		return false;
	}
	public boolean checkIfSoldierCanFight1(int i,int j)
	{

		if(_comSeeBord[i-1][j] == _cellStatus.EmptySoldier)
		{
			return true;
		}
		if( _comSeeBord[i][j-1] == _cellStatus.EmptySoldier)
		{
			return true;
		}
		if( _comSeeBord[i+1][j] == _cellStatus.EmptySoldier)
		{
			return true;
		}
		if(_comSeeBord[i][j+1] == _cellStatus.EmptySoldier)
		{
			return true;
		}
		return false;
	}
	/**check If Soldier Can Fight Empty Soldier
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean checkIfBlueSoldierCanFight(int i,int j)
	{

		if(_helpUserSeeBoard[i][j] == _cellStatus.EmptySoldier)
		{
			return true;
		}
		return false;
	}
	public boolean checkIfRedSoldierCanFight(int i,int j)
	{

		if(_helpComSeeBoard[i][j] == _cellStatus.EmptySoldier)
		{
			return true;
		}
		return false;
	}
	public boolean checkIfSoldierCanMove1(int i,int j)
	{

		if(_comSeeBord[i-1][j] == _cellStatus.Empty )
		{
			return true;
		}
		if(_comSeeBord[i][j-1] ==  _cellStatus.Empty)
		{
			return true;
		}
		if(_comSeeBord[i+1][j] ==  _cellStatus.Empty)
		{
			return true;
		}
		if(_comSeeBord[i][j+1] == _cellStatus.Empty)
		{
			return true;
		}
		return false;
	}
	/**
	 * check If Soldier Can Move to empty cell
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean checkIfRedSoldierCanMove(int i,int j)
	{

		if(_helpComSeeBoard[i][j] == _cellStatus.Empty )
		{
			return true;
		}

		return false;
	}
	public boolean checkIfRedSoldierCanMoveForward(int i,int j,int newI,int newJ)
	{

		if(_helpComSeeBoard[newI][newJ] == _cellStatus.Empty && newI - 1 == i&& j == newJ)
		{
			return true;
		}

		return false;
	}
	public boolean checkIfBlueSoldierCanMoveForward(int i,int j,int newI,int newJ)
	{

		if(_helpUserSeeBoard[newI][newJ] == _cellStatus.Empty && newI + 1 == i&& j == newJ)
		{
			return true;
		}

		return false;
	}
	public boolean checkIfBlueSoldierCanMove(int i,int j)
	{

		if(_helpUserSeeBoard[i][j] == _cellStatus.Empty )
		{
			return true;
		}

		return false;
	}
	public boolean checkIfRedSoldierCanWin1(int i,int j)
	{
		if(_comSeeBord[i][j] == _cellStatus.RedPaper)
		{
			if(_comSeeBord[i-1][j] == _cellStatus.BlueRock ||
					_comSeeBord[i][j-1] == _cellStatus.BlueRock ||
					_comSeeBord[i+1][j] == _cellStatus.BlueRock ||
					_comSeeBord[i][j+1] == _cellStatus.BlueRock)
				return true;
		}
		else if(_comSeeBord[i][j] == _cellStatus.RedRock)
		{
			if(_comSeeBord[i-1][j] == _cellStatus.BlueScissors ||
					_comSeeBord[i][j-1] == _cellStatus.BlueScissors ||
					_comSeeBord[i+1][j] == _cellStatus.BlueScissors ||
					_comSeeBord[i][j+1] == _cellStatus.BlueScissors)
				return true;
		}
		else if(_comSeeBord[i][j] == _cellStatus.RedScissors)
		{
			if(_comSeeBord[i-1][j] == _cellStatus.BluePaper ||
					_comSeeBord[i][j-1] == _cellStatus.BluePaper ||
					_comSeeBord[i+1][j] == _cellStatus.BluePaper ||
					_comSeeBord[i][j+1] == _cellStatus.BluePaper)
				return true;
		}
		return false;
	}
	public boolean checkIfRedSoldierCanWin(int i,int j,int newI,int newJ)
	{
		if(_helpComSeeBoard[i][j] == _cellStatus.RedPaper)
		{
			if(_helpComSeeBoard[newI][newJ] == _cellStatus.BlueRock )
				return true;
		}
		else if(_helpComSeeBoard[i][j] == _cellStatus.RedRock)
		{
			if(_helpComSeeBoard[newI][newJ] == _cellStatus.BlueScissors)
				return true;
		}
		else if(_helpComSeeBoard[i][j] == _cellStatus.RedScissors)
		{
			if(_helpComSeeBoard[newI][newJ] == _cellStatus.BluePaper )
				return true;
		}
		return false;
	}
	public void addOptimalRedSoldierToList()
	{
		for (int i = 1; i < _gameH-1; i++)
		{
			for (int j = 1; j < _gameW-1; j++)
			{
				if((_mat[i][j] == _cellStatus.RedPaper ||
						_mat[i][j] == _cellStatus.RedRock ||
						_mat[i][j] == _cellStatus.RedScissors ) &&
						checkIfRedSoldierCanMove1(i,j))
					_listIJOptimelRedSoldierMove.add(new IJ (i,j));

			}
		}
	}
	public boolean checkIfBlueSoldierCanWin1(int i,int j)
	{
		if(_userSeeBord[i][j] == _cellStatus.BluePaper)
		{
			if(_userSeeBord[i-1][j] == _cellStatus.RedRock ||
					_userSeeBord[i][j-1] == _cellStatus.RedRock ||
					_userSeeBord[i+1][j] == _cellStatus.RedRock ||
					_userSeeBord[i][j+1] == _cellStatus.RedRock)
				return true;
		}
		else if(_userSeeBord[i][j] == _cellStatus.BlueRock)
		{
			if(_userSeeBord[i-1][j] == _cellStatus.RedScissors ||
					_userSeeBord[i][j-1] == _cellStatus.RedScissors ||
					_userSeeBord[i+1][j] == _cellStatus.RedScissors ||
					_userSeeBord[i][j+1] == _cellStatus.RedScissors)
				return true;
		}
		else if(_userSeeBord[i][j] == _cellStatus.BlueScissors)
		{
			if(_userSeeBord[i-1][j] == _cellStatus.RedPaper ||
					_userSeeBord[i][j-1] == _cellStatus.RedPaper ||
					_userSeeBord[i+1][j] == _cellStatus.RedPaper ||
					_userSeeBord[i][j+1] == _cellStatus.RedPaper)
				return true;
		}
		return false;
	}
	public boolean checkIfBlueSoldierCanWin(int i,int j,int newI,int newJ)
	{
		if(_helpUserSeeBoard[i][j] == _cellStatus.BluePaper)
		{
			if(_helpUserSeeBoard[newI][newJ] == _cellStatus.RedRock )
				return true;
		}
		else if(_helpUserSeeBoard[i][j] == _cellStatus.BlueRock)
		{
			if(_helpUserSeeBoard[newI][newJ] == _cellStatus.RedScissors )

				return true;
		}
		else if(_helpUserSeeBoard[i][j] == _cellStatus.BlueScissors)
		{
			if(_helpUserSeeBoard[newI][newJ] == _cellStatus.RedPaper )
				return true;
		}
		return false;
	}
	//	public void addOptimalRedSoldierToList()
	//	{
	//		for (int i = 1; i < _gameH-1; i++)
	//		{
	//			for (int j = 1; j < _gameW-1; j++)
	//			{
	//				if((_mat[i][j] == _cellStatus.RedPaper ||
	//						_mat[i][j] == _cellStatus.RedRock ||
	//						_mat[i][j] == _cellStatus.RedScissors ) &&
	//						checkIfRedSoldierCanMove(i,j))
	//					_listIJOptimelRedSoldierMove.add(new IJ (i,j));
	//
	//			}
	//		}
	//	}
	public IJ randomIJFromList(LinkedList<IJ> list)
	{
		Random r = new Random();
		int n = r.nextInt(list.size());
		System.out.println("list size: " + list.size());
		System.out.println("the list is:"+list.toString());
		return list.get(n);
	}
	/**
	 * return red soldier that need to move
	 * @return
	 */
	public IJ randomSoldierMove()
	{
		IJ randomSoldierMove,randomSoldierNextMove;
		addOptimalRedSoldierToList();
		if(!_listIJOptimelRedSoldierMove.isEmpty())
		{
			randomSoldierMove = randomIJFromList(_listIJOptimelRedSoldierMove);
			System.out.println("randomSoldierMove: "+randomSoldierMove);
			return randomSoldierMove;
		}
		return new IJ(0,0);

	}
	/**
	 * arr[0] = soldier before the move;
	 * arr[1] = next cell soldier going to move
	 * @return
	 */
	public IJ[] comNextMove1()
	{
		IJ randomSoldierMove,randomSoldierNextMove;
		IJ arrIJ[] = new IJ[2];
		randomSoldierMove = randomSoldierMove();
		System.out.println(randomSoldierMove.getI() + " " + randomSoldierMove.getJ());
		if(randomSoldierMove.getI() !=0 && randomSoldierMove.getJ() != 0)
		{
			addListCellsRedSoldierCanMove1(randomSoldierMove.getI(),randomSoldierMove.getJ());
			randomSoldierNextMove = randomIJFromList(_listIJOptimelRedSoldierNextMove);
			/**
			 * 
			 */
			arrIJ[0] = randomSoldierMove;
			arrIJ[1] = randomSoldierNextMove;
			_listIJOptimelRedSoldierNextMove.removeAll(_listIJOptimelRedSoldierNextMove);
			_listIJOptimelRedSoldierMove.removeAll(_listIJOptimelRedSoldierMove);

			return arrIJ;
		}
		return null;
	}
	public IJ[] comNextMove()
	{
		Score calcBestMove;

		IJ arrIJ[] = new IJ[2];
		calcBestMove = calcBestMove();
		System.out.println("i:  "+ calcBestMove.getI()+" j: "+calcBestMove.getJ()+" \n NEW I :   "+calcBestMove.getNewI()+"NEW J :  "+ calcBestMove.getNewJ());
		arrIJ[0] = new IJ(calcBestMove.getI(),calcBestMove.getJ());
		arrIJ[1] = new IJ(calcBestMove.getNewI(),calcBestMove.getNewJ());
		return arrIJ;
	}
	public void copyMatrix()
	{
		for (int i = 0; i < _gameH; i++)
		{
			for (int j = 0; j < _gameW; j++)
			{
				_helpMat[i][j] = _mat[i][j];
				_helpComSeeBoard[i][j] = _comSeeBord[i][j];
				_helpUserSeeBoard[i][j] = _userSeeBord[i][j];
			}
		}
	}
	public void returnMatrix()
	{
		for (int i = 0; i < _gameH; i++)
		{
			for (int j = 0; j < _gameW; j++)
			{
				_mat[i][j] = _helpMat[i][j];
				_comSeeBord[i][j] = _helpComSeeBoard[i][j];
				_userSeeBord[i][j] = _helpUserSeeBoard[i][j];
			}
		}
	}
	public void previousStep()
	{
//		_mat[lastI][lastJ] = _helpMat[lastI][lastJ];
//		_comSeeBord[lastI][lastJ] = _helpComSeeBoard[lastI][lastJ];
//		_userSeeBord[lastI][lastJ] = _helpUserSeeBoard[lastI][lastJ];
//
//		_mat[newI][newJ] = _helpMat[newI][newJ];
//		_comSeeBord[newI][newJ] = _helpComSeeBoard[newI][newJ];
//		_userSeeBord[newI][newJ] = _helpUserSeeBoard[newI][newJ];
		//_mat[newI][newJ] = 
		_mat[_listSaveMoves.getLast().getFirstI()][_listSaveMoves.getLast().getFisrtJ()] = _listSaveMoves.getLast().getFirstCellStatus();
		_mat[_listSaveMoves.getLast().getLastI()][_listSaveMoves.getLast().getLastJ()] = _listSaveMoves.getLast().getCellStatus();
		
		_comSeeBord[_listSaveMoves.getLast().getFirstI()][_listSaveMoves.getLast().getFisrtJ()] = _listSaveMoves.getLast().getFirstCellComputerStatus();
		_comSeeBord[_listSaveMoves.getLast().getLastI()][_listSaveMoves.getLast().getLastJ()] = _listSaveMoves.getLast().getLastCellComputerStatus();
		
		_userSeeBord[_listSaveMoves.getLast().getFirstI()][_listSaveMoves.getLast().getFisrtJ()] = _listSaveMoves.getLast().getFirstCellUserStatus();
		_userSeeBord[_listSaveMoves.getLast().getLastI()][_listSaveMoves.getLast().getLastJ()] = _listSaveMoves.getLast().getLastCellUserStatus();
		
		if(_listSaveMoves.getLast().getIsWin())
			isWin = false;
		_listSaveMoves.removeLast();
	}
	public void move(int firstI,int firstJ,int secondI,int secondJ,boolean isBlueTurn)
	{
		boolean b = false;
		_isTie = false;
		//copyMatrix();
		_listSaveMoves.add(new saveMoves(firstI, firstJ, secondI, secondJ,this.getStatus(secondI,secondJ),getStatusComBoard(secondI, secondJ),getStatusUserBoard(secondI, secondJ),
				this.getStatus(firstI,firstJ),getStatusComBoard(firstI, firstJ),getStatusUserBoard(firstI, firstJ),isWin));
		if(!isBlueTurn)
		{
			if(isLigalClick(secondI, secondJ,isBlueTurn) && 
					isLigalClickMove(firstI, firstJ,secondI, secondJ))
			{
				//System.out.println("_isUserTURn RED MOVE ::: "+ isBlueturn);
				if(isWin)
				{
					System.out.println("you LOSE !!!!!!!!!!!!!!!!!!");
				}
				else if(getStatus(secondI,secondJ)==cellStatus.Hole)
				{
					BoardLogic.getInstance().setStatus(firstI,firstJ ,cellStatus.Empty);
					BoardLogic.getInstance().setStatusComBoard(firstI, firstJ,cellStatus.Empty);
					BoardLogic.getInstance().setStatusUserBoard(firstI, firstJ,cellStatus.Empty);
					
					//BoardLogic.getInstance().setStatus(firstI,firstJ ,cellStatus.Empty);
					//BoardLogic.getInstance().setStatusComBoard(secondI, secondJ,cellStatus.Hole);
					//BoardLogic.getInstance().setStatusUserBoard(firstI, firstJ,cellStatus.Empty);
				}
				else if(getStatus(secondI,secondJ)==cellStatus.Empty)
				{

					setStatus(secondI, secondJ, getStatus(firstI, firstJ));
					BoardLogic.getInstance().setStatusComBoard(secondI, secondJ, getStatus(firstI, firstJ));
					if(BoardLogic.getInstance().getStatusUserBoard(firstI,firstJ) != cellStatus.EmptySoldier)
						BoardLogic.getInstance().setStatusUserBoard(secondI, secondJ,getStatus(firstI, firstJ));
					else
						BoardLogic.getInstance().setStatusUserBoard(secondI, secondJ,cellStatus.EmptySoldier);


					BoardLogic.getInstance().setStatus(firstI,firstJ ,cellStatus.Empty);
					BoardLogic.getInstance().setStatusComBoard(firstI, firstJ,cellStatus.Empty);
					BoardLogic.getInstance().setStatusUserBoard(firstI, firstJ,cellStatus.Empty);
					_isTie=false;
					b = true;
					isBlueTurn = true;
				}
				
				if(!b)
				{

					//System.out.println("i: "+i+ "j:"+ j+"last i:" + indexSoldierClicked.getI()+"last j:"+indexSoldierClicked.getJ());
					/**
					 * 
					 */
					_fightResultHelper = BoardLogic.getInstance().fight(firstI,firstJ,secondI, secondJ);
					/**
					 * blue win
					 */
					if(_fightResultHelper==fightResult.BlueWin)
					{


						BoardLogic.getInstance().setStatus(firstI,firstJ ,cellStatus.Empty);
						BoardLogic.getInstance().setStatusComBoard(firstI,firstJ,cellStatus.Empty);
						BoardLogic.getInstance().setStatusUserBoard(firstI,firstJ,cellStatus.Empty);

						BoardLogic.getInstance().setStatusComBoard(secondI,secondJ,getStatus(secondI,secondJ));

						_isTie=false;
						isBlueTurn = true;
					}
					/**
					 * red Win
					 */
					else if(_fightResultHelper==fightResult.RedWin)
					{

						setStatus(secondI,secondJ,getStatus(firstI,firstJ));
						setStatusComBoard(secondI,secondJ,getStatus(firstI,firstJ));
						setStatusUserBoard(secondI,secondJ,getStatus(firstI,firstJ));

						BoardLogic.getInstance().setStatus(firstI,firstJ ,cellStatus.Empty);
						BoardLogic.getInstance().setStatusUserBoard(firstI,firstJ,cellStatus.Empty);
						BoardLogic.getInstance().setStatusComBoard(firstI,firstJ,cellStatus.Empty);
						_isTie=false;
						isBlueTurn = true;
					}
					else if(_fightResultHelper==fightResult.Tie)
					{
						_isTie = true;
					}
				}
			}
		}
		else if(isBlueTurn)
		{
			//.BoardLogic.boolean b = false;
			/**
			 * current soldier
			 */

			//if (_board[i][j].isArrow())
			if(isLigalClick(secondI, secondJ,isBlueTurn) && isLigalClickMove(secondI, secondJ,firstI,firstJ))
			{
				System.err.println("isWin"+BoardLogic.getInstance().isWin);
				if(isWin)
				{
					System.out.println("you W  I  N !!!!!!!!!!!!!!!!!!");
				}
				else if(getStatus(secondI,secondJ)==cellStatus.Hole)
				{
					BoardLogic.getInstance().setStatus(firstI,firstJ ,cellStatus.Empty);
					BoardLogic.getInstance().setStatusComBoard(firstI, firstJ,cellStatus.Empty);
					BoardLogic.getInstance().setStatusUserBoard(firstI, firstJ,cellStatus.Empty);
					
					//BoardLogic.getInstance().setStatus(firstI,firstJ ,cellStatus.Empty);
					//BoardLogic.getInstance().setStatusComBoard(secondI, secondJ,cellStatus.Hole);
					//BoardLogic.getInstance().setStatusUserBoard(secondI, secondJ,cellStatus.Hole);
				}
				else if(getStatus(secondI, secondJ)==BoardLogic.cellStatus.Empty)
				{
					//_board[i][j].setImgSoldier(_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].getImgSoldier());
					//_board[i][j].setCellStatus(soldier);
					setStatus(secondI, secondJ, getStatus(firstI, firstJ));
					setStatusUserBoard(secondI, secondJ, getStatus(firstI, firstJ));
					//_board[i][j].repaint();
					if(BoardLogic.getInstance().getStatusComBoard(firstI,firstJ) !=cellStatus.EmptySoldier)
						setStatusComBoard(secondI, secondJ, getStatus(firstI, firstJ));
					else
						setStatusComBoard(secondI, secondJ,cellStatus.EmptySoldier);
					//_board[i][j].setCellStatus(cellStatus);
					//_continue =true;
					isBlueTurn = false;
					_isTie=false;
				}
				else if(isLigalClickMove(secondI, secondJ,firstI,firstJ))
				{
					/**
					 * blue win
					 */
					//System.out.println("i: "+i+ "j:"+ j+"last i:" + indexSoldierClicked.getI()+"last j:"+indexSoldierClicked.getJ());
					_fightResultHelper = BoardLogic.getInstance().fight(secondI, secondJ,firstI,firstJ);
					if(_fightResultHelper==fightResult.BlueWin)
					{
						//System.out.println("asdsada");
						//	_board[_i][_j].setImgSoldier(null);/////////////////////////i,i
						//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
						//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
						//_board[_i][_j].setImgSoldier(getImgAfterFight(indexSoldierClicked.getI(),indexSoldierClicked.getJ()));
						setStatus(secondI,secondJ ,getStatus(firstI,firstJ));

						setStatusComBoard(secondI, secondJ,getStatus(firstI,firstJ));
						setStatusUserBoard(secondI, secondJ,getStatus(firstI,firstJ));
						//_board[_i][_j].repaint();
						//_isTie = false;
						_isTie=false;
						isBlueTurn = false;
					}
					/**
					 * red Win
					 */
					else if(_fightResultHelper==fightResult.RedWin)
					{
						//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
						//System.out.println("last i:" + indexSoldierClicked.getI()+"last j:"+indexSoldierClicked.getJ());
						setStatusUserBoard(secondI,secondJ ,getStatus(secondI,secondJ));
						setStatus(firstI,firstJ ,cellStatus.Empty);
						setStatusComBoard(firstI,firstJ ,cellStatus.Empty);
						setStatusUserBoard(firstI,firstJ ,cellStatus.Empty);

						//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].repaint();
						//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
						//_board[_i][_j].setImgSoldier(getImgAfterFight(_i,_j));
						//_board[_i][_j].repaint();
						//_isTie = false;
						_isTie=false;
						isBlueTurn = false;
					}
					else if( _fightResultHelper == fightResult.Tie)
					{
						_isTie = true;
					}

				}
				//);
				if(!b)
				{
					//_board[_arrowIJ.get(0).getI()][_arrowIJ.get(0).getJ()].setArrow(false);
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setImgSoldier(null);
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setSoldierType(null);
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].setCellStatus(Cell.cellStatus.Empty);
					setStatus(firstI,firstJ ,cellStatus.Empty);
					setStatusComBoard(firstI,firstJ ,cellStatus.Empty);
					setStatusUserBoard(firstI,firstJ ,cellStatus.Empty);
					//_board[indexSoldierClicked.getI()][indexSoldierClicked.getJ()].repaint();
					//_board[_i][_j].repaint();
					//indexSoldierClicked.setI(-1);
					//indexSoldierClicked.setJ(-1);
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

		}
	}
	/**
	 *  check the ligal click move
	 * @param i
	 * @param j
	 * @param lastI
	 * @param lastJ
	 * @return
	 */
	public boolean isLigalClickMove(int i,int j,int lastI,int lastJ)
	{
		/**
		 * up
		 */
		//System.out.println("i:"+i+"j:"+j+"last i:" + lastI+"last j:"+lastJ);
		if(((i-1)==lastI && j==lastJ) && (_mat[i][j] != cellStatus.Empty ||  _mat[lastI][lastJ]!= cellStatus.Empty ))
		{

			//if(_mat[i][j] == _)
			return true;
		}
		/**
		 * down
		 */
		if(((i+1)==lastI&&j==lastJ) && (_mat[i][j] != cellStatus.Empty ||  _mat[lastI][lastJ]!= cellStatus.Empty))
		{
			return true;
		}
		/**
		 * left
		 */
		if((i==lastI&&(j-1)==lastJ) && (_mat[i][j] != cellStatus.Empty ||  _mat[lastI][lastJ]!= cellStatus.Empty))
		{
			return true;
		}
		/**
		 * right
		 */
		if((i==lastI&&j+1==lastJ) && (_mat[i][j] != cellStatus.Empty ||  _mat[lastI][lastJ]!= cellStatus.Empty))
		{
			return true;
		}
		return false;
	}
	public cellStatus[][] get_mat() {
		return _mat;
	}
	public void set_mat(cellStatus[][] _mat) {
		this._mat = _mat;
	}
	/**
	 * check how is the winner in the fןעיא
	 * @param redI
	 * @param redJ
	 * @param blueI
	 * @param blueJ
	 * @return
	 */

	public fightResult fight(int redI,int redJ,int blueI,int blueJ)
	{
		//System.out.println("redI redJ"+ _mat[redI][redJ].toString());
		//System.out.println("blueI blueJ" + _mat[blueI][blueJ].toString());
		if(_mat[redI][redJ] != _mat[blueI][blueJ])
		{
			//red win
			if(_mat[redI][redJ] ==_cellStatus.RedPaper &&_mat[blueI][blueJ] == _cellStatus.BlueRock)
			{
				_mat[blueI][blueJ] = _cellStatus.Empty;
				return fightResult.RedWin;
			}
			if(_mat[redI][redJ] ==_cellStatus.RedRock &&_mat[blueI][blueJ] == _cellStatus.BlueScissors)
			{
				_mat[blueI][blueJ] = _cellStatus.Empty;
				return fightResult.RedWin;
			}
			if(_mat[redI][redJ] ==_cellStatus.RedScissors &&_mat[blueI][blueJ] == _cellStatus.BluePaper)
			{
				_mat[blueI][blueJ] = _cellStatus.Empty;
				return fightResult.RedWin;
			}
			//blue win
			if(_mat[redI][redJ] ==_cellStatus.RedPaper &&_mat[blueI][blueJ] == _cellStatus.BlueScissors)
			{
				_mat[redI][redJ] =_cellStatus.Empty;
				return fightResult.BlueWin;
			}
			if(_mat[redI][redJ] ==_cellStatus.RedRock &&_mat[blueI][blueJ] == _cellStatus.BluePaper)
			{
				_mat[redI][redJ] =_cellStatus.Empty;
				return fightResult.BlueWin;
			}
			if(_mat[redI][redJ] ==_cellStatus.RedScissors &&_mat[blueI][blueJ] == _cellStatus.BlueRock)
			{
				_mat[redI][redJ] =_cellStatus.Empty;
				return fightResult.BlueWin;
			}
			return fightResult.Tie;
		}
		return null;

	}
	
//	public fightResult fightWhenSeeForward(int redI,int redJ,int blueI,int blueJ)
//	{
//		System.out.println("redI redJ"+ _mat[redI][redJ].toString());
//		System.out.println("blueI blueJ" + _mat[blueI][blueJ].toString());
//		if(_mat[redI][redJ] != _mat[blueI][blueJ])
//		{
//			//red win
//			if(_mat[redI][redJ] ==_cellStatus.RedPaper &&_mat[blueI][blueJ] == _cellStatus.BlueRock)
//			{
//				_mat[blueI][blueJ] = _cellStatus.Empty;
//				return fightResult.RedWin;
//			}
//			if(_mat[redI][redJ] ==_cellStatus.RedRock &&_mat[blueI][blueJ] == _cellStatus.BlueScissors)
//			{
//				_mat[blueI][blueJ] = _cellStatus.Empty;
//				return fightResult.RedWin;
//			}
//			if(_mat[redI][redJ] ==_cellStatus.RedScissors &&_mat[blueI][blueJ] == _cellStatus.BluePaper)
//			{
//				_mat[blueI][blueJ] = _cellStatus.Empty;
//				return fightResult.RedWin;
//			}
//			//blue win
//			if(_mat[redI][redJ] ==_cellStatus.RedPaper &&_mat[blueI][blueJ] == _cellStatus.BlueScissors)
//			{
//				_mat[redI][redJ] =_cellStatus.Empty;
//				return fightResult.BlueWin;
//			}
//			if(_mat[redI][redJ] ==_cellStatus.RedRock &&_mat[blueI][blueJ] == _cellStatus.BluePaper)
//			{
//				_mat[redI][redJ] =_cellStatus.Empty;
//				return fightResult.BlueWin;
//			}
//			if(_mat[redI][redJ] ==_cellStatus.RedScissors &&_mat[blueI][blueJ] == _cellStatus.BlueRock)
//			{
//				_mat[redI][redJ] =_cellStatus.Empty;
//				return fightResult.BlueWin;
//			}
//			return fightResult.Tie;
//		}
//		return null;
//
//	}
	/**
	 * check the score for current move
	 * @param i
	 * @param j
	 * @param newI
	 * @param newJ
	 * @param isMyTurn
	 * @return
	 */
	public int score(int i,int j,int newI,int newJ, boolean isMyTurn)
	{
		int score = 0;
		if(!isMyTurn)
		{
			
			if(checkIfRedSoldierCanMove(newI,newJ))
				score = 10;
			if(checkIfRedSoldierCanMoveForward(i,j,newI,newJ))
				score = 25;
			if(checkIfRedSoldierCanFight(newI,newJ))
				score = 250;
			if(checkIfRedSoldierCanWin(i, j,newI,newJ))
				score = 500;
		}
		else
		{
		
			if(checkIfBlueSoldierCanMove(newI,newJ))
				score = -10;
			if(checkIfBlueSoldierCanMoveForward(i,j,newI,newJ))
				score = -25;
			if(checkIfBlueSoldierCanFight(newI,newJ))
				score = -250;
			if(checkIfBlueSoldierCanWin(i, j,newI,newJ))
				score = -500;
		}
		return score;
	}
	/**
	 * calculate the score
	 * @param i
	 * @param j
	 * @param newI
	 * @param newJ
	 * @param isMyTurn
	 * @return
	 */
	public int calcPoints(int i,int j,int newI, int newJ, boolean isMyTurn)
	{
		boolean win = isWin( i, j);
		boolean _isBlue = (isMyTurn)?true:false;
		int score = 0;

		if(win) //if the player won
		{
			score = 999;

			if (_isBlue)
				score *= -1;
			return score;
		}

		//grading a move
		score = score(i,j,newI,newJ,isMyTurn);

		//if (_isBlue)
		//	score *= -1;
		return score;

	}
	/**
	 * find the min or max
	 * @param gradeslist
	 * @param isMyTurn
	 * @return
	 */
	public int findMinOrMax(LinkedList<Score> gradeslist, boolean isMyTurn)
	{
		if(gradeslist.size()==0)
			return 0;

		int minormax = gradeslist.get(0).getScore();

		if(isMyTurn)
		{
			for(int i=1;i<gradeslist.size();i++)
			{
				if(minormax < gradeslist.get(i).getScore())
					minormax= gradeslist.get(i).getScore();
			}
		}
		else
		{
			for(int i=1;i<gradeslist.size();i++)
			{
				if(minormax > gradeslist.get(i).getScore())
				{
					minormax= gradeslist.get(i).getScore();
				}
			}
		}
		return minormax;
	}
	/**
	 * minimax
	 * @param depth
	 * @param isBlueTurn
	 * @return
	 */
	public LinkedList<Score> buildList(int depth ,boolean isBlueTurn)
	{
		LinkedList<Score> grades = new LinkedList<Score>();
		//CarColor c = (isBlueTurn)?CarColor.BLUE:CarColor.RED;
		boolean b = false;
		for(int i = 1;i<_gameH-1;i++)
		{
			for(int j = 1; j<_gameW-1;j++)
			{
				if(isLigaFirstlClick(i,j,isBlueTurn))
				{


					//if (_carsCells[i*_boardSize+j]==c)
					for(int m = i - 1;m < i + 2;m++)
					{
						for(int n = j - 1; n < j + 2;n++)
						{


							if((i == m &&  n == j - 1) ||
									(i == m &&  n  == j + 1 )||
									(m  == i + 1 &&  n == j )||
									(m  == i - 1 &&  n  == j ))
							{
								if(isLigalClick(m,n,isBlueTurn) && isLigalClickMove(i, j, m, n))
								{
									move(i, j, m, n, isBlueTurn);
									b=true;
		
									
									if(depth == 0)
									{
										grades.add(new Score(i,j,m,n,calcPoints(i,j,m,n,isBlueTurn)));
									}
									else
									{
										boolean win = false;
										win = isWin(i, j);
										if(win)
										{
											if(isBlueTurn)
												grades.add(new Score(i,j,m,n,-999));
											else
												grades.add(new Score(i,j,m,n,999));
										}

										if(!win)
										{
											LinkedList<Score> tmpGrades = buildList(depth-1, !isBlueTurn);
											//previousStep(i,j,m,n,isBlueTurn);
											grades.add(new Score(i,j,m,n,findMinOrMax(tmpGrades,isBlueTurn)+calcPoints(i,j,m,n,isBlueTurn)));
										}
									}
//									if(_isTie)
//									{
//										return grades;
//									}
								}
							}
							if((i == m &&  n == j - 1) ||
									(i == m &&  n  == j + 1 )||
									(m  == i + 1 &&  n == j )||
									(m  == i - 1 &&  n  == j ))
							{
								if(b)
								{
									previousStep();
									b=false;
								}
							}
						}

					}
				}
			}
		}
		return grades;

	}
	public void print(boolean isBlueTurn)
	{
		for(int i = 1;i<_gameH-1;i++)
		{
			for(int j = 1; j<_gameW-1;j++)
			{
//				//if (_carsCells[i*_boardSize+j]==c)
//				for(int m = i - 1;m < i + 2;m++)
//				{
//					for(int n = j - 1; n < j + 2;n++)
//					{
//						if((i == m &&  n == j - 1) ||
//								(i == m &&  n  == j + 1 )||
//								(m  == i + 1 &&  n == j )||
//								(m  == i - 1 &&  n  == j ))
//						{
//							if(isLigalClick(i,j,isBlueTurn) && isLigalClickMove(m, n, i, j))
//							{
//								System.out.println("i: "+i+" j: "+j+"--------> i: "+m+" j: "+n);
//							}
//						}
//					}
//				}
			}
		}
	}
	//	
	/**
	 * calculate the best move by the score
	 * @return
	 */
	public Score calcBestMove()
	{
		copyMatrix();
		LinkedList<Score> listPoints = buildList(_maxDepth,false);
		//returnMatrix();
		System.out.println("list Points size = "+ listPoints.size());
		System.out.println(listPoints.toString());
		if (listPoints.size() ==  0)
			return null;

		Score p = listPoints.get(0);
		for (int k=0; k < listPoints.size();k++)
		{
			if (listPoints.get(k).getScore() > p.getScore())
				p = listPoints.get(k);
		}
		return p;
	}

	/**
	 * calc computer moves 
	 * @param c
	 * @return
	 */


	/**
	 * is the user win???
	 * @param redI
	 * @param redJ
	 * @param blueI
	 * @param blueJ
	 * @return
	 */
	public boolean isWin(int i,int j)
	{
		if(isWin)
			return true;
		return false;
	}


	//    public long evaluate(char player)
	//    {
	//        long heurist=0;
	//        for(int i=0;i<HeuristicSize;i++)
	//        {
	//            int playerCounter=0;
	//            int otherCounter=0;
	//            for (int j=0;j<seq;j++)
	//            {
	//             byte sign=gameMat[winCord[i][j].y][winCord[i][j].x];
	//                playerCounter=(sign==player)?playerCounter+1:playerCounter;
	//                otherCounter=(sign==other(player))?otherCounter+1:otherCounter;
	//            }
	//            heurist+=HeuristicArray[playerCounter][otherCounter];
	//        }
	//        return heurist;
	//    }


	private ArrayList <Point []> gatherValidMovements(int row, int col)
	{
		int i;
		ArrayList <Point []>list=new ArrayList <Point []>();
		for ( i = 0; i < movementIndex; i++) 
		{
			if(movementCordMat[i][0].y == row && movementCordMat[i][0].x == col )
			{
				if(_comSeeBord[movementCordMat[i][1].y][movementCordMat[i][1].x] == cellStatus.Empty || 
						_comSeeBord[movementCordMat[i][1].y][movementCordMat[i][1].x] == cellStatus.EmptySoldier)
				{
					list.add(movementCordMat[i]);
				}
			}
		}
		return list;
	}


	//    public long bestMove(boolean isUserTurn, Point square,int MoveNumber,long alfa,long beta,int n)
	//    {
	//        Point bestSquare=new Point(-1, -1); // משתנה שיכיל את הצעד הטוב ביותר
	//        int moves=0;
	//        int i;
	//        int jj=0;
	//               
	//        HeuristicType sortedHeuristic[]= new HeuristicType[HeuristicSize]; // מערך חד מימדי שיכיל את הציונים של המהלכים האפשריים
	//        
	//        
	//        for (i=1;(i<7);i++) 						// לולאה העוברת כמספר האופציות למהלכים
	//        {
	//        	for (jj=1;(jj<8);jj++) 						// לולאה העוברת כמספר האופציות למהלכים
	//            {
	//        	/*if(_comSeeBord[i][jj]!=_cellStatus.Empty)
	//        	{
	//        		
	//        	}*/
	//            if (islegalstep(i, player,0))	 		// אם המהלך חוקי
	//            {
	//                long heuristic;
	//                int j;
	//                heuristic=evaluate(player); 		// חשב ציון עבור המהלך
	//                Countofchip[i]++; 					// מחיקת הצעד שנעשתה בפונקציה של מציאת הצעד החוקי
	//                _comSeeBord[Countofchip[i]][i]=_cellStatus.Empty;
	//                //הלולאה ממקמת את הציון הגבוה ביותר בראש המערך של הציונים של המהלכים
	//                for (j=moves-1;(j>=0) && (sortedHeuristic[j].heuristic<heuristic);j--) 
	//                {
	//                    sortedHeuristic[j+1]=new HeuristicType();
	//                    sortedHeuristic[j+1].heuristic=sortedHeuristic[j].heuristic;
	//                    sortedHeuristic[j+1].square=sortedHeuristic[j].square;
	//                }
	//                sortedHeuristic[j+1]=new HeuristicType();
	//                sortedHeuristic[j+1].heuristic=heuristic;
	//                sortedHeuristic[j+1].square=new Point(Countofchip[i],i);
	//                moves++;
	//            }
	//        }
	//        
	//        System.out.println("N is "+n);
	//        for(int kk=0;kk<moves;kk++)
	//        {
	//        	System.out.println(" bbb "+sortedHeuristic[kk].heuristic+" line "+sortedHeuristic[kk].square.y+" col "+sortedHeuristic[kk].square.x);
	//        }
	//        
	//        // איפוס מערך הציונים למהלכים
	//        if(player=='X')
	//        {
	//	        for(int kk=0;kk<moves;kk++)
	//	        {
	//	        	sortedHeuristic[kk].heuristic=-sortedHeuristic[kk].heuristic;
	//	        }
	//        }
	//        
	//        //הוצאת הקורדינטות של מיקום המהלך בעל הציון הגבוה ביותר
	//        bestSquare.y=sortedHeuristic[0].square.y;
	//        bestSquare.x=sortedHeuristic[0].square.x;
	//
	//        
	//        for(i=0;(i<moves);i++) // לולאה הסורקת כמספר האופציות האפשריות למהלכים
	//        {
	//            long score;
	//            Location tempHeuristicSquare=sortedHeuristic[i].square;
	//            char winner;
	//            board[tempHeuristicSquare.y][tempHeuristicSquare.x]=(byte)player; // יצירת הצעד על הלוח
	//            Countofchip[tempHeuristicSquare.x]--;
	//            tempNode=board2[tempHeuristicSquare.y][tempHeuristicSquare.x];
	//            list.deleteNode(board2[tempHeuristicSquare.y][tempHeuristicSquare.x]); 
	//            winner=isWinner();
	//            if(winner=='O') // אם המחשב ניצח ניתן את הציון הטוב ביותר 
	//            {
	//                score=Infinity;
	//            }
	//            else if(winner=='X') // אם השחקן ניצח נין לו את הציון הטוב ביותר עבורו
	//            {
	//                score=-Infinity; 
	//            }
	//            else if(winner=='C')
	//            {
	//                score=0; 
	//            }
	//            else if(n==0)
	//            {
	//                score=(player=='X')?-sortedHeuristic[i].heuristic:sortedHeuristic[i].heuristic; // לשחקן האנושי ניתן ציון שלילי ולמחשב חיובי
	//            }
	//            else
	//            {
	//                score=bestMove(other(player), square, MoveNumber + 1, alfa,beta,n-1);
	//            }
	//            board[tempHeuristicSquare.y][tempHeuristicSquare.x]=0;
	//            Countofchip[tempHeuristicSquare.x]++;
	//            list.insertNode(tempNode);  
	//            // תהליך הגיזום שמוותר על פיתוח ענפים שאינם יכולים לשנות את ערך השורש במינימקס
	//            if (player=='O') 	// אם תור המחשב
	//            {
	//                if(score>=beta) 
	//                {
	//                    square.y=tempHeuristicSquare.y;
	//                    square.x=tempHeuristicSquare.x;
	//                    return score;
	//                }
	//                else if(score>alfa) // אם הציון גדול יותר מערך הציון כרגע של המחשב כלומר נמצא המהלך טוב יותר
	//                {
	//                    alfa=score;
	//                    bestSquare.y=tempHeuristicSquare.y;
	//                    bestSquare.x=tempHeuristicSquare.x;
	//                }
	//            }
	//            else 			// אם תור השחקן האנושי
	//            {
	//                if(score<=alfa) // אם הציון שהושג קטן מהציון כרגע אצל המחשב אין צורך בו
	//                {
	//                    square.y=tempHeuristicSquare.y;
	//                    square.x=tempHeuristicSquare.x;
	//                    return score;
	//                }
	//                else if(score<beta) // אם הציון של השחקן גדול יותר מזה של המחשב נמצא המהלך הטוב 
	//                {
	//                    beta = score;
	//                    bestSquare.y=tempHeuristicSquare.y;
	//                    bestSquare.x=tempHeuristicSquare.x;
	//                }
	//            }
	//        }
	//        // המשתנה של הצעד מקבל בסוף את הקורדינטות של הצעד הטוב ביותר
	//        square.y=bestSquare.y;
	//        square.x=bestSquare.x;
	//        return (player=='O')?alfa:beta;
	//    }    
	//}




}
