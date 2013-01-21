package org.jubis.trispace.model;

import org.jubis.trispace.TriSpace;

/**
 * Special ScreenEffect to be shown when the game is over.
 * In this effect the important thing is the moment the effect is over.
 */
public class GameOver extends ScreenEffect {
	private static final int GAME_OVER_DURATION = 3000;
	
	private TriSpace container;

	/**
	 * Creates a new effect. When this effect is over it will end the game 
	 * in given container. 
	 * 
	 * @param container The container of the game application
	 */
	public GameOver( TriSpace container ) {
		super( "Game Over!", GAME_OVER_DURATION, true );
		this.container = container;
	}
	
	/**
	 * The game will be exitted when this effect is done.
	 */
	@Override
	public void effectIsOver() {
		this.container.gameOver();
	}

}
