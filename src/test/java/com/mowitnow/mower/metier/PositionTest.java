/**
 * 
 */
package com.mowitnow.mower.metier;

import static com.mowitnow.mower.enums.OrientationEnum.N;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author aZeufack
 * Test file for the class Position.
 */
class PositionTest {

	@Test
	@DisplayName("WHEN given a position, THEN return the string formatted position.")
	void toStringTest() {
		Position position = new Position(1, 2, N);
		assertThat(position.toString()).isEqualTo("1 2 N");
	}

}
