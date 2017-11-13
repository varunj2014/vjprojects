package bliffoscope.src;

/**
 * Class storing the coordinates of the particles in 2d plane.
 * 
 * @author Varun
 * 
 */
public class Coordinates {
	public int x;
	public int y;

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}