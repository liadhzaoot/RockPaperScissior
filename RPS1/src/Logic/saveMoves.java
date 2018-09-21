package Logic;

public class saveMoves
{
	private int _firstI;
	private int _fisrtJ;
	private int _lastI;
	private int _lastJ;
	private boolean _isWin = false;
	//public static enum cellStatus{RedRock, BlueRock,RedPaper,BluePaper,RedScissors,BlueScissors,Empty,BlueFlag,Hole,EmptySoldier,RedFlag};
	private Logic.BoardLogic.cellStatus _firstCellStatus;
	private Logic.BoardLogic.cellStatus _firstCellComputerStatus;
	private Logic.BoardLogic.cellStatus _firstCellUserStatus;
	private Logic.BoardLogic.cellStatus _lastCellStatus;
	private Logic.BoardLogic.cellStatus _lastCellComputerStatus;
	private Logic.BoardLogic.cellStatus _lastCellUserStatus;
	public saveMoves(int firstI, int fisrtJ, int lastI, int lastJ,
			Logic.BoardLogic.cellStatus cellStatus,Logic.BoardLogic.cellStatus lastCellComputerStatus,Logic.BoardLogic.cellStatus lastCellUserStatus,
			Logic.BoardLogic.cellStatus firstCellStatus,Logic.BoardLogic.cellStatus firstCellComputerStatus,Logic.BoardLogic.cellStatus firstCellUserStatus,
			boolean isWin) {
		super();
		this._firstI = firstI;
		this._fisrtJ = fisrtJ;
		this._lastI = lastI;
		this._lastJ = lastJ;
		this._lastCellStatus = cellStatus;
		this._lastCellComputerStatus = lastCellComputerStatus;
		this._lastCellUserStatus = lastCellUserStatus;
		this._firstCellStatus = firstCellStatus;
		this._firstCellComputerStatus = firstCellComputerStatus;
		this._firstCellUserStatus = firstCellUserStatus;
		this._isWin = isWin;
	}
	public int getFirstI() {
		return _firstI;
	}
	public void setIsWin(boolean isWin) {
		this._isWin = isWin;
	}
	public boolean getIsWin() {
		return _isWin;
	}
	public void setFirstI(int firstI) {
		this._firstI = firstI;
	}
	public int getFisrtJ() {
		return _fisrtJ;
	}
	public void setFisrtJ(int fisrtJ) {
		this._fisrtJ = fisrtJ;
	}
	public int getLastI() {
		return _lastI;
	}
	public void setLastI(int lastI) {
		this._lastI = lastI;
	}
	public int getLastJ() {
		return _lastJ;
	}
	public void setLastJ(int lastJ) {
		this._lastJ = lastJ;
	}
	public Logic.BoardLogic.cellStatus getCellStatus() {
		return _lastCellStatus;
	}
	public void setCellStatus(Logic.BoardLogic.cellStatus cellStatus) {
		this._lastCellStatus = cellStatus;
	}
	public Logic.BoardLogic.cellStatus getLastCellComputerStatus() {
		return _lastCellComputerStatus;
	}
	public void setLastCellComputerStatus(Logic.BoardLogic.cellStatus lastCellComputerStatus) {
		this._lastCellComputerStatus = lastCellComputerStatus;
	}
	public Logic.BoardLogic.cellStatus getLastCellUserStatus() {
		return _lastCellUserStatus;
	}
	public void setLastCellUserStatus(Logic.BoardLogic.cellStatus lastCellUserStatus) {
		this._lastCellUserStatus = lastCellUserStatus;
	}
	
	
	
	public Logic.BoardLogic.cellStatus getFirstCellStatus() {
		return _firstCellStatus;
	}
	public void setFirstCellStatus(Logic.BoardLogic.cellStatus firstCellStatus) {
		this._firstCellStatus = firstCellStatus;
	}
	public Logic.BoardLogic.cellStatus getFirstCellComputerStatus() {
		return _firstCellComputerStatus;
	}
	public void setFirstCellComputerStatus(Logic.BoardLogic.cellStatus firstCellComputerStatus) {
		this._firstCellComputerStatus = firstCellComputerStatus;
	}
	public Logic.BoardLogic.cellStatus getFirstCellUserStatus() {
		return _firstCellUserStatus;
	}
	public void setFirstCellUserStatus(Logic.BoardLogic.cellStatus firstCellUserStatus) {
		this._firstCellUserStatus = firstCellUserStatus;
	}
	
}
