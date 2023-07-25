package battleship.battleshipfx;

import java.util.ArrayList;
import java.util.Random;

// Игрок
public abstract class Player {
    protected String name; // имя игрока
    protected GameBoard gameBoard; // игровая доска, на которой расположены корабли
    protected ShootingBoard shootingBoard; // доска, на которой отмечаются выстрела игрока
    protected ArrayList<Ship> ships; // список кораблей
    protected TypePlayer type; // тип игрока
    // Ниже константы сколько кораблей какого типа надо создать
    public final int COUNT_BATTLESHIP = 1;
    public final int COUNT_CRUISER = 2;
    public final int COUNT_DESTROYER = 3;
    public final int COUNT_CARRIER = 4;

    public Player(String name, TypePlayer type) {
        setName(name); // запоминаем имя

        ships = new ArrayList<Ship>(); // иниц. список кораблей

        // Добавляем нужное кол-во кораблей определенного типа
        for (int i = 0; i < COUNT_CARRIER; ++i) {
            ships.add(new Carrier());
        }

        // Добавляем нужное кол-во кораблей определенного типа
        for (int i = 0; i < COUNT_DESTROYER; ++i) {
            ships.add(new Destroyer());
        }

        // Добавляем нужное кол-во кораблей определенного типа
        for (int i = 0; i < COUNT_CRUISER; ++i) {
            ships.add(new Cruiser());
        }

        // Добавляем нужное кол-во кораблей определенного типа
        for (int i = 0; i < COUNT_BATTLESHIP; ++i) {
            ships.add(new Battleship());
        }

        // Создаем доски
        gameBoard = new GameBoard();
        shootingBoard = new ShootingBoard();
        this.type = type; // запоминаем тип
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TypePlayer getType() {
        return type;
    }

    // Функция проверяет все ли корабли потонули
    public boolean isAllSunk()
    {
        for (var ship : ships) { // проходим по списку
            if (!ship.isKilled()) // если он не убит
                return false; // значит не все корабли утонули
        }

        return true; // значит все
    }

    // Функция размещает корабли
    // Она абстрактная, так как каждый тип пользователя может по своему их располагать
    public abstract void placeShips();

    // Вывод доски на Консоль (для отладки)

    public void outputBoards()
    {
        ArrayList<Cell> cells = gameBoard.getCells();
        System.out.println(name);
        System.out.println("Own Board:                          Firing Board:");

        ArrayList<Cell> rangeCells = new ArrayList<>();
        for (Cell cell : cells) {
            int row = cell.getCoordinate().getRow();
            int col = cell.getCoordinate().getColumn();
            CellState state = cell.getState();
            if (state == CellState.Empty)
                System.out.print("E ");
            else
                System.out.print("S ");
            if (cell.getCoordinate().getColumn() == 10)
                System.out.println();
        }
    }


    // Функция возвращает координаты случайного выстрела
    Coordinate getRandomShoot() {
        Random rnd = new Random(); // генератор случ. чисел
        int row = rnd.nextInt(1,11); // вернуть номер строки от 1 до 10 включительно
        int col = rnd.nextInt(1, 11); // вернуть номер столбца от 1 до 10 включительно

        return new Coordinate(row, col); // вернуть координаты
    }

    // Функция получает ячейку поля доски и по нему возвращает корабль
    // Который на нем расположен
    public Ship getShip(Cell cell) {
        for (Ship ship : ships) { // проходим по списку кораблей
            if (ship.cells != null && ship.cells.contains(cell))  // если корабль содержит такую ячейку
                return ship; // вернем его
        }

        return null; // иначе ничего
    }
}
