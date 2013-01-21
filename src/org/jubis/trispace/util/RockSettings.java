package org.jubis.trispace.util;

/**
 * Settings for the Space about the amount of Rocks that should be created.
 * The calculation is based on the difficulty level.
 */
public class RockSettings {
	private static final int[][] AMOUNT_SETTINGS = { { 10,3,10 }, 
													 { -15,2,14 },
													 { -10,1,16 },
													 { -20,1,17 } };
	
	private int lastLevel = 0;
	private int base;
	private int factor;
	private int size;
	
	/**
	 * Updates the Rock amount to meet given level and returns the amount. 
	 * 
	 * @param level Difficulty level
	 * 
	 * @return Amount of Rocks that should be created
	 */
	public int getRockAmount( int level ) {
		this.adjustSettings( level );
		return this.base + level * this.factor;
	}
	
	/**
	 * Updates the Rock size to meet given level and returns the amount. 
	 * 
	 * @param level Difficulty level
	 * 
	 * @return Amount of Rocks that should be created
	 */
	public int getRockSize( int level ) {
		this.adjustSettings( level );
		return size;
	}

	private void adjustSettings( int level ) {
		if( level != this.lastLevel ) {
			int[] settings = getSettings( level );
			
			base = settings[ 0 ];
			factor = settings[ 1 ];
			size = settings[ 2 ];
			
			this.lastLevel = level;
		}
	}
	
	private static int[] getSettings( int level ) {
		int tens = (level-level%10)/10;
		tens = Math.min( tens, AMOUNT_SETTINGS.length-1 );
		
		return AMOUNT_SETTINGS[ tens ];
	}
}
