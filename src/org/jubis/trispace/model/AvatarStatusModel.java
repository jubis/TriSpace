package org.jubis.trispace.model;

import org.jubis.trispace.spaceobject.object.SpaceShip;

/**
 * Special kind of {@link GameStatusModel} that has something to do with
 * the avatar. Quite a handy code to take care of the avatar stuff.
 * 
 * @param <T> Type of the value this model contains
 */
public abstract class AvatarStatusModel<T> extends GameStatusModel<T> {
	protected SpaceShip avatar;

	/**
	 * Creates a new instance.
	 * @param avatar Avatar to be available in the subclasses
	 */
	public AvatarStatusModel( SpaceShip avatar ) {
		this.avatar = avatar;
	}
	
	/**
	 * Changes the avatar in use
	 * @param avatar New avatar
	 */
	public void setAvatar( SpaceShip avatar ) {
		this.avatar = avatar;
	}
	
	/**
	 * Is the any avatar set, that is does this model have any up-to-date 
	 * information yet. 
	 * @return Is avatar se or not
	 */
	public boolean isAvatar() {
		return this.avatar != null;
	}
	
	/**
	 * The update is done only if the avatar is set
	 */
	@Override
	public void update() {
		if( this.avatar != null ) {
			super.update();
		}
	}
}
