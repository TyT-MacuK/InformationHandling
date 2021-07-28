package learn.information_handling.parser;

import org.testng.annotations.Test;

import by.learn.information_handling.entity.ComponentType;
import by.learn.information_handling.entity.Symbol;
import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.entity.TextComposite;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.parser.impl.TextParser;

import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TextParserTest {
	TextParser parser;
	String text;
	TextComponent expected;

	@BeforeClass
	public void initialize() {
		parser = new TextParser();
		text = "Hi hi.     Hi.";

		TextComponent firstWord = new TextComposite(ComponentType.WORD);
		firstWord.addComponent(new Symbol('H', ComponentType.LETTER));
		firstWord.addComponent(new Symbol('i', ComponentType.LETTER));

		TextComponent word2 = new TextComposite(ComponentType.WORD);
		word2.addComponent(new Symbol('h', ComponentType.LETTER));
		word2.addComponent(new Symbol('i', ComponentType.LETTER));

		TextComponent lexeme1 = new TextComposite(ComponentType.LEXEME);
		lexeme1.addComponent(firstWord);

		TextComponent lexeme2 = new TextComposite(ComponentType.LEXEME);
		lexeme2.addComponent(word2);
		lexeme2.addComponent(new Symbol('.', ComponentType.PUNCTUATION));

		TextComponent lexeme3 = new TextComposite(ComponentType.LEXEME);
		lexeme3.addComponent(firstWord);
		lexeme3.addComponent(new Symbol('.', ComponentType.PUNCTUATION));

		TextComponent sentence1 = new TextComposite(ComponentType.SENTENCE);
		sentence1.addComponent(lexeme1);
		sentence1.addComponent(lexeme2);

		TextComponent sentence2 = new TextComposite(ComponentType.SENTENCE);
		sentence2.addComponent(lexeme3);

		TextComponent paragraph1 = new TextComposite(ComponentType.PARAGRAPH);
		paragraph1.addComponent(sentence1);

		TextComponent paragraph2 = new TextComposite(ComponentType.PARAGRAPH);
		paragraph2.addComponent(sentence2);

		expected = new TextComposite(ComponentType.TEXT);
		expected.addComponent(paragraph1);
		expected.addComponent(paragraph2);
	}

	@Test(description = "Test method parse")
	public void parseTest() throws InformationHandlingException {
		TextComponent actual = parser.parse(text);
		Assert.assertEquals(actual, expected);
	}

	@AfterClass
	public void tierDown() {
		TextParser parser = null;
		String text = null;
		TextComponent expected = null;
	}
}
