package by.learn.information_handling.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.learn.information_handling.entity.ComponentType;
import by.learn.information_handling.entity.Symbol;
import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.entity.TextComposite;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.parser.DataParser;

public class SentenceParser implements DataParser {
	private static final Logger logger = LogManager.getLogger();
	private DataParser lexemeParser = new LexemeParser();
	private static final String SPASE = "\s";
	private static final String PUNCTUATION = "\\p{Punct}";

	@Override
	public TextComponent parse(String sentence) throws InformationHandlingException {
		logger.log(Level.INFO, "method: parse");
		if (sentence == null || sentence.isEmpty()) {
			logger.log(Level.ERROR, "Sentence is empty or null");
			throw new InformationHandlingException("Sentense is not correct");
		}
		TextComposite composite = new TextComposite(ComponentType.SENTENCE);
		String[] lexemes = sentence.split(SPASE);
		for (String lexeme : lexemes) {
			if (!lexeme.matches(PUNCTUATION)) {
				TextComponent component = lexemeParser.parse(lexeme);
				composite.addComponent(component);
			} else {
				Symbol punctuation = new Symbol(lexeme.charAt(0), ComponentType.PUNCTUATION);
				composite.addComponent(punctuation);
			}
		}

		logger.log(Level.INFO, "method parse finished. Component {}", composite);
		return composite;
	}

}
