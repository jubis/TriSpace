package org.jubis.trispace.spaceobject.gameentity;

import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Field;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.factory.RockFactory;
import org.jubis.trispace.spaceobject.feature.Wanted;
import org.jubis.trispace.spaceobject.object.NormalSpaceObject;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Motion;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Speed;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * Round random shaped object that flies in the space.<br>
 * When something damage rock it splits in two smaller ones.
 * Finally very small rocks just disappear.<br>
 * <br>
 * When a Rock is completely destroyed or has gone out of the space 
 * (also its children). It decreases the counter in RockFactory so that 
 * the counter knows how many Rocks (as whole or split into pieces) really exists.
 * That's why Rock knows its parent and the amount of its children alive and
 * also contains some magic to be able to decrease the counter when it's time. 
 * 
 * @see RockFactory
 */
public class Rock extends NormalSpaceObject implements Wanted {
	public static final int REACTION_TIME_MILLIS = 300;
	
	private RockFactory	factory;
	private final int size;
	private final Rock parent;
	private int children;
	
	private SpaceObject killer;
	
	/**
	 * Creates new rock with given parameters.
	 * 
	 * @param position Exact position for the rock
	 * @param size Size of the Rock - in a logarithmic scale (base is 2)
	 * @param motion Motion for the new Rock
	 * @param adder Adder so that the Rock can create children
	 * @param parent The Rock that this was before it split
	 * @param factory Factory Factory that created this Rock
	 */
	public Rock( Point position, 
	             int size, 
	             Motion motion, 
	             Adder adder,
	             Rock parent,
	             RockFactory factory ) {
		super( generateShape( size ), position, adder );
		this.motion = motion;
		this.size = size;
		this.parent = parent;
		this.factory = factory;
	}
	
	/**
	 * Creates a new "first-class" Rock so that is doesn't have a parent.
	 * 
	 * @param position Exact position for the rock
	 * @param size Size of the Rock - in a logarithmic scale (base is 2)
	 * @param motion Motion for the new Rock
	 * @param adder Adder so that the Rock can create children
	 * @param factory Factory Factory that created this Rock
	 */
	public Rock( Point position, 
	             int size, 
	             Motion motion, 
	             Adder adder,
	             RockFactory factory ) {
		this( position, size, motion, adder, null, factory );
	}
	
	/**
	 * @return Parent of this Rock
	 */
	public Rock getParent() {
		return this.parent;
	}
	/**
	 * @return The object that killed this object (if it has been killed)
	 */
	public SpaceObject getKiller() {
		return killer;
	}
	
	/**
	 * Collision shape is the is the smallest circle that can be drawn 
	 * round the Rock.
	 */
	@Override
	public Shape createCollisionShape() {
		float radius = this.shape.getBoundingCircleRadius();
		return new Circle( this.shape.getCenterX(), 
		                   this.shape.getCenterY(), 
		                   radius );
	}
	
	/**
	 * By damaging a Rock an entity gets 12-rockSize amount of scores 
	 * (min 1 score)
	 */
	@Override
	public int getScores() {
		return Math.max( 12 - this.size, 1 );
	}
	
	/**
	 * Rock damages objects it hits. However Rock first checks should it react
	 * at all. 
	 */
	@Override
	public void reactToCollision( SpaceObject collider ) {
		if( this.canReact( collider ) ) {
			//System.out.println( "Rock"+ this.hashCode() +" damages, damage: " + this.size );
			this.damageDamageable( collider, this.size );
		}
	}
	
	private void die( boolean completely ) {
		this.alive = false;
		
		if( completely ) { // no splitting, just wipe out this Rock
			if( this.parent == null ) {
				this.factory.decreaseCouter();
			} else {
				this.parent.childDied();
			}
		} 
		else { // tries to split this Rock
			if( this.size > 5 ) {
				this.split();
			} else {
				this.parent.childDied();
			}
		}
	}
	
	/**
	 * When a Rock is damaged it splits into pieces. However it first checks 
	 * should it react at all.
	 */
	@Override
	public void damage( SpaceObject damager, int damage ) {
		if( !this.canReact( damager ) ) {
			return;
		}
		//System.out.println( "Rock"+ this.hashCode() +" damaged : " + damager );
		this.die( false );
		this.killer = damager;
	}
	
	/**
	 * The ability to react is based on a reaction time. It has to go some time
	 * after the creation of the Rock before it can react. The reason is that
	 * Rocks don't disappear after the collision and they would collide again
	 * and again with the same object.
	 * 
	 * @return Can it react or not
	 */
	private boolean canReact( SpaceObject opponent ) {
		if( this.parent == null ) {
			return true;
		}
		
		boolean reactionTimeGoneBy = 
					System.currentTimeMillis() - this.creationTime > REACTION_TIME_MILLIS;
		if( opponent instanceof Rock &&
			! reactionTimeGoneBy ) {
			return false;
		}
		if( opponent.equals( this.parent.getKiller() ) &&
			! reactionTimeGoneBy ) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Creates new smaller Rocks and adds them to the model.
	 * 
	 * @see Adder
	 */
	private void split() {
		for( int i = 0; i < 2; i++ ) {
			Motion motion = this.calculateMotionForChild( i );
			this.adder.addObject( 
			           new Rock( this.getPosition(), 
			                     this.size - 2, 
			                     motion,
			                     this.adder, 
			                     this,
			                     factory ));
		}
		this.children = 2;
	}
	
	/**
	 * Calculates the Motion of the new smaller Rock after the collision.
	 * The Motion differs according to the number of the child.
	 * 
	 * @param i The number of the child
	 * 
	 * @return Motion for the child
	 * 
	 * @see Motion
	 */
	private Motion calculateMotionForChild( int i ) {
		Vector2f direction = this.getMotion().speed.getDirection();
		Vector2f perpendicular = direction.getPerpendicular();
		// knows Motions only for two children (+45 and -45 degrees)
		switch( i ) {
			case 0:
				break;
			case 1: 
				perpendicular.negateLocal();
				break;
		}
		direction.add( perpendicular );
		Speed speed = new Speed( this.getMotion().speed.getValue(), 
		                         direction );
		return new Motion( speed, this.getMotion().anguralSpeed );
	}
	
	/**
	 * Handles the situation when some of the children of this Rock
	 * has been destroyed completely.
	 */
	private void childDied() {
		this.children--;
		
		// if no children left this whole rock has been destroyed
		if( this.children == 0 ) {
			this.die( true );
		}
	}
	
	/**
	 * The Rock dies if it goes outside the Space
	 */
	@Override
	public void update( int delta, Space space ) {
		super.update(	delta,
						space );
		if( ! space.isInsideSpace( this ) ) {
			this.die( true );
		}
	}
	
	/**
	 * Rocks don't react to any field
	 */
	@Override
	public boolean doReactToField( Field<?> interaction ) {
		return false;
	}

	/**
	 * Contains the magic of how a Rock really created
	 * 
	 * @param size Guideline for the size of the shape
	 * 
	 * @return The shape for a new Rock
	 */
	private static Polygon generateShape( int size ) {
		Vector2f toNextPoint = (new Vector2f( 0, 1 )).scale( size );
		Polygon rock = new Polygon();
		Point lastPoint = new Point();
		Point newPoint = null;
		
		// This algorithm creates a regular polygon which have size amount
		// of points. To make the polygon look like a rock it adds some
		// randomness to every point. 
		for( int i = 0; i < size; i++ ) {
			// add vector to the last point to get the next point
			newPoint = lastPoint.translate( toNextPoint );
			// save the "clear" version of the point
			lastPoint = newPoint;
			
			// create some randomness to the position of the point
			newPoint = newPoint.translate( Point.randomPoint( 0, size, 0, size ) );
			
			// add point to the polygon
			rock.addPoint( newPoint.x, newPoint.y );
			
			// turn the vector to create a circle
			toNextPoint.setTheta( (float)i / (size) * 360 );
		}
		
		return rock;
	}
}
