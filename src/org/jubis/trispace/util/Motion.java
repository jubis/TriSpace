package org.jubis.trispace.util;

/**
 * State of the movement of the  object. Because there are no restrictions
 * for the attributes they are both public.
 */
public class Motion {
	/**
	 * Translation factor of the Motion
	 */
	public Speed speed;
	/**
	 * Rotation factor of the Motion
	 */
	public float anguralSpeed;
	
	/**
	 * Just creates new instance
	 * 
	 * @param speed Speed in this Motion
	 * @param angularSpeed Angular speed in this Motion
	 */
	public Motion( Speed speed, float angularSpeed ) {
		this.speed = speed;
		this.anguralSpeed = angularSpeed;
	}
	
	/**
	 * More informative declaration of this object
	 */
	public String toString() {
		return this.speed.toString() + "::" + this.anguralSpeed;
	};
}
