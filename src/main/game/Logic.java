package main.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.constant.Constants;

public class Logic {

	private List<Row> rows;

	public Logic() {
		Random random = new Random();

		rows = new ArrayList<>();
		for (int i = 0; i < Constants.COUNT_ROWS; i++) {
			Row row = new Row();

			List<Cell> columns = new ArrayList<>();
			for (int j = 0; j < Constants.COUNT_COLUMNS; j++) {
				columns.add(new Cell(random.nextBoolean()));
			}
			row.setColumns(columns);

			rows.add(row);
		}
	}

	public void nextFrame() {

		List<Row> newRows = copyRows();

		for (int current_row = 0; current_row < newRows.size(); current_row ++) {
			Logic.Row row = newRows.get(current_row);

			List<Cell> columns = row.getColumns();
			for (int current_cell = 0; current_cell < columns.size(); current_cell++) {

				Cell oldCell = rows.get(current_row).getColumns().get(current_cell);
				Cell cell = columns.get(current_cell);

				int livingNeighbours = countLivingNeighbours(current_row,current_cell);

				// Eine tote Zelle mit genau drei lebenden Nachbarn wird in der Folgegeneration neu geboren.
				if (!oldCell.isLiving() && livingNeighbours == 3) {
					cell.setLiving(true);
				}

				// Lebende Zellen mit weniger als zwei lebenden Nachbarn sterben in der Folgegeneration an Einsamkeit.
				if (oldCell.isLiving() && livingNeighbours < 2) {
					cell.setLiving(false);
				}

				// Eine lebende Zelle mit zwei oder drei lebenden Nachbarn bleibt in der Folgegeneration am Leben.
				if (oldCell.isLiving() && (livingNeighbours == 2 || livingNeighbours == 3)) {
					cell.setLiving(true);
				}

				// Lebende Zellen mit mehr als drei lebenden Nachbarn sterben in der Folgegeneration an Überbevölkerung.
				if (oldCell.isLiving() && livingNeighbours > 3) {
					cell.setLiving(false);
				}

			}
		}

		rows = newRows;


	}

	int countLivingNeighbours(int current_row, int current_cell) {
		int livingNeighbours = 0;

		//TOP
		if (current_row != 0) {
			//MIDDLE
			if (rows.get(current_row - 1).getColumns().get(current_cell).isLiving()) livingNeighbours++;

			//LEFT
			if (current_cell != 0 && rows.get(current_row - 1).getColumns().get(current_cell - 1).isLiving()) livingNeighbours++;

			// RIGHT
			if (current_cell != (Constants.COUNT_COLUMNS -1) && rows.get(current_row - 1).getColumns().get(current_cell + 1).isLiving()) livingNeighbours++;
		}

		//BOTTOM
		if (current_row != (Constants.COUNT_ROWS - 1)) {
			//MIDDLE
			if (rows.get(current_row + 1).getColumns().get(current_cell).isLiving()) livingNeighbours++;

			//LEFT
			if (current_cell != 0 && rows.get(current_row + 1).getColumns().get(current_cell - 1).isLiving()) livingNeighbours++;

			//RIGHT
			if (current_cell != (Constants.COUNT_COLUMNS -1) && rows.get(current_row + 1).getColumns().get(current_cell + 1).isLiving()) livingNeighbours++;
		}

		//LEFT
		if (current_cell != 0 && rows.get(current_row).getColumns().get(current_cell - 1).isLiving()) livingNeighbours++;

		//RIGHT
		if (current_cell != (Constants.COUNT_COLUMNS -1) && rows.get(current_row).getColumns().get(current_cell + 1).isLiving()) livingNeighbours++;

		return livingNeighbours;
	}


	private List<Row> copyRows() {

		List<Row> newRow = new ArrayList<>();
		for (int i = 0; i < Constants.COUNT_ROWS; i++) {
			Row row = new Row();

			List<Cell> columns = new ArrayList<>();
			for (int j = 0; j < Constants.COUNT_COLUMNS; j++) {
				boolean oldLiving = rows.get(i).getColumns().get(j).isLiving();
				columns.add(new Cell(oldLiving));
			}
			row.setColumns(columns);

			newRow.add(row);
		}

		return newRow;

	}

	public List<Row> getRows() {
		return this.rows;
	}

	public class Row {
		private List<Cell> columns;

		public List<Cell> getColumns() {
			return columns;
		}

		public void setColumns(List<Cell> columns) {
			this.columns = columns;
		}
	}
}

