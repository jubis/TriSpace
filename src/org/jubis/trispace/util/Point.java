package org.jubis.trispace.util;

import org.newdawn.slick.geom.Vector2f;

public class Point {
	public final float x;
	public final float y;
	
	public Point( float x, float y ) {
		this.x = x;
		this.y = y;
	}
	
	public Point( float[] point ) {
		this( point[0], point[1] );
	}
	
	public Point translate( Point change ) {
		if( change == null ) {
			return this;
		}
		return new Point( this.x + change.x, this.y + change.y );
	}

	public static Vector2f toVector( Point start, Point end ) {
		float dx = end.x - start.x;
		float dy = end.y - start.y;
		return new Vector2f( dx, dy );
	}
	
	public static Vector2f toVector( float[] start, float[] end ) {
		return toVector( new Point( start ), new Point( end ) );
	}
}
