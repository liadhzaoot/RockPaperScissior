package BordGraphics;

public class IJ
{
	int _i;
	int _j;
	public IJ(int i,int j)
	{
		_i=i;
		_j=j;
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
	@Override
	public String toString() {
		return "IJ [_i=" + _i + ", _j=" + _j + "]";
	}
	
	
}
