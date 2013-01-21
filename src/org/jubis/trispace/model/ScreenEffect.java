package org.jubis.trispace.model;

import org.jubis.trispace.util.Point;
import org.jubis.trispace.util.Util;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Effect that has text and a particle effect on it. Screen effect are not to
 * be used as game effects thus screen effect's coordinates are fixed to the
 * coordinate system of the screen.
 */
public class ScreenEffect {
	/**
	 * Particle system rendered as a part of this effect
	 */
	protected ParticleSystem particleSystem;
	/**
	 * The text shown in this effect
	 */
	protected String text;
	/**
	 * The font that is used in the text
	 */
	protected UnicodeFont font;
	/**
	 * Duration of the effect in milliseconds
	 */
	protected int duration;
	/**
	 * Position of the text (the center of the text)
	 */
	protected Point position;
	private final long creationTime;
	private boolean relativeToScreenCenter;
	
	/**
	 * Creates a new effect with given text but with no particle effect.
	 * Particle effect should be added separately.
	 * 
	 * @param text Text to be shown
	 * @param duration Effect duration in milliseconds
	 * @param position Position of the center of the text
	 * @param relativeToScreenCenter Should the position to be added to
	 * 								 the center of the screen
	 */
	public ScreenEffect( String text, 
	                     int duration, 
	                     Point position,
	                     boolean relativeToScreenCenter ) {
		this.text = text;
		this.duration = duration;
		this.position = position;
		this.relativeToScreenCenter = relativeToScreenCenter;
		
		this.creationTime = System.currentTimeMillis();
		
		this.font = Util.createFont( 30 );
	}
	
	/**
	 * Creates a new effect with given text but with no particle effect.
	 * Particle effect should be added separately.
	 * <p>
	 * This should be used while the position is relative to the screen center
	 * and actually is the exact screen center.
	 * 
	 * @param text Text to be shown
	 * @param duration Effect duration in milliseconds
	 * @param relativeToScreenCenter Should the position to be added to
	 * 								 the center of the screen
	 */
	public ScreenEffect( String text, 
	                     int duration,
	                     boolean relativeToScreenCenter ) {
		this( text, duration, new Point(), relativeToScreenCenter );
	}
	
	/**
	 * Adds the particle system to this effect. Positioning of the system
	 * wont be changed.
	 * 
	 * @param particleSystem System to be used in this effect
	 */
	public void setParticleSystem( ParticleSystem particleSystem ) {
		this.particleSystem = particleSystem;
	}
	
	/**
	 * Updates mainly the particle system (if any)
	 * 
	 * @param delta Time since last update in milliseconds
	 */
	public void update( int delta ) {
		if( this.particleSystem != null ) {
			this.particleSystem.update( delta );
		}
	}
	
	/**
	 * Renders the text and the particle system on the screen.
	 * 
	 * @param g Graphics object to be used while drawing
	 * @param screenCenter Center of the screen to be used if the positioning
	 * 					   is relative to the center of the screen.
	 */
	public void render( Graphics g, Point screenCenter ) {
		g.setColor( Color.white );
		
		if( this.particleSystem != null ) {
			this.particleSystem.render();
		}
		
		Point textCenteringOffset = new Point( -this.font.getWidth( this.text )/2,
		                                       -this.font.getHeight( this.text )/2 );
		Point position = textCenteringOffset;
		if( this.relativeToScreenCenter ) {
			position = screenCenter.translate( position );
		} else {
			position = this.position.translate( position );
		}
		this.font.drawString( position.x, position.y, this.text);
	}
	
	/**
	 * After the time of the duration the effect is "dead" and can be
	 * removed.
	 * 
	 * @return Is the effect over or not
	 */
	public boolean isAlive() {
		return System.currentTimeMillis() - this.creationTime < this.duration;
	}
	
	/**
	 * Thís should be called so that the effect can react when it will be
	 * removed.
	 * <p>
	 * For subclass writers: no update or render will occur anymore after
	 * this method is been called.
	 */
	public void effectIsOver() {}
}
