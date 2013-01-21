package org.jubis.trispace.spaceobject.gameentity;

import org.jubis.trispace.spaceobject.object.ExplosiveType;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.spaceobject.object.SpaceShip;
import org.jubis.trispace.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Special kind of Wall that provides a shelter for SpaceShips (only current avatar). 
 * Only SpaceShip can enter the Home. Home also increases the health of the 
 * SpaceShip and gives it some BasiRockets.
 */
public class Home extends Wall {
	private final int healingPower;
	private SpaceShip avatar;
	
	private int scoresWhileLastVisited;
	private int ammoBoxSize;
	
	/**
	 * Creates a new Home which loading ability is preset.
	 * 
	 * @param width Width of the Wall
	 * @param height Height of the Wall
	 * @param position Position of the object center
	 * @param healingPower Amount that SpaceShip is healed per visit
	 * @param ammoBoxSize Size of ammo box that is given to the SpaceShip 
	 * 					  when it visits
	 */
	public Home( float width, 
	             float height, 
	             Point position,
	             int healingPower,
	             int ammoBoxSize ) {
		super(	width, height, position );
		this.healingPower = healingPower;
		this.ammoBoxSize = ammoBoxSize;
		
		this.color = Color.black ;
	}
	
	/**
	 * If the collider is the avatar it will be loaded. However between the
	 * loads the avatar has to collect a bit more scores. At the first visit
	 * no loading will take place. 
	 */
	@Override
	public void reactToCollision( SpaceObject object ) {
		
		if( object instanceof SpaceShip ) {
			SpaceShip spaceShip = (SpaceShip)object;
			spaceShip.setInsideHome( true );
			
			if( spaceShip.isAvatar() ) {
				
				// if avatar hasn't visited home yet it won't be healed
				// -> first visit only activates the healing feature
				if( this.avatar != null &&
					this.isScoreChangeBigEnough( spaceShip.getScores() ) ) {
					spaceShip.heal( this.healingPower );
					spaceShip.pickAmmoBox( this.createAmmoBox() );
				}
				this.scoresWhileLastVisited = spaceShip.getScores();
				this.avatar = spaceShip;
				return;
			}
		}
		super.reactToCollision( object );
	}
	
	private AmmoBox createAmmoBox() {
		return new AmmoBox( ExplosiveType.BASIC_ROCKET, this.ammoBoxSize, false );
	}
	
	private int getNeededChange() {
		return this.healingPower*4;
	}
	
	private boolean isScoreChangeBigEnough( int avatarScores ) {
		int scoreChange = avatarScores - this.scoresWhileLastVisited;
		return scoreChange >= this.getNeededChange();
	}
	
	/**
	 * Renders the Home with right color and with a tip text about the 
	 * amount of scores needed for next loading. Note that the original color
	 * of the Home is black. So that it will become visible after first visit.
	 */
	@Override
	public void render( Graphics g, Point onScreenPosition ) {
		if( this.avatar != null ) {
			this.color = this.isScoreChangeBigEnough( this.avatar.getScores() ) ?
			                     Color.green : Color.red;
		}
		                                      			
		super.render( g, onScreenPosition );
		
		if( this.avatar != null ) {
			this.drawScoresWhileLastVisited( g, onScreenPosition );
		}
		
	}

	private	void drawScoresWhileLastVisited( Graphics g, Point onScreenPosition ) {
		float scoresToNextHeal = this.avatar.getScores() - 
							 this.scoresWhileLastVisited - 
							 this.getNeededChange();
		g.drawString( (int)scoresToNextHeal+"", 
		              onScreenPosition.x - this.shape.getWidth()/2 + 5, 
		              onScreenPosition.y - this.shape.getHeight()/2 + 5 );
		
		
	}
}
