package org.jubis.trispace.util;

import org.newdawn.slick.geom.Vector2f;

/**
 * Speed as SI quantity
 */
public class Speed {
	/**
	 * Value of the speed, (pixels/ms)<br>
	 * This can't never be negative.
	 */
	private float value;
	/**
	 * Direction of the speed<br>
	 * Length of this must always be one unit. 
	 */
	private Vector2f direction;
	
	/**
	 * Creates a new instance so that the direction doesn't affect to the
	 * value in any case.
	 * 
	 * @param value Value of speed
	 * @param direction Direction of speed
	 */
	public Speed( float value, Vector2f direction ) {
		this.setValue( value );
		this.setDirection( direction );
	}
	
	public Speed() {
		this( 0, new Vector2f( 1, 0 ) );
	}
	
	/**
	 * Turns a Vector2f into a Speed instance using the length as value and
	 * the vector itself as a direction
	 * 
	 * @param speed Object to be turned into a Speed
	 */
	public Speed( Vector2f speed ) {
		this( speed.length(), speed );
	}

	/**
	 * @return Current value
	 */
	public float getValue() {
		return value;
	}
	/**
	 * Changes the value so that it's always positive. If it is set to negative
	 * direction is negated.
	 * 
	 * @param value Value for the Speed (may be negative)
	 */
	public void setValue( float value ) {
		if( value < 0 ) {
			this.value = -value;
			this.direction.negateLocal();
		} else {
			this.value = value;
		}
	}
	
	/**
	 * @return Copy of the direction
	 */
	public Vector2f getDirection() {
		return new Vector2f( this.direction );
	}
	/**
	 * Changes the direction so that its length stays in one unit.<br>
	 * Given Vector2f must not be a zero vector
	 * 
	 * @param direction
	 */
	public void setDirection( Vector2f direction ) {
		// vector is not a direction if its zero vector
		if( direction.length() == 0 ) {
			throw new IllegalArgumentException( "Direction of speed is set " +
												"to zero vector" );
		}
		this.direction = direction.normalise();
	}
	
	/**
	 * Negates the direction which is the same as negating the value.
	 */
	public void negate() {
		this.direction.negateLocal();
	}

	/**
	 * Adds given Speed to this. 
	 * 
	 * @param change Speed to be added
	 */
	public void sum( Speed change ) {
		Vector2f thisAsVector = this.toVector();
		thisAsVector.add( change.toVector() );
		
		this.value = thisAsVector.length();
		this.direction = thisAsVector.normalise();
	}
	
	/**
	 * Adds given change to the value.
	 * 
	 * @param change Amount to be added
	 */
	public void sum( float change ) {
		this.value += change;
	}

	/**
	 * Creates a new Vector2f out of this instance. Direction is the base for
	 * the vector and the value is the length.
	 * 
	 * @return This instance as a vector
	 */
	public Vector2f toVector() {
		return this.direction.copy().scale( this.value );
	}
	
	/**
	 * Handles Speed as a vector and calculates its vector projection to
	 * given vector. If the resulting vector is a zero vector value is set to
	 * zero and direction is not modified.
	 * 
	 * @param projectOnto The line into which Speed is projected
	 */
	public void projectSpeed( Vector2f projectOnto ) {
		Vector2f newSpeedVector = new Vector2f();
		this.toVector().projectOntoUnit( projectOnto, 
		                                 newSpeedVector );
		this.setValue( newSpeedVector.length() );
		if( newSpeedVector.length() > 0 ) {
			this.setDirection( newSpeedVector );
		}
	}
	
	/**
	 * More informative description about this instance
	 */
	@Override
	public String toString() {
		return Float.toString( this.value ) + ":" + this.direction;
	}
}
