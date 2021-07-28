package by.learn.information_handling.entity;

import java.util.List;

public class Symbol implements TextComponent {
	public static final int SYMBOL_LENGHT = 1;
	private char value;
	private ComponentType componentType;

	public Symbol(char symbol, ComponentType componentType) {
		value = symbol;
		this.componentType = componentType;
	}

	public char getValue() {
		return value;
	}

	@Override
	public List<TextComponent> getComponent() {
		throw new UnsupportedOperationException("Unsupported operation \"getComponent\" on leaf");
	}

	@Override
	public ComponentType getComponentType() {
		return componentType;
	}

	@Override
	public void addComponent(TextComponent component) {
		throw new UnsupportedOperationException("Unsupported operation \"getComponent\" on leaf");
	}

	@Override
	public int countSymbol() {
		return SYMBOL_LENGHT;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + Character.hashCode(value);
		result = prime * result + componentType.hashCode();
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
		Symbol symbol = (Symbol) object;
		if (value != symbol.getValue()) {
			return false;
		}
		return symbol.getComponentType().equals(this.componentType);
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(value);
		return stringBuilder.toString();
	}
}
