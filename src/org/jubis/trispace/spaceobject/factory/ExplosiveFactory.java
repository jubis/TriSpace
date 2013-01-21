package org.jubis.trispace.spaceobject.factory;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.spaceobject.feature.Shooter;
import org.jubis.trispace.spaceobject.gameentity.BasicRocket;
import org.jubis.trispace.spaceobject.gameentity.BouncyRocket;
import org.jubis.trispace.spaceobject.gameentity.ExplosiveRocket;
import org.jubis.trispace.spaceobject.gameentity.Mine;
import org.jubis.trispace.spaceobject.object.Explosive;
import org.jubis.trispace.spaceobject.object.ExplosiveType;
import org.jubis.trispace.spaceobject.object.Rocket;
import org.jubis.trispace.util.Point;
import org.newdawn.slick.geom.Vector2f;

/**
 * Factory to create an instance of the explosion that any shooter is
 * currently shooting. The explosion created may have multiply parts.
 */
public class ExplosiveFactory {
	/**
	 * An explosion that is based on the superclass {@link Rocket}
	 */
	public final static int ROCKET_TYPE = 0;
	/**
	 * An explosion type called a {@link Mine}
	 */
	public final static int MINE_TYPE = 1;
	
	private ExplosiveType explosiveTypeInUse;
	private Adder adder;
	
	/**
	 * Creates a new factory.
	 * 
	 * @param adder Adder to be passed to explosives created
	 */
	public ExplosiveFactory( Adder adder ) {
		this.adder = adder;
	}
	
	/**
	 * Change the type of the explosion created next
	 * @param explosiveTypeInUse New type
	 */
	public void setExplosiveTypeInUse( ExplosiveType explosiveTypeInUse ) {
		this.explosiveTypeInUse = explosiveTypeInUse;
		
	}
	/**
	 * @return The current explosion type that is created in this factory.
	 */
	public ExplosiveType getTypeInUse() {
		return explosiveTypeInUse;
	}
	
	/**
	 * Creates a new explosion according to the set explosion type in use
	 * 
	 * @param position Position for the new explosion
	 * @param direction Direction for the new explosion (may not be used)
	 * @param source The shooter of the explosion
	 * 
	 * @return All parts of the explosion as an array
	 */
	public Explosive[] createExplosives( Point position,
	     	                             Vector2f direction,
	    	                             Shooter source ) {
		switch( this.explosiveTypeInUse.superType ) {
			case ROCKET_TYPE:
				return createRocket( position, direction, source );
			case MINE_TYPE:
				return createMine( position, source );
			default:
				return null;
		}
	}

	private Rocket[] createRocket( Point position,
	                            Vector2f direction,
	                            Shooter source ) {
		switch ( this.explosiveTypeInUse ) {
			case BASIC_ROCKET: 
				return new Rocket[]{ new BasicRocket( position, direction, source, this.adder, false ) };
				
			case DOUBLE_ROCKET:
				Vector2f perpendiular = direction.getPerpendicular();
				perpendiular.scale( 15/perpendiular.length() );
				Point pos1 = position.translate( perpendiular );
				Point pos2 = position.translate( perpendiular.negate() );
				
				Rocket rocket1 = new BasicRocket( pos1, direction, source, this.adder, true );
				Rocket rocket2 = new BasicRocket( pos2, direction, source, this.adder, true );
				
				rocket1.setSibling( rocket2 );
				rocket2.setSibling( rocket1 );
				
				return new Rocket[]{ rocket1, rocket2 };
				
			case BOUNCY_ROCKET:
				return new Rocket[]{ new BouncyRocket( position, direction, source, this.adder ) };
			case EXPLOSIVE_ROCKET:
				return new Rocket[]{ new ExplosiveRocket( position, direction, source, this.adder ) };
			default:
				return null;
		}
	}
	
	private Mine[] createMine( Point position, Shooter source ) {
		return new Mine[]{ new Mine( position, source, this.adder ) };
	}
}
