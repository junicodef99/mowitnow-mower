/**
 * 
 */
package com.mowitnow.mower.transverse.utils;

import static com.google.common.collect.ImmutableList.of;
import static com.mowitnow.mower.enums.OrientationEnum.E;
import static com.mowitnow.mower.enums.OrientationEnum.N;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mowitnow.mower.metier.Lawn;
import com.mowitnow.mower.metier.Mower;
import com.mowitnow.mower.metier.Position;

/**
 * @author aZeufack
 *
 */
@ExtendWith(MockitoExtension.class)
class LawnMowersUtilsTest {
	
	@InjectMocks
	LawnMowersUtils lawnMowersUtils;

	@Test
	@DisplayName("GIVEN a list of instructions, THEN return an instance of a Lawn")
	void instantiateLawnTest() {
		List<String> instructions = of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");
		Lawn lawn = LawnMowersUtils.instantiateLawn(instructions);
		assertThat(lawn).isNotNull();
		assertThat(lawn.getXSize()).isEqualTo(5);
		assertThat(lawn.getYSize()).isEqualTo(5);
	}

	@Test
	@DisplayName("GIVEN a list of instructions, THEN return a list of intantiated Mowers")
	void instantiateMowersTest() {
		List<String> instructions = of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");
		List<Mower> mowers = LawnMowersUtils.instantiateMowers(instructions);

		assertThat(mowers).isNotNull().isNotEmpty();
		assertThat(mowers).hasSize(2);

		assertThat(mowers.get(0).getIdentifier()).isEqualTo(1);
		assertThat(mowers.get(0).getPosition().getXCoordinate()).isEqualTo(1);
		assertThat(mowers.get(0).getPosition().getYCoordinate()).isEqualTo(2);
		assertThat(mowers.get(0).getPosition().getOrientation()).isEqualTo(N);
		assertThat(mowers.get(0).getItinerary().stream().map(oEnum -> oEnum.name()).collect(Collectors.joining())).isEqualTo("GAGAGAGAA");

		assertThat(mowers.get(1).getIdentifier()).isEqualTo(2);
		assertThat(mowers.get(1).getPosition().getXCoordinate()).isEqualTo(3);
		assertThat(mowers.get(1).getPosition().getYCoordinate()).isEqualTo(3);
		assertThat(mowers.get(1).getPosition().getOrientation()).isEqualTo(E);
		assertThat(mowers.get(1).getItinerary().stream().map(oEnum -> oEnum.name()).collect(Collectors.joining())).isEqualTo("AADAADADDA");
	}

	@Test
	@DisplayName("GIVEN a list of mowers, THEN return mowers informations as string.")
	void formatTest() {
		Mower mower1 = new Mower(1, new Position(1,2,N), of());
		Mower mower2 = new Mower(2, new Position(3,3,E), of());
		String formattedString = LawnMowersUtils.format(of(mower1, mower2));
		assertThat(formattedString).isEqualTo("\nMower1: " + mower1.getPosition().toString()
				.concat("\nMower2: " + mower2.getPosition().toString()));
	}

	@Test
	@DisplayName("GIVEN an empty or null list of instructions, THEN return null.")
	void instantiateLawnTest_withEmptyInstructions() {
		assertThat(LawnMowersUtils.instantiateLawn(of())).isNull();
		assertThat(LawnMowersUtils.instantiateLawn(null)).isNull();
	}

	@Test
	@DisplayName("GIVEN an empty or null list of instructions, THEN return an empty list.")
	void instantiateMowersTest_withEmptyInstructions() {
		assertThat(LawnMowersUtils.instantiateMowers(of())).isEmpty();
		assertThat(LawnMowersUtils.instantiateMowers(null)).isEmpty();
	}

	@Test
	@DisplayName("GIVEN an empty or null list of mowers, THEN return an empty string.")
	void formatTest_withEmptyMowersList() {
		assertThat(LawnMowersUtils.format(of())).isEmpty();
		assertThat(LawnMowersUtils.format(null)).isEmpty();
	}

}
