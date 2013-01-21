package org.jubis.trispace.spaceobject.feature;

import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.newdawn.slick.geom.Shape;

/**
 * Object that can collide with other Collidable objects. 
 * Collidable knows if its colliding and what should it does for
 * objects it collides with. All Collidable objects can also determine 
 * separate collision shape to be used in collision detection.
 */
public interface Collidable {
	/**
	 * Returns the special collision shape for collision detection
	 * of this object.
	 * 
	 * @return Collision shape
	 */
	public Shape getCollisionShape();
	
	/**
	 * Checks if given Collidable object is colliding with this one.
	 * Collision happened if:
	 * 
	 * 1. Collision shapes intersects
	 * 2. Either is inside the other
	 * 3. Line drawn from the last position of the collider to current
	 *    position intersects with this object. 
	 * 
	 * @param collider Another object that could collide with this one
	 * 
	 * @return Did the collision occur
	 */
	public boolean doCollide( Collidable collider );

	/**
	 * When the collision has occurred the Collidable reacts to the collision
	 * and does what ever it wants for the collider. In this method the
	 * magic should happen.
	 * 
	 * @param collider Object that certainly has collided with this one
	 */
	public void reactToCollision( SpaceObject collider );
}
