package org.jubis.trispace.spaceobject.factory;

import java.util.List;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.gameentity.Rock;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Motion;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Speed;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.geom.Vector2f;

/**
 * ObjectFactory that knows how and where the Rocks should be created.
 * 
 * @see Rock
 */
public class RockFactory extends ObjectFactory<Rock> {
	private int rockSize = 10;
	
	/**
	 * Creates new RockFactory
	 * 
	 * @param objects List of objects that are already in model
	 * @param space Space in which the Rocks well be added to
	 * @param objectAdder Adder to be given for the Rocks
	 */
	public RockFactory( List<SpaceObject> objects,
	                    Space space, 
	                    Adder objectAdder ) {
		super( objects, space, objectAdder );
	}
	
	public void setRockSize( int rockSize ) {
		this.rockSize = rockSize;
	}
	
	/**
	 * Creates new Rock with generated position and motion.
	 */
	@Override
	public Rock createNew() {
		Point position = this.generatePositionForNewRock();
		Motion motion = this.generateMotionForNewRock( position );
		Rock rock = new Rock( position, 
		                      Util.randomInt( this.rockSize-2, this.rockSize+2 ), 
		                      motion, 
		                      this.adder,
		                      this );
		return rock;
	}

	/**
	 * Generates a semi-random position for a new Rock. 
	 * The rock will be on the edge of the Space.
	 * 
	 * @return Point to be set for the new Rock.
	 * 
	 * @see Space
	 */
	private Point generatePositionForNewRock() {
		Point position = Point.randomPoint( 0, (int)this.space.getWidth(), 
		                                    0, (int)this.space.getHeight() );
		int random = Util.randomInt( 0, 3 );
		switch( random ) {
			case 0:
				position = new Point( 0, position.y );
				break;
			case 1:
				position = new Point( position.x, 0 );
				break;
			case 2:
				position = new Point( this.space.getWidth(), position.y );
				break;
			case 3:
				position = new Point( position.x, this.space.getHeight() );
				break;
		}
		return position;
	}
	
	/**
	 * Generates a semi-random motion for a new Rock. The direction of the 
	 * Speed is toward the center of the Space +/- 90 degrees
	 * 
	 * @param position Position where the Rock will be added
	 * 
	 * @return Proper Motion for the new Rock
	 * 
	 * @see Speed
	 */
	private Motion generateMotionForNewRock( Point position ) {
		float speed = Util.randomFloat( 0.2f, 0.5f );
		float angularSpeed = speed/100;
		
		Point spaceCenter = new Point( this.space.getWidth()/2, 
                                       this.space.getHeight()/2 );
		
		// first set direction toward space center
		// then add some randomness (+/- 90)
		Vector2f direction = Point.toVector( position, spaceCenter );
		direction.setTheta( direction.getTheta() + Util.randomFloat( -90, 90 ) );
		
		return new Motion( new Speed( speed, direction ), angularSpeed );
	} 

}
