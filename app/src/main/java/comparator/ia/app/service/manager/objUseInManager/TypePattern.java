package comparator.ia.app.service.manager.objUseInManager;

import java.util.regex.Pattern;

public class TypePattern {
	private String operation;
	private Pattern pattern;
	
	public TypePattern(String operation, Pattern pattern) {
		super();
		this.operation = operation;
		this.pattern = pattern;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Pattern getPattern() {
		return pattern;
	}
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
}
