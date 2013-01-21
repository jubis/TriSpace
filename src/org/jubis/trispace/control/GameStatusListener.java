package org.jubis.trispace.control;

/**
 * Generic listener for listening any value that is important for the
 * game itself.
 * 
 * @param <T> Type of the value in which this listener is interested of
 */
public interface GameStatusListener<T> {
	/**
	 * The status of the model that this listener is added to has changed.
	 * 
	 * @param value The new value
	 */
	public void statusChanged( T value );
}
