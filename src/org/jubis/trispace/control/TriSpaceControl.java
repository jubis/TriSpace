package org.jubis.trispace.control;

import org.jubis.trispace.model.TriSpaceModel;
import org.jubis.trispace.spaceobject.SpaceShip;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.view.TriSpaceView;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class TriSpaceControl {
	private final TriSpaceModel model;
	private final TriSpaceView view;
	private SpaceShip avatar;
	
	public TriSpaceControl( TriSpaceModel model, TriSpaceView view ) {
		this.model = model;
		this.view = view;
	}

	public void init( GameContainer container ) {
		this.avatar = this.model.addAvatar( new Point( 100, 100 ) );
	}

	public void update( Input input, int delta ) {
		this.controlAvatar( input, delta );
		this.model.update( delta );
	}

	private void controlAvatar( Input input, int delta ) {
		if( input.isKeyDown( Input.KEY_UP ) ) {
			for( int i = 0; i < delta; i++ ) 
				this.avatar.changeSpeed( true );
		} else if( input.isKeyDown( Input.KEY_DOWN ) ) {
			for( int i = 0; i < delta; i++ ) 
				this.avatar.changeSpeed( false );
		}
		if( input.isKeyDown( Input.KEY_LEFT ) ) {
			this.avatar.changeRotation( true );
		} else if( input.isKeyDown( Input.KEY_RIGHT ) ) {
			this.avatar.changeRotation( false );
		}
	}

}
