/**
 * 
 */
package com.mowitnow.mower.controllers;

import static com.google.common.collect.ImmutableList.of;
import static com.mowitnow.mower.enums.ActionCommandEnum.A;
import static com.mowitnow.mower.enums.ActionCommandEnum.D;
import static com.mowitnow.mower.enums.ActionCommandEnum.G;
import static com.mowitnow.mower.enums.OrientationEnum.E;
import static com.mowitnow.mower.enums.OrientationEnum.N;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import com.mowitnow.mower.exceptions.MowitnowMowerException;
import com.mowitnow.mower.metier.Lawn;
import com.mowitnow.mower.metier.Mower;
import com.mowitnow.mower.metier.Position;
import com.mowitnow.mower.rules.impl.MowitnowMowerInstructionsValidator;
import com.mowitnow.mower.services.impl.MowitnowMowerProcesserService;
import com.mowitnow.mower.services.impl.MowitnowMowerReaderService;

/**
 * @author aZeufack
 *
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class MainControllerTest {

	@Value("${mower-instructions-file.path}")
	private String mowerInstructionsFilePath;

	@InjectMocks
	MainController mainController;

	@Mock
	MowitnowMowerReaderService reader;

	@Mock
	MowitnowMowerInstructionsValidator validator;

	@Mock
	MowitnowMowerProcesserService processer;
	
	@Test
	@DisplayName("GIVEN a non empty correct intructions file, THEN return true.")
	void runTest_simpleCase() {
		List<String> instructions = of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");
		List<Mower> expectedMowers = of(new Mower(1, new Position(1,3,N), of(G,A,G,A,G,A,G,A,A)),
				new Mower(2, new Position(5,1,E), of(A,A,D,A,A,D,A,D,D,A)));
		when(reader.readFile(mowerInstructionsFilePath)).thenReturn(instructions);
		when(validator.areInstructionsValid(anyList())).thenReturn(true);
		when(processer.run(any(Lawn.class), anyList(), anyBoolean())).thenReturn(expectedMowers);
		assertThat(mainController.run(false)).isEqualTo(true);
	}
	
	@ParameterizedTest(name = "{0}")
	@MethodSource("runMethodparameters")
	void runTest(final String displayName, final List<String> instructions,
			final boolean instructionsAreConform, final boolean expectedResult) {

		when(reader.readFile(mowerInstructionsFilePath)).thenReturn(instructions);
		if (instructions.size() > 0) {
			when(validator.areInstructionsValid(anyList())).thenReturn(instructionsAreConform);
		}
		assertThat(mainController.run(false)).isEqualTo(expectedResult);
	}

	private static Stream<Arguments> runMethodparameters() {
		return Stream.of(
				Arguments.of("GIVEN an empty intructions file, THEN return true", 
						of(), true, true),
				Arguments.of("GIVEN an incorrect intructions file, THEN return false", 
						of("5 5", "1 2 N", "GZGAGAGAA", "3 3 E", "AADAADADDA"), false, false)
				);
	}
	
	@Test
	@DisplayName("application RETURNS false WHEN the read file process generate exception")
	void runTest_exceptionCase() {
		when(reader.readFile(mowerInstructionsFilePath)).thenThrow(MowitnowMowerException.class);
		assertThat(mainController.run(false)).isEqualTo(false);
	}
}
