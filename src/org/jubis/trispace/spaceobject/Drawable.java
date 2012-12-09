package org.jubis.trispace.spaceobject;

import org.jubis.trispace.util.Point;
import org.newdawn.slick.Graphics;

public interface Drawable {
	public void render( Graphics g, Point onScreenPosition );
}
