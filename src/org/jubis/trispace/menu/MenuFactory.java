package org.jubis.trispace.menu;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jubis.trispace.TriSpace;
import org.jubis.trispace.TriSpaceStateBase;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;

/**
 * This is where all stuff for the menu is created (buttons and logo).
 */
public class MenuFactory {
	private TriSpace container;
	private TriSpaceStateBase stateBase;
	private Map <MenuButton, ComponentListener> buttonListeners;
	
	private final static float LOGO_HEIGHT = 250;
	private static final float LOGO_WIDTH = 600;
	private final static float LOGO_Y = 70;
	private final static float LOGO_PADDING = 30;
	private final static float BOTTOM_PADDING = 50;

	/**
	 * Creates a new factory.
	 * 
	 * @param container Source for the screen width and height data etc.
	 * @param stateBase This is needed in some buttons to change the state.
	 */
	public MenuFactory( TriSpace container, TriSpaceStateBase stateBase ) {
		this.container = container;
		this.stateBase = stateBase;
		
		this.buttonListeners = this.createButtonListeners();
	}
	
	private Map <MenuButton, ComponentListener> createButtonListeners() {
		Map <MenuButton, ComponentListener> listeners = 
									new HashMap <MenuButton, ComponentListener> ();
		
		listeners.put( MenuButton.NEW_GAME, new ComponentListener() {
			@Override
			public void componentActivated( AbstractComponent source ) {
				container.newGame();
			}
		} );
		listeners.put( MenuButton.RESUME_GAME, new ComponentListener() {
			@Override
			public void componentActivated( AbstractComponent source ) {
				stateBase.enterState( TriSpaceStateBase.GAME_STATE );
			}
		} );
		listeners.put( MenuButton.HELP, new ComponentListener() {
			@Override
			public void componentActivated( AbstractComponent source ) {
				//new Help();
				try {
					Desktop.getDesktop().browse( (new File( "help/help.html" )).toURI() );
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		} );
		listeners.put( MenuButton.EXIT, new ComponentListener() {
			@Override
			public void componentActivated( AbstractComponent source ) {
				System.exit( 0 );
			}
		} );
		
		return listeners;
	}
	
	/**
	 * Creates all the buttons for the menu. The menu can be different whether
	 * the game is on or not.
	 * 
	 * @param gameOn Are these buttons to be used when the game is on
	 * 
	 * @return List of buttons in proper order.
	 */
	public List <MouseOverArea> createMenuButtons( boolean gameOn ) {
		List <MouseOverArea> buttons = this.createButtons( this.container.getWidth(), 
		                                                   this.container.getHeight(), 
		                                                   LOGO_HEIGHT+LOGO_Y+LOGO_PADDING,
		                                                   this.container.getHeight()-BOTTOM_PADDING,
		                                                   gameOn );
		addListenersToButtons( buttons,
		                       this.buttonListeners,
		                       gameOn );
		return buttons;
	}
	
	private List <MouseOverArea> createButtons( int gameWidth, 
	                            float gameHeight, 
	                            float buttonAreaStart,
	                            float buttonAreaEnd,
	                            boolean gameOn ) { 
		List <MouseOverArea> buttons = new ArrayList <MouseOverArea> ();
		int buttonAmount = gameOn ? 4 : 3;
		int buttonWidth = 400;
		int buttonHeight = 80;
		int x = gameWidth/2 - buttonWidth/2;
		float gap = (buttonAreaEnd - buttonAreaStart - buttonAmount*buttonHeight) / (float)buttonAmount;
		
		float y = buttonAreaStart; 
		MenuButton[] buttonTypes = gameOn ? MenuButton.buttonsGameOn() : 
										    MenuButton.buttonsGameOff();
		for( MenuButton buttonType : buttonTypes ) {
			Image image = buttonType.image;
			Image hoverImage = buttonType.hoverImage;
			
			MouseOverArea button = new MouseOverArea( container, 
			                                          image, 
			   		                                  x, (int)y, 
			   		                                  buttonWidth, buttonHeight );
			button.setMouseOverImage( hoverImage );
			
			buttons.add( button );
			
			y += gap + buttonHeight;
		}
		
		return buttons;
	}
	
	private static void addListenersToButtons( List <MouseOverArea> buttons, 
	                                    Map <MenuButton, ComponentListener> listeners,
	                                    boolean gameOn  ) {
		if( gameOn ) {
			buttons.get( 0 ).addListener( listeners.get( MenuButton.RESUME_GAME ) );
		}
		buttons.get( buttons.size()-3 ).addListener( listeners.get( MenuButton.NEW_GAME ) );
		buttons.get( buttons.size()-2 ).addListener( listeners.get( MenuButton.HELP ) );
		buttons.get( buttons.size()-1 ).addListener( listeners.get( MenuButton.EXIT ) );
	}

	/**
	 * Creates the logo instance to be used in the menu.
	 * @return Logo with right setting
	 */
	public Logo createLogo() {
		try {
			return new Logo( "resources/logo.png", 
			                 LOGO_WIDTH, 
			                 LOGO_HEIGHT, 
			                 LOGO_Y, 
			                 this.container.getWidth() );
		} catch ( SlickException e ) {
			System.err.println( "Problems while loading logo" );
			e.printStackTrace();
			return null;
		}
	}
}
