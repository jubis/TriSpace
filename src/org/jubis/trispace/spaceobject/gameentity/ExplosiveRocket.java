package org.jubis.trispace.spaceobject.gameentity;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.feature.Shooter;
import org.jubis.trispace.spaceobject.feature.Wanted;
import org.jubis.trispace.spaceobject.object.Explosive;
import org.jubis.trispace.spaceobject.object.Rocket;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Motion;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Speed;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

/**
 * Rocket that explodes by itself or by hitting something. When it has 
 * exploded it damages one object that hits the explosion.
 */
public class ExplosiveRocket extends Rocket {
	private static final int EXPLOSION_RADIUS = 50;
	private static final int EXPLOSION_DURATION = 600;
	private static final float[] ROCKET_MODEL = { 0,0, 0,5, 10,2.5f };
	
	private final int lifetime = Util.randomInt( 50, 300 );
	private boolean exploded;
	private long explosionTime;
	private boolean effectUsed;

	/**
	 * Creates a new rocket.
	 * 
	 * @param position Starting position to be set for the rocket
	 * @param direction Direction of the rocket
	 * @param source Shooter of the rocket
	 * @param adder Adder so that the rocket can create effects
	 */
	public ExplosiveRocket(	Point position,
	                       	Vector2f direction,
							Shooter source, 
							Adder adder ) {
		super( createShape( direction ), 
		       position, 
		       Explosive.getGeneralMotion( direction ), 
		       source, 
		       adder );
		this.color = Color.orange;
	}
	
	private static Shape createShape( Vector2f direction ) {
		Shape shape = new Polygon( ROCKET_MODEL );
		float rotation = (float) (Math.PI* direction.getTheta()/180);
		Transform t = Transform.createRotateTransform( rotation );
		return shape.transform( t ); 
	}
	
	/**
	 * Determines if the rocket should explode or if the explosion is over
	 */
	@Override
	public void update( int delta, Space space ) {
		super.update( delta, space );
		
		if( ! this.exploded &&
			timeHasPassed( this.creationTime + this.lifetime ) ) {
			this.bigExplosion( false );
		}
		if( this.exploded && 
			timeHasPassed( this.explosionTime + EXPLOSION_DURATION ) ) {
			this.alive = false;
		}
	}
	
	/**
	 * Wont be removed when collides but explodes first.
	 */
	@Override
	public void reactToCollision( SpaceObject collider ) {
		if( collider instanceof ExplosiveRocket ) { 
			return;
		}
		super.reactToCollision( collider );
		if( ! this.alive && !(collider instanceof Wanted) ) {
			this.alive = true;
			this.bigExplosion( true );
		}
	}
	
	private void bigExplosion( boolean collision ) {
		if( ! this.exploded ) {
			this.exploded = true;
			this.explosionTime = System.currentTimeMillis();
			this.motion = new Motion( new Speed(), 0 );
			this.shape = new Circle( this.getPosition().x, 
			 			             this.getPosition().y, 
						             1 );
			if( ! collision ) {
				this.explode();
			}
		}
	}
	
	/**
	 * The explosion effect will be created only once even if the explosion
	 * last for some time.
	 */
	@Override
	protected void explode() {
		if( ! this.effectUsed ) {
			super.explode();
			this.effectUsed = true;
		}
	}
	
	@Override
	protected String getExplosionEffectFile() {
		return "explosive_rocket.xml";
	}
	
	private static boolean timeHasPassed( long moment ) {
		return System.currentTimeMillis() > moment;
	}

	/**
	 * The collision shape is a circle. During the explosion its a big circle.
	 */
	@Override
	public Shape createCollisionShape() {
		int radius = BasicRocket.LENGTH;
		if( this.exploded ) {
			radius = EXPLOSION_RADIUS;
		}
		return new Circle( this.getPosition().x, 
		                   this.getPosition().y, 
		                   radius );
	}
	
	@Override
	protected int getDamage() {
		return 1;
	}
	
}
