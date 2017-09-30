package gallery;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import java.util.concurrent.ExecutionException;

public class Gallery extends Application {
    public static BorderPane root = new BorderPane();
    public static ScrollPane scrollPane = new ScrollPane();
    public static TilePane tilePane = new TilePane();
    public static JFXButton slideshow = new JFXButton("Slideshow");

    @Override
    public void start(Stage stage) throws Exception {
        GalleryInterface userInterface = new GalleryInterface();
        tilePane.setPrefColumns(5);
        tilePane.setPadding(new Insets(10, 10, 10, 10));
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        scrollPane.setContent(tilePane);
        root.setStyle("-fx-background-color: black;");

        //set elements

        JFXTextField url = new JFXTextField();
        url.setPromptText("URL");
        url.setLabelFloat(true);
        url.setFocusColor(Color.WHITE);
        url.setUnFocusColor(Color.WHITE);
        url.setStyle("-fx-text-fill: whitesmoke;");
        url.setMinWidth(300);
        url.setTranslateY(15);

        JFXButton refresh = new JFXButton("Refresh");
        refresh.setStyle("-fx-font-size: 12pt;");
        refresh.setTextFill(Color.LIGHTGRAY);
        refresh.setMinHeight(50);
        refresh.setOnAction(event -> {
            tilePane.getChildren().clear();
            try {
                userInterface.showGallery();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JFXButton addImage = new JFXButton("Add image");
        addImage.setMinHeight(50);
        addImage.setMinWidth(50);
        addImage.setTextFill(Color.LIGHTGRAY);
        addImage.setStyle("-fx-font-size: 12pt;");
        addImage.setOnAction(event -> {
            try {
                userInterface.addNewImage(url.getText());
                url.clear();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        slideshow.setStyle("-fx-font-size: 12pt;");
        slideshow.setTextFill(Color.LIGHTGRAY);
        slideshow.setMinHeight(50);
        slideshow.setOnAction(event -> userInterface.slideshow());

        JFXButton close = new JFXButton("Exit");
        close.setMinHeight(50);
        close.setMinWidth(50);
        close.setTextFill(Color.LIGHTGRAY);
        close.setStyle("-fx-font-size: 12pt;");
        close.setOnAction(event -> {
            userInterface.stopSlides();
            stage.close();
        });

        HBox hBox = new HBox(refresh, url, addImage, slideshow, close);
        hBox.setSpacing(50);
        hBox.setTranslateX(50);

        root.setTop(hBox);
        root.setCenter(scrollPane);
        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        userInterface.showGallery();
    }

    public static void main(String[] args) {
        launch(args);
    }
}