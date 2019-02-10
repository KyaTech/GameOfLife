package main.gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.List;

import javax.swing.*;

import main.constant.Constants;
import main.game.Cell;
import main.game.Logic;

public class Board extends JPanel {

	private Logic gameLogic;

	public Board(Logic gameLogic) {
		this.gameLogic = gameLogic;
	}

	public void updateGameLogic(Logic gameLogic) {
		this.gameLogic = gameLogic;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		setBackground(Color.WHITE);

		drawGame(g);
	}

	private void drawGame(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh
			= new RenderingHints(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);

		Dimension size = getSize();
		double width = size.getWidth();
		double height = size.getHeight();

		List<Logic.Row> rows = gameLogic.getRows();

		int cell_size = (int) (width / Constants.COUNT_ROWS);

		g2d.setColor(Color.BLACK);
		for (int current_row = 0; current_row < rows.size(); current_row ++) {
			Logic.Row row = rows.get(current_row);

			List<Cell> columns = row.getColumns();
			for (int current_cell = 0; current_cell < columns.size(); current_cell++) {
				//g2d.drawRect(current_cell * cell_size, current_row * cell_size, cell_size, cell_size);

				Cell cell = columns.get(current_cell);
				if (cell.isLiving()) {
					g2d.fillRect(current_cell * cell_size, current_row * cell_size, cell_size, cell_size);
				}


			}
		}
	}

	public void nextFrame() {
		repaint();
	}
}
