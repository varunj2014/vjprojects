package bliffoscope.src;

import java.util.List;

/**
 * Interface that defines behavior of Bliffoscope problem, provides method to process Shape.
 * 
 * @author Varun
 *
 */
public interface IBliffoscope {

	/**
	 * This method detect and process the tragets of AbstractObstructionShape type.
	 * @param shape
	 */
	List<ObstructionInfo> detectAndProcesTargets(AbstractObstructionShape shape);
	
	
}
