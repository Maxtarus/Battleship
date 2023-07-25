package battleship.battleshipfx;

import java.util.ArrayList;

// Игровое поле
public class GameBoard {
    public ArrayList<Cell> cells; // ячейки
    public final int COUNT_ROWS = 10; // размеры
    public final int COUNT_COLS = 10;

    public GameBoard() {
        cells = new ArrayList<>();

        // создаем ячейки
        for (int i = 1; i <= COUNT_ROWS; i++) {
            for (int j = 1; j <= COUNT_COLS; j++) {
                Cell cell = new Cell(i, j);
                cell.setState(CellState.Empty);
                cells.add(cell);
            }
        }
    }

    public ArrayList<Cell> getCells() {
        return cells;
    } // вернуть ячейки

    // Вернуть ячейки из диапазона
    public ArrayList<Cell> getCellsInRange(int rowStart, int rowEnd, int colStart, int colEnd) {
        ArrayList<Cell> rangeCells = new ArrayList<>();
        for (Cell cell : cells) { // проходим по ячейкам
            int row = cell.getCoordinate().getRow();
            int col = cell.getCoordinate().getColumn();

            if (row >= rowStart && row <= rowEnd && col >= colStart && col <= colEnd) // если попала в диапазон
                rangeCells.add(cell); // добавляем ее
        }

        return rangeCells; // вернуть
    }

    // Все соседние клетки свободные?
    public boolean isFreeNeighbors(int startrow, int endrow, int startcolumn, int endcolumn) {
        if (startcolumn == endcolumn) { // если вертикально размещен корабль
            Cell cell = getCellByCoordinate(startrow - 1, startcolumn); // получить ячейку снизу

            if (cell != null)
                if (!cell.isFree()) // если не свободна
                    return false;

            // получить ячейку сверху
            cell = getCellByCoordinate(endrow + 1, startcolumn);
            if (cell != null)
                if (!cell.isFree())
                    return false;

            // проходим по вертикали смежных ячеек слева
            if (startcolumn - 1 >= 1) {
                for (int i = startrow - 1; i <= endrow + 1; ++i) {
                    cell = getCellByCoordinate(i, startcolumn - 1);
                    if (cell != null && !cell.isFree())
                        return false;
                }
            }

            // проходим по вертикали смежных ячеек справа
            if (startcolumn + 1 <= 10) {
                for (int i = startrow - 1; i <= endrow + 1; ++i) {
                    cell = getCellByCoordinate(i, startcolumn + 1);
                    if (cell != null)
                        if (!cell.isFree())
                            return false;
                }
            }
        }


        // ТОЖЕ САМОЕ ТОЛЬКО ДЛЯ ГОРИЗОНТАЛИ
        // СМОТРИМ СЛЕВА И СПРАВА НА СОСЕДНИЕ
        // ПОТОМ НА СТРОКУ СВЕРХУ И СНИЗУ
        if (startrow == endrow) {
            Cell cell = getCellByCoordinate(startrow, startcolumn - 1);

            if (cell != null)
                if (!cell.isFree())
                    return false;

            cell = getCellByCoordinate(startrow, endcolumn + 1);
            if (cell != null)
                if (!cell.isFree())
                    return false;

            if (startrow - 1 >= 1) {
                for (int i = startcolumn - 1; i <= endcolumn + 1; ++i) {
                    cell = getCellByCoordinate(startrow - 1, i);
                    if (cell != null)
                        if (!cell.isFree())
                            return false;
                }
            }

            if (startrow + 1 <= 10) {
                for (int i = startcolumn - 1; i <= endcolumn + 1; ++i) {
                    cell = getCellByCoordinate(startrow + 1, i);
                    if (cell != null)
                        if (!cell.isFree())
                            return false;
                }
            }
        }
        return true;
    }

    // Вернуть ячейку по координате
    public Cell getCellByCoordinate(int row, int col) {
        if (row < 1 || row > 10 || col < 1 || col > 10)
            return null;

        for (Cell cell : cells) {
            int curRow = cell.getCoordinate().getRow();
            int curCol = cell.getCoordinate().getColumn();

            if (row == curRow && col == curCol) // если совпали, то вернуть ее
                return cell;
        }

        return null;
    }

    // Ячейка свободна
    public boolean isFreeCells(ArrayList<Cell> cells) {
        for (Cell cell : cells) {
                if (!cell.isFree()) // если нет
                    return false;
        }

        return true;
    }
}
