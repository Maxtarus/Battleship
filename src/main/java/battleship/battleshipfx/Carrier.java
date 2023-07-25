package battleship.battleshipfx;

// Тип корабля
public class Carrier extends Ship {
    public Carrier() {
        name = "Carrier";
        length = 1;
        hint = 0;
        state = CellState.Battleship.Ship;
    }
}
