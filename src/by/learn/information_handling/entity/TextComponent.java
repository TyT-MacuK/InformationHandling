package by.learn.information_handling.entity;

import java.util.List;

public interface TextComponent {
	List<TextComponent> getComponent();

	ComponentType getComponentType();

	void addComponent(TextComponent component);

	int countSymbol();
}
