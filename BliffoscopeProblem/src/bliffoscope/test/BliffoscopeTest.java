package bliffoscope.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;

import bliffoscope.src.*;

/**
 * Test File that create test data by converting to 2 dimensional char array for
 * the following:
 * <p>
 * - Bliffoscope test data.
 * </p>
 * <p>
 * - SlimeTorpedo Test Data.
 * </p>
 * <p>
 * - Space Ship Test data.
 * </p>
 * Also, use {@link Bliffoscope} instance to process the generated 2 dimensional
 * char array and process the results by showing the shape detected, coordinates
 * and confidence level. test data and save the results in the output file.
 * 
 * @author Varun
 * 
 */
public class BliffoscopeTest {

	// file name contaiing the output results
	private static final String RESULTS_OUTPUT_FILENAME = "results.txt";
	// file name for Blifoscope test data
	private static String BLIFFOSCOPE_TEST_DATA_FILE_NAME = "TestData.blf";
	// file name for SlimeTorpedo test data
	private static String SLIME_TORP_TEST_DATA_FILE_NAME = "SlimeTorpedo.blf";
	// file name for star ship test data
	private static String STARSHIP_TEST_DATA_FILE_NAME = "Starship.blf";

	private static BliffoscopeUtils bliffoscopeUtils = new BliffoscopeUtils();

	/**
	 * Default Constructor
	 */
	public BliffoscopeTest() {

	}

	/**
	 * This method loads the test data of different obstructions(rejectos) and
	 * create instance of {@link AbstractObstructionShape} and pass it to the
	 * {@link Bliffoscope} object to process the
	 * {@link AbstractObstructionShape} in the test data. The result is returned
	 * in the form of list which is being processed and written in results.txt
	 * file, that contains the confidence level of the structure, coordinates ,
	 * type of structure found.
	 * 
	 * @param blifoscope
	 */
	private static void run(IBliffoscope blifoscope) {
		char[][] starShipTestData = bliffoscopeUtils
				.populateBliffoscopeData(STARSHIP_TEST_DATA_FILE_NAME);
		char[][] torpeTestData = bliffoscopeUtils
				.populateBliffoscopeData(SLIME_TORP_TEST_DATA_FILE_NAME);
		if (blifoscope == null) {

		}
		AbstractObstructionShape slimeTorpedo = new SlimeTorpedo(torpeTestData);
		AbstractObstructionShape ship = new StarShip(starShipTestData);
		// process slime torpedo in the bliffoscope test data
		List<ObstructionInfo> slimeTorpedoList = blifoscope
				.detectAndProcesTargets(slimeTorpedo);
		// process slimetorpedo
		List<ObstructionInfo> shipList = blifoscope
				.detectAndProcesTargets(ship);
		BufferedWriter bw = null;
		try {
			File resultFile = new File(RESULTS_OUTPUT_FILENAME);
			if (resultFile.exists()) {
				resultFile.delete();
			}
			resultFile.createNewFile();

			FileOutputStream fos = new FileOutputStream(resultFile);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			// process SlimeTorpedo List
			processRejectosList(slimeTorpedoList, bw);
			// process ship List
			processRejectosList(shipList, bw);

			System.out.println("Please check File " + resultFile.getName()
					+ " located at path " + resultFile.getAbsolutePath()
					+ " for complete results");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * process the list of ObstructionInfo list and print out the confidence
	 * level of the Obstruction, type of structue and start coordinates of the
	 * obstruction.
	 * 
	 * Result would be displayed based on ascending order of confidence level
	 * 
	 * @param obstructionInfoList
	 * @param bw
	 * @throws IOException
	 */
	private static void processRejectosList(
			List<ObstructionInfo> obstructionInfoList, BufferedWriter bw)
			throws IOException {
		// sorting list based on default sorting implementaion
		Collections.sort(obstructionInfoList);

		for (ObstructionInfo info : obstructionInfoList) {
			System.out
					.println("Structure Type Found: " + info.getType().name());
			bw.write("Structure Type Found: " + info.getType().name());
			bw.newLine();
			bw.write("Coordinates of the structure are saved in the file : ");
			System.out.println();
			bw.newLine();
			for (Coordinates coordinate : info.getCoordinateList()) {
				bw.write("(" + coordinate.getX() + "," + coordinate.getY()
						+ ")");

			}
			bw.newLine();

			bw.write("Confidence level that matches to  "
					+ info.getType().name() + " is :"
					+ info.getConfidencePercentage());
			bw.newLine();
			System.out.println("Confidence level that matches to  "
					+ info.getType().name() + " is :"
					+ info.getConfidencePercentage());

			if (info.isCompleteStructure()) {
				bw.write("Complete Structure found : " + info.getType().name());
				bw.newLine();
				System.out.println("Complete Structure found: "
						+ info.getType().name());
				bw.write("Start coordinates of this stucture has x coordinate as  : "
						+ info.getStartCoordinates().getX()
						+ " and y coordinate as  : "
						+ info.getStartCoordinates().getY());
				bw.newLine();
			}
			bw.write("----------------------------------------");
			bw.newLine();
			System.out.println("----------------------------------------");

		}
	}

	/**
	 * Entry point of the test program to get solution of the bliffoscope
	 * problem.
	 * 
	 * * Confidence level in percentage like "50" or "60" and it should be less
	 * than "100" should be passed as command line arguments, this filters out
	 * the structure having confidencel percentage greater than confidence
	 * level. Else, all the neutrinous encounters will be analyzed even, if it
	 * does not match the {@link AbstractObstructionShape}.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int confidenceLevel = 0;

		if (args.length == 0) {
			System.out
					.println("Please pass command line arguments to specify confidence level, "
							+ "by default complete list would be shown");
		}
		if (args.length > 0 && (args[0] != null || args[0] != "")) {
			try {
				confidenceLevel = Integer.parseInt(args[0]);
			} catch (NumberFormatException exception) {
				System.out
						.println("Not a valid confidence level being passed in command line");
				System.out.println("Please try again with value less than 100");
				return;
			}
		}
		System.out.println("Targets with confidence level greater than "
				+ confidenceLevel + " would be found out");
		char[][] testData = bliffoscopeUtils
				.populateBliffoscopeData(BLIFFOSCOPE_TEST_DATA_FILE_NAME);
		// create the instance of IBliffoscope with concrete implementation
		IBliffoscope bliffoscope = new Bliffoscope(confidenceLevel, testData);
		try {
			run(bliffoscope);
		} catch (Exception e) {
			System.out
					.println("Exception occurred while running Bliffoscope Test :"
							+ e.getMessage());
		}
	}
}
