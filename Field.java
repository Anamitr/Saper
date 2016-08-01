package Saper;

import javafx.scene.control.Button;

class Field extends Button {
	private boolean isBomb = false;
	private int bombsAround = 0;
	public enum FieldState { COVERED, DISPLAYED, FLAGGED };
	FieldState state;
	
	Field() {
		setState(FieldState.COVERED);
	}	

	protected void setState(FieldState newState) {
		state = newState;
		switch (state) {
		case COVERED:	
			this.setId("covered");
			this.setText("");
			break;
		case DISPLAYED:	
			this.setId("displayed");
			this.setText(Integer.toString(this.getBombsAround()));
			break;
		case FLAGGED:	this.setId("flag"); break;
		}
	}
	
	protected FieldState getState() { return state; }

	protected int getBombsAround() {
		return bombsAround;
	}

	protected void setBombsAround(int bombsAround) {
		this.bombsAround = bombsAround;
	}

	protected boolean isBomb() {
		return isBomb;
	}

	protected void setBomb(boolean isBomb) {
		this.isBomb = isBomb;
	}
}
