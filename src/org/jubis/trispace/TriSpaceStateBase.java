package org.jubis.trispace;

import java.util.ArrayList;
import java.util.List;

import org.jubis.trispace.control.TriSpaceControl;
import org.jubis.trispace.model.TriSpaceModel;
import org.jubis.trispace.view.TriSpaceView;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The state system for the application.
 */
public class TriSpaceStateBase extends StateBasedGame {
	/**
	 * State ID for the game
	 */
	public final static int GAME_STATE = 0;
	/**
	 * State ID for the menu
	 */
	public final static int MENU_STATE = 1;
	
	private List <GameState> states = new ArrayList <GameState> ();
	
	/**
	 * Creates a new instance setting the game and the menu as states
	 * to this application.
	 * 
	 * @param game The game state
	 * @param menu The menu state
	 */
	public TriSpaceStateBase( TriSpaceGame game, TriSpaceMenu menu ) {
		super( "TriSpace" );
		this.states.add( GAME_STATE, game );
		this.states.add( MENU_STATE, menu );
	}
	
	/**
	 * Just adds states and enters the menu.
	 */
	@Override
	public void initStatesList( GameContainer container ) throws SlickException {
		this.addState( this.states.get( MENU_STATE ) );
		this.enterState( MENU_STATE );
	}
	
	/**
	 * Forward the given game components for the game itself and enters the 
	 * game state.
	 * 
	 * @param model new model component for the game
	 * @param view new view component for the game
	 * @param control new controller component for the game
	 * @param container the container of this game (shouldn't change)
	 */
	public void newGame( TriSpaceModel model, 
	                     TriSpaceView view, 
	                     TriSpaceControl control,
	                     GameContainer container ) {
		((TriSpaceGame) this.states.get( GAME_STATE )).newGame( model, view, control, 
		                                                        container,
		                                                        this );
		
		if( this.getStateCount() == 1 ) {
			this.addState( this.states.get( GAME_STATE ) );
		}
	}

}
