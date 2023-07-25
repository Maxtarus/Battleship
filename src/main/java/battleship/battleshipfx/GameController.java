package battleship.battleshipfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GameController {
    @FXML
    private GridPane gameBoard;

    @FXML
    private GridPane shootBoard;

    @FXML
    private GridPane gameBoardPl2;

    @FXML
    private GridPane shootBoardPl2;

    @FXML
    private TextField inputPlaceTextField;

    @FXML
    private Button shootBtn;

    @FXML
    private void onSetPlaceBtnClick() {
    }

    private Player pl1;
    private Player pl2;
    boolean isWaitShootPlayer = true; // ждем выстрела первого игрока
    boolean isFirstPlayerShoot = true; // первый игрок стреляет?
    Coordinate shootCoordinate; // координаты выстрела игрока

    // создаем игроков и рисуем игровое поле
    @FXML
    public void initialize() {
        createPlayers(TypePlayer.Human, TypePlayer.Computer);

        drawPlayerBoard(pl1, gameBoard, shootBoard);
        drawPlayerBoard(pl2, gameBoardPl2, shootBoardPl2);

        gameRound();
    }

    // Нажата кнопка Выстрел
    @FXML
    protected void onShootBtnClick() {
        String []text = null;

        // Если первый игрок стрелял
        if (isFirstPlayerShoot)
            text = inputPlaceTextField.getText().split("_"); // получаем координаты

        shootCoordinate = getCoordinateFromTextInput(text); // получить тип Coordinate после разбора текстового ввода пользователем координаты

        // если координаты не бракованные
        if (shootCoordinate.getRow() != -1 && shootCoordinate.getColumn() != -1)  {
            isWaitShootPlayer = false; // снимаем флаг что ждем выстрела первого игрока
        }

        // Очищаем поле ввода координат
        inputPlaceTextField.clear();

        gameRound(); // Вызов Раунда игры

    }

    // Вывод окна-сообщения о некорректном вводе
    void showAlertDialog(String title, String context, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
        return;
    }

    // Раунд
    public void gameRound() {
        if (isWaitShootPlayer) return; // если ждем выстрела первого игрока, выход

        CellState result = null;
        if (isFirstPlayerShoot) { // если первый выстрелил
            result = processShoot(pl1, pl2, shootCoordinate); // обрабатываем выстрел
        } else if (pl2.getType() == TypePlayer.Computer) {   // Играет компьютер
            while (true) { // пока не получили координаты выстрела (корректные)
                Coordinate coordinate = pl2.getRandomShoot(); // получить координаты
                // ПОлучаем по координатам ячейку по которой попали
                Cell shootCell = pl1.gameBoard.getCellByCoordinate(coordinate.getRow(), coordinate.getColumn());
                // обрабатываем выстрел (ДРУГАЯ ОЧЕРЕДНОСТЬ ПЕРЕДАЧИ ПАРАМЕТРОВ, ПЕРВЫЙ ЭТО КТО СТРЕЛЯЕТ)
                result = processShoot(pl2, pl1, shootCell.getCoordinate());
                break;
            }
        } else {
            result = processShoot(pl2, pl1, shootCoordinate);
        }

        // Если результат выстрела промах
        if (result == CellState.Miss) {
            isFirstPlayerShoot = !isFirstPlayerShoot; // меняем игрока (след. стреляет)
        } else { // иначе
            if (pl1.isAllSunk() || pl2.isAllSunk()) { // проверяем что кто-то потерял весь флот
                System.out.println("\n\nPLAYER WIN");
                return;
            }
        }
        if (isFirstPlayerShoot) // если первый стреляет
            isWaitShootPlayer = true; // ждем выстрела
        else
            gameRound(); // новый раунд
    }

    // создаем Игроков
    void createPlayers(TypePlayer typePl1, TypePlayer typePl2) {
        pl1 = new HumanPlayer("Valli", typePl1);
        pl2 = new ComputerPlayer("C-3PO", typePl2);

        // Размещаем корабли
        pl1.placeShips();
        pl2.placeShips();

        // Вывод игровых полей на консоль
        System.out.println("\n=============== PLAYER 1 ===============");
        pl1.outputBoards();
        System.out.println("\n=============== PLAYER 2 ===============");
        pl2.outputBoards();
    }

    // СОздаем игроавые поля
    void drawPlayerBoard(Player player, GridPane gameBoard, GridPane shootBoard ) {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                StackPane pane1 = new StackPane(); // создаем ячейку

                if (player.gameBoard.cells.get((i) * 10 + j).isFree()) // если она свободна
                    pane1.getStyleClass().add("game-grid-cell-water"); // заролняем ее стилем (см. файл style.css)
                else
                    pane1.getStyleClass().add("game-grid-cell-ship"); // иначе символ корабля

                gameBoard.add(pane1, j, i); // размещаем на игровом поле

                // теперь ячейку для доски выстрелов
                StackPane pane2 = new StackPane();
                pane2.getStyleClass().add("game-grid-cell-water");

                shootBoard.add(pane2, j, i);
            }
        }
    }

    // Получить координаты по текстовому вводу
    Coordinate getCoordinateFromTextInput(String[] text) {
        if (text.length != 2) { // если длина не равна двум элементам, то ошибка
            showAlertDialog("Ошибка ввода","Неправильно введены координаты!\n" +
                    "Формат ввода для примера: i_10", Alert.AlertType.ERROR);
            return new Coordinate(-1, -1);
        }

        String letter = text[0].toLowerCase(); // буква
        if (letter.length() != 1 || 'a' > letter.charAt(0) || letter.charAt(0) > 'j') { // если не в диапазоне
            showAlertDialog("Ошибка ввода","Неправильно введена буква координата!\n", Alert.AlertType.ERROR);
            return new Coordinate(-1, -1);
        }

        Integer digitLetter = letter.charAt(0) - 'a' + 1; // первести символ в число-координату
        if (digitLetter < 1 || digitLetter > 10) {
            {
                showAlertDialog("Ошибка ввода","Неправильно введена буква координата!\n", Alert.AlertType.ERROR);
                return new Coordinate(-1, -1);
            }
        }

        int num = Integer.parseInt(text[1]); // Получаем координату число

        if (num < 1 || num > 10) { // проверка что в диапазоне
            showAlertDialog("Ошибка ввода","Неправильно введено число-координата!\n", Alert.AlertType.ERROR);
            return new Coordinate(-1, -1);
        }

        return new Coordinate(digitLetter, num); // вернуть координату
    }

    // Обработа выстрел
    CellState processShoot(Player activePl, Player passivePl, Coordinate coordinate) {
        // ПОлучаем ячеку по которой стреляли
        Cell shootCell = passivePl.gameBoard.getCellByCoordinate(coordinate.getRow(), coordinate.getColumn());
        int numShootCell = shootCell.getNumber() - 1; // ее номер

        // если он пустая
        if (shootCell.getState() == CellState.Empty) {
            // Свойство устанавливаем Мимо
            activePl.shootingBoard.getCells().get(numShootCell).setState(CellState.Miss);
            // Перерисовываем ячеку под событие Мимо
            updateBoardsAfterShoot(activePl, passivePl, shootCell.getNumber(), CellState.Miss);
            return CellState.Miss; // вернуть результат
        }
        else if (shootCell.getState() == CellState.Ship) { // если там корабль
            Ship ship = passivePl.getShip(shootCell); // получаем корабль поячейку по которой выстрелили
            CellState state = CellState.Ship;
            if (ship != null) {
                ship.increaseHint(); // увеличиваем попадание на один
                if (ship.isKilled()) { // если он убит
                    for (Cell cell : ship.cells) { // устанавливаем ячейки корабля чтов все погибли
                        cell.setState(CellState.Kill);
                        // Обновляем доску (красим ячейки черным)
                        updateBoardsAfterShoot(activePl, passivePl, cell.getNumber(), CellState.Kill);
                    }
                    return CellState.Kill;
                } else { // иначе Ранили
                    state = CellState.Hit;
                    shootCell.setState(state);
                }
                passivePl.gameBoard.getCells().get(numShootCell).setState(state);
            }
            updateBoardsAfterShoot(activePl, passivePl, shootCell.getNumber(), state); // перерисовываем
            shootCell.setState(state);

        }
        return shootCell.getState();
    }

    // Обновит ячейку поля на экране
    void updateBoardsAfterShoot(Player activePl, Player passivePl, int numShootCell, CellState state) {
        if (activePl.equals(pl1)) { // если активен первый игрок
            if (state == CellState.Miss) { // если промах
                // удаляем старый стиль заполнения ячейки
                shootBoard.getChildren().get(numShootCell).getStyleClass().remove("game-grid-cell-water");
                // устанавливаем новый
                shootBoard.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-miss");
            } else if (state == CellState.Hit) { // если ранили
                shootBoard.getChildren().get(numShootCell).getStyleClass().remove("game-grid-cell-water");
                shootBoard.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-hit");
                gameBoardPl2.getChildren().get(numShootCell).getStyleClass().remove("game-grid-cell-ship");
                gameBoardPl2.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-hit");
            }else if (state == CellState.Kill) { // если убили
                shootBoard.getChildren().get(numShootCell).getStyleClass().clear();
                shootBoard.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-kill");
                gameBoardPl2.getChildren().get(numShootCell).getStyleClass().clear();
                gameBoardPl2.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-kill");
            }
        } else { // если второй игрок
            if (state == CellState.Miss) {
                gameBoard.getChildren().get(numShootCell).getStyleClass().remove("game-grid-cell-water");
                gameBoard.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-miss");
                shootBoardPl2.getChildren().get(numShootCell).getStyleClass().remove("game-grid-cell-water");
                shootBoardPl2.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-miss");
            } else if (state == CellState.Hit) {
                gameBoard.getChildren().get(numShootCell).getStyleClass().remove("game-grid-cell-ship");
                gameBoard.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-hit");
                shootBoardPl2.getChildren().get(numShootCell).getStyleClass().remove("game-grid-cell-water");
                shootBoardPl2.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-hit");
            } else if (state == CellState.Kill) {
                gameBoard.getChildren().get(numShootCell).getStyleClass().clear();
                gameBoard.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-kill");
                shootBoardPl2.getChildren().get(numShootCell).getStyleClass().clear();
                shootBoardPl2.getChildren().get(numShootCell).getStyleClass().add("game-grid-cell-kill");
            }
        }
    }
}