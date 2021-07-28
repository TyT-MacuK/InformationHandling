package by.learn.information_handling.comparator;

import java.util.Comparator;

import by.learn.information_handling.entity.TextComponent;

public class ParagraphComparator implements Comparator<TextComponent> {

	@Override
	public int compare(TextComponent firstComponent, TextComponent secondComponent) {
		int sentencesInFirstComponent = firstComponent.getComponent().size();
		int sentencesInSecondtComponent = secondComponent.getComponent().size();
		return Integer.compare(sentencesInFirstComponent, sentencesInSecondtComponent);
	}

}
