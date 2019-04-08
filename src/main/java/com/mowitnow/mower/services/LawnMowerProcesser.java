/**
 * 
 */
package com.mowitnow.mower.services;

import java.util.List;

import com.mowitnow.mower.metier.Lawn;
import com.mowitnow.mower.metier.Mower;

/**
 * @author aZeufack
 */
public interface LawnMowerProcesser {
	/**
	 * Pilots the mowers.
	 * @param lawn : The lawn
	 * @param mowers : The mowers to pilot
	 * @param animated : boolean indicates if grid needs to be displayed
	 * @return The mowers with their final coordinates.
	 */
	List<Mower> run(Lawn lawn, List<Mower> mowers, boolean animated);
}
