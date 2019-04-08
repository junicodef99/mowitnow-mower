package com.mowitnow.mower;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mowitnow.mower.controllers.MainController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MowitnowMowerApplicationTests {

	@InjectMocks
	MowitnowMowerApplication mowitnowMowerApplication;
	
	@Mock 
	MainController mainController;

	@Test
	@DisplayName("WHEN mowitnowMowerApplication 'main' method is called, mowitnowMowerApplication 'run' method is called once.")
	public void main() throws Exception {
		when(mainController.run(false)).thenReturn(true);
		MowitnowMowerApplication.main(new String[]{});
	}
	
	@Test
	@DisplayName("WHEN mowitnowMowerApplication 'run' method is called, mainController 'run' method is called once.")
	public void run() throws Exception {
		when(mainController.run(false)).thenReturn(false);
		mowitnowMowerApplication.run(new String[]{});
		verify(mainController, times(1)).run(false);
	}

}
