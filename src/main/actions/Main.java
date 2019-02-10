package main.actions;

import java.awt.*;

import main.gui.Application;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Application app = new Application();
			app.setVisible(true);

			app.startGame();
		});
	}

}
