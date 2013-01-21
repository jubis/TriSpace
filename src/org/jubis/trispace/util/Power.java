package org.jubis.trispace.util;

import org.jubis.trispace.spaceobject.object.SpaceShip;

/**
 * Accelerating, braking and rotating ability of a SpaceShip
 * 
 * @see SpaceShip
 */
public class Power {
	/**
	 * Accelerating power, (pixels/ms^2)
	 */
	private float acceleration;
	/**
	 * Braking power
	 */
	private float braking;
	/**
	 * Amount of rotation the SpaceShip is capable to
	 */
	private float rotation;
	
	/**
	 * Creates a new instance with given parameters.
	 * 
	 * @param acceleration Acceleration power
	 * @param braking Braking power
	 * @param rotation Amount of rotation
	 */
	public Power( float acceleration, float braking, float rotation ) {
		this.acceleration = acceleration;
		this.braking = braking;
		this.rotation = rotation;
	}
	
	/**
	 * @return Acceleration power
	 */
	public float getAcceleration() {
		return acceleration;
	}
	/**
	 * @return Braking powet
	 */
	public float getBraking() {
		return braking;
	}
	/**
	 * @return Amount of rotation
	 */
	public float getRotation() {
		return rotation;
	}
}
