package org.jubis.trispace;

import org.jubis.trispace.control.TriSpaceControl;
import org.jubis.trispace.model.TriSpaceModel;
import org.jubis.trispace.view.TriSpaceView;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The game itself. Takes care of the game initialization, updating the game
 * and rendering it. Does all this with the high level components model,
 * view and control. 
 */
public class TriSpaceGame extends BasicGameState {
	private TriSpaceView view;
	private TriSpaceControl control;
	private TriSpaceModel model;
	
	@Override
	public int getID() {
		return TriSpaceStateBase.GAME_STATE;
	}
	
	/**
	 * Receives all the components of the game, resets everything in the game.
	 * Also initializes the game so that it can be started right away.
	 * 
	 * @param model new model component for the game
	 * @param view new view component for the game
	 * @param control new controller component for the game
	 * @param container the container of this game (shouldn't change)
	 * @param stateBase the StateBasedGame instance of this game (shouldn't change)
	 */
	public void newGame( TriSpaceModel model, 
	                     TriSpaceView view, 
	                     TriSpaceControl control,
	                     GameContainer container,
	                     StateBasedGame stateBase ) {
		System.out.println( "###################### new game" );
		
		// input wont change -> the old game's connections to the input 
		// have to be removed
		if( this.control != null ) {
			this.control.abandonInput();
		}
		
		this.model = model;
		this.view = view;
		this.control = control;
		try {
			this.init( container, stateBase );
		} catch ( SlickException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * Calls view, model and control to init themselves.
	 */
	@Override
	public void init( GameContainer container, 
	                  StateBasedGame stateBase ) throws SlickException {
		this.model.init();
		this.control.init( container, stateBase );
		this.view.init( container );
	}

	/**
	 * Forwards update command for control.
	 */
	@Override
	public void update( GameContainer container, 
	                    StateBasedGame stateBase, 
	                    int delta ) throws SlickException {
		this.control.update( container.getInput(), delta );
	}

	/**
	 * Forwards render command for view.
	 */
	@Override
	public void render( GameContainer container, 
	                    StateBasedGame stateBase, 
	                    Graphics g ) throws SlickException {
		this.view.render( container, g );
	}
}
