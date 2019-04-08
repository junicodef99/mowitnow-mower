package com.mowitnow.mower;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mowitnow.mower.controllers.MainController;

@SpringBootApplication
public class MowitnowMowerApplication implements CommandLineRunner {	
	@Autowired
	private MainController mainController;
	
	private static Logger logger = LoggerFactory.getLogger(MowitnowMowerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MowitnowMowerApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		boolean animated = Arrays.stream(args).anyMatch("-i"::equals); // if user want to run in interactive mode.
		if (mainController.run(animated)) {
			logger.info("The application has terminated succesfully.");
		} else {
			logger.error("The application has terminated with errors.");
		}
	}
}
