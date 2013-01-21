package org.jubis.trispace.spaceobject.factory;

import java.util.List;

import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.gameentity.AmmoBox;
import org.jubis.trispace.spaceobject.object.ExplosiveType;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Util;

/**
 * Simple factory to create different kind of AmmoBoxes
 * 
 * @see AmmoBox
 */
public class AmmoBoxFactory extends ObjectFactory<AmmoBox> {

	/**
	 * Creates a new factory
	 * 
	 * @param objects All objects currently in the game
	 * @param space The Space where the boxes will be added
	 */
	public AmmoBoxFactory( List<SpaceObject> objects,
						   Space space ) {
		super( objects, space, null );
	}

	/**
	 * Just randomly creates a new AmmoBox
	 */
	@Override
	protected AmmoBox createNew() {
		// any other type but BASIC_ROCKET
		ExplosiveType type = ExplosiveType.values()[
		                   Util.randomInt( 1, ExplosiveType.values().length-1 ) ];
		Point position = Point.randomPoint( 0, (int)this.space.getWidth(), 
		                                    0, (int)this.space.getHeight() );
		return new AmmoBox( type, type.ammoBoxSize, false, position );
	}

}
