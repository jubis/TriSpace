package org.jubis.trispace.view;

import org.jubis.trispace.model.TriSpaceModel;
import org.jubis.trispace.spaceobject.SpaceObject;
import org.jubis.trispace.spaceobject.SpaceShip;
import org.jubis.trispace.util.Point;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class TriSpaceView {
	private final TriSpaceModel model;
	private boolean fullscreen;
	//private float[] screenPosition = { 0, 0 };
	
	public TriSpaceView( TriSpaceModel model, boolean fullscreen ) {
		this.model = model;
		this.fullscreen = fullscreen;
	}
	
	public void init( GameContainer container ) throws SlickException {
		if( this.fullscreen ) {
			((AppGameContainer)container).setDisplayMode( container.getScreenWidth(),
			                                              container.getScreenHeight(), 
			                                              true );
		}
	}

	public void render( GameContainer container, Graphics g ) {
		for( SpaceObject object : this.model.getObjects() ) {
			Point position = calculateOnScreenPosition( object ); 
			object.render( g, position );
			System.out.println( ((SpaceShip)object).getMotion() );
		}
	}

	private static Point calculateOnScreenPosition( SpaceObject object ) {
		Point position = object.getPosition().translate( null );
		return position;
	}



}
