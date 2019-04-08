package com.mowitnow.mower.services.impl;

import static com.google.common.collect.ImmutableList.of;
import static java.nio.file.Files.deleteIfExists;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import com.mowitnow.mower.exceptions.MowitnowMowerException;

@ExtendWith(MockitoExtension.class)
class MowitnowMowerReaderServiceTest {

	private static Logger logger = LoggerFactory.getLogger(MowitnowMowerReaderServiceTest.class);
	private static final String tmpFolder = "mowitnow_temp/";

	@BeforeAll
	static void init() throws IOException {
		Files.createDirectories(Paths.get(tmpFolder));
	}

	@InjectMocks
	private MowitnowMowerReaderService mowitnowMowerReaderService;

	@ParameterizedTest(name = "{0}")
	@MethodSource("parameters")
	void readFileTest(final String displayName, final String instructionsPath,
			final List<String> expectedTestResult) {
		List<String> result = mowitnowMowerReaderService.readFile(instructionsPath);

		assertThat(result).containsExactlyElementsOf(expectedTestResult);
	}

	private static Stream<Arguments> parameters() {
		return Stream.of(
				Arguments.of("GIVEN an empty file, THEN return an empty list", tmpFolder.concat("file1.lmw"),
						generateFileForTest(tmpFolder.concat("file1.lmw"), "emptyFile")),
				Arguments.of("GIVEN a file with instructions lines, THEN remove empty lines", tmpFolder.concat("file2.lmw"),
						generateFileForTest(tmpFolder.concat("file2.lmw"), "instructionsWithEmptyAndNonEmptyLines")),
				Arguments.of("GIVEN a file with instructions lines, THEN remove white spaces and empty lines",
						tmpFolder.concat("file3.lmw"), generateFileForTest(tmpFolder.concat("file3.lmw"), "instructionsWithEmptyLinesAndWhiteSpaces")),
				Arguments.of("GIVEN a file with instructions lines, THEN return a list of instruction lines",
						tmpFolder.concat("file4.lmw"), generateFileForTest(tmpFolder.concat("file4.lmw"), "instructionsWithNoEmptyLines"))
				);
	}

	private static List<String> generateFileForTest(String instructionsPath, String testCase) {
		File instructionsFile = new File(instructionsPath);
		try {
			deleteIfExists(instructionsFile.toPath());
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(instructionsFile);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				List<String> expectedTestResult = writeInstructions(printWriter, testCase);
				printWriter.close();
				return expectedTestResult;
			} catch (IOException e) {
				logger.error("An error occured during the creation of the file {}", instructionsFile.toString());
				e.printStackTrace();
			}

		} catch (IOException e) {
			logger.error("An error occured while trying to delete the file {}", instructionsFile.toString());
			e.printStackTrace();
		}
		throw new RuntimeException("An error occured during generation of tests parameters");
	}

	private static List<String> writeInstructions(PrintWriter printWriter, String testCase) {
		List<String> instructionsLines = of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");
		switch (testCase) {
		case "emptyFile":
			return emptyList();
		case "instructionsWithEmptyAndNonEmptyLines":
			printWriter.print('\n');
			printWriter.println(instructionsLines.get(0));
			printWriter.print('\n');
			printWriter.print('\n');
			printWriter.println(instructionsLines.get(1));
			printWriter.println(instructionsLines.get(2));
			printWriter.println(instructionsLines.get(3));
			printWriter.println(instructionsLines.get(4));
			printWriter.print('\n');
			return instructionsLines;
		case "instructionsWithNoEmptyLines":
			printWriter.println(instructionsLines.get(0));
			printWriter.println(instructionsLines.get(1));
			printWriter.println(instructionsLines.get(2));
			printWriter.println(instructionsLines.get(3));
			printWriter.print(instructionsLines.get(4));
			return instructionsLines;
		case "instructionsWithEmptyLinesAndWhiteSpaces":
			printWriter.print('\n');
			printWriter.println(instructionsLines.get(0).concat(" "));
			printWriter.println("    ".concat(instructionsLines.get(1)));
			printWriter.println(instructionsLines.get(2));
			printWriter.println("  ".concat(instructionsLines.get(3)).concat(" "));
			printWriter.println(instructionsLines.get(4));
			return instructionsLines;
		default:
			throw new RuntimeException("An error occured during generation of tests parameters");
		}
	}

	@Test
	@DisplayName("GIVEN a path to a non existing file, THEN generate an exception")
	void readFileExceptionTest() {
		assertThatThrownBy(() -> mowitnowMowerReaderService.readFile("no_file"))
		.isInstanceOf(MowitnowMowerException.class).hasMessageContaining("An error occured while attempting to read the file");
	}

	@AfterAll
	static void tear() throws IOException {
		File folder = new File(tmpFolder);
		FileSystemUtils.deleteRecursively(folder);
		folder.delete();
	}

}
