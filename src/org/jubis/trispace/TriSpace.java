package org.jubis.trispace;

import org.jubis.trispace.control.TriSpaceControl;
import org.jubis.trispace.model.TriSpaceModel;
import org.jubis.trispace.view.TriSpaceView;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Main class for the application.
 * Most of the functionalities are carried out by 
 * extending AppGameContainer.
 */
public class TriSpace extends AppGameContainer {
	private boolean gameOn;
	
	/**
	 * Creates a new container for the game and set some basic settings.
	 * 
	 * @param stateBase StateBasedGame instance containing the game and the menu 
	 * @param fullscreen If the app will be windowed or not
	 * 
	 * @throws SlickException General exception of the library
	 */
	private TriSpace( TriSpaceStateBase stateBase, 
	                  boolean fullscreen ) throws SlickException {
		super( stateBase );

		if( fullscreen ) {
			this.setDisplayMode( this.getScreenWidth(),
			                     this.getScreenHeight(), 
			                     true );
		} else {
			this.setDisplayMode( this.getScreenWidth()-20,
			                     this.getScreenHeight()-40, 
			                     false );
		}
		this.setVSync( true );
		this.setShowFPS( false );
		
		// game refreshrate is synced to vertical screen refreshrate
		//this.setVSync( true );
	}
	
	/**
	 * @return Has new game started yet after game over (or app start)
	 */
	public boolean isGameOn() {
		return this.gameOn;
	}
	
	/**
	 * Reinitializes all the game components and starts a new game 
	 * (only the game not the whole app)
	 */
	public void newGame() {
		this.gameOn = true;
		
		TriSpaceModel model = new TriSpaceModel();
		TriSpaceView view = new TriSpaceView( model );
		TriSpaceControl control = new TriSpaceControl( model, view );
		
		((TriSpaceStateBase)this.game).newGame( model, view, control, this );
		((TriSpaceStateBase)this.game).enterState( TriSpaceStateBase.GAME_STATE );
	}
	
	/**
	 * Makes it impossible to return to current game anymore and 
	 * goes back to menu.
	 */
	public void gameOver() {
		this.gameOn = false;
		((TriSpaceStateBase)this.game).enterState( TriSpaceStateBase.MENU_STATE );
	}
	
	/**
	 * Factory method for the game.
	 * Creates all needed components for the game and construct
	 * the game itself.
	 * 
	 * @return New game with new components
	 * 
	 * @throws SlickException Caused when errors creating the game
	 */
	public static TriSpace createTriSpace() throws SlickException {
		TriSpaceGame gameState = new TriSpaceGame();
		TriSpaceMenu menuState = new TriSpaceMenu();
		TriSpaceStateBase stateBase = new TriSpaceStateBase( gameState, 
		                                                     menuState );
		
		return new TriSpace( stateBase, true );
	}

	public static void main( String[] args ) throws SlickException {
		TriSpace app = TriSpace.createTriSpace();
		app.start();
	}
}
