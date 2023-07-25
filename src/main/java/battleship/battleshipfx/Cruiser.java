package battleship.battleshipfx;

public class Cruiser  extends Ship {
    public Cruiser() {
        name = "Cruiser";
        length = 3;
        hint = 0;
        state = CellState.Battleship.Ship;
    }
}