package org.jubis.trispace.spaceobject;

import org.jubis.trispace.util.Power;
import org.jubis.trispace.util.Speed;
import org.newdawn.slick.geom.Vector2f;

public abstract class SpaceShip extends NormalSpaceObject {
	protected float minSpeed;
	protected float maxSpeed;
	protected Power power;
	
	public SpaceShip( Power power ) {
		this.power = power;
	}
	
	public void changeSpeed( boolean accelerate ) {
		float valueChange = 0;
		Vector2f direction = this.getDirection();
		if( accelerate ) {
			valueChange = this.power.getAcceleration();
		} else {
			valueChange = this.power.getBraking();
			direction = direction.negate();
		}
		
		this.changeSpeed( new Speed( valueChange, 
		                             direction ) );
	}

	public void changeRotation( boolean clockwise ) {
		if( clockwise ) {
			this.changeRotation( -this.power.getRotation() );
		} else {
			this.changeRotation( this.power.getRotation() );
		}
		
	}
	
	protected abstract Vector2f getDirection();
}