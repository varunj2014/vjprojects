package bliffoscope.src;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the Bliffoscope problem, that tries to find specific
 * structure in the test.
 * <p>
 * - The constructor loads the test data into a char array
 * </p>
 * 
 * This concrete implementation provides method to detect the particular
 * {@link AbstractObstructionShape} object in the test data and returns with the
 * list of {@link ObstructionInfo} object that contains details about the type
 * of {@link ObstructionType} found along with the coordinates and confidence
 * level.
 * 
 * @author Varun
 * 
 */

public class Bliffoscope implements IBliffoscope {

	public static final char NEUTRINO_SYMBOL = '+';
	char[][] bliffoscopeData;
	private int confidenceLevel;

	/**
	 * Overloaded Constructor accepts the confidence Level
	 * 
	 * @param confidenceLevel
	 * @param bliffoscopeData
	 */
	public Bliffoscope(int confidenceLevel, char[][] bliffoscopeData) {
		this.confidenceLevel = confidenceLevel;
		this.bliffoscopeData = bliffoscopeData;
	}

	/**
	 * This method process the shape Object and locate the
	 * {@link AbstractObstructionShape} shape object into bliffoscope test data.
	 * 
	 * @param shape
	 */
	@Override
	public List<ObstructionInfo> detectAndProcesTargets(
			AbstractObstructionShape shape) {
		List<ObstructionInfo> obstructionInfoList = new ArrayList<ObstructionInfo>();

		if (shape == null) {
			return obstructionInfoList;
		}

		// iterate through the blifoscope test data
		for (int i = 0; i < bliffoscopeData.length; i++) {

			for (int j = 0; j < bliffoscopeData[i].length; j++) {

				if (bliffoscopeData[i][j] != NEUTRINO_SYMBOL) {
					continue;
				}

				
				if(i == 13){
					System.out.println("found potential");
				}
				
				double match = 0;
				List<Coordinates> matchedCoordinateList = new ArrayList<Coordinates>();
				// making sure the shape and its relative coordinates are with
				// in the frame of bliffoscope
				if (i + shape.getMaxHeight() <= bliffoscopeData.length
						&& j + shape.getRelativeLeftMax() > 0
						&& j + shape.getRelativeRightMax() <= bliffoscopeData[i].length) {
					List<Coordinates> coordinateList = shape
							.getRelativeCoordinateList();
					for (Coordinates coordinates : coordinateList) {

						int relVertAxis = i + coordinates.getY();
						int relHorAxis = j + coordinates.getX();

						if (relVertAxis < bliffoscopeData.length
								&& relHorAxis > 0
								&& relHorAxis < bliffoscopeData[i].length
								&& bliffoscopeData[relVertAxis][relHorAxis] == NEUTRINO_SYMBOL) {
							match++;
							// create a coordinate for the matched particles and
							// add it to the List.
							Coordinates matchedCoordinate = new Coordinates(
									relVertAxis, relHorAxis);
							matchedCoordinateList.add(matchedCoordinate);
						}
					}
				}

				// filtering out to show only those shapes that has confidence
				// level greater than passed
				double confidencePercentage = (match / shape
						.getIdealShapeParticlesCount()) * 100;
				// adding only if confidencePercentage is greater than expected
				// confidence level
				if (confidencePercentage < Integer.valueOf(confidenceLevel)
						.doubleValue()) {
					continue;
				}
				// for each match, a Obstruction Info object is created, that
				// sets the type of obstruction found.
				// set the confidence level.
				ObstructionInfo rejecto = new ObstructionInfo();
				rejecto.setType(shape.getType());
				rejecto.setConfidencePercentage(confidencePercentage);
				rejecto.setCoordinateList(matchedCoordinateList);
				if (Math.round(rejecto.getConfidencePercentage()) == 100) {
					rejecto.setCompleteStructure(true);
					rejecto.setStartCoordinates(new Coordinates(i, j));
				}
				obstructionInfoList.add(rejecto);

			}
		}
		return obstructionInfoList;

	}
}
