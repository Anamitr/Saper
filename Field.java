package Saper;

import javafx.scene.control.Button;

class Field extends Button {
	private boolean isBomb = false;
	private int bombsAround = 0;
	private boolean uncovered = false;
	private boolean isFlagged = false;
	
	protected boolean isFlagged() {
		return isFlagged;
	}

	protected void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
		if(isFlagged) setId("flag");
		else setId("uncovered");
	}

	protected boolean isUncovered() {
		return uncovered;
	}

	protected void setUncovered(boolean uncovered) {
		this.uncovered = uncovered;
		if(!uncovered) setId("covered");
	}

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
