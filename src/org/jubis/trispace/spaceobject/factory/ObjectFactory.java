package org.jubis.trispace.spaceobject.factory;

import java.util.List;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.object.SpaceObject;

/**
 * General factory for game entities. The purpose is to make it easy for the
 * model to create new entities so that they are properly placed etc.<br> 
 * This super class takes only care about that no new entity can be even 
 * partially inside of another object.
 * 
 * @param <E> Type of entity this factory creates
 */
public abstract class ObjectFactory<E extends SpaceObject> {
	protected int counter;
	private List<SpaceObject> objects;
	protected Space space;
	protected Adder adder;

	/**
	 * Creates new ObjectFactory and stores all the given parametres.
	 * 
	 * @param objects List of all objects in the model
	 * @param space Space in which given objects will be added
	 * @param adder This is given for new entities that may need
	 * 					  ObjectAdder
	 */
	public ObjectFactory( List<SpaceObject> objects,
						  Space space,
						  Adder adder ) {
		this.objects = objects;
		this.space = space;
		this.adder = adder;
	}

	/**
	 * @return Number of created objects
	 */
	public int getCounter() {
		return counter;
	}
	/**
	 * Decreases the object counter by one. This should be used if its
	 * necessary that the counter has to meet up with some system.
	 */
	public void decreaseCouter() {
		if( this.counter > 0 ) {
			this.counter--;
		}
	}
	
	/**
	 * Does its best to return new instance of entity. The new instance will
	 * meet all the requirements. If requirements are too high so that returning
	 * new instance is impossible, returns null.<br>
	 * This method also handles the counting of created objects (that are not null).
	 * 
	 * @return New instance or null if impossible
	 * 
	 * @see #createNew()
	 * @see #isProper(SpaceObject)
	 */
	public E getNew() {
		E object;
		do {
			object = this.createNew();
			// createNew() can end the loop by returning zero
		} while( object != null && !this.isProper( object ) );
		
		if( object != null ) {
			this.counter++;
		}
		
		return object;
	}
	
	/**
	 * All the requirements are in this method so that it's easy to simply 
	 * override this in subclasses. 
	 * 
	 * @param object Object to be tested
	 * 
	 * @return Was it proper or not
	 */
	protected boolean isProper( E object ) {
		return !this.collidesWithOldObject( object );
	}
	
	/**
	 * Creates the new instance, in some cases with some parameters.<br>
	 * This method shouldn't really take care about if the new instance is
	 * proper or not. Actually it can be totally random.<br>
	 * If, for some reason, this method doesn't want to try anymore to create
	 * proper instance, it returns null. In any other situation null is 
	 * inappropriate return value.
	 * 
	 * @return New instance or null only if the loop should be ended
	 */
	protected abstract E createNew();
	
	/**
	 * Checks if the new created entity would already be colliding with
	 * some old object.
	 * 
	 * @param created Object to be tested if it collides
	 * 
	 * @return Does it collide or not
	 */
	protected boolean collidesWithOldObject( E created ) {
		for( SpaceObject object : this.objects ) {
			if( !object.equals( created ) && 
				( object.doCollide( created ) ) ) {
				return true;
			}
		}
		return false;
	}
}
