package by.learn.information_handling.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextComposite implements TextComponent {
	private List<TextComponent> component = new ArrayList<>();
	ComponentType type;
	private static final String SPASE = " ";
	private static final String TAB = "\t";

	public TextComposite(ComponentType type) {
		this.type = type;
	}

	public List<TextComponent> getComponent() {
		return Collections.unmodifiableList(component);
	}

	@Override
	public ComponentType getComponentType() {
		return type;
	}

	public void remove(TextComponent component) {
		this.component.remove(component);
	}

	@Override
	public void addComponent(TextComponent component) {
		this.component.add(component);
	}

	@Override
	public int countSymbol() {
		int count = 0;
		for (TextComponent symbol : component) {
			count += symbol.countSymbol();
		}
		return count;
	}

	public int countWord() {
		int count = 0;
		for (TextComponent element : this.component) {
			if (element.getComponentType() == ComponentType.WORD) {
				count++;
			}
		}
		return count;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + component.hashCode();
		result = prime * result + type.hashCode();
		return result;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		TextComposite composite = (TextComposite) object;
		if (!component.equals(composite.getComponent())) {
			return false;
		}
		return type.equals(composite.getComponentType());
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (TextComponent c : component) {
			if (c.getComponentType() == ComponentType.LEXEME) {
				builder.append(SPASE);
			} else if (c.getComponentType() == ComponentType.PARAGRAPH) {
				builder.append(TAB);
				builder.append(SPASE);
				builder.append(SPASE);
			}
			builder.append(c.toString());

		}
		return builder.toString();
	}
}
