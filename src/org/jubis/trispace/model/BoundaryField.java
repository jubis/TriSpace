package org.jubis.trispace.model;

import org.jubis.trispace.spaceobject.object.NormalSpaceObject;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Speed;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * Special kind of field around the Space that prevent
 * the objects to outside of the Space
 * 
 * @see Space
 */
public class BoundaryField extends Field<NormalSpaceObject> {
	private float width;
	private float height;
	
	/**
	 * Creates a new field with given size.
	 * 
	 * @param width Width of the field
	 * @param height Height of the field
	 */
	public BoundaryField( float width, float height ) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Type approved by this class is {@link NormalSpaceObject}
	 */
	@Override
	protected Class<NormalSpaceObject> getType() {
		return NormalSpaceObject.class;
	}
	
	/**
	 * Virtually if an objects is trying to go outside the {@link Space}
	 * allows only movement along the edge.
	 */
	@Override
	protected void createEffect( NormalSpaceObject object, int delta ) {
		Shape shape = ((SpaceObject)object).getShape(); 
		Speed currentSpeed = object.getMotion().speed;
		Float currentDirection = (float) currentSpeed.getDirection().getTheta();
		
		if( currentSpeed.getValue() == 0 ) {
			return;
		}
		
		if( ( shape.getMinX() <= 0 && Util.isInRange( currentDirection, 90, 270 ) ) ||
			( shape.getMaxX() >= this.width && Util.isInRange( currentDirection, 
			                                              270, 360, 0, 90 ) ) ) {
			// hits left or right side - allow only y movement
			currentSpeed.projectSpeed( new Vector2f( 0, 1 ) );
		} 
		if( ( shape.getMinY() <= 0 && Util.isInRange( currentDirection, 180, 360 ) ) ||
		    ( shape.getMaxY() >= this.height && Util.isInRange( currentDirection, 0, 180 ) ) ) {
			// hits top or bottom - allow only x movement
			currentSpeed.projectSpeed( new Vector2f( 1, 0 ) );
		}
		// ps. in case of both ifs are true the object won't move at all
		
		object.getMotion().speed = currentSpeed;
	}

}
