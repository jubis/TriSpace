package org.jubis.trispace.menu;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Handy class to take care all the stuff around the game logo showed in menu.
 */
public final class Logo {
	private Image image;
	private float width;
	private float height;
	private float x;
	private float y;
	
	/**
	 * Creates a new instance with given setting.
	 * 
	 * @param file The file where the logo image is located
	 * @param width Width of the logo (pixels)
	 * @param height Height of the logo (pixels)
	 * @param y Vertical coordinate for the top of the logo
	 * @param appWidth The width of the application window
	 * 
	 * @throws SlickException Thrown if loading the image wasn't successful.
	 */
	public Logo( String file, 
	             float width, 
	             float height,
	             float y,
	             float appWidth ) throws SlickException {
		//URL path = ResourceLoader.getResource( "resource/logo.png" );
		this.image = new Image( "resources/logo.png" );
		this.width = width;
		this.height = height;
		this.x = this.getX( appWidth );
		this.y = y;
	}
	
	private float getX( float appWidth ) {
		return appWidth/2 - this.width/2;
	}
	
	/**
	 * Draws the logo image with settings given in constructor.
	 */
	public void draw() {
		this.image.draw( this.x, this.y, this.width, this.height );
	}
}
