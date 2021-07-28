package learn.information_handling.service;

import org.testng.annotations.Test;

import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.parser.DataParser;
import by.learn.information_handling.parser.impl.SentenceParser;
import by.learn.information_handling.parser.impl.TextParser;
import by.learn.information_handling.reader.DataReader;
import by.learn.information_handling.service.impl.ServiceInformatoinHandlingImpl;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class ServiceTest {
	static final String TEXT_PATH = "test\\resources\\data.txt";
	static final String SORTED_TEXT_PATH = "test\\resources\\data2.txt";
	ServiceInformatoinHandlingImpl service;
	DataReader reader;
	DataParser parser;
	SentenceParser sentenceParser;
	String text;
	String sortedText;

	@BeforeClass
	public void beforeClass() throws InformationHandlingException {
		service = new ServiceInformatoinHandlingImpl();
		reader = new DataReader();
		parser = new TextParser();
		sentenceParser = new SentenceParser();
		text = reader.readDataFromFile(TEXT_PATH);
		sortedText = reader.readDataFromFile(SORTED_TEXT_PATH);
	}

	@DataProvider(name = "data_text")
	public Object[][] createParagraphs() throws InformationHandlingException {
		return new Object[][] { { parser.parse(text), parser.parse(sortedText) } };
	}

	@Test(description = "Test sort method", dataProvider = "data_text")
	public void sortParagraphsTest(TextComponent unparsedComponent, TextComponent expected)
			throws InformationHandlingException {
		TextComponent actual = service.sortParagraphs(unparsedComponent);
		;
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "same_words_data")
	public Object[][] findSentencesWithLongestWordData() throws InformationHandlingException {
		return new Object[][] { { parser.parse(text), parser.parse("  See you later.") } };
	}

	@Test(description = "Test method findSentencesWithLongestWord", dataProvider = "same_words_data")
	public void findSentencesWithLongestWordTest(TextComponent text, TextComponent expectedSentence)
			throws InformationHandlingException {
		Set<TextComponent> actual = service.findSentencesWithLongestWord(text);
		Set<TextComponent> expected = service.findSentencesWithLongestWord(expectedSentence);
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "Test method removeSentencesWithFewerWords")
	public void removeSentencesWithFewerWordsTest() throws InformationHandlingException {
		TextComponent textComponent = parser.parse(text);
		service.removeSentencesWithFewerWords(textComponent, 2);
		String actual = textComponent.toString().trim();
		String expected = "See you later.";
		Assert.assertEquals(actual, expected);
	}

	@Test(description = " Test method findTheSameWords")
	public void findTheSameWordsTest() throws InformationHandlingException {
		TextComponent textComponent = parser.parse(text);
		String expected = "[Bye]";
		Set<TextComponent> actualSet = service.findTheSameWords(textComponent);
		Assert.assertEquals(expected, actualSet.toString());
	}

	@Test(description = "Test method countConsonantsLetters")
	public void countConsonantsLettersTest() throws InformationHandlingException {
		TextComponent sentence = sentenceParser.parse("See you later.");
		int actual = service.countConsonantsLetters(sentence);
		int expected = 5;
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "Test method countVowelLetters")
	public void countVowelLettersTest() throws InformationHandlingException {
		TextComponent sentence = sentenceParser.parse("See you later.");
		int actual = service.countVowelLetters(sentence);
		int expected = 6;
		Assert.assertEquals(actual, expected);
	}

	@AfterClass
	public void tierDown() {
		ServiceInformatoinHandlingImpl service = null;
		DataReader reader = null;
		DataParser parser = null;
		SentenceParser sentenceParser = null;
		String text = null;
		String sortedText = null;

	}
}
