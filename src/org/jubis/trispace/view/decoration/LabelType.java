package org.jubis.trispace.view.decoration;

import org.jubis.trispace.util.Point;

/**
 * Enumeration to contain setting for the labels
 */
public enum LabelType {
	PlayerLevel( "Level", new Point( 500, 0 ) ), 
	Health( "Health", new Point( 200, 0 ) ), 
	Score( "Score", new Point( 350, 0 ) );
	
	/**
	 * Name to be shown on the label
	 */
	public final String name;
	/**
	 * The upper left corner of the label
	 */
	public final Point position;
	
	private LabelType( String name, Point position ) {
		this.name = name;
		this.position = position;
	}
	
}
