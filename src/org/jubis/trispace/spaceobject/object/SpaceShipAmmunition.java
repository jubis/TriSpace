package org.jubis.trispace.spaceobject.object;

import java.util.HashMap;
import java.util.Map;

import org.jubis.trispace.spaceobject.gameentity.AmmoBox;
import org.jubis.trispace.util.AmmoCounter;

/**
 * A class to take care about the ammunition of a SpaceShip
 */
public class SpaceShipAmmunition {
	private Map<ExplosiveType, AmmoCounter> ammunition = 
									new HashMap<ExplosiveType, AmmoCounter>();
	
	/**
	 * Amount of ammo by explosive type. Note that the endless amount will be
	 * indicated by returning the maximum ammo amount.
	 * 
	 * @param type Type which amount is wanted to be returned
	 * 
	 * @return Amount of explosives still available
	 */
	public int getAmount( ExplosiveType type ) {
		if( this.ammunition.get( type ) != null ) {
			return  this.ammunition.get( type ).getAmount();
		} else {
			return 0;
		}
	}
	
	public Map<ExplosiveType, AmmoCounter> getAll() {
		return new HashMap <ExplosiveType, AmmoCounter> ( this.ammunition );
	}
	
	public void insertAmmoBox( AmmoBox box ) {
		if( this.ammunition.containsKey( box.type ) ) {
			this.ammunition.get( box.type ).add( box.getAmount() );
		} else {
			AmmoCounter newCounter = new AmmoCounter( box.getAmount(), box.endless );
			this.ammunition.put( box.type, newCounter );
		}
	}
	
	/**
	 * Uses one ammo of given type.
	 * 
	 * @param type Type of the explosive to be used
	 * 
	 * @return If it was possible to use an ammo or not.
	 */
	public boolean use( ExplosiveType type ) {
		if( this.ammunition.containsKey( type ) ) {
			AmmoCounter counter = this.ammunition.get( type );
			boolean result = counter.reduce();
			if( counter.isEmpty() ) {
				this.ammunition.remove( type );
			}
			return result;
		} else {
			return false;
		}
	}
}
