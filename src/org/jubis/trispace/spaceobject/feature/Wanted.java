package org.jubis.trispace.spaceobject.feature;


/**
 * Something that is wanted to be damaged and that gives scores for that damage.
 * Of course this means that Wanted object is a special kind of {@link Damageable}
 * object. 
 */
public interface Wanted extends Damageable {
	/**
	 * The amount of points that should be given when scoring damaging this.
	 * 
	 * @return Amount of scores
	 */
	public int getScores();
}
