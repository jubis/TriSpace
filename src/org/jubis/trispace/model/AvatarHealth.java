package org.jubis.trispace.model;

import org.jubis.trispace.spaceobject.object.SpaceShip;

/**
 * Model of the health of the avatar
 */
public class AvatarHealth extends AvatarStatusModel<Float> {
	
	public AvatarHealth( SpaceShip avatar ) {
		super( avatar );   
		this.value = 0f ;
	}
	
	
	/**
	 * Loads the value from the avatar.
	 */
	@Override
	protected Float updateValue() {
		if( this.isAvatar() ) {
			return new Float( this.avatar.getStatus().getHealth() );
		} else {
			return null;
		}
	}
	
	/**
	 * @return current health / max health * 100% 
	 */
	public float getHealthPercentage() {
		return this.avatar.getStatus().getHealthPercentage();
	}
}
