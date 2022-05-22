import javafx.scene.layout.*;

public class Tile {

    char color;
    static char[] colors = {'O', 'G', 'W', 'B', 'R', 'Y'};

    public Tile() {

    }

    public static Pane getTile(char current) {
        Pane pane = new Pane();
        pane.setPrefSize(45, 45);
        switch (current) {
            case 'W' -> {
                pane.setStyle("-fx-background-color: White;");
                return pane;
            }
            case 'R' -> {
                pane.setStyle("-fx-background-color: Red;");
                return pane;
            }
            case 'O' -> {
                pane.setStyle("-fx-background-color: Orange;");
                return pane;
            }
            case 'Y' -> {
                pane.setStyle("-fx-background-color: Yellow;");
                return pane;
            }
            case 'G' -> {
                pane.setStyle("-fx-background-color: Green;");
                return pane;
            }
            case 'B' -> {
                pane.setStyle("-fx-background-color: Blue;");
                return pane;
            }
            default -> {
                pane.setStyle("-fx-background-color: Black;");
                return pane;
            }
        }
    }

}
