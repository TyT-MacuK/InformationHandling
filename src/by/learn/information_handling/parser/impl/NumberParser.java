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

public class NumberParser implements DataParser {
	Logger logger = LogManager.getLogger();

	@Override
	public TextComponent parse(String number) throws InformationHandlingException {
		logger.log(Level.INFO, "method: parse");
		if (number == null || number.isEmpty()) {
			logger.log(Level.ERROR, "Number is empty or null");
			throw new InformationHandlingException("Number is not correct");
		}
		TextComposite composite = new TextComposite(ComponentType.NUMBER);
		char[] digits = number.toCharArray();
		Symbol symbol;
		for (char digit : digits) {
			symbol = new Symbol(digit, ComponentType.DIGIT);
			composite.addComponent(symbol);
		}
		logger.log(Level.INFO, "method parse finished. Component: {}", composite.getComponent());
		return composite;
	}

}
