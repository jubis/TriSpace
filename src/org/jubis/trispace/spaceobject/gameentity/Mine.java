package org.jubis.trispace.spaceobject.gameentity;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.feature.Movable;
import org.jubis.trispace.spaceobject.feature.Shooter;
import org.jubis.trispace.spaceobject.object.Explosive;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * Explosive that doesn't move but explodes immediately when object comes too
 * close. However the Mine has to activate itself first.
 */
public class Mine extends Explosive {
	public static final long ACTIVATION_TIME_MILLIS = 3000;
	private boolean	activated;

	/**
	 * Creates a new Mine.
	 * 
	 * @param position Starting position to be set for the Mine
	 * @param source Shooter that left the Mine
	 * @param adder Adder so that the Mine can create effects
	 */
	public Mine( Point position, Shooter source, Adder adder ) {
		super( createShape(),
		       position,
		       source,
		       adder );
		this.color = Color.white;
	}

	private static Shape createShape() {
		Shape shape = new Rectangle( 0, 0, 10, 10 );
		shape = shape.transform( Transform.createRotateTransform( (float) (Math.PI/4), 5, 5 ) );
		return shape;
	}
	
	@Override
	protected int getDamage() {
		return 10;
	}

	/**
	 * Mine's collision shape is a large circle that recognizes if objects
	 * come too close.
	 */
	@Override
	protected Shape createCollisionShape() {
		float radius = 100;
		return new Circle( this.shape.getCenterX(), 
		                   this.shape.getCenterY(), 
		                   radius );
	}
	
	/**
	 * A Mine explodes only if the collider is a moving object and is not another
	 * Mine (technically Mine is moving object cause it's an Explosive).
	 */
	@Override
	public void reactToCollision( SpaceObject collider ) {
		if( collider instanceof Movable && 
			! (collider instanceof Mine) &&
			this.activated ) {
			super.reactToCollision( collider );
		}
	}
	
	@Override
	protected String getExplosionEffectFile() {
		return "mine_explosion.xml";
	}
	
	/**
	 * In addition to the normal update a Mine checks if it can be activated yet.
	 */
	@Override
	public void update( int delta, Space space ) {
		super.update(	delta,
						space );
		
		if( ! this.activated ) {
			this.checkActivation();
		}
	}
	
	private void checkActivation() {
		long timeSinceCreation = System.currentTimeMillis() - this.creationTime;
		float readiness = (float)timeSinceCreation / ACTIVATION_TIME_MILLIS;
		
		if( readiness > 0.5 ) {
			this.color = new Color( 1, ( 1 - readiness ) * 2, 0 );
			
			if( readiness >= 1 ) {
				this.activated = true;
				this.fill = true;
			}
		}
	}
	
}
