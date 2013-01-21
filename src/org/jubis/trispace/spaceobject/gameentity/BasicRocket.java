package org.jubis.trispace.spaceobject.gameentity;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.spaceobject.feature.Shooter;
import org.jubis.trispace.spaceobject.object.Rocket;
import org.jubis.trispace.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * Kind of default Rocket type. Appearance is only a line with length of 10px.
 * To make it easier to hit using BasicRocket, the collision shape is a circle
 * with the length as its diameter.
 */
public class BasicRocket extends Rocket {
	protected static final int LENGTH = 10;

	/**
	 * Creates new BasicRocket with given parameters.
	 * 
	 * @param position Starting position to be set for the rocket
	 * @param direction Direction of the rocket
	 * @param source Shooter of the rocket
	 * @param adder Adder so that the rocket can create effects
	 */
	public BasicRocket( Point position, 
	                    Vector2f direction, 
	                    Shooter source,
	                    Adder adder,
	                    boolean asDoubleRocket ) {
		super(	createShape( direction ),
				position,
				getGeneralMotion( direction ),
				source,
				adder );
		if( asDoubleRocket ) {
			this.color = new Color( 0.4f, 0.4f, 1f );
		}
	}
	
	private static Shape createShape( Vector2f direction ) {
		direction.normalise().scale( LENGTH );
		return new Line( direction.x, direction.y );
	}

	/**
	 * Returns a small damage
	 */
	@Override
	protected int getDamage() {
		return 1;
	}
	
	@Override
	protected String getExplosionEffectFile() {
		return "basic_rocket.xml";
	}
	
	/**
	 * Creates a circle to be the collision shape of this rocket.
	 */
	@Override
	public Shape createCollisionShape() {
		return new Circle( this.shape.getCenterX(), 
		                   this.shape.getCenterY(), 
		                   LENGTH );
	}
	
//	@Override
//	public void render( Graphics g, Point onScreenPosition ) {
//		g.draw( Util.getShapeInPosition( this.collisionShape, onScreenPosition ) );
//	}
}
