package org.jubis.trispace.spaceobject.object;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Field;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.feature.Collidable;
import org.jubis.trispace.spaceobject.feature.Damageable;
import org.jubis.trispace.spaceobject.feature.Shooter;
import org.jubis.trispace.util.Motion;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Speed;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Superclass for all small objects that is in the space and damage
 * other entities. Dies immediately when completely outside of the Space.
 * 
 * @see Space#isInsideSpace(SpaceObject)
 */
public abstract class Explosive extends NormalSpaceObject {
	protected Shooter source;
	protected Explosive sibling;
	
	private ParticleSystem explosion;
	
	/**
	 * Creates a new explosive with given parameters.
	 * 
	 * @param shape Appearance of this explosive (also default for collision shape)
	 * @param position Position where the center of the shape will be set to
	 * @param source The game entity that created this explosion
	 * @param adder Adder so that new effects can be added
	 */
	public Explosive( Shape shape, 
	                  Point position, 
	                  Shooter source,
	                  Adder adder ) {
		super( shape, position, adder );
		
		this.source = source;
		this.explosion = Util.loadParticleSystem( this.getExplosionEffectFile(), 
	                                              this.getPosition() );
	}
	
	/**
	 * Generates a general motion that can be used by moving explosives.
	 * 
	 * @param direction Direction of the motion.
	 * 
	 * @return The generated motion
	 */
	protected static Motion getGeneralMotion( Vector2f direction ) {
		return new Motion( new Speed( 2f, direction ), 0 );
	}
	
	/**
	 * Sets the explosion information that it has been created simultaneously
	 * with some other explosive.
	 * @param sibling Explosive that was created simultaneously with this one
	 */
	public void setSibling( Explosive sibling ) {
		this.sibling = sibling;
	}
	
	/**
	 * When Explosive collides with something else than it source it dies
	 * and damages the object it collided with.
	 * 
	 * @see NormalSpaceObject#damageDamageable(Collidable, int)
	 */
	@Override
	public void reactToCollision( SpaceObject collider ) {
		if( ! collider.getClass().equals( this.getClass() ) &&
			! collider.equals( (SpaceObject)this.source ) && 
			! collider.equals( this.sibling ) ) {
			if( this.damageDamageable( collider, this.getDamage() ) ) {
				this.alive = false;
				this.explode();
				this.source.score( (Damageable)collider );
			}
		}
	}
	
	/**
	 * Creates the explosion effect.
	 */
	protected void explode() {
		this.explosion.setPosition( this.getPosition().x, this.getPosition().y );
		this.adder.addParticleSystem( this.explosion );
	}
	
	/**
	 * Explosives don't react to any Field
	 * 
	 * @see Field
	 */
	@Override
	public boolean doReactToField( Field<?> interaction ) {
		return false;
	}
	
	/**
	 * After basic update checks if explosive is outside space. If it is it dies.
	 */
	@Override
	public void update( int delta, Space space ) {
		super.update( delta, space );
		if( ! space.isInsideSpace( this ) ) {
			this.alive = false;
		}
	}
	
	/**
	 * Return how much this explosive does when damaging other objects.
	 * 
	 * @return Amount of damage that damaged object should be given
	 */
	protected abstract int getDamage();
	
	/**
	 * @return Filename for the XML file that contains the particle effect
	 * 		   for the explosion of this Explosion.
	 */
	protected abstract String getExplosionEffectFile();
}
