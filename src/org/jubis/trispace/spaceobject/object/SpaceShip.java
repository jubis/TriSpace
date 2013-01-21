package org.jubis.trispace.spaceobject.object;

import java.util.Map;

import org.jubis.trispace.control.ShooterScoreListener;
import org.jubis.trispace.model.Adder;
import org.jubis.trispace.model.Space;
import org.jubis.trispace.spaceobject.factory.ExplosiveFactory;
import org.jubis.trispace.spaceobject.feature.Damageable;
import org.jubis.trispace.spaceobject.feature.Shooter;
import org.jubis.trispace.spaceobject.gameentity.AmmoBox;
import org.jubis.trispace.util.AmmoCounter;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Power;
import org.jubis.trispace.util.Speed;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * Special kind of NormalSpaceObject that is meant to fly around
 * the space etc.<br>
 * Technically its special feature is Power. Its rotation and
 * acceleration can be made so that in every update it accelerates the 
 * amount of its Power. This feature is very useful when the spaceship 
 * is the avatar of the player.<br>
 * SpaceShip has also ability to shoot Rockets<br>
 * It's possible to set ShooterScoreListener to listen when SpaceShip scores. 
 * To make the code simpler only one listener can be set at a time.
 * 
 * @see Power
 * @see Explosive
 * @see ShooterScoreListener
 */
public abstract class SpaceShip extends NormalSpaceObject implements Shooter {
	
	/**
	 * The power of this SpaceShip
	 */
	protected Power power;
	/**
	 * The status of this very SpaceShip
	 */
	protected final SpaceShipStatus status;
	/**
	 * The ammo storage of this SpaceShip
	 */
	protected final SpaceShipAmmunition ammunition = new SpaceShipAmmunition();
	// to avoid unnecessary stuff only one listener can be set
	private ShooterScoreListener scoreListener;
	private boolean shoot;
	/**
	 * The factory that creates all the new explosive that the SpaceShip uses
	 */
	protected final ExplosiveFactory explosiveFactory;
	/**
	 * If this SpaceShio is the avatar
	 */
	protected final boolean isAvatar;
	private boolean insideHome;
	private int	scores;
	
	/**
	 * Created new SpaceShip with given parameters.
	 * 
	 * @param shape Appearance of the SpaceShip (also default collision shape)
	 * @param position Starting position to be set for the SpaceShip
	 * @param adder ObjectAdder so that SpaceShip can create Rockets
	 * @param power Power of the SpaceShip
	 * @param status Starting status of this SpaceShip
	 * @param isAvatar Is this SpaceShip the avatar of this game
	 */
	public SpaceShip( Shape shape, 
	                  Point position, 
	                  Adder adder,
	                  Power power,
	                  SpaceShipStatus status,
	                  boolean isAvatar ) {
		super( shape, position, adder );
		this.power = power;
		this.status = status;
		this.isAvatar = isAvatar;
		
		this.explosiveFactory = new ExplosiveFactory( adder );
		
		this.explosiveFactory.setExplosiveTypeInUse( ExplosiveType.BASIC_ROCKET );
		this.ammunition.insertAmmoBox( new AmmoBox( ExplosiveType.BASIC_ROCKET, 200, false ) );
//		this.ammunition.insertAmmoBox( new AmmoBox( ExplosiveType.DOUBLE_ROCKET, 0, true ) );
//		this.ammunition.insertAmmoBox( new AmmoBox( ExplosiveType.BOUNCY_ROCKET, 0, true ) );
//		this.ammunition.insertAmmoBox( new AmmoBox( ExplosiveType.MINE, 0, true ) );
//		this.ammunition.insertAmmoBox( new AmmoBox( ExplosiveType.EXPLOSIVE_ROCKET, 0, true ) );
	}
	
	/**
	 * Returns only a copy of the status
	 * @return Copy of the status
	 */
	public SpaceShipStatus getStatus() {
		return new SpaceShipStatus( this.status );
	}
	
	/**
	 * Increases the health status of this SpaceShip 
	 * </p>
	 * This is no method to damage the SpaceShip, the healing amount must be
	 * positive.
	 * 
	 * @param amount Amount of health that will be added.
	 */
	public void heal( int amount ) {
		if( amount < 0 ) {
			throw new IllegalArgumentException( "Healing amount not positive" );
		}
		this.status.changeHealth( amount );
		this.color = this.status.getHealthColor();
	}
	
	/**
	 * Get copy of the map of ammunition of this SpaceShip
	 * @return Copy of the ammunition list
	 */
	public Map<ExplosiveType, AmmoCounter> getAmmunition() {
		return this.ammunition.getAll();
	}
	/**
	 * @return The type of explosion that this SpaceShio uses currently
	 */
	public ExplosiveType getExplosiveTypeInUse() {
		return this.explosiveFactory.getTypeInUse();
	}
	/**
	 * @return Is this SpaceShip the avatar
	 */
	public boolean isAvatar() {
		return this.isAvatar;
	}
	/**
	 * Change the status if the SpaceShip is inside a Home or not.
	 * If true shooting is disabled. 
	 * @param insideHome
	 */
	public void setInsideHome( boolean insideHome ) {
		this.insideHome = insideHome;
	}
	/**
	 * @return The score amount earned by this shooter
	 */
	public int getScores() {
		return this.scores;
	}
	
	/**
	 * Very easy setter method that only requires the number of the wanted
	 * explosion. The right numbers can be seen in the array ExplosiveType.values().
	 * Notice that first number is one not zero.
	 * 
	 * @param number Number of explosive (first explosive is number one not zero)
	 */
	public void setExplosiveInUse( int number ) {
		if( Util.isInRange( number, 0, ExplosiveType.values().length ) ) {
			ExplosiveType type = ExplosiveType.values()[ number-1 ];
			if( this.ammunition.getAmount( type ) > 0 ) {
				this.explosiveFactory.setExplosiveTypeInUse( type );
			}
		}
	}
	
	/**
	 * Handles the given ammo box for this SpaceShip and makes it to put
	 * in its storage.
	 * 
	 * @param box The box to be picked
	 * 
	 * @see AmmoBox
	 */
	public void pickAmmoBox( AmmoBox box ) {
		this.ammunition.insertAmmoBox( box );
	}
	
	/**
	 * Replaces the score listener with given listener. 
	 * @param scoreListener New listener
	 */
	public void setScoreListener( ShooterScoreListener scoreListener ) {
		this.scoreListener = scoreListener;
	}
	
	/**
	 * Accelerates the SpacShip the amount of its Power. 
	 * Same thing with braking.
	 * 
	 * @param accelerate Accelerate or brake
	 * @param delta Time in millis since last update
	 * 
	 * @see Power
	 */
	public void changeSpeed( boolean accelerate, int delta ) {
		float valueChange = 0;
		Vector2f direction = this.getDirection();
		if( accelerate ) {
			valueChange = this.power.getAcceleration();
		} else {
			valueChange = this.power.getBraking();
			direction = direction.negate();
		}
		
		valueChange = valueChange * delta;
		
		this.changeSpeed( new Speed( valueChange, 
		                             direction ) );
	}

	/**
	 * Set the rotation of the SpacShip to be the amount of its Power.
	 * The direction is determined with the parameter.<br>
	 * No delta is used because the SpaceShip has a fixed angular speed 
	 * determined in its Power. So the SpaceShip either rotates with that
	 * speed or doesn't rotate at all.
	 * 
	 * @param clockwise Direction of the rotation
	 * 
	 * @see Power
	 */
	public void changeRotation( boolean clockwise ) {
		float angularSpeed = this.power.getRotation();
		if( clockwise ) {
			angularSpeed = -angularSpeed;
		}
		this.motion.anguralSpeed = angularSpeed;
	}
	
	/**
	 * When the SpaceShip accelerates it has to be known what
	 * is the direction of the acceleration.
	 * (differs from current Speeds direction).
	 * This method gives the direction.
	 * 
	 * @return Direction of the acceleration
	 * 
	 * @see Speed
	 */
	protected abstract Vector2f getDirection();
	
	/**
	 * @return The position where a shot explosive will be positioned
	 */
	protected abstract Point getBarrel();
	
	/**
	 * Just the general implementation, except SpaceShip can't shoot while 
	 * inside home.
	 */
	@Override
	public void shoot() {
		// no shooting inside home
		this.shoot = !this.insideHome;
	}
	
	private void excecuteShooting() {
		if( this.ammunition.use( this.getExplosiveTypeInUse() ) ) {
			Explosive[] parts = 
					this.explosiveFactory.createExplosives( this.getBarrel(), 
					                                        this.getDirection(),
					                                        this );
			for( Explosive part : parts ) {
				this.adder.addObject( part );
			}
		} 
		else if( this.changeToWorseExplosive() ) {
			this.excecuteShooting();
		}
	}
	 
	private boolean changeToWorseExplosive() {
		int currentType = this.getExplosiveTypeInUse().ordinal();
		for( int i = currentType; i > -1; i-- ) {
			ExplosiveType type = ExplosiveType.values()[i];
			if( this.ammunition.getAll().containsKey( type ) ) {
				this.explosiveFactory.setExplosiveTypeInUse( type );
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Notifies the score listener about scoring.
	 * 
	 * @see ShooterScoreListener
	 */
	@Override
	public void score( Damageable damaged ) {
		if( scoreListener != null ) {
			this.scores = this.scoreListener.scored( damaged );
		}
	}

	/**
	 * Decreases the health of the SpaceShip the amount of damage.
	 */
	@Override
	public void damage( SpaceObject damager, int damage ) {
		this.status.damage( damage );
		this.color = this.status.getHealthColor();
		
		if( this.status.getHealth() <= 0 ) {
			this.alive = false;
		}
	}
	
	
	/**
	 * When SpaceShip updates it also shoots if it is ordered to shoot
	 * in next update.
	 */
	@Override
	public void update( int delta, Space space ) {
		this.insideHome = false;
		
		super.update( delta, space );
		
		if( this.shoot ) {
			this.excecuteShooting();
			this.shoot = false;
		}
	}
}