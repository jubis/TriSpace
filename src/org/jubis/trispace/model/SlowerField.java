package org.jubis.trispace.model;

import org.jubis.trispace.spaceobject.object.NormalSpaceObject;
import org.jubis.trispace.util.Speed;

/**
 * Air resistance like {@link Field} that reduces the {@link Speed} of
 * object. The effect is directly proportional to the value of the {@link Speed}.
 */
public class SlowerField extends Field<NormalSpaceObject> {
	/**
	 * Factor for the amount of the effect
	 */
	private float force;
	
	/**
	 * Creates a new field with given slowing force.
	 * 
	 * @param force Float used as a factor while slowing objects down
	 */
	public SlowerField( float force ) {
		this.force = force;
	}
	
	/**
	 * SlowerField effects only on NormalSpaceObjects
	 */
	@Override
	protected Class<NormalSpaceObject> getType() {
		return NormalSpaceObject.class;
	}
	
	/**
	 * Reduces the {@link Speed} of the given object.
	 * @see NormalSpaceObject#changeSpeedBoundToPositive(float)
	 */
	@Override
	protected void createEffect( NormalSpaceObject object, int delta ) {
		float effect = -force * delta * object.getMotion().speed.getValue();
		object.changeSpeedBoundToPositive( effect );
	}
	
}
