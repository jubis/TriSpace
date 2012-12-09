package org.jubis.trispace.spaceobject;

import org.jubis.trispace.util.Motion;
import org.jubis.trispace.util.Speed;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public abstract class NormalSpaceObject extends SpaceObject implements Movable /* more properties will be added */ {
	protected final Motion motion;

	public NormalSpaceObject() {
		this.motion = new Motion( new Speed( 0, new Vector2f( 0, 1 ) ), 1f/1000 );
	}
	
	@Override
	public Motion getMotion() {
		return motion;
	}

	@Override
	public void changeSpeed( Speed change ) {
		this.motion.speed.sum( change );
	}

	@Override
	public void changeRotation( float angularSpeed ) {
		this.motion.anguralSpeed += angularSpeed;
	}

	@Override
	public void changeSpeed( float change ) {
		this.motion.speed.sum( change );
	}

	@Override
	public void move( int delta ) {
		this.translate( delta );
		this.rotate( delta );
	}
	
	private void rotate( int delta ) {
		Transform t = Transform.createRotateTransform( this.motion.anguralSpeed,
		                                               this.shape.getCenterX(), 
		                                               this.shape.getCenterY() );
		this.shape = this.shape.transform( t );
		this.motion.anguralSpeed = 0;
	}

	private void translate( int delta ) {
		float dx = this.motion.speed.toVector().getX() * delta;
		float dy = this.motion.speed.toVector().getY() * delta;
		Transform t = Transform.createTranslateTransform( dx, dy );
		this.shape = this.shape.transform( t );
	}

}
