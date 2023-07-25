package battleship.battleshipfx;

import java.util.Objects;

// Класс ячейка
public class Cell {
    private CellState state;
    private Coordinate coordinate;
    private int number;

    public Cell(int row, int column)
    {
        coordinate = new Coordinate(row, column);
        state = CellState.Empty;
        number = ((row-1)*10) + column; // По номеру строки и столбца считаем номер
    }

    public int getNumber() {
        return number;
    }

    public boolean isFree()
    {
        return  state == CellState.Empty;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return coordinate.equals(cell.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }
}