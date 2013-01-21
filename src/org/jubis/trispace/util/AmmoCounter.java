package org.jubis.trispace.util;

/**
 * Class to help the SpaceShipAmmunition to count ammo. 
 */
public class AmmoCounter {
	/**
	 * Maximum amount of ammo that can be in one counter
	 */
	public final static int MAX = 200;
	
	/**
	 * If this counter has endless amount of ammo
	 */
	public final boolean endless;
	private int amount;
	
	/**
	 * Creates a new counter.
	 * 
	 * @param amount Starting amount of ammo
	 * @param endless If the amount is endless
	 */
	public AmmoCounter( int amount, boolean endless ) {
		this.amount = (endless || amount > MAX) ? MAX : amount;
		this.endless = endless;
	}
	
	/**
	 * Creates simply a copy of an old counter
	 * 
	 * @param old Counter to be copied
	 */
	public AmmoCounter( AmmoCounter old ) {
		this.amount = old.amount;
		this.endless = old.endless;
	}
	
	/**
	 * @return Returns the ammo count 
	 */
	public int getAmount() {
		return this.amount;
	}
	/**
	 * @return ammo count / max ammo count
	 */
	public float getLoadPercent() {
		return (float)this.amount / MAX;
	}
	/**
	 * Adds more ammo to the counter. The counter can't go above maximum.
	 * @param addition Amount to add
	 */
	public void add( int addition ) {
		this.amount += addition;
		this.amount = Math.min( this.amount, MAX );
	}
	
	/**
	 * Reduces the counter by one if counter is not endless. Counter can
	 * never go under zero.
	 * 
	 * @return If it was possible to take an ammo from this counter or not.
	 */
	public boolean reduce() {
		if( ! this.endless && this.amount > 0) {
			this.amount--;
			return true;
		} else {
			return false || this.endless;
		}
	}
	
	/**
	 * @return Is the counter already empty (count == zero)
	 */
	public boolean isEmpty() {
		return this.amount == 0 && ! this.endless;
	}
	
	/**
	 * Returns the amount in such format it can be used in GUI
	 */
	@Override
	public String toString() {
		return this.endless ? "endless" : this.amount+"";
	}
}
