package com.mowitnow.mower.transverse;

import static com.google.common.collect.ImmutableList.of;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mowitnow.mower.exceptions.MowitnowMowerException;

/**
 * Test file for the class ExceptionsManager
 * @author aZeufack
 *
 */
@ExtendWith(MockitoExtension.class)
class ExceptionsManagerTest {
	
	@InjectMocks
	ExceptionsManager exceptionsManager;
	
	@Test
	@DisplayName("Test logExceptions method")
	void logExceptionsTest() {
		ExceptionsManager.logExceptions(of(new MowitnowMowerException())) ;
	}

}
