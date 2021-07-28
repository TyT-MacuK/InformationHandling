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

public class WordParser implements DataParser {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public TextComponent parse(String word) throws InformationHandlingException {
		logger.log(Level.INFO, "method: parse");
		if (word == null || word.isEmpty()) {
			logger.log(Level.ERROR, "Word is empty or null");
			throw new InformationHandlingException("Word is not correct");
		}
		TextComposite composite = new TextComposite(ComponentType.WORD);
		char[] letters = word.toCharArray();
		Symbol symbol;
		for (char letter : letters) {
			symbol = new Symbol(letter, ComponentType.LETTER);
			composite.addComponent(symbol);
		}
		logger.log(Level.INFO, "method parse finished. Component {}", composite);
		return composite;
	}
}
