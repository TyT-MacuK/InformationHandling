package by.learn.information_handling.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.learn.information_handling.exception.InformationHandlingException;

public class DataReader {
	private static final Logger logger = LogManager.getLogger();

	public String readDataFromFile(String path) throws InformationHandlingException {
		logger.log(Level.INFO, "method: readDataFromFile");
		if (path == null || Files.notExists(Path.of(path))) {
			logger.log(Level.INFO, "Path {} is not correct", path);
			throw new InformationHandlingException();
		}
		String text;
		try (Stream<String> streamData = Files.newBufferedReader(Path.of(path)).lines()) {
			text = streamData.collect(Collectors.joining());
		} catch (IOException e) {
			logger.log(Level.INFO, "Exception in method: Files.newBufferedReader(Path.of(path)).lines()");
			throw new InformationHandlingException("IOException in readDataFromFile method ", e);
		}
		logger.log(Level.INFO, "Data was read: {}", text);
		return text;
	}
}
