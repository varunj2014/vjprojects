package bliffoscope.src;

/**
 * This class get behavior from {@link AbstractObstructionShape} with type as
 * StarShip
 * 
 * @author Varun
 * 
 */
public class StarShip extends AbstractObstructionShape {

	private ObstructionType type;

	public StarShip(char[][] char2D) {
		super(char2D);
		this.char2D = char2D;
		type = ObstructionType.SPACESHIP;
	}

	@Override
	public ObstructionType getType() {
		return type;
	}

}
