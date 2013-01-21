package org.jubis.trispace.control;

import org.newdawn.slick.Input;

/**
 * Keyboard listener for commands to control only the game.
 * This listener reacts for example when the user wants to exit the game.
 */
public class GameKeyListener extends KeyAdapter {
	
	/**
	 * Creates a new listener.
	 * 
	 * @param input The input to be listened
	 * @param control Receiver for the game command to be sent
	 */
	public GameKeyListener( Input input, TriSpaceControl control ) {
		super( input, control );
	}
	
	/**
	 * Forwards all the commands for right methods in the handler.
	 */
	@Override
	public void keyPressed( int key, char c ) {
		switch( key ) {
			case Input.KEY_ESCAPE:
				this.control.toMenu();
		}
	}
}
