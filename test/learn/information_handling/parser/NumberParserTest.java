package learn.information_handling.parser;

import org.testng.annotations.Test;

import by.learn.information_handling.entity.ComponentType;
import by.learn.information_handling.entity.Symbol;
import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.entity.TextComposite;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.parser.impl.NumberParser;

import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class NumberParserTest {
	NumberParser parser;
	String number = "13";
	TextComponent expected;

	@BeforeClass
	public void initialize() {
		parser = new NumberParser();
		number = "13";
		expected = new TextComposite(ComponentType.NUMBER);
		expected.addComponent(new Symbol('1', ComponentType.DIGIT));
		expected.addComponent(new Symbol('3', ComponentType.DIGIT));
	}

	@Test(description = "Test method parse")
	public void parseTest() throws InformationHandlingException {
		TextComponent actual = parser.parse(number);
		Assert.assertEquals(actual, expected);
	}

	@AfterClass
	public void tierDown() {
		NumberParser parser = null;
		String number = null;
		TextComponent expected = null;
	}
}
