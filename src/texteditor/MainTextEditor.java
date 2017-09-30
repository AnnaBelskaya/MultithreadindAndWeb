package texteditor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import texteditor.ui.UserInterface;

public class MainTextEditor extends Application {
    public static BorderPane root = new BorderPane();
    public static Stage window;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        root.setStyle("-fx-background-color: #515151;");

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        window = stage;
        window.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 1000, 600);
        window.setScene(scene);
        window.setResizable(false);
        window.show();

        new UserInterface();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
