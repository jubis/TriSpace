package org.jubis.trispace.control;

import org.jubis.trispace.TriSpace;
import org.jubis.trispace.TriSpaceStateBase;
import org.jubis.trispace.model.AvatarHealth;
import org.jubis.trispace.model.TriSpaceModel;
import org.jubis.trispace.spaceobject.feature.Movable;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.spaceobject.object.SpaceShip;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.view.TriSpaceView;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/**
 * High level component of the application to handle all the messages and
 * commands gotten from the gameloop and the platform of the game.
 */
public final class TriSpaceControl {
	private final TriSpaceModel model;
	private final TriSpaceView view;
	private SpaceShip avatar;
	private TriSpace container;
	private AvatarControl avatarControl;
	private float averageFps;
	private int secs;
	private int deltaSum = 1000;
	private TriSpaceStateBase stateBase;
	private GameKeyListener appKeyListener;
	
	/**
	 * Creates new control to change and update given model and to
	 * control given view.
	 * 
	 * @param model Model to be updates etc
	 * @param view View to be notified about screen positioning
	 */
	public TriSpaceControl( TriSpaceModel model, TriSpaceView view ) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Adds listeners and stuff.
	 * Sets up every detail according to game setting from user.
	 * 
	 * @param container Application the controlled game is in.
	 * @param stateBase The StateBasedGame so that states can be changed
	 */
	public void init( GameContainer container, StateBasedGame stateBase ) {
		this.container = (TriSpace) container;
		this.stateBase = (TriSpaceStateBase) stateBase;
		
		this.avatar = this.model.addAvatar();
		
		this.avatarControl = new AvatarControl( this.avatar, 
		                                        this.container.getInput() );
		this.appKeyListener = new GameKeyListener( container.getInput(), this );
		
		((AvatarHealth) this.model.getModel( AvatarHealth.class ))
							.addListener( new AvatarHealthListener( this ) );
	}
	
	/**
	 * Removes all listeners from the input offered by the game container.
	 */
	public void abandonInput() {
		this.avatarControl.setInput( null );
		this.appKeyListener.setInput( null );
	}
	
	/**
	 * Forwards the general update command for model and
	 * also controls avatar according to user input.
	 * Also does the collision checking every update. 
	 * Calculates and passes forward position for the screen 
	 * according to the position of avatar.
	 * 
	 * @param input The {@link Input} object that is attached to
	 * 				this game
	 * @param delta Time in millis since last update
	 */
	public void update( Input input, int delta ) {
		if( this.avatar != null ) {
			this.avatarControl.controlAvatarMovement( delta );
		}
		
		this.model.update( delta );
		
		this.checkCollisions();
		if( this.avatar != null ) {
			this.view.setScreenPosition( this.calculateScreenPosition() );
		}
		
		this.calculateAvgFps( delta );
	}
	
	private void calculateAvgFps( int delta ) {
		
		int fps = this.container.getFPS();
		if( fps == 0 ) return;
		
		this.deltaSum += delta;
		
		// fps is updated only once a second so the average is changed then only
		if( this.deltaSum >= 1000 ) {
			this.deltaSum = 0;
			float sum = this.averageFps * this.secs;
			this.averageFps = (sum+fps) / (this.secs+1);
			this.secs++;
		}
		
		System.out.println( "fps: " + fps + " avg fps: " + this.averageFps );
	}
	
	/**
	 * Checks if any object has collided with any other object.
	 * Some optimization included
	 */
	public void checkCollisions() {
		
		for( int i = 0; i < this.model.getObjects().size(); i++ ) {
			SpaceObject target = this.model.getObjects().get( i );
			
			if( ! target.isAlive() ) {
				continue;
			}
			
			for( int j = i + 1; j < this.model.getObjects().size(); j++ ) {
				SpaceObject collider = this.model.getObjects().get( j );
				
				if( ! collider.isAlive() ) {
					continue;
				}
				
				// checks if the objects are even near to each other because
				// the actual collision checking is very time demanding
				if( ( collider instanceof Movable || target instanceof Movable )
					&& collider.isNear( target ) ) {
					
					if( collider.doCollide( target ) || 
						target.doCollide( collider ) ) {
						// tell both objects about the collision
						target.reactToCollision( collider );
						collider.reactToCollision( target );
					}
				}
				
				// if target has died, its no use to check its collision anymore
				if( ! target.isAlive() ) {
					break;
				}
			}
		}
	}

	/**
	 * Calculates where the screen should be so that the avater
	 * would be in the middle of the screen.
	 * 
	 * @return Screen top left coordinates
	 */
	private Point calculateScreenPosition() {
		Point screenCenter = new Point( this.container.getWidth()/2,
		                                this.container.getHeight()/2 );
		Point avatarPosition = this.avatar.getPosition();
		// Screen origin is avatar position minus half of the screen.
		Point screenOrigin = avatarPosition.translate( screenCenter.negate() );
		
		return screenOrigin;
	}	
	
	/**
	 * Primitive method to end the game
	 * 
	 * @param cause Something to tell the user
	 */
	public void gameOver( String cause ) {
		this.model.gameOver( this.container );
	}
	
	/**
	 * Just pauses the game and enters the menu.
	 */
	public void toMenu() {
		this.stateBase.enterState( TriSpaceStateBase.MENU_STATE );
	}

}
