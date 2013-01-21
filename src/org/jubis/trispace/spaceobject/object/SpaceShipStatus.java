package org.jubis.trispace.spaceobject.object;

import org.newdawn.slick.Color;

/**
 * Information about the condition of a SpaceShip. Knows the health and
 * takes care about that the health is not under zero or over the maximum health.<br>
 * There is also a damage factor that is used when SpaceShip is damaged.
 * 
 * @see SpaceShip
 */
public final class SpaceShipStatus {
	private static final int[][] HEALTH_COLORS = { { 255,0,0 },
												   { 255,62,0 },
												   { 255,125,0 },
												   { 255,255,0 },
												   { 255,255,125 },
												   { 255,255,255 } };
	
	private float health;
	private int maxHealth;
	private float damageFactor;
	
	/**
	 * Creates new instance with given maximum health as starting health.
	 * 
	 * @param maxHealth Upper boundary for the health and also starting health
	 * @param damageFactor Factor to be used when SpaceShip is damaged
	 */
	public SpaceShipStatus( int maxHealth, float damageFactor ) {
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.damageFactor = damageFactor;
	}
	
	/**
	 * Copies the given old instance
	 * 
	 * @param old Instance to be copied
	 */
	public SpaceShipStatus( SpaceShipStatus old ) {
		this( old.maxHealth, old.damageFactor );
		this.health = old.health;
	}
	
	/**
	 * @return The amount of health left
	 */
	public float getHealth() {
		return this.health;
	}
	
	/**
	 * @return current health / maximum health
	 */
	public float getHealthPercentage() {
		return this.health/this.maxHealth;
	}
	
	/**
	 * Changes the health with given change. The health can't go under zero or
	 * above maximum health.
	 * 
	 * @param change Amount of the change (positive or negative)
	 * 
	 * @return Is the SpaceShip still alive
	 */
	public boolean changeHealth( float change ) {
		this.health += change;
		this.health = Math.min( this.health, this.maxHealth );
		this.health = Math.max( this.health, 0 );
		
		System.out.println( "Health changed, health: " + this.health + 
		                    " change: " + change );
		
		return this.isAlive();
	}
	
	/**
	 * Change the health according to given damage. Uses damage factor.
	 * 
	 * @param damage Amount determined by the damager
	 * 
	 * @return If SpaceShip died by this damage
	 */
	public boolean damage( int damage ) {
		return this.changeHealth( - damage * this.damageFactor );
	}
	
	/**
	 * Is the health above zero.
	 * @return Is alive or not
	 */
	public boolean isAlive() {
		return this.health > 0;
	}
	
	/**
	 * Returns a color to indicate the health of the SpaceShip. The color
	 * can be used as avatar's color.
	 * 
	 * @return A color that indicates the health status
	 */
	public Color getHealthColor() {
		int color = (int)(this.getHealthPercentage() * HEALTH_COLORS.length);
		// because percent scale is [0,100] but array's range is only [0,length[
		// some little fix has to be done
		color = (color == 6) ? 5 : color;
		int[] c = HEALTH_COLORS[ color ];
		return new Color( c[0], c[1], c[2] );
	}
	
}
