package com.mowitnow.mower.rules.impl;

import static com.google.common.collect.ImmutableList.of;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class MowitnowMowerInstructionsValidatorTest {

	@InjectMocks
	private MowitnowMowerInstructionsValidator mowitnowMowerInstructionsValidator;

	@Test
	@DisplayName("GIVEN incorrect number of instruction, THEN return false")
	void areInstructionsValidTest_incorrectNumberOfInstructions() {
		List<String> instructions = of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E");
		assertThat(mowitnowMowerInstructionsValidator.areInstructionsValid(instructions)).isEqualTo(false);
	}

	@Test
	@DisplayName("GIVEN correct number of instructions, THEN return true")
	void areInstructionsValidTest_correctNumberOfInstructions() {
		List<String> instructions = of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");
		assertThat(mowitnowMowerInstructionsValidator.areInstructionsValid(instructions)).isEqualTo(true);
	}

	@Test
	@DisplayName("GIVEN bad lawn coordinates, THEN return false")
	void verifySyntaxTest_badLawnCoordinates() {
		List<List<String>> instructions = of(of("5 5a", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA"),
				of("55", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA"),
				of("5 5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA")
				);
		instructions.forEach(inst -> {
			assertThat(mowitnowMowerInstructionsValidator.verifySyntax(inst)).isEqualTo(false);
		});
	}

	@Test
	@DisplayName("GIVEN correct lawn coordinates, THEN return true")
	void verifySyntaxTest_correctLawnCoordinates() {
		List<String> instructions = of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");
		assertThat(mowitnowMowerInstructionsValidator.verifySyntax(instructions)).isEqualTo(true);
	}

	@Test
	@DisplayName("GIVEN bad instructions order, THEN return false")
	void verifySyntaxTest_badInstructionsOrder() {
		List<List<String>> instructions = of(of("5 5", "1 2 N", "3 3 E", "GAGAGAGAA", "AADAADADDA"),
				of("5  5", "1 2 N", "3 3 E", "GAGAGAGAA", "AADAADADDA"),
				of("5 5", "GAGAGAGAA", "1 2 N", "AADAADADDA", "3 3 E"),
				of("5 5", "AADAADADDA", "GAGAGAGAA", "1 2 N", "3 3 E"),
				of("1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA", "5 5")
				);
		instructions.forEach(inst -> {
			assertThat(mowitnowMowerInstructionsValidator.verifySyntax(inst)).isEqualTo(false);
		});
	}

	@Test
	@DisplayName("GIVEN bad mowers coordinates, THEN return false")
	void verifySyntaxTest_badMowersCoordinates() {
		List<List<String>> instructions = of(of("5 5", "1 2  N", "GAGAGAGAA"),
				of("5 5", "12N", "GAGAGAGAA"),
				of("5 5", "1 2N", "GAGAGAGAA"),
				of("5 5", "12 N", "GAGAGAGAA"),
				of("5 5", "1 2 A", "GAGAGAGAA"),
				of("5 5", "1 2  N", "GAGAGAGAA"),
				of("5 5", "6 2 N", "GAGAGAGAA"),
				of("5 5", "5 6 N", "GAGAGAGAA")
				);
		instructions.forEach(inst -> {
			assertThat(mowitnowMowerInstructionsValidator.verifySyntax(inst)).isEqualTo(false);
		});
	}

	@Test
	@DisplayName("GIVEN bad mowers commands, THEN return false")
	void verifySyntaxTest_badMowersCommands() {
		List<List<String>> instructions = of(of("5 5", "1 2 N", "G A G A G A G A A"),
				of("5 5", "1 2 N", "GAGAGAGA A"),
				of("5 5", "1 2 N", "GBGAGAGAA")
				);
		instructions.forEach(inst -> {
			assertThat(mowitnowMowerInstructionsValidator.verifySyntax(inst)).isEqualTo(false);
		});
	}

	@Test
	@DisplayName("GIVEN correct instructions, THEN return true")
	void areInstructionsValidTest_correctInstructions() {
		List<String> instructions = of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");
		assertThat(mowitnowMowerInstructionsValidator.areInstructionsValid(instructions)).isEqualTo(true);
	}
}
