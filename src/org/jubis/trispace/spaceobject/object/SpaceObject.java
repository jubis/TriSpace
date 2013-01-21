package org.jubis.trispace.spaceobject.object;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Field;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.feature.Collidable;
import org.jubis.trispace.spaceobject.feature.Drawable;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

/**
 * Base for all object that can exist in the game. This class knows all the
 * basic stuff and contains default implementations for all necessary 
 * interfaces.
 *
 */
public abstract class SpaceObject implements Drawable,
											 Collidable {

	/**
	 * Appearance and inner model of the object
	 */
	protected Shape shape;
	/**
	 * Color of the rendered object
	 */
	protected Color color = Color.white;
	/**
	 * Will the shape be filled when drawing or should only the outline be rendered.
	 */
	protected boolean fill;
	/**
	 * Shape to be used when testing if this is colliding
	 */
	protected Shape collisionShape;
	/**
	 * If object wants to be removed it just sets alive = false
	 */
	protected boolean alive = true;
	/**
	 * This is for creating children for this object. This can't be used
	 * if no ObjectAdder was given in constructor.
	 */
	protected Adder adder;
	/**
	 * The system time when the constructor of this object was being finished.
	 */
	protected final long creationTime;
	
	/**
	 * Creates new object with given shape and position.
	 * 
	 * @param shape Inner model for the object
	 * @param position The position the center of the shape will be set to
	 */
	public SpaceObject( Shape shape, Point position ) {
		this.shape = shape;
		this.shape = this.getShapeInPosition( position );
		this.collisionShape = this.shape;
		
		this.creationTime = System.currentTimeMillis();
	}
	
	/**
	 * Creates new object with given shape and position and also makes it
	 * possible to use object adder.
	 * 
	 * @param shape Inner model for the object
	 * @param position The position the center of the shape will be set to
	 * @param adder Adder to be used if this object wants to
	 * 					  create children etc.
	 */
	public SpaceObject( Shape shape, 
	                    Point position, 
	                    Adder adder ) {
		this( shape, position );
		this.adder = adder;
	}

	/**
	 * @return The inner model and appearance of this object
	 */
	public Shape getShape() {
		return this.shape;
	}
	/**
	 * Just returns the collision shape already created during last update.
	 */
	@Override
	public Shape getCollisionShape() {
		return this.collisionShape;
	}
	/**
	 * @return Center of the shape of this position,<br>
	 *  	   same as new Point(getShape.getCenter())
	 */
	public Point getPosition() {
		return new Point( this.shape.getCenter() );
	}
	
	/**
	 * Checks roughly if this and the given object are colliding. This method
	 * does the check using bounding circles so the result is not accurate.<br>
	 * This should be used to optimize collision detection.
	 * 
	 * @param object Object to be checked if it's near this object.
	 * 
	 * @return Is it near or not
	 * 
	 * @see Shape#getBoundingCircleRadius()
	 */
	public boolean isNear( SpaceObject object ) {
		Point thisCenter = new Point( this.getCollisionShape().getCenterX(), 
		                              this.getCollisionShape().getCenterY() );
		Point objectCenter = new Point( object.getCollisionShape().getCenterX(), 
		                                object.getCollisionShape().getCenterY() );
		
		float dx = thisCenter.x - objectCenter.x;
		float dy = thisCenter.y - objectCenter.y;
		
		float radiusSum = this.getCollisionShape().getBoundingCircleRadius() +
						  object.getCollisionShape().getBoundingCircleRadius();
		
		// Pythagoras'
		if( radiusSum*radiusSum > dx*dx + dy*dy ) {
			return true;
		} else {
			return false;
		}
		
	}
	
	private boolean wentThrough( Collidable collider ) {
		if( !( collider instanceof NormalSpaceObject ) ) {
			return false;
		}
		NormalSpaceObject mover = (NormalSpaceObject) collider;
		Line line = Point.toLine( mover.getOldPosition(), mover.getPosition() );
		if( line.intersects( this.shape ) || this.shape.intersects( line ) ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return Should this object be removed
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * In this method the object does everything it periodically does when the
	 * game goes on.
	 * 
	 * @param delta
	 * @param space
	 */
	public void update( int delta, Space space ) {};
	
	/**
	 * Renders the object using its shape and color. Fills the object
	 * depending on the setting. Also draws a black background so that it's
	 * not possible to see "through" the object.
	 */
	@Override
	public void render( Graphics g, Point onScreenPosition ) {
		Shape shape = this.getShapeInPosition( onScreenPosition );
		
		g.setColor( Color.black );
		g.fill( shape );
		
		g.setColor( this.color );
		if( this.fill ) {
			g.fill( shape );
		} else {
			g.draw( shape );
		}
		
	}

	/**
	 * Returns a copy of the shape of this object transformed to given position.
	 * Doesn't change anything itself.
	 * 
	 * @param position Wanted position for the center of the object
	 * 
	 * @return Copy of the shape in given position
	 */
	public Shape getShapeInPosition( Point position ) {
		return Util.getShapeInPosition( this.shape, position );
	}

	/**
	 * Implements the interface method as it should be done. The checking is 
	 * based on the collision shapes.
	 */
	@Override
	public boolean doCollide( Collidable collider ) {
		return Util.isInRange( collider.getCollisionShape(), this.shape ) ||
			   this.wentThrough( collider ) ||
			   this.getCollisionShape().intersects( collider.getCollisionShape() );
		//return true;
	}
	
	/**
	 * General SpaceObject doesn't do anything when colliding
	 */
	@Override
	public void reactToCollision( SpaceObject collider ) {
	}

	/**
	 * Returns if this object wants that different kind of Fields will 
	 * have an effect on it. If the object wants to be picky the parameter
	 * tells what exact Field we are talking about.
	 * 
	 * @param field Reference to the Field so that this method has better basis
	 * 				for its decision.
	 * 
	 * @return Does this object want to be effected on or not
	 */
	public boolean doReactToField( Field<?> field ) {
		return true;
	}
}
