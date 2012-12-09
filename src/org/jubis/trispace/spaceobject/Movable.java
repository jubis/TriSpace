package org.jubis.trispace.spaceobject;

import org.jubis.trispace.util.Motion;
import org.jubis.trispace.util.Speed;

public interface Movable {
	public Motion getMotion();
	
	public void changeSpeed( Speed change );
	public void changeSpeed( float change );
	
	public void changeRotation( float angularSpeed );
	
	public void move( int delta );
}
