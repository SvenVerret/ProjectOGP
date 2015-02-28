package Mazub;
import be.kuleuven.cs.som.annotate.*;

public class WrongDimensionException extends RuntimeException{

	public WrongDimensionException(int height, int width) {
        this.height = height;
        this.width = width;
    }

	@Basic @Immutable
    public int getHeight() {
		return height;
	}
	
	@Basic @Immutable
	public int getWidth() {
		return width;
	}

	private final int height;
	private final int width;
	private static final long serialVersionUID = 2003001L;

}
