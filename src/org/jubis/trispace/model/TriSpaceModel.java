package org.jubis.trispace.model;

import java.util.ArrayList;
import java.util.List;

import org.jubis.trispace.spaceobject.Movable;
import org.jubis.trispace.spaceobject.SpaceObject;
import org.jubis.trispace.spaceobject.SpaceShip;
import org.jubis.trispace.spaceobject.Tri;
import org.jubis.trispace.util.Point;

public class TriSpaceModel {
	private final List<SpaceObject> objects = new ArrayList<SpaceObject>();

	public void addObject( SpaceObject object ) {
		if( object != null ) {
			this.objects.add( object );
		}
	}
	
	public List<SpaceObject> getObjects() {
		return this.objects;
	}

	public SpaceShip addAvatar( Point start ) {
		Tri tri = new Tri( start );
		this.addObject( tri );
		return tri;
	}

	public void update( int delta ) {
		for( SpaceObject object : this.objects ) {
			if( object instanceof Movable ) {
				((Movable) object).move( delta );
			}
		}
	}

}
