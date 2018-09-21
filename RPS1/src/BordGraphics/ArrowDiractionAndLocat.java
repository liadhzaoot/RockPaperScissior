package BordGraphics;
/**
 * save the arrow location (i,j) and the diraction)
 * @author liadhazoot
 *
 */
public class ArrowDiractionAndLocat
{
	int _i;
	int _j;
	public static enum ArrowDirections{Straight,Left,Right,Back};
	ArrowDirections _diraction;
	Img _Arrow;
	public ArrowDiractionAndLocat(int i, int j,ArrowDirections diraction) {
		
		this._i = i;
		this._j = j;
		_diraction = diraction;
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
	public ArrowDirections getDiraction() {
		return _diraction;
	}
	public void setDiraction(ArrowDirections diraction) {
		this._diraction = diraction;
	}
	
	
}
