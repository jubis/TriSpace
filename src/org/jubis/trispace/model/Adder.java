package org.jubis.trispace.model;

import java.util.Collection;

import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Simple class to enhance encapsulation.
 * Offers the possibility to add something to the model even if there 
 * wouldn't be access to it otherwise.
 */
public class Adder {
	private final Collection<SpaceObject> objects;
	private final Collection<ParticleSystem> effects;
	private final Collection<ScreenEffect> screenEffects;
	
	/**
	 * Creates a new adder to add stuff to given lists.
	 * 
	 * @param objects List to add the new objects into
	 * @param effects List to add the new particle effects into
	 * @param screenEffects List to add the new screen effects into
	 */
	public Adder( Collection<SpaceObject> objects,
	              Collection<ParticleSystem> effects,
	              Collection<ScreenEffect> screenEffects ) {
		this.objects = objects;
		this.effects = effects;
		this.screenEffects = screenEffects;
	}
	
	/**
	 * Adds new object to the list.
	 * @param object Object to be added
	 */
	public void addObject( SpaceObject object ) {
		this.objects.add( object );
	}
	
	/**
	 * Adds new particle effect to the list. 
	 * @param effect Particle system to be added
	 */
	public void addParticleSystem( ParticleSystem effect ) {
		this.effects.add( effect );
	}
	
	/**
	 * Adds new screen effect to the list.
	 * @param screenEffect Screen effect to be added
	 */
	public void addScreenEffect( ScreenEffect screenEffect ) {
		this.screenEffects.add( screenEffect );
	}
}
