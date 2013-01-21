package org.jubis.trispace.view.decoration;

import org.jubis.trispace.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Super class for all the graph shown for the user
 */
public abstract class Graph extends Decoration {
	/**
	 * Size of the box around the graph
	 */
	protected Point graphSize;
	/**
	 * Is the graph vertical or not
	 */
	protected boolean vertical;
	/**
	 * Color of the graph itself
	 */
	protected Color graphColor = Color.lightGray;

	/**
	 * Creates a new graph.
	 * 
	 * @param position Position of the left top corner of the graph
	 * @param graphSize Size of the box around the graph
	 * @param vertical Is the graph vertical or not
	 */
	public Graph( Point position, Point graphSize, boolean vertical ) {
		super( position );
		this.graphSize = graphSize;
		this.vertical = vertical;
	}
	
	/**
	 * Draws a graph that shows the value in the center and draws a bar that
	 * represents the percentage.
	 * 
	 * @param g Graphics object for the rendering
	 * @param percent Percentage for drawing of the percent bar
	 * @param value The value that is shown in the center of the graph
	 */
	protected void renderPercentGraph( Graphics g,
	                                float percent, 
	                                String value ) {
		g.setColor( Color.black );
		g.fillRect( this.leftTopPosition.x, this.leftTopPosition.y, 
		            this.graphSize.x, this.graphSize.y );
		
		g.setColor( this.graphColor );
		Point graphBoxSize = vertical ? new Point( this.graphSize.x, percent*this.graphSize.y ) : 
										new Point( percent*this.graphSize.x, this.graphSize.y );
		g.fillRect( this.leftTopPosition.x, this.leftTopPosition.y, 
		            graphBoxSize.x, graphBoxSize.y );
		
		g.setColor( Color.white );
//		g.drawString( level, 
//		              this.leftTopPosition.x + 5, 
//		              this.leftTopPosition.y + 5 );
		float textX = this.leftTopPosition.x + this.graphSize.x/2 - this.font.getWidth( value )/2;
		float textY = this.leftTopPosition.y + this.graphSize.y/2 - this.font.getHeight( value )/2;
		this.font.drawString( textX, textY, value );
		Point position = this.leftTopPosition;
		g.drawRect( position.x, position.y, 
		            this.graphSize.x, this.graphSize.y );
	}

}
