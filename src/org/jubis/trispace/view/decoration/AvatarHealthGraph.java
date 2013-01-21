package org.jubis.trispace.view.decoration;

import org.jubis.trispace.model.AvatarHealth;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Graph that shows the health status of the avatar
 */
public class AvatarHealthGraph extends Graph {
	
	private static final Point SIZE = new Point( 70, 140 );
	
	private AvatarHealth health;

	/**
	 * Creates a new graph that gets all the data from the model.
	 * 
	 * @param model Source for the health status information
	 */
	public AvatarHealthGraph( AvatarHealth model ) {
		super( new Point( 50, 150 ), SIZE, true );
		this.health = model;
		
		this.font = Util.createFont( 30 );
	}

	/**
	 * Uses the {@link #renderPercentGraph(Graphics, float, String)} method
	 */
	@Override
	public void render( Graphics g ) {
		this.graphColor = this.calculateColor();
		this.renderPercentGraph( g,
		                         this.health.getHealthPercentage(), 
		                         this.health.getValue().intValue()+"" );
	}

	private Color calculateColor() {
		float percent = this.health.getHealthPercentage();
		if( percent >= 0.5 ) {
			return new Color( (1-percent)*2, 1, 0 );
		} else {
			return new Color( 1, percent*2, 0 );
		}
	}

}
