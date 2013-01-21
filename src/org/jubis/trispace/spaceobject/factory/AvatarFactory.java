package org.jubis.trispace.spaceobject.factory;

import java.util.List;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.gameentity.Tri;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.spaceobject.object.SpaceShip;
import org.jubis.trispace.util.Point;
import org.newdawn.slick.geom.Vector2f;

/**
 * Factory to create the one and only avatar to the game. The avatar is
 * created as close the set position as possible.
 * 
 * @see SpaceShip
 */
public class AvatarFactory extends ObjectFactory<Tri> {
	private Point lastPosition;
	private Vector2f lastDirection;
	private int circleSize;

	/**
	 * Create new AvatarFactory
	 * 
	 * @param objects List of all the objects already in the model
	 * @param space Space in which avatar will be added
	 * @param objectAdder Adder to be given for the avatar
	 */
	public AvatarFactory( List<SpaceObject> objects, 
	                      Space space,
	                      Adder objectAdder) {
		super( objects, space, objectAdder );
	}
	
//	public void setPosition( Point position ) {
//		this.position = position;
//	}

	/**
	 * Creates new avatar as close the Space center as possible.
	 * 
	 * @see Space
	 */
	@Override
	protected Tri createNew() {
		this.circleSize++;
		
		Point position;
		Vector2f direction = this.lastDirection;
		if( this.lastPosition == null ) {
			position = new Point( this.space.getWidth()/2, 
								  this.space.getHeight()/2 );
			direction = new Vector2f( 0, 1 );
		} else {
			double theta = direction.getTheta();
			direction.setTheta( theta + 360 / this.circleSize );
			position = this.lastPosition.translate( direction );
		}
		
		this.lastPosition = position;
		this.lastDirection = direction;
		
		Tri tri = new Tri( position, this.adder, true );
		
		return tri;
	}

}
