package org.jubis.trispace.spaceobject.feature;

import org.jubis.trispace.util.Motion;
import org.jubis.trispace.util.Speed;

/**
 * Object type that can fly in the space.
 * The movement of the object is based on its current Motion.
 * </p>
 * Basically changing methods are accessors for the Motion.
 * Move method is to update the position and angle of the object
 * according to its motion. 
 */
public interface Movable {
	/**
	 * Get the current {@link Motion} of this object
	 * 
	 * @return Objects {@link Motion}
	 */
	public Motion getMotion();
	
	/**
	 * Adds given speed to current speed.<br>
	 * Changes both direction and value.
	 * 
	 * @see Speed
	 * 
	 * @param change Speed to be added
	 */
	public void changeSpeed( Speed change );
	/**
	 * Adds given speed to current speed.<br>
	 * Changes only the value direction stays as it is.
	 * 
	 * @see Speed
	 * 
	 * @param change Speed to be added
	 */
	public void changeSpeed( float change );
	/**
	 * Adds given speed to current speed.<br>
	 * Changes only the value and direction stays as it is.<br>
	 * By using this the speed can never get negative.
	 * 
	 * @see Speed
	 * 
	 * @param change Speed to be added
	 */
	public void changeSpeedBoundToPositive( float change );
	
	/**
	 * Adds given angular speed to current rotation.
	 * Directions are according to radian directions 
	 * 
	 * @param angularSpeed Value to be added to current angular speed
	 */
	public void changeRotation( float angularSpeed );
	
	/**
	 * Does the actual moving according to current {@link Motion}.
	 * Does everything delta times.
	 * 
	 * @param delta Time in millis since last update
	 */
	public void move( int delta );
}
