package org.jubis.trispace.util;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

/**
 * As java.awt.Point but with floats. To increase safety and
 * encapsulation it is immutable.<br>
 * This Point is also easy to use with float[2] and Vector2f.
 * 
 * @see Vector2f
 * @see java.awt.Point
 */
public class Point {
	/**
	 * X coordinate - easy to access with no method
	 */
	public final float x;
	/**
	 * Y coordinate - easy to access with no method
	 */
	public final float y;
	
	/**
	 * Creates new Point
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Point( float x, float y ) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a new Point using the first cell as X and the second one as Y.
	 * Given array must have exactly two cells.
	 * 
	 * @param point Coordinates as an array
	 */
	public Point( float[] point ) {
		this( point[0], point[1] );
		if( point.length != 2 ) {
			throw new IllegalArgumentException( "Float[] must have exactly two cells" );
		}
		
	}
	
	/**
	 * Creates a new Point in origin
	 */
	public Point() {
		this( 0, 0 );
	}
	
	/**
	 * Creates a copy of this Point that is moved the amount of the given Point.
	 * 
	 * @param change Amount of the translation
	 * 
	 * @return Copy of this Point translated
	 */
	public Point translate( Point change ) {
		if( change == null ) {
			return this;
		}
		return new Point( this.x + change.x, this.y + change.y );
	}
	
	/**
	 * Creates a copy of this Point that is moved the amount of the given vector.
	 * 
	 * @param change Amount of the translation
	 * 
	 * @return Copy of this Point translated
	 */
	public Point translate( Vector2f change ) {
		return this.translate( new Point( change.x, change.y ) );
	}
	
	public Point translate( float dx, float dy ) {
		return this.translate( new Point( dx, dy ) );
	}
	
	/**
	 * Creates a copy of this Point that is a negation.
	 * 
	 * @return Copy of this Point negated
	 */
	public Point negate() {
		return new Point( -this.x, -this.y );
	}
	
	/**
	 * Creates a new copy of this Point with certain condition:<br>
	 * The boundaries are the edges of the rectangle between the given
	 * Points.<br>
	 * If this Point is inside the boundaries a copy will be returned.<br>
	 * If this Point is outside the boundaries a new Point will be returned
	 * so that it is the Point inside the boundaries closest to this one. 
	 * 
	 * @param min Left top corner of the boundaries
	 * @param max Right bottom corner of the boundaries
	 * 
	 * @return If inside, a copy; else a new Point inside
	 */
	public Point boundTo( Point min, Point max ) {
		float x = Math.min( max.x, Math.max( min.x, this.x ) );
		float y = Math.min( max.y, Math.max( min.y, this.y ) );
		
		return new Point( x, y );
	}

	/**
	 * Creates a Vector2f with given starting and ending Points.
	 * 
	 * @param start Starting Point of the vector
	 * @param end Ending Point of the vector
	 * 
	 * @return Created Vector2f
	 */
	public static Vector2f toVector( Point start, Point end ) {
		float dx = end.x - start.x;
		float dy = end.y - start.y;
		return new Vector2f( dx, dy );
	}
	
	/**
	 * Creates a Vector2f with given starting and ending Points by 
	 * transforming given array into Points.
	 * 
	 * @param start Starting point of the vector
	 * @param end Ending point of the vector
	 * 
	 * @return Created Vector2f
	 */
	public static Vector2f toVector( float[] start, float[] end ) {
		return toVector( new Point( start ), new Point( end ) );
	}
	
	/**
	 * Creates a totally random Point between the bounds
	 * 
	 * @param xLower Left bound
	 * @param xUpper Right bound
	 * @param yLower Top bound
	 * @param yUpper Bottom bound
	 * 
	 * @return Randomly created Point object
	 */
	public static Point randomPoint( int xLower, int xUpper,
	                                 int yLower, int yUpper ) {
		return new Point( Util.randomFloat( xLower, xUpper ),
		                  Util.randomFloat( yLower, yUpper ) );
	}
	
	/**
	 * Creates a position vector out of this Point
	 * 
	 * @return The created vector
	 */
	public Vector2f toVector() {
		return new Vector2f( this.x, this.y );
	}
	
	/**
	 * Creates a Line out given Points
	 * 
	 * @param start Starting Point of the Line
	 * @param end Ending Point of the Line
	 * 
	 * @return The created Line
	 */
	public static Line toLine( Point start, Point end ) {
		return new Line( start.x, start.y, end.x, end.y );
	}
	
	/**
	 * More informative way to express the data
	 */
	@Override
	public String toString() {
		return (int)this.x + " " + (int)this.y;
	}
}
