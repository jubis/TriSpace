package org.jubis.trispace.util;

public class Power {
	private float acceleration;
	private float braking;
	private float rotation;
	
	public Power( float acceleration, float braking, float rotation ) {
		this.acceleration = acceleration;
		this.braking = braking;
		this.rotation = rotation;
	}
	
	public float getAcceleration() {
		return acceleration;
	}
	
	public float getBraking() {
		return braking;
	}
	
	public float getRotation() {
		return rotation;
	}
	
//	public void accelerate( Motion motion ) {
//		motion.speed.value += this.acceleration;
//	}
//	
//	public void brake( Motion motion ) {
//		motion.speed.value -= braking;
//	}
//	
//	public void rotate( Motion motion ) {
//		motion.anguralSpeed = rotation;
//	}
}
