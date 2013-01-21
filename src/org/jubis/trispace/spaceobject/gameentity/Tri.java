package org.jubis.trispace.spaceobject.gameentity;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.spaceobject.object.SpaceShip;
import org.jubis.trispace.spaceobject.object.SpaceShipStatus;
import org.jubis.trispace.util.Motion;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Power;
import org.jubis.trispace.util.Speed;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ConfigurableEmitter.SimpleValue;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Default avatar<br>
 * Little triangular SpaceShip
 */
public class Tri extends SpaceShip {
	private static final float[] TRI_MODEL = { 0,0,0,25,50,12.5f };
	
	private static final SpaceShipStatus TRI_DEFAULT_STATUS = new SpaceShipStatus( 300, 1 );
	
	private ParticleSystem motorEffect;
	private boolean motor;
	
	/**
	 * Creates new Tri to given starting point.
	 * 
	 * @param start First position of the Tri
	 */
	public Tri( Point start, Adder adder, boolean isAvatar ) {
		super( new Polygon( TRI_MODEL ), 
		       start,
		       adder,
		       Tri.getPower(),
		       new SpaceShipStatus( TRI_DEFAULT_STATUS ),
		       isAvatar );
		
	}	
	
	
	private static Power getPower() {
		return new Power( 3.0f/1000, 1.0f/1000, 5.0f/1000 );
	}

	/**
	 * With Tri the acceleration direction is the direction from the center
	 * of the triangle to the sharpest angle.
	 */
	@Override
	protected Vector2f getDirection() {
		Vector2f direction = Point.toVector( this.shape.getCenter(),
		                                     this.shape.getPoint( 2 ) );
		return direction;
	}
	
	/**
	 * The point that the explosive is born when shooting
	 */
	@Override
	protected Point getBarrel() {
		return new Point( this.shape.getPoint( 2 ) );
	}
	
	/**
	 * If Tri is accelerating, motor will be set on (shows the effect)
	 */
	@Override
	public void changeSpeed( boolean accelerate, int delta ) {
		this.motor = accelerate;
		super.changeSpeed(	accelerate,
							delta );
	}
	
	/**
	 * The motor effect has to be set to right direction etc in ever update.
	 */
	@Override
	public void update( int delta, Space space ) {
		super.update( delta, space );
		
		if( this.alive ) {
			this.motor();
		}
	}
	
	private void motor() {
		if( this.motorEffect == null ) {
			this.motorEffect = Util.loadParticleSystem( "tri_motor.xml", 
	                                                    this.getPosition() );
			this.adder.addParticleSystem( this.motorEffect );
		}
		
		this.motorEffect.setVisible( this.motor );
		this.motor = false;
		
		this.motorEffect.setPosition( this.getPosition().x, this.getPosition().y );
		ConfigurableEmitter emitter = (ConfigurableEmitter)this.motorEffect.getEmitter(0);
		float motorDirection = (float)(this.getDirection().getTheta() - 90);
		((SimpleValue)emitter.angularOffset).setValue( motorDirection );
	}
	
	/**
	 * If Tri dies, it stops and explodes.
	 */
	@Override
	public void damage( SpaceObject damager, int damage ) {
		super.damage( damager, damage );
		
		if( this.status.getHealth() <= 0 ) {
			this.motion = new Motion( new Speed(), 0 );
			this.explosion();
			this.motorEffect.setVisible( false );
		}
	}
	
	private void explosion() {
		ParticleSystem explosionEffect = Util.loadParticleSystem( "tri_explosion.xml", 
																  this.getPosition() );
		this.adder.addParticleSystem( explosionEffect );
	}
}
