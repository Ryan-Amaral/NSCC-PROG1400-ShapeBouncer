package shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;

import application.ShapesPanel;

/**
 * Called AShape to not get mixed up with existing classes.
 * @author Ryan
 *
 */
public abstract class AShape {
	
	// position from the top left
	protected int xPos;
	protected int yPos;
	// position from the center 
	protected int centerXPos;
	protected int centerYPos;
	// how much the shape moves each tick
	protected int xVelocity;
	protected int yVelocity;
	// radius to collide from
	protected int collisionRadius;
	// certain amount of hits removes the object
	protected int hitsLeft;
	// gets velocity added each time
	protected int totalXFromStart = 0;
	protected int totalYFromStart = 0;
	protected Color color;
	protected Color color2;
	protected Color color3;
	protected Color color4;
	
	protected Random random = new Random();
	
	protected static final int DEFAULT_HITS = 20; // default amount of hits before disappearing
	
	protected static Color colors[] = {Color.RED, Color.ORANGE, Color.YELLOW,
			Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.PINK,
			Color.WHITE};
	
	// identifiers for shapes
	private static final int RECTANGLE = 0;
	private static final int OVAL = 1;
	private static final int TRIANGLE = 2;
	private static final int RECTANGLE_ROUNDED = 3;
	private static final int CUBE = 4;
	
	protected static final int AXIS_X = 0;
	protected static final int AXIS_Y = 1;
	
	/**
	 * Creates one of AShapes inherited classes casted as AShape.
	 * @return
	 */
	public static AShape createAShape(int shapeNum, int xPos, int yPos){
		AShape aShape = null; // shape to return
		switch(shapeNum){
			case RECTANGLE:
				aShape = (AShape) new Rectangle(xPos, yPos);
				break;
			case OVAL:
				aShape = (AShape) new Oval(xPos, yPos);
				break;
			case TRIANGLE:
				aShape = (AShape) new Triangle(xPos, yPos);
				break;
			case RECTANGLE_ROUNDED:
				aShape = (AShape) new RectangleRounded(xPos, yPos);
				break;
			case CUBE:
				aShape = (AShape) new Cube(xPos, yPos);
				break;
			default: // default to rectangle if invalid number
				aShape = (AShape) new Rectangle(xPos, yPos);
				break;
		}
		return aShape;
	}
	
	protected abstract void draw(Graphics2D g, ShapesPanel sp);
	
	/**
	 * Return the index of the shape we collided with. -1 is no collion.
	 * @param sp
	 * @return
	 */
	protected int detectCollision(List<AShape> movingShapes){
		int collidedIndex = -1;
		// iterate through other shapes until our first collision
		for(int i = 0; i < movingShapes.size(); i++){
			// use the distance formula to determine how close the shapes are
			int initialDistance = (int)Math.sqrt(Math.pow(Math.abs(centerXPos - movingShapes.get(i).centerXPos), 2) +
					Math.pow(Math.abs(centerYPos - movingShapes.get(i).centerYPos), 2));
			// don't collide with self
			if(initialDistance != 0){
				int collisionDetectionDistance = collisionRadius + movingShapes.get(i).collisionRadius;
				// collision with a shape
				if(initialDistance - collisionDetectionDistance < 0){
					collidedIndex = i;
					break; // only count first collision
				}
			}
		}
		return collidedIndex;
	}
	
	public void hit(){
		hitsLeft--;
	}
	
	public void update(Graphics2D g, ShapesPanel sp){
		//System.out.println(centerXPos);
		// draw the shape
		draw(g, sp);
		// check for collisions
		List<AShape> movingShapes = sp.getMovingShapes();
		int collision = detectCollision(movingShapes);
		// update stats
		if(collision >= 0){ // swap velocity for other shapes velocity for bouncing
			int tempX = xVelocity;
			int tempY = yVelocity;
			xVelocity = movingShapes.get(collision).xVelocity;
			yVelocity = movingShapes.get(collision).yVelocity;
			movingShapes.get(collision).xVelocity = tempX;
			movingShapes.get(collision).yVelocity = tempY;
			hit(); // one less hit left
			movingShapes.get(collision).hit();
			// remove this object if it has less than one hits
			if(hitsLeft <= 0){
				movingShapes.remove((AShape) this);
			}
		}
		// for updating physical center collisions
		centerXPos += xVelocity;
		centerYPos += yVelocity;
		// for updating total movement for translating in jpanel
		totalXFromStart += xVelocity;
		totalYFromStart += yVelocity;
		
		wallBounce(sp); // move away from any walls that we are to close to.
	}
	
	/**
	 * Detect if this shape is outside of the wall, if so, make it move the other direction.
	 */
	protected void wallBounce(ShapesPanel sp){
		// make sure each wall is atleast as far away as the collision radius
		// on left wall
		if (centerXPos - collisionRadius <= 0){
			changeDirection(AXIS_X, true);
		}
		// top wall
		if (centerYPos - collisionRadius <= 0){
			changeDirection(AXIS_Y, true);
		}
		// right wall
		if (centerXPos + collisionRadius >= sp.getWidth()){
			changeDirection(AXIS_X, false);
		}
		// bottom wall
		if (centerYPos + collisionRadius >= sp.getHeight()){
			changeDirection(AXIS_Y, false);
		}
	}
	
	/**
	 * Changes the direction of velocity on a single axis. If toPositive is true,
	 * the velocity will change to a positive one.
	 * @param gt0
	 */
	protected void changeDirection(int axis, boolean toPositive){
		if(axis == AXIS_X){
			if(toPositive){
				if(xVelocity < 0){
					xVelocity = -xVelocity;
				}else if(xVelocity == 0){
					xVelocity = 3;
				}
			}else{
				if(xVelocity > 0){
					xVelocity = -xVelocity;
				}else if(xVelocity == 0){
					xVelocity = -3;
				}
			}
		}else if(axis == AXIS_Y){
			if(toPositive){
				if(yVelocity < 0){
					yVelocity = -yVelocity;
				}else if(yVelocity == 0){
					yVelocity = 3;
				}
			}else{
				if(yVelocity > 0){
					yVelocity = -yVelocity;
				}else if(yVelocity == 0){
					yVelocity = -3;
				}
			}
		}
	}

	public int getTotalXFromStart() {
		return totalXFromStart;
	}

	public int getTotalYFromStart() {
		return totalYFromStart;
	}

	public int getCenterXPos() {
		return centerXPos;
	}

	public int getCenterYPos() {
		return centerYPos;
	}

	public int getXVelocity() {
		return xVelocity;
	}

	public int getYVelocity() {
		return yVelocity;
	}
	
}
