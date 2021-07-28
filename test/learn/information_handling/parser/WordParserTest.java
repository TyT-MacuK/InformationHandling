package learn.information_handling.parser;

import org.testng.annotations.Test;

import by.learn.information_handling.entity.ComponentType;
import by.learn.information_handling.entity.Symbol;
import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.entity.TextComposite;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.parser.impl.WordParser;

import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class WordParserTest {
	WordParser parser;
	String word;
	TextComponent expected;

	@BeforeClass
	public void initialize() {
		parser = new WordParser();
		word = "Hi";
		expected = new TextComposite(ComponentType.WORD);
		expected.addComponent(new Symbol('H', ComponentType.LETTER));
		expected.addComponent(new Symbol('i', ComponentType.LETTER));
	}

	@Test(description = "Test method parse")
	public void parseTest() throws InformationHandlingException {
		TextComponent actual = parser.parse(word);
		Assert.assertEquals(actual, expected);
	}

	@AfterClass
	public void tierDown() {
		WordParser parser = null;
		String word = null;
		TextComponent expected = null;
	}
}
