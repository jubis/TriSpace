package org.jubis.trispace.control;

import org.jubis.trispace.spaceobject.object.SpaceShip;
import org.newdawn.slick.Input;

/**
 * {@link AvatarControl} takes care of all commands that are for avatar:
 * moving, shooting etc.
 */
public class AvatarControl extends KeyAdapter {
	private SpaceShip avatar;
	
	/**
	 * Creates a new instance to control given avatar with through given input.
	 * 
	 * @param avatar SpaceShip to be controlled
	 * @param input Input source to be listened
	 */
	public AvatarControl( SpaceShip avatar, Input input ) {
		super( input, null );
		this.avatar = avatar;
	}
	
	/**
	 * Replaces the avatar.
	 * @param avatar New avatar to be controlled
	 */
	public void setAvatar( SpaceShip avatar ) {
		this.avatar = avatar;
	}
	
	/**
	 * This method compiles all key inputs into commands
	 */
	@Override
	public void keyPressed( int key, char c ) {
		switch( key ) {
			case Input.KEY_SPACE:
				this.avatar.shoot();
		}
		
		if( Character.isDigit( c ) ) {
			int number = Integer.parseInt( Character.toString( c ) );
			this.avatar.setExplosiveInUse( number );
		}
	}
	
	/**
	 * Checks if any controlling key is pressed and
	 * compiles the input into command.
	 * 
	 * The actual pressing or releasing of the key doesn't matter,
	 * the method just checks if the key is pressed during this update. 
	 * 
	 * All changes are multiplied delta time.
	 * 
	 * @see SpaceShip#changeSpeed(boolean, int)
	 * @see SpaceShip#changeRotation(boolean)
	 * 
	 * @param delta Time in millis since last update
	 */
	public void controlAvatarMovement( int delta ) {
		
		// to help the player to control the avatar, the avatar only
		// rotates for one update and then stops rotating
		this.avatar.getMotion().anguralSpeed = 0;
		
		if( this.input.isKeyDown( Input.KEY_UP ) ) {
			this.avatar.changeSpeed( true, delta );
		} else if( this.input.isKeyDown( Input.KEY_DOWN ) ) {
			this.avatar.changeSpeed( false, delta );
		}
		if( this.input.isKeyDown( Input.KEY_LEFT ) ) {
			this.avatar.changeRotation( true );
		} else if( this.input.isKeyDown( Input.KEY_RIGHT ) ) {
			this.avatar.changeRotation( false );
		}
	}

}
