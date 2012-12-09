package org.jubis.trispace.spaceobject;

import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Power;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

/**
 * 
 * 
 * @author Matias
 *
 */
public class Tri extends SpaceShip {
	public static final float[] TRI_MODEL = { 0,0,0,25,50,12.5f };
	
	public Tri( Point start ) {
		super( Tri.getPower() );
		this.position = start;
		this.shape = new Polygon( TRI_MODEL );
	}
	
	public static Power getPower() {
		return new Power( 1.0f/1000, 1.0f/1000, 5.0f/1000 );
	}

	@Override
	protected Vector2f getDirection() {
		Vector2f direction = Point.toVector( this.shape.getCenter(),
		                                     this.shape.getPoint( 2 ) );
		return direction.normalise();
	}
}
