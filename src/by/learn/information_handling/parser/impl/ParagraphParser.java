package by.learn.information_handling.parser.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.learn.information_handling.entity.ComponentType;
import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.entity.TextComposite;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.parser.DataParser;

public class ParagraphParser implements DataParser {
	private static final Logger logger = LogManager.getLogger();
	private DataParser sentenceParser = new SentenceParser();
	private static final String SENTENCE_DELIMITER = "([^.!?]+([.!?]+))";

	@Override
	public TextComponent parse(String paragraph) throws InformationHandlingException {
		logger.log(Level.INFO, "method: parse");
		if (paragraph == null || paragraph.isEmpty()) {
			logger.log(Level.ERROR, "Paragraph is empty or null");
			throw new InformationHandlingException("Paragraph is not correct");
		}
		TextComposite composite = new TextComposite(ComponentType.PARAGRAPH);
		Pattern pattern = Pattern.compile(SENTENCE_DELIMITER);
		Matcher matcher = pattern.matcher(paragraph);
		int startIndex = 0;
		int endIndex = 0;
		String sentence = "";
		while (matcher.find()) {
			startIndex = matcher.start();
			endIndex = matcher.end();
			sentence = paragraph.substring(startIndex, endIndex);
			TextComponent component = sentenceParser.parse(sentence.trim());
			composite.addComponent(component);
		}
		logger.log(Level.INFO, "method parse finished. Component {}", composite);
		return composite;
	}

}
