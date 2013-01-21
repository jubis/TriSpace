package org.jubis.trispace.spaceobject.object;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.spaceobject.feature.Shooter;
import org.jubis.trispace.util.Motion;
import org.jubis.trispace.util.Point;
import org.newdawn.slick.geom.Shape;

/**
 * Simply an explosive that has ability to move.
 */
public abstract class Rocket extends Explosive {

	/**
	 * Creates a new rocket with given motion.
	 * 
	 * @param shape Appearance of this explosive (also default for collision shape)
	 * @param position Position where the center of the shape will be set to
	 * @param motion Starting motion
	 * @param source The game entity that created this explosion
	 * @param adder Adder so that new effects can be added
	 */
	public Rocket( Shape shape, 
	               Point position, 
	               Motion motion, 
	               Shooter source,
	               Adder adder ) {
		super( shape, position, source, adder );
		this.motion = motion;
	}

}
