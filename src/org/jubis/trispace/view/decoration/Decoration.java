package org.jubis.trispace.view.decoration;

import org.jubis.trispace.spaceobject.feature.Drawable;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Shape;

/**
 * Any kind of decorative or informative thing drawn on the screen that is
 * not part of the game. Use this for example to show avatar's status.
 */
public abstract class Decoration implements Drawable {
	/**
	 * Position of the left top corner of the decoration.
	 */
	protected Point leftTopPosition;
	/**
	 * Font that can be used in the subclass
	 */
	protected UnicodeFont font = Util.createFont( 10 );

	/**
	 * Creates a new Decoration to given position
	 * 
	 * @param position Left top corner of the Decoration
	 */
	public Decoration( Point position ) {
		this.leftTopPosition = position;
	}
	
	/**
	 * Returns given shape so that its left top corner is in the position
	 * determined in this class.
	 * 
	 * @param shape Any shape to be translated
	 * 
	 * @return Given shape translated
	 */
	protected Shape getShapeInPosition( Shape shape ) {
		Point position = this.leftTopPosition.translate( 
		                           new Point ( shape.getCenter() ).negate() );
		return Util.getShapeInPosition( shape, position );
	}

	/**
	 * Forwards the render call without useless parameter onScreenPosition
	 */
	@Override
	public void render( Graphics g, Point onScreenPosition ) {
		this.render( g );
	}
	
	/**
	 * Render the decoration on the screen.
	 * 
	 * @param g Graphics object that is used to draw the decoration
	 */
	public abstract void render( Graphics g );
	
	
}
