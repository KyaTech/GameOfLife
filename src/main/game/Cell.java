package main.game;

public class Cell {

	public Cell(boolean living) {
		this.living = living;
	}

	private boolean living;

	public void setLiving(boolean living) {
		this.living = living;
	}

	public boolean isLiving() {
		return living;
	}

}
