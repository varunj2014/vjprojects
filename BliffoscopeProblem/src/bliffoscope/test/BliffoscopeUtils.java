package bliffoscope.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Method containing utility method for populating test data.
 * @author Varun
 *
 */
public class BliffoscopeUtils {

	/**
	 * populate the bliffoscope test data in to two dimensional char array.
	 */
	public char[][] populateBliffoscopeData(String fileName) {
		char[][] data = null;
		BufferedReader reader = null;
		try {
			File file = new File(BliffoscopeUtils.class.getResource(fileName)
					.getPath());
			reader = new BufferedReader(new FileReader(file));
			String str;
			List<String> list = new ArrayList<String>();
			while ((str = reader.readLine()) != null) {
				list.add(str);
			}

			String[] array = new String[list.size()];
			list.toArray(array);
			data = new char[array.length][];
			for (int i = 0; i < array.length; i++) {
				data[i] = array[i].toCharArray();
			}
		} catch (Exception e) {
			System.out
					.println("Exception occured while populating test data of blifoscope"
							+ e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.out
							.println("IO Exception occured while closing reader stream"
									+ e.getMessage());
				}
			}
		}
		return data;
	}
}
