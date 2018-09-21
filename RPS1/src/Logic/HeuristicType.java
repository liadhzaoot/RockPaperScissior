package Logic;

import java.awt.Point;

public class HeuristicType {
	public Point _square;
    public long _heuristic;
    public HeuristicType()
    { 
    }
	public Point getSquare() {
		return _square;
	}
	public void setSquare(Point square) {
		this._square = square;
	}
	public long getHeuristic() {
		return _heuristic;
	}
	public void setHeuristic(long heuristic) {
		this._heuristic = heuristic;
	}
    

}
