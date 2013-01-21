package org.jubis.trispace.view;

import java.util.ArrayList;
import java.util.List;

import org.jubis.trispace.model.AvatarAmmunition;
import org.jubis.trispace.model.AvatarHealth;
import org.jubis.trispace.model.AvatarScores;
import org.jubis.trispace.model.PlayerLevel;
import org.jubis.trispace.model.ScreenEffect;
import org.jubis.trispace.model.TriSpaceModel;
import org.jubis.trispace.spaceobject.object.SpaceObject;
import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Util;
import org.jubis.trispace.view.decoration.AvatarAmmunitionGraph;
import org.jubis.trispace.view.decoration.AvatarHealthGraph;
import org.jubis.trispace.view.decoration.Decoration;
import org.jubis.trispace.view.decoration.LevelGraph;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * High level component of the application.
 * Takes care of every drawing and calculating objects' on screen positions.
 */
public final class TriSpaceView {
	private final TriSpaceModel model;
	private float screenWidth;
	private float screenHeight;
	private Point screenPosition = new Point( 0, 0 );
	private List<Decoration> decorations = new ArrayList<Decoration>();
	
	/**
	 * Creates a new instance attached to given model. 
	 * 
	 * @param model Source for the data about the game state
	 */
	public TriSpaceView( TriSpaceModel model ) {
		this.model = model;
	}
	
	private Point getBoundaries() {
		return new Point( this.screenWidth, this.screenHeight );
	}
	
	private Point getCenter() {
		return new Point( this.screenWidth/2, this.screenHeight/2 );
	}
	
	/**
	 * Initializes all the necessary things.
	 * 
	 * @param container Container of this game
	 */
	public void init( GameContainer container ) {
		
		this.screenWidth = container.getWidth();
		this.screenHeight = container.getHeight();
		
//		this.decorations.add( new AvatarStatusLabel( LabelType.Health, 
//		                                             this.model.getModel( AvatarHealth.class ) ) );
//		this.decorations.add( new AvatarStatusLabel( LabelType.Score, 
//		                                             this.model.getModel( AvatarScores.class ) ) );
//		this.decorations.add( new StatusLabel( LabelType.PlayerLevel,
//		                                       this.model.getModel( PlayerLevel.class ) ) );
		
		this.decorations.add( new AvatarAmmunitionGraph( this.screenWidth, 
		                                                 this.model.getModel( AvatarAmmunition.class ) ) );
		this.decorations.add( new LevelGraph( this.model.getModel( PlayerLevel.class ),
		                                      this.model.getModel( AvatarScores.class ) ) );
		this.decorations.add( new AvatarHealthGraph( this.model.getModel( AvatarHealth.class ) ) );
	}

	/**
	 * Draws all objects to their right positions.
	 * 
	 * @param container Container of this game
	 * @param g Graphics object for the drawing
	 */
	public void render( GameContainer container, Graphics g ) {
		container.setShowFPS( container.getFPS() < 60 );
		
		this.renderEffects( container, g );
		this.renderObjects( container, g );
		this.renderDecorations( container, g );
		this.renderScreenEffects( g );
	}

	private void renderObjects( GameContainer container, Graphics g ) {
		int total = 0;
		int drawn = 0;
		for( SpaceObject object : this.model.getObjects() ) {
			total++;
			Point position = this.calculateOnScreenPosition( object );
			if( this.isOnScreen( object.getShapeInPosition( position ) ) ) {
				drawn++;
				object.render( g, position );
			}
		}
		System.out.println( drawn + "/" + total );
	}
	
	private void renderEffects( GameContainer container, Graphics g ) {
		for( ParticleSystem effect : this.model.getEffects() ) {
			Point position = this.calculateOnScreenPosition( effect );
			effect.render( position.x, position.y );
		}
	}
	private void renderDecorations( GameContainer container, Graphics g ) {
		this.drawBorders( g );
		
		for( Decoration decoration : this.decorations ) {
			g.setColor( Color.white );
			decoration.render( g );
		}
	}
	
	private void renderScreenEffects( Graphics g ) {
		for( ScreenEffect effect : this.model.getScreenEffects() ) {
			effect.render( g, this.getCenter() );
		}
	}

	/**
	 * Checks if the shape is even partially on the screen. Use this to 
	 * determine if it should be drawn.
	 * 
	 * @param shapeInPosition Shape to be tested set to its position on the
	 * 						  screen (or outside the screen)
	 * 
	 * @return Is it on screen or not
	 */
	private boolean isOnScreen( Shape shapeInPosition ) {
		return Util.isPartiallyInRange( shapeInPosition, 
		                                0, this.screenWidth, 
		                                0, this.screenHeight );
	}

	/**
	 * Draws the borders of the space (even if they wouldn't on screen).
	 * 
	 * @param g
	 */
	private void drawBorders( Graphics g ) {
		Rectangle borders = this.model.getSpace().getBorders();
		Transform t = Transform.createTranslateTransform( -this.screenPosition.x, 
		                                                  -this.screenPosition.y );
		
		g.setColor( Color.white );
		g.draw( borders.transform( t ) );
	}

	/**
	 * Calculates how much the object has to be moved from the origin.<br>
	 * Does the conversion from coordinates in space to coordinates on screen.
	 * 
	 * @param object Object which position is been calculated
	 * 
	 * @return Offset to be added to the default shape of the object
	 */
	private Point calculateOnScreenPosition( SpaceObject object ) {
		Point position = object.getPosition().translate( this.screenPosition.negate() );
		return position;
	}
	
	private Point calculateOnScreenPosition( ParticleSystem particleSystem ) {
		Point position = new Point( particleSystem.getPositionX(),
		                            particleSystem.getPositionY() );
		return position.translate( this.screenPosition.negate() );
	}

	/**
	 * Tells the screen where it is located in the space. The information is
	 * used to determining where the objects in the space are on the screen.
	 * 
	 * @param screenPosition Top left corner of the screen in space coordinates
	 */
	public void setScreenPosition( Point screenPosition ) {
		// finding the point where screen origin has to be for the
		// screen bottom right corner to be on the edge of the space
		Point spaceBoundaries = this.model.getSpace().getBoundaries();
		spaceBoundaries = spaceBoundaries.translate( this.getBoundaries().negate() );
		// wont set the screen position so that things outside the space
		// would be shown
		this.screenPosition = screenPosition.boundTo( new Point( 0, 0 ), 
		                                              spaceBoundaries );
	}

}
