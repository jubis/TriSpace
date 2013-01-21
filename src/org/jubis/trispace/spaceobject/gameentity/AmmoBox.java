package org.jubis.trispace.spaceobject.gameentity;

import org.jubis.trispace.spaceobject.object.Explosive;
import org.jubis.trispace.spaceobject.object.ExplosiveType;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.spaceobject.object.SpaceShip;
import org.jubis.trispace.util.Point;
import org.newdawn.slick.geom.Rectangle;

/**
 * Box that just stays still in the space. The magic is that when a SpaceShip
 * collides to it, the ship will pick and get more ammo.
 * 
 * @see Explosive
 * @see SpaceShip
 */
public class AmmoBox extends SpaceObject {
	/**
	 * The type of explosives this box contains, immutable and thus public
	 */
	public final ExplosiveType type;
	/**
	 * Whether the amount of ammo is endless, immutable and thus public
	 */
	public final boolean endless;
	private int amount;
	
	/**
	 * Creates a new box with given ammo load
	 * 
	 * @param type Type of the explosives this box contains
	 * @param amount Amount of ammo this box contains
	 * @param endless Whether the ammo load is endless
	 * @param position Position of the concrete object
	 */
	public AmmoBox( ExplosiveType type, 
	                int amount, 
	                boolean endless, 
	                Point position ) {
		super( new Rectangle( 0, 0, 15, 15 ), position );
		this.type = type;
		this.amount = amount;
		this.endless = endless;
		
		this.color = this.type.color;
	}
	
	/**
	 * Creates a new box. This constructor should be used only when creating
	 * non-concrete boxes e.g. as a starting ammo for avatar. 
	 * 
	 * @param type Type of the explosives this box contains
	 * @param amount Amount of ammo this box contains,
	 * 				 if endless is true this amount doesn't matter
	 * @param endless Whether the ammo load is endless
	 */
	public AmmoBox( ExplosiveType type, 
	                int amount, 
	                boolean endless ) {
		this( type, amount, endless, new Point() );
	}
	
	/**
	 * Returns the amount of ammo. This method cannot provide any useful
	 * information if endless is true
	 * 
	 * @return Amount of ammo in this box
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * If the collider is SpaceShip, the ship picks up the box.
	 */
	@Override
	public void reactToCollision( SpaceObject collider ) {
		if( collider instanceof SpaceShip ) {
			((SpaceShip)collider).pickAmmoBox( this );
			this.alive = false;
		}
	}
}
