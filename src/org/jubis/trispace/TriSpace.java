package org.jubis.trispace;

import org.jubis.trispace.control.TriSpaceControl;
import org.jubis.trispace.model.TriSpaceModel;
import org.jubis.trispace.view.TriSpaceView;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class TriSpace extends AppGameContainer {
	
	private TriSpace( TriSpaceGame game ) throws SlickException {
		super( game );
	}
	
	public static TriSpace createTriSpace() throws SlickException {
		TriSpaceModel model = new TriSpaceModel();
		TriSpaceView view = new TriSpaceView( model, true );
		TriSpaceControl control = new TriSpaceControl( model, view );
		TriSpaceGame game = new TriSpaceGame( view, control );
		return new TriSpace( game );
	}
	
	public static void main( String[] args ) throws SlickException {
		TriSpace app = TriSpace.createTriSpace();
		
		app.start();
	}
}
