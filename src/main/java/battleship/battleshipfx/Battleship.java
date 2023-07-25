package battleship.battleshipfx;

public class Battleship extends Ship {
    public Battleship() {
        name = "Battleship";
        length = 4;
        hint = 0;
        state = CellState.Battleship.Ship;
    }
}