package by.learn.information_handling.parser.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.learn.information_handling.entity.ComponentType;
import by.learn.information_handling.entity.Symbol;
import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.entity.TextComposite;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.parser.DataParser;

public class LexemeParser implements DataParser {
	private static final Logger logger = LogManager.getLogger();
	private DataParser wordParser = new WordParser();
	private DataParser numberParser = new NumberParser();
	private static final String WORD = "\\p{Upper}?\\p{Lower}+";
	private static final String BRACKET_WORD_BRACKET = "\\(\\p{Lower}+\\)";
	private static final String PUNCT_WORD = "\\p{Punct}\\p{Upper}?\\p{Lower}+";
	private static final String WORD_PUNCT = "\\p{Upper}?\\p{Lower}+\\p{Punct}{1,3}";
	private static final String WORD_WITH_HYPHEN = "[\\p{Lower}-]+";
	private static final String PUNCT = "\\p{Punct}";
	private static final String HYPHEN = "-";

	@Override
	public TextComponent parse(String lexeme) throws InformationHandlingException {
		logger.log(Level.INFO, "method: parse");
		if (lexeme == null || lexeme.isEmpty()) {
			logger.log(Level.ERROR, "Lexeme is empty or null");
			throw new InformationHandlingException("Lexeme is not correct");
		}
		TextComposite composite = new TextComposite(ComponentType.LEXEME);
		if (lexeme.matches(WORD)) {
			TextComponent word = wordParser.parse(lexeme);
			composite.addComponent(word);
		} else if (lexeme.matches(BRACKET_WORD_BRACKET)) {
			addWordInBracks(composite, lexeme);
		} else if (lexeme.matches(PUNCT_WORD)) {
			addWordWithPunctuationBefore(composite, lexeme);
		} else if (lexeme.matches(WORD_PUNCT)) {
			addWordWithPunctuationAfrer(composite, lexeme);
		} else if (lexeme.matches(WORD_WITH_HYPHEN)) {
			addWordWithHyphen(composite, lexeme);
		} else {
			addNumber(composite, lexeme);
		}
		logger.log(Level.INFO, "method parse finished. Component {}", composite);
		return composite;
	}

	private int countPouncInLexeme(String lexeme) {
		Pattern pattern = Pattern.compile(PUNCT);
		Matcher matcher = pattern.matcher(lexeme);
		int punctInLexeme = 0;
		while (matcher.find()) {
			punctInLexeme++;
		}
		return punctInLexeme;
	}

	private void addWordInBracks(TextComposite composite, String lexeme) throws InformationHandlingException {
		Symbol openingBracke = new Symbol(lexeme.charAt(0), ComponentType.PUNCTUATION);
		composite.addComponent(openingBracke);
		TextComponent word = wordParser.parse(lexeme.substring(1, lexeme.length() - 1));
		composite.addComponent(word);
		Symbol closingBracket = new Symbol(lexeme.charAt(lexeme.length() - 1), ComponentType.PUNCTUATION);
		composite.addComponent(closingBracket);
	}

	private void addWordWithPunctuationBefore(TextComposite composite, String lexeme)
			throws InformationHandlingException {
		Symbol punctuation = new Symbol(lexeme.charAt(0), ComponentType.PUNCTUATION);
		composite.addComponent(punctuation);
		TextComponent word = wordParser.parse(lexeme.substring(1));
		composite.addComponent(word);
	}

	private void addWordWithPunctuationAfrer(TextComposite composite, String lexeme)
			throws InformationHandlingException {
		int punctInLexeme = countPouncInLexeme(lexeme);
		TextComponent word = wordParser.parse(lexeme.substring(0, lexeme.length() - punctInLexeme));
		composite.addComponent(word);
		for (int i = lexeme.length() - punctInLexeme; i < lexeme.length(); i++) {
			Symbol punctuation = new Symbol(lexeme.charAt(i), ComponentType.PUNCTUATION);
			composite.addComponent(punctuation);
		}
	}

	private void addWordWithHyphen(TextComposite composite, String lexeme) throws InformationHandlingException {
		String[] splitLexeme = lexeme.split(HYPHEN);
		for (int i = 0; i < splitLexeme.length; i++) {
			TextComponent word = wordParser.parse(splitLexeme[i]);
			composite.addComponent(word);
			if (i < splitLexeme.length - 1) {
				Symbol hyphen = new Symbol('-', ComponentType.PUNCTUATION);
				composite.addComponent(hyphen);
			}
		}
	}

	private void addNumber(TextComposite composite, String lexeme) throws InformationHandlingException {
		int indexBeginNumber = 0;
		int indexEndNumber = 0;

		for (int i = 0; i < lexeme.length(); i++) {
			if (!Character.isDigit(lexeme.charAt(i)) || i == lexeme.length()) {
				indexEndNumber = i;
				Symbol punctuation = new Symbol(lexeme.charAt(i), ComponentType.PUNCTUATION);
				if (i > 0 && Character.isDigit(lexeme.charAt(i - 1))) {
					TextComponent number = numberParser.parse(lexeme.substring(indexBeginNumber, indexEndNumber));
					composite.addComponent(number);
				}
				composite.addComponent(punctuation);
				indexBeginNumber = indexEndNumber + 1;
			} else if (Character.isDigit(lexeme.charAt(lexeme.length() - 1)) && i == lexeme.length() - 1) {
				indexEndNumber = lexeme.length();
				TextComponent number = numberParser.parse(lexeme.substring(indexBeginNumber, indexEndNumber));
				composite.addComponent(number);
			}
		}
	}
}
