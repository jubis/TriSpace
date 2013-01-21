package org.jubis.trispace.model;

import java.util.ArrayList;
import java.util.List;

import org.jubis.trispace.spaceobject.factory.AmmoBoxFactory;
import org.jubis.trispace.spaceobject.factory.RockFactory;
import org.jubis.trispace.spaceobject.factory.WallFactory;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.RockSettings;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * The environment where the game takes place. Takes care off all stuff that
 * is related to the world around the avatar. E.g. Adds the effects of 
 * {@link Field}s, creates new enemies, contains all the solid objects etc.
 */
public class Space {
	private List <Field<?>> fields = new ArrayList <Field<?>> ();
	
	private RockFactory rockFactory;
	private RockSettings rockSettings = new RockSettings();
	private WallFactory wallFactory;
	private AmmoBoxFactory ammoBoxFactory;
	
	private Adder adder;
	private List<SpaceObject> objects;
	
	private float width;
	private float height;
	
	private PlayerLevel	level;
	
	/**
	 * Creates a new Space with given size. 
	 * 
	 * @param width Width of the Space
	 * @param height Height of the Space
	 * @param adder Adder to be used when adding Walls and Rocks
	 * @param objects List of object passed for the factories
	 * @param level Level to be used when determining the stuff that should
	 * 				be added to the Space while game is on.
	 */
	public Space( float width, float height, 
	              Adder adder, 
	              List<SpaceObject> objects,
	              PlayerLevel level ) {
		this.width = width;
		this.height = height;
		this.adder = adder;
		this.objects = objects;
		this.level = level;
		
		this.fields.add( new BoundaryField( width, height ) );
		this.fields.add( new SlowerField( 2f/1000 ) );
		
		this.rockFactory = new RockFactory( this.objects, 
		                                    this, 
		                                    this.adder );
		this.wallFactory = new WallFactory( this.objects, 
		                                    this );
		this.ammoBoxFactory = new AmmoBoxFactory( this.objects, this );
	}

	/**
	 * Just creates all the Walls
	 */
	public void constructSpace() {
		for( int i = 0; i < 20; i++ ) {
			objects.add( this.wallFactory.getNew() );
		}
	}

	/**
	 * @return The Space as a rectangle (which it actually is)
	 */
	public Rectangle getBorders() {
		return new Rectangle( 1, 1, this.width, this.height );
	}

	/**
	 * @return Right bottom corner of the Space
	 */
	public Point getBoundaries() {
		return new Point( this.width, this.height );
	}
	/**
	 * @return Width of the Space
	 */
	public float getWidth() {
		return width;
	}
	/**
	 * @return Height of the Space
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Adds more stuff to the Space and makes all the Fields to effect on
	 * all the objects.
	 * 
	 * @param objects List of the object currently in this Space
	 * @param delta Time since last update (milliseconds)
	 * 
	 * @see Field
	 */
	public void update( List<SpaceObject> objects, int delta ) {
		this.addRock();
		this.addAmmoBox();
		
		for( SpaceObject object : objects ) {
			this.useFields( object, delta );
		}
	}
	
	private void addRock() {
		int level = this.level.getValue();
		int rocks = this.rockSettings.getRockAmount( level );
		this.rockFactory.setRockSize( this.rockSettings.getRockSize( level ) );
		
		if( this.rockFactory.getCounter() < rocks &&
			Util.randomFloat( 0, 1 ) < 0.1 ) {
			objects.add( this.rockFactory.getNew() );
		}
		
		System.out.println( rocks );
	}
	
	private void addAmmoBox() {
		if( Util.randomFloat( 0, 1 ) < 0.001 ) {
			objects.add( this.ammoBoxFactory.getNew() );
		}
	}
	
	private void useFields( SpaceObject object, int delta ) {
		for( Field<?> field : this.fields ) {
			field.affect( object, delta );
		}
	}

	/**
	 * Checks if the given object is even a little bit inside the Space
	 * 
	 * @param object Object to be tested
	 * 
	 * @return Was it at least partly inside or not
	 */
	public boolean isInsideSpace( SpaceObject object ) {
		Shape shape = object.getShape();
		return Util.isPartiallyInRange( shape, 0, this.width, 0, this.height );
	}
	/**
	 * Checks if the given object is completely inside the Space
	 * 
	 * @param object Object to be tested
	 * 
	 * @return Was it completely inside or not
	 */
	public boolean isCompletelyInsideSpace( SpaceObject object ) {
		Shape shape = object.getShape();
		return Util.isInRange( shape, 0, this.width, 0, this.height );
	}
}
