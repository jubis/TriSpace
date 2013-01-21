package org.jubis.trispace.spaceobject.feature;


/**
 * Game entity that is able to shoot. Shooter is also notified it it scores.
 */
public interface Shooter {
	/**
	 * Tells the Shooter to shoot next time its updated.
	 */
	public void shoot();
	/**
	 * This method is called when Shooter has scored and the given Damageable
	 * entity was the object.
	 * 
	 * @param damaged The game entity that was damaged by this shooter
	 * 
	 * @see Damageable
	 */
	public void score( Damageable damaged );
}
