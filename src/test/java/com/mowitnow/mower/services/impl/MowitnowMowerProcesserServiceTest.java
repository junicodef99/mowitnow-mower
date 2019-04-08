/**
 * 
 */
package com.mowitnow.mower.services.impl;

import static com.google.common.collect.ImmutableList.of;
import static com.mowitnow.mower.enums.ActionCommandEnum.A;
import static com.mowitnow.mower.enums.ActionCommandEnum.D;
import static com.mowitnow.mower.enums.ActionCommandEnum.G;
import static com.mowitnow.mower.enums.OrientationEnum.E;
import static com.mowitnow.mower.enums.OrientationEnum.N;
import static com.mowitnow.mower.enums.OrientationEnum.S;
import static com.mowitnow.mower.enums.OrientationEnum.W;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mowitnow.mower.metier.Lawn;
import com.mowitnow.mower.metier.Mower;
import com.mowitnow.mower.metier.Position;

/**
 * Test file for the class MowitnowMowerProcesserService
 * @author aZeufack
 *
 */
@ExtendWith(MockitoExtension.class)
class MowitnowMowerProcesserServiceTest {

	@InjectMocks
	MowitnowMowerProcesserService mowitnowMowerProcesserService;

	@Test
	@DisplayName("GIVEN mowers with coordinates 1 2 L and 3 3 E, THEN return 1 3 N and 5 1 E.")
	void runTest() {
		Lawn lawn = new Lawn(5,5);
		List<Mower> mowers = of(new Mower(1, new Position(1,2,N), of(G,A,G,A,G,A,G,A,A)),
				new Mower(2, new Position(3,3,E), of(A,A,D,A,A,D,A,D,D,A))
				);

		List<Mower> movedMowers = mowitnowMowerProcesserService.run(lawn, mowers, false);
		checkAssertions(mowers, movedMowers, of(of(1, 3, N), of(5, 1, E)));
	}
	
	@Test
	@DisplayName("GIVEN mowers with coordinates 1 2 L and 3 3 E, THEN return 1 3 N and 5 1 E.")
	void runTest_interractiveMode() {
		Lawn lawn = new Lawn(5,5);
		List<Mower> mowers = of(new Mower(1, new Position(1,2,N), of(G,A,G,A,G,A,G,A,A)),
				new Mower(2, new Position(3,3,E), of(A,A,D,A,A,D,A,D,D,A))
				);

		List<Mower> movedMowers = mowitnowMowerProcesserService.run(lawn, mowers, true);
		checkAssertions(mowers, movedMowers, of(of(1, 3, N), of(5, 1, E)));
	}

	@Test
	@DisplayName("Test with lawn blocking cases. GIVEN 4 mowers with coordinates 1 2 N and different instructions,"
			+ " THEN return respectively 0 2 W, 0 5 N, 0 0 S, and 5 2 E.")
	void runTest_withLawnBlockingOnWest() {
		Lawn lawn = new Lawn(5,5);
		List<Mower> mowers = of(new Mower(1, new Position(1,2,N), of(G,A,A)), // Mower should block on West.
				new Mower(1, new Position(1,2,N), of(G,A,D,A,A,A,A,A)), // Mower should block on North.
				new Mower(1, new Position(1,2,N), of(G,A,G,A,A,A)),	// Mower should block on South.
				new Mower(1, new Position(1,2,N), of(D,A,A,A,A,A))); // Mower should block on East.

		List<Mower> movedMowers = mowitnowMowerProcesserService.run(lawn, mowers, false);
		checkAssertions(mowers, movedMowers, of(of(0, 2, W),
				of(0, 5, N),
				of(0, 0, S),
				of(5, 2, E)));

		assertThat(movedMowers).hasSameSizeAs(mowers);
	}

	/**
	 * Checks the assertions for all the mowers given in parameter
	 * @param mowers : The initial mowers with their coordinates
	 * @param movedMowers : The driven mowers with new coordinates
	 * @param expectedResults : the expected coordinates of the driven mowers
	 */
	private void checkAssertions(List<Mower> mowers, List<Mower> movedMowers, List<List<Object>> expectedResults) {
		assertThat(movedMowers).hasSameSizeAs(mowers);
		for (int i = 0; i < movedMowers.size(); i++) {
			assertThat(movedMowers.get(i).getPosition().getXCoordinate()).isEqualTo(expectedResults.get(i).get(0));
			assertThat(movedMowers.get(i).getPosition().getYCoordinate()).isEqualTo(expectedResults.get(i).get(1));
			assertThat(movedMowers.get(i).getPosition().getOrientation()).isEqualTo(expectedResults.get(i).get(2));
		}
	}

}
