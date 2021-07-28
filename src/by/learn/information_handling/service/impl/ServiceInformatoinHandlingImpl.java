package by.learn.information_handling.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.learn.information_handling.comparator.ParagraphComparator;
import by.learn.information_handling.entity.ComponentType;
import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.entity.TextComposite;
import by.learn.information_handling.exception.InformationHandlingException;
import by.learn.information_handling.service.ServiceInformatoinHandling;

public class ServiceInformatoinHandlingImpl implements ServiceInformatoinHandling {
	private static final Logger logger = LogManager.getLogger();
	private static final String VOWEL_LETTERS = "[AEIOUaeiou]";
	private static final String CONSONANTS_LETTERS = "[BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz]";

	@Override
	public TextComponent sortParagraphs(TextComponent text) throws InformationHandlingException {
		logger.log(Level.INFO, "method: sortParagraphs");
		if (text.getComponentType() != ComponentType.TEXT) {
			logger.log(Level.ERROR, "The value have type {} but type must be \"TEXT\"", text.getComponentType());
			throw new InformationHandlingException("Incorrect text type");
		}
		TextComposite sortedComposite = new TextComposite(ComponentType.TEXT);
		List<TextComponent> paragraphs = new ArrayList<>(text.getComponent());
		paragraphs.sort(new ParagraphComparator());
		for (TextComponent paragraph : paragraphs) {
			sortedComposite.addComponent(paragraph);
		}
		logger.log(Level.INFO, "method sortParagraphs finished.");
		return sortedComposite;
	}

	@Override
	public Set<TextComponent> findSentencesWithLongestWord(TextComponent text) throws InformationHandlingException {
		logger.log(Level.INFO, "method: findSentencesWithLongestWord");
		if (text.getComponentType() != ComponentType.TEXT) {
			logger.log(Level.ERROR, "The value have type {} but type must be \"TEXT\"", text.getComponentType());
			throw new InformationHandlingException("Incorrect text type");
		}
		int maxLengthWord = findlengthLongestWord(text);
		Set<TextComponent> result = new HashSet<>();

		for (TextComponent paragraph : text.getComponent()) {
			for (TextComponent sentence : paragraph.getComponent()) {
				for (TextComponent lexeme : sentence.getComponent()) {
					if (lexeme.getComponentType() != ComponentType.PUNCTUATION) {
						for (TextComponent word : lexeme.getComponent()) {
							if (maxLengthWord == word.countSymbol()) {
								result.add(sentence);
							}
						}
					}
				}
			}
		}
		logger.log(Level.INFO, "method findSentencesWithLongestWord finished.");
		return result;
	}

	private int findlengthLongestWord(TextComponent text) {
		logger.log(Level.INFO, "method: finglengthLongestWord");
		int maxLengthWord = 0;
		for (TextComponent paragraph : text.getComponent()) {
			for (TextComponent sentence : paragraph.getComponent()) {
				for (TextComponent lexeme : sentence.getComponent()) {
					if (lexeme.getComponentType() != ComponentType.PUNCTUATION) {
						for (TextComponent word : lexeme.getComponent()) {
							int currentAmount = word.countSymbol();
							if (currentAmount > maxLengthWord) {
								maxLengthWord = currentAmount;
							}
						}
					}
				}
			}
		}
		logger.log(Level.INFO, "method finglengthLongestWord finished.");
		return maxLengthWord;
	}

	@Override
	public TextComponent removeSentencesWithFewerWords(TextComponent text, int numberWords)
			throws InformationHandlingException {
		logger.log(Level.INFO, "method: removeSentencesWithFewerWords");
		if (text.getComponentType() != ComponentType.TEXT) {
			logger.log(Level.ERROR, "The value have type {} but type must be \"TEXT\"", text.getComponentType());
			throw new InformationHandlingException("Incorrect text type");
		}
		int countWords = 0;
		List<TextComponent> notValidSentences = new ArrayList<>();
		for (TextComponent paragraph : text.getComponent()) {
			for (TextComponent sentence : paragraph.getComponent()) {
				for (TextComponent lexeme : sentence.getComponent()) {
					if (lexeme.getComponentType() != ComponentType.PUNCTUATION) {
						countWords += ((TextComposite) lexeme).countWord();
					}
				}
				if (countWords < numberWords) {
					notValidSentences.add(sentence);
				}
				countWords = 0;
			}
		}
		for (TextComponent paragraph : text.getComponent()) {
			for (TextComponent notValidSentence : notValidSentences) {
				((TextComposite) paragraph).remove(notValidSentence);
			}
		}
		logger.log(Level.INFO, "method finished. Sentences delited {}", notValidSentences.size());
		return text;
	}

	@Override
	public Set<TextComponent> findTheSameWords(TextComponent text) throws InformationHandlingException {
		logger.log(Level.INFO, "method: countTheSameWords");
		if (text.getComponentType() != ComponentType.TEXT) {
			logger.log(Level.ERROR, "The value have type {} but type must be \"TEXT\"", text.getComponentType());
			throw new InformationHandlingException("Incorrect text type");
		}
		Set<TextComponent> wordsWithoutRepetitions = new HashSet<>();
		Set<TextComponent> repeatingWords = new HashSet<>();

		for (TextComponent paragraph : text.getComponent()) {
			for (TextComponent sentence : paragraph.getComponent()) {
				for (TextComponent lexeme : sentence.getComponent()) {
					if (lexeme.getComponentType() != ComponentType.PUNCTUATION) {
						for (TextComponent partOfLexeme : lexeme.getComponent()) {
							if (partOfLexeme.getComponentType() == ComponentType.WORD) {
								if (wordsWithoutRepetitions.contains(partOfLexeme)) {
									repeatingWords.add(partOfLexeme);
								} else {
									wordsWithoutRepetitions.add(partOfLexeme);
								}
							}
						}
					}
				}
			}
		}
		logger.log(Level.INFO, "method finished. The same words found {}", repeatingWords.size());
		return repeatingWords;
	}

	@Override
	public int countVowelLetters(TextComponent sentence) throws InformationHandlingException {
		logger.log(Level.INFO, "method: countVowelLetters");
		if (sentence.getComponentType() != ComponentType.SENTENCE) {
			logger.log(Level.ERROR, "The value have type {} but type must be \"SENTENCE\"",
					sentence.getComponentType());
			throw new InformationHandlingException("Incorrect text type");
		}
		int result = countLettersByRexEx(sentence.toString(), VOWEL_LETTERS);
		logger.log(Level.INFO, "method countVowelLetters finished. Vowel letters found - {}", result);
		return result;
	}

	@Override
	public int countConsonantsLetters(TextComponent sentence) throws InformationHandlingException {
		logger.log(Level.INFO, "method: countConsonantsLetters");
		if (sentence.getComponentType() != ComponentType.SENTENCE) {
			logger.log(Level.ERROR, "The value have type {} but type must be \"SENTENCE\"",
					sentence.getComponentType());
			throw new InformationHandlingException("Incorrect text type");
		}
		int result = countLettersByRexEx(sentence.toString(), CONSONANTS_LETTERS);
		logger.log(Level.INFO, "method countConsonantsLetters finished. Consonants letters found - {}", result);
		return result;
	}

	private int countLettersByRexEx(String sentence, String regEx) {
		logger.log(Level.INFO, "method: countConsonantsLetters");
		int count = 0;
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(sentence);
		while (matcher.find()) {
			count++;
		}
		logger.log(Level.INFO, "method countLettersByRexEx finished.");
		return count;
	}
}
