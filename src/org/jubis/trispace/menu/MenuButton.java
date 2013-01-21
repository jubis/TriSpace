package org.jubis.trispace.menu;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Simple enumeration to hold information about what buttons should
 * be showed in the menu and how does they look like.
 */
public enum MenuButton {
	NEW_GAME( "menu_new_game_off.png", "menu_new_game.png" ),
	RESUME_GAME( "menu_resume_off.png", "menu_resume.png" ),
	HELP( "menu_help_off.png", "menu_help.png" ),
	EXIT( "menu_exit_off.png", "menu_exit.png" );
	
	/**
	 * Normal image for the button
	 */
	public final Image image;
	/**
	 * Image for the button when the mouse goes over it
	 */
	public final Image hoverImage;

	private MenuButton( String image, String hoverImage ) {
		this.image = loadMenuButtonImage( image );
		this.hoverImage = loadMenuButtonImage( hoverImage );
	}
	
	private static Image loadMenuButtonImage( String file ) {
		try {
			//URL path = TriSpace.class.getResource( "/org/jubis/trispace/resource/" + file );
			//URL path = ResourceLoader.getResource( "resource/" + file );
			return new Image( "resources/" + file );
		} catch ( SlickException e ) {
			e.printStackTrace();
			throw new RuntimeException( "Missing menu button image: " + file );
		}
	}
	
	/**
	 * @return List of buttons in order to be shown when the game is off.
	 */
	public static MenuButton[] buttonsGameOff() {
		return new MenuButton[] { NEW_GAME, HELP, EXIT };
	}
	/**
	 * @return List of buttons in order to be shown when the game is on.
	 */
	public static MenuButton[] buttonsGameOn() {
		return new MenuButton[] { RESUME_GAME, NEW_GAME, HELP, EXIT };
	}
}
