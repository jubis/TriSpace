package org.jubis.trispace.spaceobject.factory;

import java.util.List;
import java.util.Random;

import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.gameentity.Home;
import org.jubis.trispace.spaceobject.gameentity.Wall;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Point;

/**
 * Factory to create totally random Walls to the Space. To sure that one
 * of the Walls is the {@link Home} of the avatar, Home is created as the first
 * Wall.
 * 
 * @see Home
 */
public class WallFactory extends ObjectFactory<Wall> {

	/**
	 * Creates a new factory and stores all the given parameters.
	 * 
	 * @param objects List of all objects in the model
	 * @param space Space in which given objects will be added
	 */
	public WallFactory( List<SpaceObject> objects, Space space ) {
		super( objects, space, null );
	}
	
	/**
	 * Creates a random sized wall in a random position.
	 */
	@Override
	protected Wall createNew() {
		Random rand = new Random();
		Point position = Point.randomPoint( 0, (int)this.space.getWidth(), 
		                                    0, (int)this.space.getWidth() );
		if( this.getCounter() == 0 ) {
			return new Home( 150, 150, position, 50, 100 );
		} else {
			return new Wall( rand.nextInt( 200 ), 
			                 rand.nextInt( 200 ), 
			                 position );
		}
	}
	
	/**
	 * In addition to other requirements, Walls must be created completely 
	 * inside the Space.
	 * 
	 * @see Space#isCompletelyInsideSpace(SpaceObject)
	 */
	@Override
	protected boolean isProper( Wall object ) {
		return super.isProper( object ) && 
			   this.space.isCompletelyInsideSpace( object );
	}

}
