package battleship.battleshipfx;

// Класс Координата, хранит строку и столбец
public class Coordinate {
    private int row;
    private int column;

    public Coordinate(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
}
