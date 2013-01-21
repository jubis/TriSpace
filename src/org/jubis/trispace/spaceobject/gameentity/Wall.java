package org.jubis.trispace.spaceobject.gameentity;

import org.jubis.trispace.spaceobject.feature.Damageable;
import org.jubis.trispace.spaceobject.object.NormalSpaceObject;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Speed;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
/**
 * Bouncy rectangle that stays still in the Space
 */
public class Wall extends SpaceObject implements Damageable {
	/**
	 * Creates new Wall with given size and position.
	 * 
	 * @param width Width of the Wall
	 * @param height Height of the Wall
	 * @param position Position that will be set as the center of the Wall
	 */
	public Wall( float width, float height, Point position ) {
		super( createShape( width, height ), position );
	}

	/**
	 * Creates the shape (a rectangle) for new Wall
	 * 
	 * @param width Width of the shape
	 * @param height Height of the shape
	 * 
	 * @return Rectangle with given size
	 */
	private static Shape createShape( float width, float height ) {
		return new Rectangle( 0, 0, width, height );
	}
	
	/**
	 * Bounces away the collider.
	 */
	@Override
	public void reactToCollision( SpaceObject object ) {
		NormalSpaceObject collider = (NormalSpaceObject) object;
		Speed colliderSpeed = collider.getMotion().speed;
		Shape wallShape = this.shape;
		
//		System.out.println( collider.getClass().getSimpleName() + collider.hashCode() + 
//		                    " collided to " +
//							this.getClass().getSimpleName() + this.hashCode() );
		
		// we want to be sure that object can't do anything that causes it to
		// be inside or collide with this or it is put back to where it was
		collider.resetMove();
		
		Point center = new Point( object.getShape().getCenter() );
		
		Vector2f direction = colliderSpeed.getDirection();
		if( Util.isInRange( center.x, wallShape.getMinX(), wallShape.getMaxX() ) ) {
			// its either on top or bellow - negate vertical speed
			direction.y = -direction.y;
		} else if( Util.isInRange( center.y, wallShape.getMinY(), wallShape.getMaxY() ) ) {
			// its on either side - negate horizontal speed
			direction.x = -direction.x;
		} else {
			// it has collided to wall corner - 
			// gives the object speed away from the corner
			Point corner = null;
			if( center.x < wallShape.getMinX() && center.y < wallShape.getMinY() ) {
				corner = new Point( wallShape.getPoint( 0 ) );
			} else if( center.x > wallShape.getMaxX() && center.y < wallShape.getMinY() ) {
				corner = new Point( wallShape.getPoint( 1 ) );
			} else if( center.x > wallShape.getMaxX() && center.y > wallShape.getMaxY() ) {
				corner = new Point( wallShape.getPoint( 2 ) );
			} else if( center.x < wallShape.getMaxX() && center.y > wallShape.getMaxY() ) {
				corner = new Point( wallShape.getPoint( 3 ) );
				
			}
			direction = Point.toVector( corner, center ).normalise();
		}
		
		colliderSpeed.setDirection( direction );
		
		// to avoid eternal bouncing: reduce speed a bit per each collision
		colliderSpeed.setValue( colliderSpeed.getValue() * 1 - 1f/100 );
	}

	@Override
	public void damage( SpaceObject damager, int damage ) {
	}
}
