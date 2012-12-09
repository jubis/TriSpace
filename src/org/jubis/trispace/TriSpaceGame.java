package org.jubis.trispace;

import org.jubis.trispace.control.TriSpaceControl;
import org.jubis.trispace.view.TriSpaceView;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class TriSpaceGame implements Game {
	private final TriSpaceView view;
	private final TriSpaceControl control;

	public TriSpaceGame( TriSpaceView view, TriSpaceControl control ) {
		this.view = view;
		this.control = control;
	}

	@Override
	public boolean closeRequested() {
		return true;
	}

	@Override
	public String getTitle() {
		return "TriSpace";
	}

	@Override
	public void init( GameContainer container ) throws SlickException {
		this.control.init( container );
		this.view.init( container );
	}

	@Override
	public void update( GameContainer container, int delta ) throws SlickException {
		this.control.update( container.getInput(), delta );
	}

	@Override
	public void render( GameContainer container, Graphics g ) throws SlickException {
		this.view.render( container, g );
	}
}
