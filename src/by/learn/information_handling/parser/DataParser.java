package by.learn.information_handling.parser;

import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.exception.InformationHandlingException;

public interface DataParser {
	TextComponent parse(String text) throws InformationHandlingException;
}
