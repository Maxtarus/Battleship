package battleship.battleshipfx;

import java.util.ArrayList;
import java.util.Random;

// Игрок человек
public class HumanPlayer extends Player
{
    public HumanPlayer(String name, TypePlayer type) {
        super(name, type);
    }

    // Переопределяем функцию размещения кораблей
    @Override
    public void placeShips() {
        Random rand = new Random();
        for(Ship ship : ships) { // проходим по каждому кораблю, который надо разместить
            boolean isOpen = true; // признак все ли хорошо

            while (isOpen)
            {
                // получаем случ. число номера столбца от которого будеи строить корабль на игровом поле
                var startcolumn = rand.nextInt(1,11);
                // получаем случ. число номера строки от которого будеи строить корабль на игровом поле
                var startrow = rand.nextInt(1, 11);
                int endrow = startrow, endcolumn = startcolumn; // начало и конец строки пока равны начальным
                // получить расположение корабля (1 - горизонтально строим, 0 - вертикально)
                var orientation = rand.nextInt(1, 101) % 2; //

                if (orientation == 0) { // если горизонтально
                    for (int i = 1; i < ship.length; i++) // увеличиваем номер строки ячейки на поле
                    {
                        endrow++;
                    }
                }
                else { // иначе
                    for (int i = 1; i < ship.length; i++) // увеличиваем номер столбца ячейки на поле
                    {
                        endcolumn++;
                    }
                }

                // если концы корабля вышли за игровое поле
                if(endrow > 10 || endcolumn > 10)
                {
                    isOpen = true;
                    continue; // начинаем снова пытаться его построить
                }

                // получаем ячейки, которые расположены на полученных строке (столбце)
                ArrayList<Cell> rangeCells = gameBoard.getCellsInRange(startrow, endrow, startcolumn, endcolumn);

                // если кто-то не свободен
                if (!gameBoard.isFreeCells(rangeCells)) {
                    isOpen = true;
                    continue; // снова пытаемся построить корабль (в начало цикла идем)
                }

                // есть ли у этих ячеек соседи (соседних смежных кораблей)
                if (!gameBoard.isFreeNeighbors(startrow, endrow, startcolumn, endcolumn)) {
                    isOpen = true;
                    continue; // снова строим если есть
                }

                // Прошли все проверки, теперь можно занимать эти ячейки (устанавливаем состояние корабля)
                for (Cell cell : rangeCells)
                {
                    cell.setState(ship.state);
                }
                isOpen = false;
            }
        }
    }
}
