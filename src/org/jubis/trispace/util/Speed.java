package org.jubis.trispace.util;

import org.newdawn.slick.geom.Vector2f;

public class Speed {
	public float value;
	public Vector2f direction;
	
	public Speed( float value, Vector2f direction ) {
		this.value = value;
		if( direction.length() == 0 ) {
			throw new IllegalArgumentException();
		} else {
			this.direction = direction.normalise();
		}
	}

	public Speed negate() {
		return new Speed( this.value, this.direction.negate() );
	}

	public void sum( Speed change ) {
		Vector2f toBeChanged = this.toVector();
		toBeChanged.add( change.toVector() );
		
		this.value = toBeChanged.length();
		this.direction = toBeChanged.normalise();
	}
	
	public void sum( float change ) {
		this.value += change;
	}

	public Vector2f toVector() {
		return this.direction.copy().scale( this.value );
	}
	
	@Override
	public String toString() {
		return Float.toString( this.value ) + ":" + this.direction;
	}
}
