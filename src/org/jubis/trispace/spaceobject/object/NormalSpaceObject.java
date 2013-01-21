package org.jubis.trispace.spaceobject.object;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.feature.Collidable;
import org.jubis.trispace.spaceobject.feature.Damageable;
import org.jubis.trispace.spaceobject.feature.Movable;
import org.jubis.trispace.util.Motion;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Speed;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

/**
 * A bit more special kind of SpaceObject. Because SpaceObject knows already 
 * all the boring stuff this is able to do something more special like moving.<br>
 * This class should be used as a super class for game entities that need all the
 * properties that are included in this.<br>
 * <br>
 * In addition to methods that are part of some implementation this class also
 * contains some methods to help the usage of special features of this class. 
 * 
 * @see SpaceObject
 */
public abstract class NormalSpaceObject extends SpaceObject implements Movable,
																	   Damageable {
	/**
	 * Motion of this object
	 */
	protected Motion motion = 
						new Motion( new Speed( 0, new Vector2f( 0, 1 ) ), 0 );
	private Shape oldShape;

	/**
	 * Creates a new object.
	 * 
	 * @param shape Appearance for the object
	 * @param position Position of the center of the object
	 */
	public NormalSpaceObject( Shape shape, Point position ) {
		this( shape, position, null );
	}
	
	/**
	 * Creates a new object with ability use Adder.
	 * 
	 * @param shape Appearance for the object
	 * @param position Position of the center of the object
	 * @param adder Adder to be used by the object
	 */
	public NormalSpaceObject( Shape shape, Point position, Adder adder ) {
		super( shape, position, adder );
	}
	
//	@Override
//	public void render( Graphics g, Point onScreenPosition ) {
//		g.draw( this.getShapeInPosition( onScreenPosition ) );
//
//		Point newPosition = onScreenPosition.translate( Point.toVector( this.collisionShape.getCenter(), 
//		                                                                this.shape.getCenter() ).negate() );
//		g.draw( Util.getShapeInPosition( this.collisionShape, newPosition ) );
//	}

	/**
	 * Just returns the motion.
	 */
	@Override
	public Motion getMotion() {
		return motion;
	}
	
	/**
	 * @return The last position of this object. Returns the current position
	 * 		   if the object was just created.
	 */
	public Point getOldPosition() {
		if( this.oldShape != null ) {
			return new Point( this.oldShape.getCenter() );
		} else {
			return this.getPosition();
		}
		
	}
	
	/**
	 * Just moves the object
	 */
	@Override
	public void update( int delta, Space space ) {
		this.move( delta );
	}

	@Override
	public void changeSpeed( Speed change ) {
		this.motion.speed.sum( change );
	}
	@Override
	public void changeSpeed( float change ) {
		this.motion.speed.sum( change );
	}
	@Override
	public void changeSpeedBoundToPositive( float change ) {
		this.changeSpeed( change );
		this.motion.speed.setValue( Math.max( this.motion.speed.getValue(), 
		                                      0 ) );
	}
	@Override
	public void changeRotation( float angularSpeed ) {
		this.motion.anguralSpeed += angularSpeed;
	}

	/**
	 * Changes the position and orientation according to its current motion.
	 */
	@Override
	public void move( int delta ) {
		this.oldShape = this.shape;
		this.rotate( delta );
		this.translate( delta );
		this.updateCollisionShape();
	}
	
	/**
	 * Resets this object to be exactly like it was before last update.
	 */
	public void resetMove() {
		if( this.oldShape != null ) {
			this.shape = this.oldShape;
			this.updateCollisionShape();
		}
	}
	
	/**
	 * Updates angle of the object according to its current angular speed.
	 * Does the rotation delta times.
	 * 
	 * @param delta Millis since last update
	 */
	private Transform rotate( int delta ) {
		Transform t = Transform.createRotateTransform( this.motion.anguralSpeed * delta,
		                                               this.shape.getCenterX(), 
		                                               this.shape.getCenterY() );
		this.shape = this.shape.transform( t );
		return t;
	}

	/**
	 * Updates position of the object according to its {@link Speed}.
	 * Does the move delta times.
	 * 
	 * @param delta Millis since last update
	 */
	private Transform translate( int delta ) {
		float dx = this.motion.speed.toVector().getX() * delta;
		float dy = this.motion.speed.toVector().getY() * delta;
		Transform t = Transform.createTranslateTransform( dx, dy );
		this.shape = this.shape.transform( t );
		return t;
	}
	
	@Override
	public void reactToCollision( SpaceObject collider ) {
		this.damageDamageable( collider, 0 );
	}
	
	private void updateCollisionShape() {
		this.collisionShape = this.createCollisionShape();
	}
	
	/**
	 * Creates a new collision shape so that it can be used when the object
	 * is in its current position.
	 * 
	 * @return New updates collision shape 
	 */
	protected Shape createCollisionShape() {
		return this.shape;
	}
	
	/**
	 * This should be called when something is damaging this object.
	 */
	@Override
	public void damage( SpaceObject damager, int damage ) {
	}
	
	/**
	 * Makes this object to damage the collider if it can be damaged.
	 * 
	 * @param collider Object to be damaged
	 * @param damage Damage that will be created to the collider
	 * 
	 * @return Did the damaging really occur
	 */
	protected boolean damageDamageable( Collidable collider, int damage ) {
		if( collider instanceof Damageable ) {
			((Damageable)collider).damage( this, damage );
			return true;
		} else {
			return false;
		}
	}
}
