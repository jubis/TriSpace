package org.jubis.trispace.spaceobject.gameentity;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.spaceobject.feature.Shooter;
import org.jubis.trispace.spaceobject.object.Rocket;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * Circle shaped rocket that doesn't explode when it hits a Wall but instead
 * bounces away.
 */
public class BouncyRocket extends Rocket {
	private static final float RADIUS = 5;
	
	/**
	 * Creates a new rocket.
	 * 
	 * @param position Starting position to be set for the rocket
	 * @param direction Direction of the rocket
	 * @param source Shooter of the rocket
	 * @param adder Adder so that the rocket can create effects
	 */
	public BouncyRocket( Point position, 
	                     Vector2f direction, 
	                     Shooter source,
	                     Adder adder ) {
		super( new Circle( 0, 0, RADIUS ), 
		       position, 
		       getGeneralMotion( direction ), 
		       source,
		       adder );
		this.fill = true;
		this.color = Color.green;
	}
	
	@Override
	protected int getDamage() {
		return 1;
	}
	
	@Override
	protected String getExplosionEffectFile() {
		return "bouncy_rocket.xml";
	}
	
	/**
	 * Creates a circle to be the collision shape of this rocket.
	 */
	@Override
	public Shape createCollisionShape() {
		return new Circle( this.shape.getCenterX(), 
		                   this.shape.getCenterY(), 
		                   RADIUS * 4 );
	}
	
	/**
	 * The BounyRocket wont explode normally if it hits a Wall.
	 */
	@Override
	public void reactToCollision( SpaceObject collider ) {
		if( ! (collider instanceof Wall) ) {
			super.reactToCollision( collider );
		}
		
	}

}
