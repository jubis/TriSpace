package org.jubis.trispace;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jubis.trispace.menu.Logo;
import org.jubis.trispace.menu.MenuFactory;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The menu of the application. Unlike game everything in menu 
 * (updating, rendering and input handling) is located to this very class.
 */
public class TriSpaceMenu extends BasicGameState {
	
	private List<MouseOverArea> buttons = new ArrayList <MouseOverArea> ();
	private List<MouseOverArea> buttonsGameOff = new ArrayList <MouseOverArea> ();
	private List<MouseOverArea> buttonGameOn = new ArrayList <MouseOverArea> ();
	private Logo logo;
	
	private TriSpaceStateBase stateBase;
	private TriSpace container;
	
	private ParticleSystem stars;
	private ParticleSystem shootinStars;
	
	@Override
	public int getID() {
		return TriSpaceStateBase.MENU_STATE;
	}

	/**
	 * Sets everything to be ready in the menu. This wont be changed while
	 * the application is running.
	 */
	@Override
	public void init( GameContainer container, 
	                  StateBasedGame stateBase ) throws SlickException {
		this.stateBase = (TriSpaceStateBase) stateBase;
		this.container = (TriSpace) container;
		
		MenuFactory menuFactory = new MenuFactory( this.container, this.stateBase );
		this.buttonGameOn = menuFactory.createMenuButtons( true );
		this.buttonsGameOff = menuFactory.createMenuButtons( false );
		this.logo = menuFactory.createLogo();
		
		try {
			this.stars = ParticleIO.loadConfiguredSystem( "resources/stars.xml" );
			this.stars.setPosition( container.getWidth()/2, container.getHeight()/2 );
			this.shootinStars = ParticleIO.loadConfiguredSystem( "resources/shooting_stars.xml" );
			this.shootinStars.setPosition( container.getWidth()/2, container.getHeight()/2 );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the right buttons visible according to whether the game can still
	 * be resumed or not.
	 */
	@Override
	public void enter( GameContainer container, StateBasedGame game ) throws SlickException {
		if( this.container.isGameOn() ) {
			this.buttons = this.buttonGameOn;
		} else {
			this.buttons = this.buttonsGameOff;
		}
		
		
	}

	/**
	 * Renders all the stuff in menu
	 */
	@Override
	public void render( GameContainer container,
						StateBasedGame game,
						Graphics g ) throws SlickException {
		for( MouseOverArea area : this.buttons ) {
			area.render( container, g );
		}
		this.logo.draw();
		this.stars.render();
		this.shootinStars.render();
	}

	/**
	 * Updates the particle-effect on the background.
	 */
	@Override
	public void update( GameContainer container,
						StateBasedGame game,
						int delta ) throws SlickException {
		stars.update( delta );
		shootinStars.update( delta );
	}

}
