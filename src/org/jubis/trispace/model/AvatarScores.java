package org.jubis.trispace.model;

import org.jubis.trispace.control.ShooterScoreListener;
import org.jubis.trispace.spaceobject.feature.Damageable;
import org.jubis.trispace.spaceobject.feature.Wanted;
import org.jubis.trispace.spaceobject.object.SpaceShip;

/**
 * Model of the scores of the avatar which also listens the scores to
 * update itself.
 */
public class AvatarScores extends AvatarStatusModel<Integer>
											implements ShooterScoreListener {
	private Integer newValue = 0; 
	
	/**
	 * Creates a new instance.
	 * @param avatar SpaceShip which scores well be monitored
	 */
	public AvatarScores( SpaceShip avatar ) {
		super( avatar );
		this.value = 0;
		if( this.avatar != null ) {
			this.avatar.setScoreListener( this );
		}
	}
	
	/**
	 * Set new avatar and also deattaches and reattaches the listener
	 */
	@Override
	public void setAvatar( SpaceShip avatar ) {
		if( this.avatar != null ) {
			this.avatar.setScoreListener( null );
		}
			super.setAvatar( avatar );
		if( this.avatar != null ) {
			this.avatar.setScoreListener( this );  
		}
	}
	
	/**
	 * Stores the scores avatar got
	 */
	@Override
	public int scored( Damageable damaged ) {
		// avatar get scores only by scoring wanted objects
		if( damaged instanceof Wanted ) {
			this.newValue = this.value + ((Wanted)damaged).getScores();
			System.out.println( "Scores: " + this.newValue +
			                    " damaged was: " + 
								damaged.getClass().getSimpleName() + 
								damaged.getClass().hashCode() );
		}
		return this.newValue.intValue();
	}

	/**
	 * Because of the listener nature of this class this method doesn't do
	 * anything. (The update comes via the scored(...) method.)
	 */
	@Override
	protected Integer updateValue() {
		// a trick has to be done because the value isn't updates normally
		// but new values are got by listening
		return this.newValue;
	}

}
