package org.jubis.trispace.view.decoration;

import org.jubis.trispace.model.AvatarScores;
import org.jubis.trispace.model.PlayerLevel;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.Graphics;

/**
 * Graph to show the level and the scores of the player
 */
public class LevelGraph extends Graph {
	
	private static final int SIDE_LENGTH = 70;
	
	private PlayerLevel level;
	private AvatarScores scores;

	/**
	 * Creates a new graph
	 * 
	 * @param level Source for the level value
	 * @param scores Source for the score percentage
	 */
	public LevelGraph( PlayerLevel level, AvatarScores scores ) {
		super( new Point( 50, 50 ), new Point( SIDE_LENGTH, SIDE_LENGTH ), false );
		this.level = level;
		this.scores = scores;
		
		this.font = Util.createFont( 30 );
		
	}

	/**
	 * Just uses the {@link #renderPercentGraph(Graphics, float, String)} method
	 */
	@Override
	public void render( Graphics g ) {
		if( ! this.scores.isAvatar() ) {
			return;
		}
		
		String level = this.level.getValue() >= 10 ? 
		                    ""+this.level.getValue() : "0"+this.level.getValue();
		                    
		this.renderPercentGraph( g, 
		                         this.level.getPercentToNextLevel(), 
		                         level );
		
	}

}
