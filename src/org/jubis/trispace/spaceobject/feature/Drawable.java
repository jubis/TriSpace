package org.jubis.trispace.spaceobject.feature;

import org.jubis.trispace.util.Point;
import org.jubis.trispace.view.TriSpaceView;
import org.newdawn.slick.Graphics;

/**
 * Object type that draws itself when render is called.
 * The drawing can be based on some shape but virtually Drawable can
 * draw what ever it wants on the screen. 
 * 
 * If Drawable is used as a game entity the {@link TriSpaceView} has some 
 * opinion about where it should be positioned on the screen.
 */
public interface Drawable {
	/**
	 * In this method Drawable draws itself. 
	 * Game entity objects don't know their positions on the screen 
	 * so it has to be given.<br>
	 * The position is just for help. It doesn't have to be used if
	 * there is no reason to do so.
	 * 
	 * @param g Graphics object used for actual drawing
	 * @param onScreenPosition Coordinates for the figure to be painted
	 */
	public void render( Graphics g, Point onScreenPosition );
}
