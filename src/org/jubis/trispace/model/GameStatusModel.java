package org.jubis.trispace.model;

import java.util.ArrayList;
import java.util.List;

import org.jubis.trispace.control.GameStatusListener;

/**
 * Generic model for holding any value that is important for the
 * game itself.
 * 
 * @param <T> Type of the value in which is hold in this model 
 * 			  (must not be primitive type)
 */
public abstract class GameStatusModel<T> {
	protected T value;
	private List<GameStatusListener<T>> listeners = new ArrayList<GameStatusListener<T>>();
	
	/**
	 * Returns the reference to the value object in this class.
	 * @return Value of this model
	 */
	public T getValue() {
		return this.value;
	}
	
	/**
	 * Forwards the updating command of the value and notifies
	 * listeners if necessary.
	 */
	public void update() {
		T oldValue = this.value;
		this.value = this.updateValue();
		if( this.value != null &&
			! this.value.equals( oldValue ) ) {
			this.notifyListeners( this.value );
		}
	}
	
	/**
	 * This is the method that loads the new updated value and
	 * returns it. Does nothing for the current value held in this
	 * object.
	 * 
	 * @return New updated value
	 */
	protected abstract T updateValue();
	
	/**
	 * Notifies all the listeners of this model
	 * 
	 * @param newValue The current value of the model
	 */
	private void notifyListeners( T newValue ) {
		for( GameStatusListener<T> listener : this.listeners ) {
			listener.statusChanged( newValue );
		}
	}
	
	/**
	 * Add listener to this model
	 * @param listener Listener to be added
	 */
	public void addListener( GameStatusListener<T> listener ) {
		this.listeners.add( listener );
	}
	
	/**
	 * Removes listener from this model
	 * @param listener Listener to be removed
	 */
	public void removeListener( GameStatusListener<T> listener ) {
		this.listeners.remove( listener );
	}
}
