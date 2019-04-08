/**
 * 
 */
package com.mowitnow.mower.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mowitnow.mower.enums.ActionCommandEnum;
import com.mowitnow.mower.metier.Lawn;
import com.mowitnow.mower.metier.Mower;
import com.mowitnow.mower.services.LawnMowerProcesser;
import com.mowitnow.mower.views.MowerDisplayer;

/**
 * @author aZeufack
 */
@Service
public class MowitnowMowerProcesserService implements LawnMowerProcesser {
	private static Logger logger = LoggerFactory.getLogger(MowitnowMowerProcesserService.class);
	
	/**
	 * Pilots the mowers.
	 * @param lawn : The lawn
	 * @param mowers : The mowers to pilot
	 * @param animated : boolean indicates if grid needs to be displayed
	 * @return The mowers with their final coordinates.
	 */
	@Override
	public List<Mower> run(Lawn lawn, List<Mower> mowers, boolean animated) {
		MowerDisplayer mowerDisplayer = new MowerDisplayer(lawn.getXSize(), lawn.getYSize());
		mowers.forEach(mower -> {
			logger.info("Mower{} start in position {}..", mower.getIdentifier(), mower.getPosition().toString());
			if (animated) mowerDisplayer.paintMower(mower);
			mower.getItinerary().forEach(actionCommand -> {
				driveMower(lawn, mower, actionCommand);
				if (animated) mowerDisplayer.paintMower(mower);
			});
			logger.info("Mower{} has terminated with position {}", mower.getIdentifier(), mower.getPosition().toString());
		});
		return mowers;
	}
	

	/**
	 * Drives a mower. turn left, turn right, or move.
	 * @param lawn : The lawn
	 * @param mower : The mower to drive
	 * @param actionCommand : The command used to drive the mower
	 */
	private void driveMower(Lawn lawn, Mower mower, ActionCommandEnum actionCommand) {
		if (actionCommand.equals(ActionCommandEnum.G)) {
			mower.getPosition().setOrientation(mower.getPosition().getOrientation().getLeft());
		} else if (actionCommand.equals(ActionCommandEnum.D)) {
			mower.getPosition().setOrientation(mower.getPosition().getOrientation().getRight());
		} else if (actionCommand.equals(ActionCommandEnum.A)){
			safelyMoveMower(lawn, mower);
		}
	}
	
	
	/**
	 * Attempts to move the mower to the North direction, South, East, or West.
	 * @param lawn : The lawn
	 * @param mower : The mower to move
	 */
	private void safelyMoveMower(Lawn lawn, Mower mower) {
		int xCoord, yCoord;
		switch (mower.getPosition().getOrientation()) {
			case N:
				if ((yCoord = mower.getPosition().getYCoordinate()) < lawn.getYSize())
					mower.getPosition().setYCoordinate(yCoord + 1);
			break;
			case E:
				if ((xCoord = mower.getPosition().getXCoordinate()) < lawn.getXSize())
					mower.getPosition().setXCoordinate(xCoord + 1);
			break;
			case S:
				if ((yCoord = mower.getPosition().getYCoordinate()) > 0)
					mower.getPosition().setYCoordinate(yCoord - 1);
			break;
			case W:
				if ((xCoord = mower.getPosition().getXCoordinate()) > 0)
					mower.getPosition().setXCoordinate(xCoord - 1);
			break;
		}
	}
}
