package learn.information_handling.reader;

import org.testng.annotations.Test;

import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.reader.DataReader;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class DataReaderTest {
	DataReader reader;
	static final String CORRECT_PATH = "test\\resources\\data.txt";
	static final String WRONG_PATH = "data";
	static final String NULL_PATH = null;

	@BeforeClass
	public void initialize() {
		reader = new DataReader();
	}

	@Test(description = "Testing reader with right path")
	public void positiveReaderTest() throws InformationHandlingException {
		reader.readDataFromFile(CORRECT_PATH);
	}

	@Test(description = "Testing reader with wrong path", expectedExceptions = InformationHandlingException.class)
	public void negativeReaderTest() throws InformationHandlingException {
		reader.readDataFromFile(WRONG_PATH);
	}

	@Test(description = "Testing reader with null", expectedExceptions = InformationHandlingException.class)
	public void nullReaderTest() throws InformationHandlingException {
		reader.readDataFromFile(NULL_PATH);
	}

	@AfterClass
	public void afterClass() {
		reader = null;
	}

}
