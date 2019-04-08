/**
 * 
 */
package com.mowitnow.mower.views;

import static com.mowitnow.mower.transverse.utils.LawnMowersUtils.format;

import java.util.concurrent.TimeUnit;

import static com.google.common.collect.ImmutableList.of;
import com.mowitnow.mower.exceptions.MowitnowMowerException;
import com.mowitnow.mower.metier.Mower;

/**
 * @author aZeufack
 */
public class MowerDisplayer {
	private final static int REFRESH_DURATION = 700;
	private int gridXDimension;
	private int gridYDimension;
	private char[][]grid;

	/**
	 * Constructor
	 * @param gridXDimension : X size of the grid to display.
	 * @param gridYDimension : Y size of the grid to display.
	 */
	public MowerDisplayer(int gridXDimension, int gridYDimension) {
		this.gridXDimension = gridXDimension;
		this.gridYDimension = gridYDimension;
		this.grid = new char[gridXDimension + 1][gridYDimension + 1];
		this.initializeGrid();
	}

	/**
	 * Initializes the grid to display
	 */
	private void initializeGrid() {
		for (int i = 0; i <= this.gridXDimension; i++) {
			for (int j = 0; j <= this.gridYDimension; j++) {
				grid[i][j] = '+';
			}
		}	
	}

	/**
	 * Paints the grid with the current position of the mower
	 * @param mower : The mower to paint on the grid
	 */
	public void paintMower(Mower mower) {
		this.initializeGrid();
		System.out.println(format(of(mower)));
		this.grid[mower.getPosition().getXCoordinate()]
				[this.gridYDimension-mower.getPosition().getYCoordinate()] = mower.getPosition().getOrientation().getSymbol();
		try {
			this.paint();
		} catch (MowitnowMowerException e) {
			throw e;
		}
	}

	/**
	 * Displays the the grid
	 */
	private void paint() {
		try {
			if(System.getProperty("os.name" ).startsWith("Windows" ))
				Runtime.getRuntime().exec("cls" );
			else
				Runtime.getRuntime().exec("clear" );
		} catch(Exception excpt) {}

		for (int j = 0; j <= this.gridYDimension; j++) {
			for (int i = 0; i <= this.gridXDimension; i++) { 
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		try {
			TimeUnit.MILLISECONDS.sleep(REFRESH_DURATION);
		} catch (InterruptedException e) {
			throw new MowitnowMowerException("An error occured while trying to paint the grid", e);
		}
	}

}
