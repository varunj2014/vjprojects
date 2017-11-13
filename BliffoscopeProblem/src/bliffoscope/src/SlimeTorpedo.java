package bliffoscope.src;

/**
 * This class get behavior from {@link AbstractObstructionShape} with type as
 * Torpedo
 * 
 * @author Varun
 * 
 */
public class SlimeTorpedo extends AbstractObstructionShape {

	private ObstructionType type;

	public SlimeTorpedo(char[][] char2D) {
		super(char2D);
		this.char2D = char2D;
		type = ObstructionType.TORPEDO;
	}

	@Override
	public ObstructionType getType() {
		return type;
	}
}