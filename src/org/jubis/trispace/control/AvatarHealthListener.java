package org.jubis.trispace.control;

import org.jubis.trispace.model.AvatarHealth;

/**
 * Listener for the {@link AvatarHealth} so that the game can be ended
 * when health reaches zero
 */
public class AvatarHealthListener implements GameStatusListener<Float> {
	private TriSpaceControl control;
	
	/**
	 * Creates a new instance to send the command to given control.
	 * 
	 * @param control The control the game over command will be sent
	 */
	public AvatarHealthListener( TriSpaceControl control ) {
		this.control = control;
	}
	
	
	/**
	 * Ends the game if health reaches zero
	 */
	@Override
	public void statusChanged( Float value ) {
		if( value <= 0 ) {
			this.control.gameOver( "avatar died" );
		}
	}
}
