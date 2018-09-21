package Logic;

public class Score 
{
	private int _i;
	private int _j;
	private int _newI;
	private int _newJ;
	private int _score;
	public Score(int i, int j,int newI,int newJ, int score) {
		//super();
		this._i = i;
		this._j = j;
		this._score = score;
		this._newI =newI;
		this._newJ = newJ;
	}
	public int getNewI() {
		return _newI;
	}
	public void setNewI(int newI) {
		this._newI = newI;
	}
	public int getNewJ() {
		return _newJ;
	}
	public void setNewJ(int newJ) {
		this._newJ = newJ;
	}
	public int getI() {
		return _i;
	}
	public void setI(int i) {
		this._i = i;
	}
	public int getJ() {
		return _j;
	}
	public void setJ(int j) {
		this._j = j;
	}
	public int getScore() {
		return _score;
	}
	public void setScore(int score) {
		this._score = score;
	}
	@Override
	public String toString() {
		return "Score [_i=" + _i + ", _j=" + _j + ", _newI=" + _newI + ", _newJ=" + _newJ + ", _score=" + _score + "]";
	}
	
}
