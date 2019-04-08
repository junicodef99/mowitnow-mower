package com.mowitnow.mower.enums;

/**
 * @author aZeufack
 */
public enum ActionCommandEnum {
	A("Go"),
	D("Turn left"),
	G("Turn right");
	
	private String actionCommand;

	/**
	 * @param actionCommand
	 */
	private ActionCommandEnum(String actionCommand) {
		this.actionCommand = actionCommand;
	}

	/**
	 * @return the actionCommand
	 */
	public String getActionCommand() {
		return actionCommand;
	}
}
