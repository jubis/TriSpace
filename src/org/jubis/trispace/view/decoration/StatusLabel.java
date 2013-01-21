package org.jubis.trispace.view.decoration;

import org.jubis.trispace.model.GameStatusModel;
import org.newdawn.slick.Graphics;

public class StatusLabel extends Decoration {
	/**
	 * Type of the label
	 */
	protected LabelType type;
	/**
	 * Model for the data to be shown on the label
	 */
	protected GameStatusModel<?> model;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param type Type of this label to determine some basic settings
	 * @param model Model to be the source of the label's value
	 */
	public StatusLabel( LabelType type, GameStatusModel<?> model ) {
		super( type.position );
		this.type = type;
		this.model = model;
	}
	
	/**
	 * @return Default label using the name and the value on the model
	 */
	protected String getLabel() {
		return this.type.name + ": " + this.model.getValue();
	}
	
	/**
	 * Draws the name and the status on the screen as a string.
	 */
	@Override
	public void render( Graphics g ) {
		g.drawString( this.getLabel(), 
		              this.leftTopPosition.x, 
		              this.leftTopPosition.y );
	}
}
