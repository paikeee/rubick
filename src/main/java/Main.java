import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;


public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("rubick.fxml"));
        stage.setTitle("Rubick");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 600, 700));
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}