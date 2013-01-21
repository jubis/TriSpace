package org.jubis.trispace.util;

import java.awt.Font;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Utility class containing only static methods to simplify the code in 
 * other classes. 
 */
public class Util {
	/**
	 * Random object used by all the methods in this class 
	 */
	private static Random rand = new Random();
	
	/**
	 * Generates a random integer "the normal way".<br>
	 * The lower bound must not be greater than the upper bound.
	 * 
	 * @param lowerBound randomInt >= lowerBound
	 * @param upperBound randomInt <= upperBound
	 * 
	 * @return Random int
	 */
	public static int randomInt( int lowerBound, int upperBound ) {
		if( lowerBound > upperBound ) {
			throw new IllegalArgumentException( "Lower bound greater than upper" );
		} else {
			return rand.nextInt( upperBound-lowerBound+1 ) + lowerBound;
		}
	}
	
	/**
	 * Generates a random float by first generating a random float [0,1[ 
	 * and the scaling it with the distance of the bounds.<br>
	 * The lower bound must not be greater than the upper bound.
	 * 
	 * @param lowerBound randomInt >= lowerBound
	 * @param upperBound randomInt <= upperBound
	 * 
	 * @return Random float
	 */
	public static float randomFloat( float lowerBound, float upperBound ) {
		if( lowerBound > upperBound ) {
			throw new IllegalArgumentException( "Lower bound greater than upper" );
		}
		double random = rand.nextDouble() * (upperBound-lowerBound)  + lowerBound;
		return (float)random;
	}

	/**
	 * Checks if value = ]lowerBound,upperBound[
	 * 
	 * @param value Value to be tested
	 * @param lowerBound Lower bound of the range
	 * @param upperBound Upper bound of the range
	 * 
	 * @return Is it inside the range or not
	 */
	public static boolean isInRange( float value, 
	                                 float lowerBound, 
	                                 float upperBound ) {
		if( value < lowerBound || value > upperBound ) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Checks if value = ]lower1,upper1[ or value = ]lower2,upper2[
	 * 
	 * @param value Value to be tested
	 * @param lower1 First lower bound
	 * @param upper1 First upper bound
	 * @param lower2 Second lower bound
	 * @param upper2 Second upper bound
	 * 
	 * @return Is it inside either range or not
	 */
	public static boolean isInRange( float value, 
	                                 float lower1, float upper1,
	                                 float lower2, float upper2 ) {
		if( isInRange( value, lower1, upper1 ) ||
			isInRange( value, lower2, upper2 ) ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if the shape is inside the given range.
	 * 
	 * @param shape Shape to be tested
	 * @param left Left bound
	 * @param right Right bound
	 * @param top Upper bound
	 * @param bottom Lower bound
	 * 
	 * @return Is it inside or not
	 */
	public static boolean isInRange( Shape shape, 
	                                 float left, float right,
	                                 float top, float bottom ) {
		return shape.getMinX() > left &&
			   shape.getMaxX() < right &&
			   shape.getMinY() > top &&
			   shape.getMaxY() < bottom;
	}
	
	/**
	 * Checks if the shape is even partially inside given range.
	 * 
	 * @param shape Shape to be tested
	 * @param left Left bound
	 * @param right Right bound
	 * @param top Upper bound
	 * @param bottom Lower bound
	 * 
	 * @return Is it inside or not
	 */
	public static boolean isPartiallyInRange( Shape shape,
	                                          float left, float right,
	                                          float top, float bottom  ) {
		// TODO:without contains(...)
		Rectangle range = getRangeRect( left, right, top, bottom );
		if( range.contains( shape.getMinX(), shape.getMinY() ) || 
			range.contains( shape.getMaxX(), shape.getMinY() ) ||
			range.contains( shape.getMaxX(), shape.getMaxY() ) ||
			range.contains( shape.getMinX(), shape.getMaxY() ) ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if the shape is completely inside the range.
	 * 
	 * @param shape Shape to be tested
	 * @param range Shape used as a range
	 * 
	 * @return Is it inside or not
	 */
	public static boolean isInRange( Shape shape, Shape range ) {
		return isInRange( shape, 
		                  range.getMinX(), range.getMaxX(),
		                  range.getMinY(), range.getMaxY() );
	}
	
	/**
	 * Checks if the shape is even partially inside the range.
	 * 
	 * @param shape Shape to be tested
	 * @param range Shape used as a range
	 * 
	 * @return Is it inside or not
	 */
	public static boolean isPartiallyInRange( Shape shape, Shape range ) {
		return isPartiallyInRange( shape, 
		                           range.getMinX(), range.getMaxX(), 
		                           range.getMinY(), range.getMaxY() );
	}

	private static Rectangle getRangeRect( float left, float right,
		                              float top, float bottom ) {
		return new Rectangle( left, top, right-left, bottom-top );
	}
	
	/**
	 * Translates the shape so that is center is in the given position.
	 * 
	 * @param shape Shape to be translated
	 * @param position Wanted location for the center of the shape
	 * 
	 * @return Translated shape
	 */
	public static Shape getShapeInPosition( Shape shape, Point position ) {
		float[] origin = { 0, 0 };
		// using getCenterX and getCenterY because of bug in Slick2D library
		Vector2f toOrigin = Point.toVector( new float[]{ shape.getCenterX(), shape.getCenterY() }, 
		                                    origin );
		Vector2f toPosition = Point.toVector( new Point(), position );
		
		Vector2f total = toOrigin.add( toPosition );
		
		Transform t = Transform.createTranslateTransform( total.x, 
		                                                  total.y );
		Shape moved = shape.transform( t );
		return moved;
	}
	
	/**
	 * Load a particle system from given XML file and does some setup.
	 * 
	 * @param filename Only the name of the XML file
	 * @param position Position to be set for the particle system
	 * 
	 * @return The loaded particle system
	 */
	public static ParticleSystem loadParticleSystem( String filename,
	                                                 Point position ) {
		try {
			//URL path = ResourceLoader.getResource( "resource/" + filename );
			ParticleSystem system = ParticleIO.loadConfiguredSystem( "resources/" + filename );
			system.setRemoveCompletedEmitters( true );
			system.setPosition( position.x, position.y );
			return system;
		} catch ( IOException e ) {
			e.printStackTrace();
			throw new RuntimeException( "Problem loading particle system: " + filename );
		}
		
	}
	
	/**
	 * Creates a new UnicodeFont (font family is Verdana)
	 * 
	 * @param size Size for the font
	 * 
	 * @return The created font
	 */
	@SuppressWarnings("unchecked")
	public static UnicodeFont createFont( int size ) {
		Font font = new Font( "Verdana", Font.PLAIN, 10 );
		UnicodeFont uFont = new UnicodeFont( font, size, false, false );
		uFont.addAsciiGlyphs();
		uFont.addGlyphs( 1, 1 );
		uFont.getEffects().add( new ColorEffect( java.awt.Color.white ) );
		try {
			uFont.loadGlyphs();
		} catch ( SlickException e ) {
			e.printStackTrace();
		}
		return uFont;
	}
	
//	public static void main( String[] args ) {
//		float lower = 0;
//		float upper = 1;
//		float avg = (lower+upper)/2;
//		
//		float offsetSum = 0;
//		float tests = 1000000000;
//		for( int i = 0; i < tests; i++ ) {
//			offsetSum += ( randomFloat( lower, upper ) - avg ) / avg;
//		}
//		System.out.println( offsetSum/tests );
//	}
}
