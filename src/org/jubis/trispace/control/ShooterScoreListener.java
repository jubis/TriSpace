package org.jubis.trispace.control;

import org.jubis.trispace.spaceobject.feature.Damageable;
import org.jubis.trispace.spaceobject.feature.Shooter;

/**
 * Listener to be notified when certain Shooter has scored.
 * 
 * @see Shooter
 */
public interface ShooterScoreListener {
	/**
	 * Notification that the Shooter has scored the damaged object.
	 * 
	 * @param damaged Scored object
	 * 
	 * @see Damageable
	 */
	public int scored( Damageable damaged );
}
