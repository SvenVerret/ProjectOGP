package Mazub;
import be.kuleuven.cs.som.annotate.*;

public class OutOfBoundsException extends RuntimeException{

	/**
     * Initialize new out of bounds exception with given position.
     * 
     * @param   position
     *          The position given for the out of bounds exception.
     * @post    The position of the new out of bounds exception is set
     *          to the given position.
     *          | new.getPosition() == position
     */
    public OutOfBoundsException(int position) {
        this.position = position;
    }
    
    /**
     * Return the position registered for the out of bounds exception.
     */
    @Basic 
    public int getPosition() {
		return position;
	}

	/**
     * Variable registering the position involved in this out of bounds exception.
     * 
     */
	private final int position;


    /**
     * The Java API strongly recommends to explicitly define a version
     * number for classes that implement the interface Serializable.
     * At this stage, that aspect is of no concern to us. 
     */
    private static final long serialVersionUID = 2003001L;

}
