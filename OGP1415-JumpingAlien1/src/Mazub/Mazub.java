package Mazub;
import be.kuleuven.cs.som.annotate.*;

public class Mazub {
	
	//test
	
	public Mazub(int posx, int posy, int height, int width, double maxvelx, double initvelx, double dt) throws OutOfBoundsException {
		if (posx > 1023){
			throw new OutOfBoundsException(1023);
		} else if (posx < 0){
			throw new OutOfBoundsException(0);
		} else{
			this.setPosX(posx);
		}
		if (posy > 767){
			throw new OutOfBoundsException(767);
		} else if (posy < 0){
			throw new OutOfBoundsException(0);
		} else{
			this.setPosY(posy);
		}
		
		this.StandingHeight = height;
		this.setHeight(height);
		this.setWidth(width);
		
		
		
		if ((maxvelx >= initvelx) && (initvelx >= 1.0)){
			this.MaxVelocityX= maxvelx;
			this.setMaxVelocityXCurr(maxvelx);
			this.InitVelocityX= initvelx;
		} else{
			throw new IllegalArgumentException();
		}
		
		if ((dt > 0.0) && (dt < 0.2)){
			this.deltaT= dt;
		} else{
			throw new IllegalArgumentException();
		}

	}

	// Methods
	
	public void startMove(boolean direction){
		if (direction == true){
			this.setVelocityX(this.getInitVelocityX());

		}
		if (direction == false){
			this.setVelocityX(-1*this.getInitVelocityX());}

			
		double dt= this.getDeltaT();
		
		while (Math.abs(this.getVelocityX()) < this.getMaxVelocityXCurr() ){
			if (direction == true){
				this.setVelocityX( this.getVelocityX() + this.getAccXFwd() * dt );
			} else{
				this.setVelocityX( this.getVelocityX() - this.getAccXBkw() * dt );
			}
			
			try {
				Thread.sleep((long) (dt * 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
	}
	public void endMove(){
		this.setVelocityX(0.0);
	}
	
	public void startJump(){
		this.setVelocityY(INITVELOCITYY);
	}
	public void endJump(){
		if (this.getVelocityY() > 0.0){
			this.setVelocityY(0.0);
		}
	}
	
	public void controlFall(){
		int posy = this.getPosY();
		
		if (posy > 0){
			this.setAccYCurr(ACCY);
		}
		if (posy == 0){
			this.setAccYCurr(0.0);
			this.setVelocityY(0.0);
		}
	}
	
	//TODO defensive?
	public void startDuck(){
		this.setHeight(this.getStandingHeight()/2);
		this.setMaxVelocityXCurr(1.0);
	}
	public void endDuck(){
		this.setHeight(this.getStandingHeight());
		this.setMaxVelocityXCurr(getMaxVelocityX());
	}

	public void advanceTime(){

		boolean direction = this.getOrientation();
		double dt = this.getDeltaT();
		
		int posx = this.getPosX();
		int posy = this.getPosY();
		double velx = this.getVelocityX();
		double vely = this.getVelocityY();
		double accy = this.getAccYCurr();
				
		if (direction == true){
			// accx is positive (m/s)
			// velx is positive (m/s)
			// s in cm (1 pixel = 1 cm)
			
			double accx = this.getAccXFwd();
			int s= (int) (100*(velx * dt + 0.5*accx*Math.pow(dt,2)));
			if (isValidPosX(posx) && isValidPosX(posx + s))
				this.setPosX( posx + s);
			else{
				throw new OutOfBoundsException(posx + s);
			}
			
			if (velx < this.getMaxVelocityXCurr()){
				this.setVelocityX( velx + accx * dt );
			}
			
			
		} else{

			// accx is positive (m/s)
			// velx is negative (m/s)
			// s in cm (1 pixel = 1 cm), making s negative (moving to the left)
			
			double accx = this.getAccXBkw();
			int s= (int) (100*(velx * dt - 0.5*accx*Math.pow(dt,2)));
			
			if (isValidPosX(posx) && isValidPosX(posx + s))
				this.setPosX( posx + s);
			else{
				throw new OutOfBoundsException(posx + s);
			}
			
			if (velx < this.getMaxVelocityXCurr()){
				this.setVelocityX( velx - accx * dt );
			}
		}
		
		
		// vely and accy can be positive or negative => same formula for both cases
		
		int s= (int) (100*(vely * dt + 0.5*accy*Math.pow(dt,2)));
		if (isValidPosY(posy) && isValidPosY(posy + s))
			this.setPosY( posy + s);
		else{
			throw new OutOfBoundsException(posy + s);
		}
		this.setVelocityY( vely + accy * dt );
		
	

	}

	
	// Getters & setters

	@Basic
	public int getPosX() {
		return this.PosX;
	}
	public void setPosX(int posx) throws OutOfBoundsException {
		if (PosX > 1023){
			throw new OutOfBoundsException(1023);
		} else if (PosX < 0){
			throw new OutOfBoundsException(0);
		} else {
			this.PosX = posx;
		}	
	}
	
	@Basic
	public int getPosY() {
		return this.PosY;
	}
	public void setPosY(int posy) throws OutOfBoundsException {
		if (posy > 1023){
			throw new OutOfBoundsException(1023);
		} else if (posy < 0){
			throw new OutOfBoundsException(0);
		} else {
			this.PosY = posy;
		}
	}
	
	public boolean isValidPosX(int posx){
		return ((posx >=0) && (posx <= 1023));
	}
	public boolean isValidPosY(int posy){
		return ((posy >=0) && (posy <= 767));
	}
	
	@Basic
	public int getHeight() {
		return Height;
	}
	public void setHeight(int height) {
		this.Height= height;
	}
	
	@Basic
	public int getWidth() {
		return Width;
	}
	public void setWidth(int width) {
		this.Width= width;
	}
	@Basic @Immutable
	public int getStandingHeight() {
		return StandingHeight;
	}

	@Basic
	public boolean getOrientation() {
		return Orientation;
	}
	public void setOrientation(boolean orientation) {
		Orientation = orientation;
	}
	@Basic
	public double getDeltaT() {
		return deltaT;
	}
	
	@Basic
	public double getVelocityX() {
		return VelocityX;
	}
	public void setVelocityX(double velocityX) {
		VelocityX = velocityX;
	}
	
	@Basic
	public double getVelocityY() {
		return VelocityY;
	}
	public void setVelocityY(double velocityY) {
		VelocityY = velocityY;
	}
	
	@Basic @Immutable
	public double getInitVelocityX() {
		return InitVelocityX;
	}
	
	@Basic @Immutable
	public double getMaxVelocityX() {
		return MaxVelocityX;
	}
	
	@Basic
	public double getMaxVelocityXCurr() {
		return MaxVelocityXCurr;
	}

	public void setMaxVelocityXCurr(double maxVelocityXCurr) {
		MaxVelocityXCurr = maxVelocityXCurr;
	}


	@Basic
	public double getAccXFwd() {
		return AccXFwd;
	}
	@Basic
	public double getAccXBkw() {
		return AccXBkw;
	}
	
	@Basic
	public double getAccYCurr() {
		return AccYCurr;
	}
	public void setAccYCurr(double accYCurr) {
		AccYCurr = accYCurr;
	}
	
	
	// Variables

	private int PosX;
	private int PosY;
	private int Height;
	private int Width;
	private final int StandingHeight;
	
	/**
	 * Orientation = true: right
	 * 				false: left
	 */
	private boolean Orientation = true;
	private final double deltaT;
	
	private double VelocityX;
	private final double InitVelocityX;
	private final double MaxVelocityX;
	private double MaxVelocityXCurr;
	
	private double VelocityY;
	private static final double INITVELOCITYY = 8.0;
	
	private static double AccXFwd;
	private static double AccXBkw;
	private double AccYCurr;
	private static final double ACCY = -10.0;

	////////////////////////////////////////////////////////
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String args[]){
	     AccXFwd = 1.0;
	     AccXBkw = 0.5;
	     
	}











	

	
	
	
	
	
	

	
	
	
	
	

}
