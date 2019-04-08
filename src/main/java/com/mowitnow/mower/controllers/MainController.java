/**
 * 
 */
package com.mowitnow.mower.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.mowitnow.mower.exceptions.MowitnowMowerException;
import com.mowitnow.mower.metier.Lawn;
import com.mowitnow.mower.metier.Mower;
import com.mowitnow.mower.rules.impl.MowitnowMowerInstructionsValidator;
import com.mowitnow.mower.services.impl.MowitnowMowerProcesserService;
import com.mowitnow.mower.services.impl.MowitnowMowerReaderService;
import com.mowitnow.mower.transverse.ExceptionsManager;
import com.mowitnow.mower.transverse.utils.LawnMowersUtils;

/**
 * @author aZeufack
 */
@Controller
public class MainController {
	private static Logger logger = LoggerFactory.getLogger(MainController.class);

	@Value("${mower-instructions-file.path}")
	private String mowerInstructionsFilePath;

	@Autowired
	MowitnowMowerReaderService reader;
	
	@Autowired
	MowitnowMowerInstructionsValidator validator;
	
	@Autowired
	MowitnowMowerProcesserService processer;


	/**
	 * default controller
	 */
	public MainController() {
	}

	/**
	 * @param animated : A boolean indicating if application needs to run in interactive mode
	 * @return a boolean as result of the application execution.
	 */
	public boolean run(boolean animated) {
		boolean runnedSuccesfully = true;
		
		List<MowitnowMowerException> globalExceptions = new ArrayList<>();

		try {
			logger.info("Attempt to read the lawn mowers instructions file..");
			List<String> instructions = reader.readFile(mowerInstructionsFilePath);
			if (instructions.size() == 0) {
				logger.warn("The mowers instructions file is empty.");
			} else {
				logger.info("The mowers instructions file has been read. Instrunctions :");
				instructions.forEach(logger::info);
				logger.info("Check of the instructions conformity..");
				if (!validator.areInstructionsValid(instructions)) {
					logger.error("The mowers instructions are invalid !");
					runnedSuccesfully = false;
				} else {
					logger.info("The mowers instructions are valid.");
					logger.info("Instantiate the lawn..");
					Lawn lawn = LawnMowersUtils.instantiateLawn(instructions);
					logger.info("Instantiate the lawn OK.");
					logger.info("Instantiate the mowers.");
					List<Mower> mowers = LawnMowersUtils.instantiateMowers(instructions);
					logger.info("Instantiate the mowers OK.");
					logger.info("Run the mowers..");
					mowers = processer.run(lawn, mowers, animated);
					logger.info("End of mowers tasks.");
					logger.info("Mowers final positions : {}", LawnMowersUtils.format(mowers));
				}
			}
		} catch (MowitnowMowerException e) {
			globalExceptions.add(e);
		}

		if (globalExceptions.size() > 0) {
			runnedSuccesfully = false;
			ExceptionsManager.logExceptions(globalExceptions);
		}

		return runnedSuccesfully;
	}
}
