package org.jubis.trispace.view.decoration;

import org.jubis.trispace.model.AvatarStatusModel;

/**
 * Simple text on the screen that presents the status of the avatar.
 */
public class AvatarStatusLabel extends StatusLabel {
	
	public AvatarStatusLabel( LabelType type, AvatarStatusModel<?> model ) {
		super( type, model );
	}
	
	/**
	 * Draws the name and the status on the screen as a string.   
	 */
	@Override
	public String getLabel() {
		String label;
		if( ((AvatarStatusModel<?>)this.model).isAvatar() ) {
			label = this.type.name + ": " + this.model.getValue();
		} else {
			label = "No avatar yet";
		}
		return label;
	}

}
