package gallery;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Gallery extends Application {
    public static BorderPane root = new BorderPane();
    public static ScrollPane scrollPane = new ScrollPane();
    public static TilePane tilePane = new TilePane();

    @Override
    public void start(Stage stage) throws Exception {
        UserInterface userInterface = new UserInterface();
        tilePane.setPrefColumns(5);
        tilePane.setPadding(new Insets(10, 10, 10, 10));
        tilePane.setVgap(5);
        tilePane.setHgap(5);
        scrollPane.setContent(tilePane);
        root.setStyle("-fx-background-color: black;");

        JFXButton refresh = new JFXButton("Refresh");
        refresh.setStyle("-fx-font-size: 12pt;");
        refresh.setTextFill(Color.LIGHTGRAY);
        refresh.setMinHeight(50);
        refresh.setOnAction(event -> {
            tilePane.getChildren().clear();
            try {
                userInterface.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JFXButton close = new JFXButton("X");
        close.setMinHeight(50);
        close.setMinWidth(50);
        close.setTextFill(Color.LIGHTGRAY);
        close.setStyle("-fx-font-size: 16pt;");
        close.setOnAction(event -> stage.close());

        HBox hBox = new HBox(refresh, close);
        hBox.setSpacing(950);
        hBox.setTranslateX(100);

        root.setTop(hBox);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1200, 600);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

        userInterface.show();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}