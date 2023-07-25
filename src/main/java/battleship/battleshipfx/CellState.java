package battleship.battleshipfx;

// Состояние ячейки
public enum CellState {
    Empty,      // Пустое поле
    Battleship, // Линкор  (4 клетки)
    Cruiser,    // Крейсер (3 клетки)
    Destroyer,  // Эсминец (2 клетки)
    Carrier,    // Катер   (1 клетка)
    Hit,        // Попал
    Miss,       // Мимо
    Kill,       // Убил
    Ship
}
