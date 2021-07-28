package by.learn.information_handling.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.learn.information_handling.entity.ComponentType;
import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.entity.TextComposite;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.parser.DataParser;

public class TextParser implements DataParser {
	private static final Logger logger = LogManager.getLogger();
	private static final String PARAGRAPH_DELIMITER = "\\s{2,4}";
	private DataParser paragraphParser = new ParagraphParser();

	@Override
	public TextComponent parse(String text) throws InformationHandlingException {
		logger.log(Level.INFO, "method: parse");
		if (text == null || text.isEmpty()) {
			logger.log(Level.ERROR, "Text is empty or null");
			throw new InformationHandlingException("Text is not correct");
		}
		TextComposite composite = new TextComposite(ComponentType.TEXT);
		String[] paragraphs = text.split(PARAGRAPH_DELIMITER);
		for (String paragraph : paragraphs) {
			if (!paragraph.isEmpty()) {
				TextComponent component = paragraphParser.parse(paragraph.trim());
				composite.addComponent(component);
			}
		}
		logger.log(Level.INFO, "method parse finished. Component {}", composite);
		return composite;
	}

}
