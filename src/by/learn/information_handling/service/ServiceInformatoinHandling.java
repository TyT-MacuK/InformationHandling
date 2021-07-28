package by.learn.information_handling.service;

import java.util.Set;

import by.learn.information_handling.entity.TextComponent;
import by.learn.information_handling.exception.InformationHandlingException;

public interface ServiceInformatoinHandling {

	TextComponent sortParagraphs(TextComponent text) throws InformationHandlingException;

	Set<TextComponent> findSentencesWithLongestWord(TextComponent text) throws InformationHandlingException;

	TextComponent removeSentencesWithFewerWords(TextComponent text, int numberWords)
			throws InformationHandlingException;

	Set<TextComponent> findTheSameWords(TextComponent text) throws InformationHandlingException;

	int countVowelLetters(TextComponent sentence) throws InformationHandlingException;

	int countConsonantsLetters(TextComponent sentence) throws InformationHandlingException;
}
