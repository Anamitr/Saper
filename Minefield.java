package Saper;

import javafx.scene.control.Button;

class Minefield extends Button {
	private boolean isBomb = false;
	private int bombsAround = 0;

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