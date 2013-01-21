package org.jubis.trispace.spaceobject.object;

import org.jubis.trispace.spaceobject.factory.ExplosiveFactory;
import org.newdawn.slick.Color;

/**
 * Container for all the information related to the explosion types.
 */
public enum ExplosiveType {
	BASIC_ROCKET( "Basic rocket", 
	              ExplosiveFactory.ROCKET_TYPE, 
	              Color.yellow,
	              100 ),
	BOUNCY_ROCKET( "Bouncy rocket", 
	               ExplosiveFactory.ROCKET_TYPE, 
	               Color.green,
	               50 ),
	EXPLOSIVE_ROCKET( "Explosive rocket", 
	                  ExplosiveFactory.ROCKET_TYPE, 
	                  Color.orange,
	                  50 ),
	DOUBLE_ROCKET( "Double rocket", ExplosiveFactory.ROCKET_TYPE, Color.blue,
	               50 ),
	MINE( "Space mine", 
	      ExplosiveFactory.MINE_TYPE, 
	      Color.red,
	      25 );
	
	/**
	 * Name to be showed for the player
	 */
	public final String name;
	/**
	 * The supertype of the explosion. Used by the {@link ExplosiveFactory}.
	 */
	public final int superType;
	/**
	 * Color to be used in stuff related to the explosive type.
	 */
	public final Color color;
	/**
	 * The default amount of ammo in the ammo box containing this type
	 * of explosives.
	 */
	public final int ammoBoxSize;
	
	private ExplosiveType( String name,
	                       int superType,
	                       Color boxColor,
	                       int ammoBoxSize ) {
		this.name = name;
		this.superType = superType;
		this.color = boxColor;
		this.ammoBoxSize = ammoBoxSize;
	}
}
