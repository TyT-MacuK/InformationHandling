package learn.information_handling.parser;

import org.testng.annotations.Test;

import by.learn.information_handling.entity.ComponentType;
import by.learn.information_handling.entity.Symbol;
import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.entity.TextComposite;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.parser.impl.SentenceParser;

import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class SentenceParserTest {
	SentenceParser parser;
	String sentence;
	TextComponent expected;

	@BeforeClass
	public void initialize() {
		parser = new SentenceParser();
		sentence = "Hi hi.";

		TextComponent word1 = new TextComposite(ComponentType.WORD);
		word1.addComponent(new Symbol('H', ComponentType.LETTER));
		word1.addComponent(new Symbol('i', ComponentType.LETTER));

		TextComponent word2 = new TextComposite(ComponentType.WORD);
		word2.addComponent(new Symbol('h', ComponentType.LETTER));
		word2.addComponent(new Symbol('i', ComponentType.LETTER));

		TextComponent lexeme1 = new TextComposite(ComponentType.LEXEME);
		lexeme1.addComponent(word1);

		TextComponent lexeme2 = new TextComposite(ComponentType.LEXEME);
		lexeme2.addComponent(word2);
		lexeme2.addComponent(new Symbol('.', ComponentType.PUNCTUATION));

		expected = new TextComposite(ComponentType.SENTENCE);
		expected.addComponent(lexeme1);
		expected.addComponent(lexeme2);

	}

	@Test(description = "Test method parse")
	public void parseTest() throws InformationHandlingException {
		TextComponent actual = parser.parse(sentence);
		Assert.assertEquals(actual, expected);
	}

	@AfterClass
	public void tierDown() {
		SentenceParser parser = null;
		String sentence = null;
		TextComponent expected = null;
	}

}
