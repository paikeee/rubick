import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Controller {

    Cube cube;
    boolean gameStarted = false;

    @FXML
    private GridPane gridPane0;

    @FXML
    private GridPane gridPane1;

    @FXML
    private GridPane gridPane2;

    @FXML
    private GridPane gridPane3;

    @FXML
    private GridPane gridPane4;

    @FXML
    private GridPane gridPane5;

    @FXML
    private Button newGameButton;

    @FXML
    private Button randomButton;

    @FXML
    private Button solveButton;

    @FXML
    private Button move;

    @FXML
    private Button testing;

    @FXML
    private Pane paneGrid;

    @FXML
    private Button l0;

    @FXML
    private Button l1;

    @FXML
    private Button l2;

    @FXML
    private Button l3;

    @FXML
    private Button l4;

    @FXML
    private Button l5;

    @FXML
    private Button r0;

    @FXML
    private Button r1;

    @FXML
    private Button r2;

    @FXML
    private Button r3;

    @FXML
    private Button r4;

    @FXML
    private Button r5;

    @FXML
    private HBox hBoxSteps;

    @FXML
    private Label labelSteps;

    @FXML
    private HBox hBoxTest;

    @FXML
    private Label labelTest;

    @FXML
    void initialize() {
        newGameButton.setOnAction(event -> {
            gridClear();
            cube = new Cube();
            gameStarted = true;
            testing.setVisible(true);
            solveButton.setVisible(true);
            move.setVisible(false);
            paneGrid.setVisible(true);
            hBoxSteps.setVisible(false);
            hBoxTest.setVisible(false);
            cube.setGameTiles();
            gridAdd(cube.getGameTiles());
        });
        testing.setOnAction(event -> {
            Test test = new Test();
            paneGrid.setVisible(false);
            hBoxTest.setVisible(true);
            if (test.testing()) {
                labelTest.setText("Success");
            } else {
                labelTest.setText("Error");
            }
            testing.setVisible(false);
            solveButton.setVisible(false);
        });
        randomButton.setOnAction(event -> {
            cube = new Cube();
            gameStarted = true;
            solveButton.setVisible(true);
            move.setVisible(false);
            testing.setVisible(false);
            paneGrid.setVisible(false);
            hBoxSteps.setVisible(false);
            hBoxTest.setVisible(false);
            cube.randomGameTiles();
            gridAdd(cube.getGameTiles());
        });
        solveButton.setOnAction(event -> {
            gridClear();
            solveButton.setVisible(false);
            paneGrid.setVisible(false);
            move.setVisible(true);
            testing.setVisible(false);
            Solver solver = new Solver(cube);
            solver.solveCube();
            hBoxSteps.setVisible(true);
            labelSteps.setText("Steps: " + cube.getSteps());
            gridAdd(cube.getGameTiles());
        });
        move.setOnAction(event -> {
            Tile[][][] body = cube.getStackBody();
            if (body != null) {
                gridClear();
                gridAdd(body);
            } else {
                move.setVisible(false);
            }
        });
        l0.setOnAction(event -> {
            gridClear();
            cube.rotateSideLeft(0);
            gridAdd(cube.getGameTiles());
        });
        l1.setOnAction(event -> {
            gridClear();
            cube.rotateSideLeft(1);
            gridAdd(cube.getGameTiles());
        });
        l2.setOnAction(event -> {
            gridClear();
            cube.rotateSideLeft(2);
            gridAdd(cube.getGameTiles());
        });
        l3.setOnAction(event -> {
            gridClear();
            cube.rotateSideLeft(3);
            gridAdd(cube.getGameTiles());
        });
        l4.setOnAction(event -> {
            gridClear();
            cube.rotateSideLeft(4);
            gridAdd(cube.getGameTiles());
        });
        l5.setOnAction(event -> {
            gridClear();
            cube.rotateSideLeft(5);
            gridAdd(cube.getGameTiles());
        });
        r0.setOnAction(event -> {
            gridClear();
            cube.rotateSideRight(0);
            gridAdd(cube.getGameTiles());
        });
        r1.setOnAction(event -> {
            gridClear();
            cube.rotateSideRight(1);
            gridAdd(cube.getGameTiles());
        });
        r2.setOnAction(event -> {
            gridClear();
            cube.rotateSideRight(2);
            gridAdd(cube.getGameTiles());
        });
        r3.setOnAction(event -> {
            gridClear();
            cube.rotateSideRight(3);
            gridAdd(cube.getGameTiles());
        });
        r4.setOnAction(event -> {
            gridClear();
            cube.rotateSideRight(4);
            gridAdd(cube.getGameTiles());
        });
        r5.setOnAction(event -> {
            gridClear();
            cube.rotateSideRight(5);
            gridAdd(cube.getGameTiles());
        });
    }

    private void gridClear() {
        gridPane0.setGridLinesVisible(false);
        gridPane1.setGridLinesVisible(false);
        gridPane2.setGridLinesVisible(false);
        gridPane3.setGridLinesVisible(false);
        gridPane4.setGridLinesVisible(false);
        gridPane5.setGridLinesVisible(false);
        gridPane0.getChildren().clear();
        gridPane1.getChildren().clear();
        gridPane2.getChildren().clear();
        gridPane3.getChildren().clear();
        gridPane4.getChildren().clear();
        gridPane5.getChildren().clear();
        gridPane0.setGridLinesVisible(true);
        gridPane1.setGridLinesVisible(true);
        gridPane2.setGridLinesVisible(true);
        gridPane3.setGridLinesVisible(true);
        gridPane4.setGridLinesVisible(true);
        gridPane5.setGridLinesVisible(true);
    }

    private void gridAdd(Tile[][][] gameTiles) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridPane0.add(Tile.getTile(gameTiles[0][i][j].color), j, i);
                gridPane1.add(Tile.getTile(gameTiles[1][i][j].color), j, i);
                gridPane2.add(Tile.getTile(gameTiles[2][i][j].color), j, i);
                gridPane3.add(Tile.getTile(gameTiles[3][i][j].color), j, i);
                gridPane4.add(Tile.getTile(gameTiles[4][i][j].color), j, i);
                gridPane5.add(Tile.getTile(gameTiles[5][i][j].color), j, i);
            }
        }
    }

}
