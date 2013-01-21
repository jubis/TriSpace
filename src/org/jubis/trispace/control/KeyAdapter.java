package org.jubis.trispace.control;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Adapter class to carry out all the boring stuff for the actual KeyListeners.
 * Implements already all the methods so they only have to be overwritten.
 * 
 * Documentation mostly in {@link KeyListener} in library.
 */
public abstract class KeyAdapter implements KeyListener {
	/**
	 * The Input this listener is attached to
	 */
	protected Input input;
	/**
	 * The main game control, probably used by subclasses
	 */
	protected TriSpaceControl control; 

	/**
	 * Creates a new listener.
	 * 
	 * @param input The input to be listened
	 * @param control Control to be set ready for the subclasses
	 */
	public KeyAdapter( Input input, TriSpaceControl control ) {
		this.input = input;
		this.control = control;
		this.input.addKeyListener( this );
	}
	
	/**
	 * Changes the input and does the re-adding
	 */
	@Override
	public void setInput( Input input ) {
		if( this.input != null ) {
			this.input.removeKeyListener( this );
		}
		this.input = input;
		if( input != null ) { 
			this.input.addKeyListener( this );
		}
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public void keyPressed( int key, char c ) {}

	@Override
	public void keyReleased( int key, char c ) {}

}
