package bliffoscope.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that provides behavior methods for the specific shape and
 * loads the specific parameters for the shape that would be used to found the
 * specific shape in the Blifosocope data.
 * 
 * @author Varun
 * 
 */
public abstract class AbstractObstructionShape {

	// relative coordinate list
	protected List<Coordinates> relativeCoordinateList = new ArrayList<Coordinates>();
	// max height of the shape from the first particle to last found particle.
	protected int maxHeight;
	// max relative coordinate on the left found wrt the relative coordinate of
	// the shape.
	protected int relativeLeftMax;
	// max relative coordinate on the right found wrt the relative coordinate of
	// the shape
	protected int relativeRightMax;
	// complete data set for the specific structure.
	protected char[][] char2D;

	/**
	 * Constructor that loads the Shape structure by doing the following :
	 * <p>
	 * calculate the first relative coordinates as first Neutrino particle
	 * found.
	 * </p>
	 * 
	 * <p>
	 * Calculate the set of relative coordinates of all the particles that
	 * consists the shape with repect to the first relative coordinate found.
	 * </p>
	 * 
	 * <p>
	 * calculate the height of the shape
	 * </p>
	 * 
	 * <p>
	 * calculate the relative left max with respect to the relative coordinate.
	 * </p>
	 * 
	 * <p>
	 * calculate the relative right max with respect to the relative coordinate.
	 * </p>
	 * 
	 * @param char2D
	 */
	public AbstractObstructionShape(char[][] char2D) {
		this.char2D = char2D;
		Coordinates relativeCoordinates = null;

		// Populating the first coordinates of neutrinos that is encountered
		// this coordinate would be the relative coordinate
		for (int i = 0; i < char2D.length; i++) {
			for (int j = 0; j < char2D[i].length; j++) {
				if (char2D[i][j] != Bliffoscope.NEUTRINO_SYMBOL) {
					continue;
				}
				relativeCoordinates = new Coordinates(j, i);
				break;
			}
			if (relativeCoordinates != null) {
				break;
			}
			System.out.println("");
		}

		// getting all the coordinates of the neutrinos relative to the
		// first relative coordinate encountered
		// relativeRightMax is how much max width to the left from relative
		// relativeLeftMax is how much max width to the right from relative
		// maxHeight is the height of the structure

		for (int k = 0; k < char2D.length; k++) {
			for (int l = 0; l < char2D[k].length; l++) {
				if (char2D[k][l] != Bliffoscope.NEUTRINO_SYMBOL) {
					continue;
				}

				Coordinates coordinate = null;
				if (l == relativeCoordinates.getX()
						&& k == relativeCoordinates.getY()) {
					coordinate = new Coordinates(
							relativeCoordinates.getX() - l,
							relativeCoordinates.getY() - k);
				} else if (l == relativeCoordinates.getX()) {
					coordinate = new Coordinates(
							l - relativeCoordinates.getX(), k
									- relativeCoordinates.getY());
				} else if (l < relativeCoordinates.getX()) {
					coordinate = new Coordinates(
							l - relativeCoordinates.getX(), k
									- relativeCoordinates.getY());
					if (l - relativeCoordinates.getX() < relativeLeftMax) {
						relativeLeftMax = l - relativeCoordinates.getX();
					}
				} else {
					coordinate = new Coordinates(
							l - relativeCoordinates.getX(), k
									- relativeCoordinates.getY());
					if (relativeRightMax < l - relativeCoordinates.getX()) {
						relativeRightMax = l - relativeCoordinates.getX();
					}
				}
				relativeCoordinateList.add(coordinate);
				maxHeight = k - relativeCoordinates.getY() + 1;

			}
			System.out.println("");
		}

	}

	/**
	 * get Number of Neutrionos particles (+) in the shape
	 * 
	 * @return
	 */
	public int getIdealShapeParticlesCount() {
		return getIdealMatch();
	}

	/**
	 * Get the total match of neutrinos in a particlar instance of
	 * {@link AbstractObstructionShape}
	 * 
	 * @return
	 */
	private int getIdealMatch() {
		int neutrinosCount = 0;
		for (int i = 0; i < char2D.length; i++) {
			for (int j = 0; j < char2D[i].length; j++) {
				if (char2D[i][j] == Bliffoscope.NEUTRINO_SYMBOL) {
					neutrinosCount++;
				}
			}
		}
		// System.out.println(neutrinosCount);
		return neutrinosCount;
	}

	/**
	 * Return the char array of the particles
	 * 
	 * @return
	 */
	public char[][] getParticleDimensions() {
		return char2D;
	}

	/**
	 * return the type of the ObstructionShape
	 * 
	 * @return
	 */
	public abstract ObstructionType getType();

	/**
	 * Return list of coordinates relative with the first coordinate found.
	 * 
	 * @return
	 */
	public List<Coordinates> getRelativeCoordinateList() {
		return relativeCoordinateList;
	}

	/**
	 * return the height of the obstruction
	 * 
	 * @return
	 */
	public int getMaxHeight() {
		return maxHeight;
	}

	/**
	 * Return the relative Left max wrt to relative coordinate.
	 * 
	 * @return
	 */
	public int getRelativeLeftMax() {
		return relativeLeftMax;
	}

	/**
	 * return relative right max wrt to relative coordinate.
	 * 
	 * @return
	 */

	public int getRelativeRightMax() {
		return relativeRightMax;
	}

}
