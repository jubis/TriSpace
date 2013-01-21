package org.jubis.trispace.spaceobject.feature;

import org.jubis.trispace.spaceobject.object.SpaceObject;

/**
 * Any object that can be damaged.
 */
public interface Damageable {
	/**
	 * Something has damaged this object
	 * 
	 * @param damager The object that damaged this object
	 * @param damage The amount of damage the damager created to this
	 */
	public void damage( SpaceObject damager, int damage );
}
