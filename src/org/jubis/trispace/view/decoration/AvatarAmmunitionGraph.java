package org.jubis.trispace.view.decoration;

import org.jubis.trispace.model.AvatarAmmunition;
import org.jubis.trispace.spaceobject.object.ExplosiveType;
import org.jubis.trispace.util.AmmoCounter;
import org.jubis.trispace.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Graph that shows the ammunition status of the avatar for player
 */
public class AvatarAmmunitionGraph extends Decoration {
	private static final float BAR_WIDTH_MAX = 100;
	private static final float BAR_HEIGHT = 20;
	private static final float TEXT_HEIGHT = 20;
	
	private AvatarAmmunition model;
	
	/**
	 * Creates a new instance to get its data from given model.
	 * 
	 * @param screenWidth Screen width so that the graph can be positioned
	 * 					  correctly.
	 * @param model
	 */
	public AvatarAmmunitionGraph( float screenWidth, AvatarAmmunition model ) {
		super( new Point( screenWidth - BAR_WIDTH_MAX - 50, 
		                  50 ) );
		this.model = model;
	}

	/**
	 * Renders explosion type names, graphs and ammo amounts
	 */
	@Override
	public void render( Graphics g ) {
		if( ! this.model.isAvatar() ) {
			return;
		}
		
		Point pos = this.leftTopPosition;
		for( ExplosiveType type : ExplosiveType.values() ) {
			Color color = type == this.model.getTypeInUse() ? type.color : Color.white;
			
			g.setColor( color );
			g.drawString( type.name, pos.x, pos.y );
			pos = pos.translate( 0, TEXT_HEIGHT );
			
			
			AmmoCounter counter = this.model.getValueByType( type );
			
			float barWidth = 0;
			String amountText = "";
			if( counter != null ) {
				barWidth = counter.getLoadPercent() * BAR_WIDTH_MAX;
				amountText = counter.toString();
			} else {
				barWidth = 0;
				amountText = "0";
			}
			
			g.setColor( Color.black );
			g.fillRect( pos.x, pos.y, barWidth, BAR_HEIGHT );
			
			g.setColor( color );
			g.drawRect( pos.x, pos.y, barWidth, BAR_HEIGHT );
			g.drawString( amountText, pos.x+5, pos.y );
			pos = pos.translate( 0, BAR_HEIGHT );
		}
	}

}
