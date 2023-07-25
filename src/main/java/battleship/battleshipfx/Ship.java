package battleship.battleshipfx;

import java.util.ArrayList;

// Абстрактный класс Корабль
public abstract class Ship
{
    protected String name; // имя
    protected int length;  // его длина (кол-во палуб)
    protected int hint;    // кол-во попаданий
    protected ArrayList<Cell> cells; // ячейки на которых он располагается
    public CellState state; // состояние

    // проверка убит ли корабль
    public boolean isKilled() {
        return length <= hint;  // если кол-во попаданий больше или равно его длине, значит утонул
    }

    // установить ячейкиЮ на которых расположен корабль
    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    // увеличить кол-во попаданий на единицу
    public void increaseHint() { ++hint; }
}
