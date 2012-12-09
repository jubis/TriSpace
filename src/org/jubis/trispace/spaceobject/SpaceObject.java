package org.jubis.trispace.spaceobject;

import org.jubis.trispace.util.Point;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * Base for all object that can be drawn in the game.
 * 
 * @author Matias
 *
 */
public abstract class SpaceObject implements Drawable {
	/**
	 * Inner model of the object
	 */
	protected Shape shape;
	protected Point position;
	
	/**
	 * Creates new object with given shape and position.
	 * 
	 * @param shape Inner model for the object
	 * @param start Starting coordinates for the object
	 */
	public SpaceObject() {
		this.shape = null;
		this.position = new Point( 0, 0 );
	}
	
	public Point getPosition() {
		return position;
	}
	
	@Override
	public void render( Graphics g, Point onScreenPosition ) {
		g.draw( this.getShapeInPosition( onScreenPosition ) );
	}

	private Shape getShapeInPosition( Point onScreenPosition ) {
		Transform t = Transform.createTranslateTransform( onScreenPosition.x, 
		                                                  onScreenPosition.y );
		Shape moved = this.shape.transform( t );
		return moved;
	}
}
