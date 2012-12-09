package org.jubis.trispace.util;

public class Point {
	public final float x;
	public final float y;
	
	public Point( float x, float y ) {
		this.x = x;
		this.y = y;
	}
	
	public Point translate( Point change ) {
		if( change == null ) {
			return this;
		}
		return new Point( this.x + change.x, this.y + change.y );
	}
}
