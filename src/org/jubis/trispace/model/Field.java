package org.jubis.trispace.model;

import org.jubis.trispace.spaceobject.object.SpaceObject;

/**
 * Any kind of energy field that affects on the objects in the Space.
 * 
 * @param <T> Type of the object that this field affects on
 */
public abstract class Field<T> {
	private Class<T> type;
	
	/**
	 * Creates a new instance.
	 */
	public Field() {
		this.type = this.getType();
	}
	
	/**
	 * The only type of objects this Field is able to effect on.
	 * @return Type of object that this Field accepts
	 */
	protected abstract Class<T> getType();
	
	/**
	 * Checks if this field should have effect on given object and 
	 * affects it if allowed.<br>
	 * Any object can be given so that the user of this field doesn't
	 * have to know if the object is proper.<br>
	 * 
	 * @param object Object that will be affected
	 * @param delta Time in millis since last affect (= since last update)
	 */
	public void affect( SpaceObject object, int delta ) {
		if( type.isInstance( object )  &&
			object.doReactToField( this ) ) {
			this.createEffect( type.cast( object ), delta );
		}
	}
	
	/**
	 * This method does the magic that this {@link Field} wants to do.
	 * 
	 * @param object Subject of the effect
	 * @param delta Time in millis since last effect (= since last update)
	 */
	protected abstract void createEffect( T object, int delta );
}
