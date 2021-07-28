package learn.information_handling.parser;

import org.testng.annotations.Test;

import by.learn.information_handling.entity.ComponentType;
import by.learn.information_handling.entity.Symbol;
import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.entity.TextComposite;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.parser.impl.LexemeParser;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LexemeParserTest {
	LexemeParser parser;
	TextComponent word;
	TextComponent number;

	@BeforeClass
	public void initialize() {
		parser = new LexemeParser();

		word = new TextComposite(ComponentType.WORD);
		word.addComponent(new Symbol('h', ComponentType.LETTER));
		word.addComponent(new Symbol('i', ComponentType.LETTER));

		number = new TextComposite(ComponentType.NUMBER);
		number.addComponent(new Symbol('1', ComponentType.DIGIT));
		number.addComponent(new Symbol('3', ComponentType.DIGIT));
	}

	@DataProvider
	public Object[][] createLexems() {
		TextComponent lexemeWord = new TextComposite(ComponentType.LEXEME);
		lexemeWord.addComponent(word);

		TextComponent bracketWordBracket = new TextComposite(ComponentType.LEXEME);
		bracketWordBracket.addComponent(new Symbol('(', ComponentType.PUNCTUATION));
		bracketWordBracket.addComponent(word);
		bracketWordBracket.addComponent(new Symbol(')', ComponentType.PUNCTUATION));

		TextComponent punctWord = new TextComposite(ComponentType.LEXEME);
		punctWord.addComponent(new Symbol('<', ComponentType.PUNCTUATION));
		punctWord.addComponent(word);

		TextComponent wordPunct = new TextComposite(ComponentType.LEXEME);
		wordPunct.addComponent(word);
		wordPunct.addComponent(new Symbol('>', ComponentType.PUNCTUATION));

		TextComponent wordWithHyphen = new TextComposite(ComponentType.LEXEME);
		wordWithHyphen.addComponent(word);
		wordWithHyphen.addComponent(new Symbol('-', ComponentType.PUNCTUATION));
		wordWithHyphen.addComponent(word);

		TextComponent numberWithPunct = new TextComposite(ComponentType.LEXEME);
		numberWithPunct.addComponent(number);
		numberWithPunct.addComponent(new Symbol('-', ComponentType.PUNCTUATION));

		return new Object[][] { { "hi", lexemeWord }, { "(hi)", bracketWordBracket }, { "<hi", punctWord },
				{ "hi>", wordPunct }, { "hi-hi", wordWithHyphen }, { "13-", numberWithPunct } };
	}

	@Test(description = "Test method parse", dataProvider = "createLexems")
	public void parseTest(String data, TextComponent expected) throws InformationHandlingException {
		TextComponent actual = parser.parse(data);
		Assert.assertEquals(actual, expected);
	}

	@AfterClass
	public void tierDown() {
		LexemeParser parser = null;
		TextComponent word = null;
		TextComponent number = null;
	}
}
