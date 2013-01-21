package org.jubis.trispace.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jubis.trispace.TriSpace;
import org.jubis.trispace.spaceobject.factory.AvatarFactory;
import org.jubis.trispace.spaceobject.feature.Movable;
import org.jubis.trispace.spaceobject.gameentity.Tri;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.spaceobject.object.SpaceShip;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * High level component of the application to contain all object data, 
 * all the {@link GameStatusModel}s the {@link Space} and to update 
 * them all when model is called to update itself.
 */
public final class TriSpaceModel {
	private final List<SpaceObject> objects = new ArrayList<SpaceObject>();
	private AvatarFactory avatarFactory;
	private Space space;
	
	private Map <Class<?>,GameStatusModel<?>> models = 
								new HashMap <Class<?>,GameStatusModel<?>> ();
	
	private List<ParticleSystem> effects = new ArrayList<ParticleSystem>();
	private List<ScreenEffect> screenEffects = new ArrayList<ScreenEffect>();
	
	private final Adder adder = new Adder( this.objects, 
	                                       this.effects, 
	                                       this.screenEffects );
	
	/**
	 * Initializes the model
	 */
	public void init() {
		this.initModels();
		
		
		this.space = new Space( 2000, 2000, 
		                        this.adder, 
		                        this.objects,
		                        (PlayerLevel)this.models.get( PlayerLevel.class ) );
		this.space.constructSpace();
		
		this.avatarFactory = new AvatarFactory( this.objects, 
		                                        this.space, 
		                                        this.adder );
		
		Point starCenter = new Point( this.space.getWidth()/2, 
		                              this.space.getHeight()/2 );
		this.effects.add( Util.loadParticleSystem( "stars.xml", starCenter ) );
		
		ScreenEffect sEffect = new ScreenEffect( "The Game is On", 
		                                         3000, 
		                                         true );
		this.adder.addScreenEffect( sEffect );
	}
	
	private void initModels() {
		AvatarScores avatarScores = new AvatarScores( null );
		
		PlayerLevel level = new PlayerLevel( avatarScores );
		
		this.models.put( AvatarScores.class, avatarScores );
		this.models.put( AvatarHealth.class, new AvatarHealth( null ) );
		this.models.put( AvatarAmmunition.class, new AvatarAmmunition( null ) );
		this.models.put( PlayerLevel.class, level );
	}
	
	/**
	 * @return Reference to the Space where the game takes place
	 */
	public Space getSpace() {
		return this.space;
	}
	
	/**
	 * @return List of all objects
	 */
	public List<SpaceObject> getObjects() {
		return this.objects;
	}
	/**
	 * @return List of all particle systems in this model
	 */
	public List<ParticleSystem> getEffects() {
		return this.effects;
	}
	/**
	 * @return List of all screen effects in this model
	 */
	public List<ScreenEffect> getScreenEffects() {
		return screenEffects;
	}

	/**
	 * When the game should be ended this method have to be called.
	 * This makes all the effects and finally ends the game.
	 * 
	 * @param container Reference to the game container so that the game
	 * 					can really be ended.
	 */
	public void gameOver( TriSpace container ) {
		this.adder.addScreenEffect( new GameOver( container ) );
	}
	
	/**
	 * Creates the avatar and adds it to objects.
	 * 
	 * @return Avatar
	 */
	public SpaceShip addAvatar() {
		Tri tri = this.avatarFactory.getNew();
		this.objects.add( tri );
		
		for( Entry<Class<?>,GameStatusModel<?>> model : this.models.entrySet() ) {
			if( model.getValue() instanceof AvatarStatusModel<?> ) {
				AvatarStatusModel<?> asm = ((AvatarStatusModel<?>)model.getValue());
				asm.setAvatar( tri );
			}
		}
		
		return tri;
	}

	/**
	 * Update the model: move {@link Movable}s etc.
	 * 
	 * @param delta
	 */
	public void update( int delta ) {
		System.out.println( "update === " + delta );
		
		this.updateObjects( delta );
		this.updateParticleSystems( delta );
		this.updateScreenEffects( delta );
		this.updateModels();

//		for( int i = 0; i < 100000000; i++ ) {
//			int a = 10*10;
//		}
	}
	

	private void updateObjects( int delta ) {
		this.space.update( this.objects, delta );
		for( int i = 0; i < this.objects.size(); i++ ) {
			SpaceObject object = this.objects.get( i );
			object.update( delta, this.space );
			
			if( ! object.isAlive() ) {
				this.objects.remove( i );
			}
		}
	}

	private void updateParticleSystems( int delta ) {
		for( int i = 0; i < this.effects.size(); i++ ) {
			ParticleSystem particleSystem = this.effects.get( i );
			
			particleSystem.update( delta );
			
			if( particleSystem.getEmitterCount() == 0 ) {
				this.effects.remove( particleSystem );
			}
		}
	}
	
	private void updateScreenEffects( int delta ) {
		for( int i = 0; i < this.screenEffects.size(); i++ ) {
			ScreenEffect screenEffect = this.screenEffects.get( i );
			
			screenEffect.update( delta );
			
			if( ! screenEffect.isAlive() ) {
				this.screenEffects.remove( screenEffect );
				if( screenEffect instanceof GameOver ) {
					((GameOver)screenEffect).effectIsOver();
				}
			}
		}
	}

	private void updateModels() {
		for( Entry <Class<?>,GameStatusModel<?>> model : this.models.entrySet() ) {
			model.getValue().update();
		}
	}

	/**
	 * Accessor for all the GameStatusModels.
	 * 
	 * @param type Type of the model to be returned (only one model per type exists)
	 * 
	 * @return Reference to the model itself
	 */
	@SuppressWarnings("unchecked")
	public <M extends GameStatusModel<?>> M getModel( Class<M> type ) {
		return (M)this.models.get( type );
	}
}
