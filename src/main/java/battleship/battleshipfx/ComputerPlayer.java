package battleship.battleshipfx;

import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String name, TypePlayer type) {
        super(name, type);
    }

    // Размещает также как и реализована для игрока
    @Override
    public void placeShips() {
        Random rand = new Random();
        for(Ship ship : ships) {
           boolean isOpen = true;

            while (isOpen) {
                var startcolumn = rand.nextInt(1,11);
                var startrow = rand.nextInt(1, 11);
                int endrow = startrow, endcolumn = startcolumn;
                var orientation = rand.nextInt(1, 101) % 2; //0 for Horizontal

                if (orientation == 0) {
                    for (int i = 1; i < ship.length; i++) {
                        endrow++;
                    }
                }
                else
                {
                    for (int i = 1; i < ship.length; i++)
                    {
                        endcolumn++;
                    }
                }

                if(endrow > 10 || endcolumn > 10)
                {
                    isOpen = true;
                    continue;
                }

                ArrayList<Cell> rangeCells = gameBoard.getCellsInRange(startrow, endrow, startcolumn, endcolumn);

                if (!gameBoard.isFreeCells(rangeCells)) {
                    isOpen = true;
                    continue;
                }

                if (!gameBoard.isFreeNeighbors(startrow, endrow, startcolumn, endcolumn)) {
                    isOpen = true;
                    continue;
                }

                for (Cell cell : rangeCells)
                {
                    cell.setState(ship.state);
                }

                ship.setCells(rangeCells);
                isOpen = false;
            }
        }
    }
}
