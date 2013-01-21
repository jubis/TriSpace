package org.jubis.trispace.model;

/**
 * Model that calculates and contains the information about the difficulty
 * level the player has achieved. 
 */
public class PlayerLevel extends GameStatusModel<Integer>  {
	private AvatarScores scoreModel;
	
	/**
	 * Creates a new instance.
	 * @param scoreModel Score model to be used while calculating the level.
	 */
	public PlayerLevel( AvatarScores scoreModel ) {
		this.scoreModel = scoreModel;
		this.value = 1;
	}
	
	/**
	 * Calculates the current level
	 */
	@Override
	protected Integer updateValue() {
		if( scoreModel.getValue() != null ) {
			return scoreModel.getValue() / 100 + 1;
		} else {
			return null;  
		}
	}
	
	/**
	 * Returns how big part of this level is already completed
	 * @return [scores] % [scores needed to a level] / 100
	 */
	public float getPercentToNextLevel() {
		return scoreModel.getValue() % 100 / 100f;
	}

}
