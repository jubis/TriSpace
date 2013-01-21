package org.jubis.trispace.model;

import java.util.Map;

import org.jubis.trispace.spaceobject.object.ExplosiveType;
import org.jubis.trispace.spaceobject.object.SpaceShip;
import org.jubis.trispace.util.AmmoCounter;

/**
 * The GameStatusModel to contain information about avatar's ammunition status.
 */
public class AvatarAmmunition extends AvatarStatusModel<Map<ExplosiveType,AmmoCounter>> {

	/**
	 * Creates a new instance
	 * @param avatar The SpaceShip which ammunition status will be monitored.
	 */
	public AvatarAmmunition( SpaceShip avatar ) {
		super( avatar );
	}
	
	/**
	 * @return The explosive type used currently by avatar
	 */
	public ExplosiveType getTypeInUse() {
		return this.avatar.getExplosiveTypeInUse();
	}

	/**
	 * Just gets the map from avatar
	 */
	@Override
	protected Map<ExplosiveType, AmmoCounter> updateValue() {
		return this.avatar.getAmmunition();
	}
	
	/**
	 * Easy way to get just one ammunition status at a time.
	 * 
	 * @param type Type of the explosive which status will be returned
	 * 
	 * @return Status of given explosive type
	 */
	public AmmoCounter getValueByType( ExplosiveType type ) {
		if( this.avatar != null && this.value.containsKey( type ) ) {
			return new AmmoCounter( this.value.get( type ) );
		} else {
			return null;
		}
	}

}
