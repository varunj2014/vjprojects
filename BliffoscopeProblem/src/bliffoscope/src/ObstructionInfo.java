package bliffoscope.src;

import java.util.List;

/**
 * Value holder class that identifies the rejectos found in the blifoscope data.
 * <p>
 * type - stores the Type of the Rejectors it can be either Torpedo or start
 * ship
 * </p>
 * <p>
 * confidencePercentage - is the confidence for a particular kind of Rejector
 * </p>
 * <p>
 * isCompleteStructure - whether the structure is complete or not.
 * </p>
 * <p>
 * startcoordinates - the start coordinate of the particular
 * {@link ObstructionType}
 * </p>
 * <p>
 * CoordinateList - total list of the coordinates match found for the particlaur
 * shape in bliffoscope test data.
 * </p>
 * 
 * @author Varun
 * 
 */
public class ObstructionInfo implements Comparable<ObstructionInfo> {

	private ObstructionType type;
	private double confidencepercentage;
	private boolean isCompleteStructure;
	private Coordinates startCoordinates;
	private List<Coordinates> coordinateList;

	public ObstructionType getType() {
		return type;
	}

	public void setType(ObstructionType type) {
		this.type = type;
	}

	public double getConfidencePercentage() {
		return confidencepercentage;
	}

	public void setConfidencePercentage(double confidencepercentage) {
		this.confidencepercentage = confidencepercentage;
	}

	public boolean isCompleteStructure() {
		return isCompleteStructure;
	}

	public void setCompleteStructure(boolean isCompleteStructure) {
		this.isCompleteStructure = isCompleteStructure;
	}

	public Coordinates getStartCoordinates() {
		return startCoordinates;
	}

	public void setStartCoordinates(Coordinates coordinates) {
		this.startCoordinates = coordinates;
	}

	public List<Coordinates> getCoordinateList() {
		return coordinateList;
	}

	public void setCoordinateList(List<Coordinates> coordinateList) {
		this.coordinateList = coordinateList;
	}

	/**
	 * Sorting based on confidence level, in ascending order.
	 */
	@Override
	public int compareTo(ObstructionInfo o) {
		return (int) Math.round(this.confidencepercentage
				- o.confidencepercentage);
	}
}
