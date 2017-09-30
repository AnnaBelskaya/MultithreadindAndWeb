package texteditor.ui;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import texteditor.utils.FileUtils;

import static texteditor.MainTextEditor.root;
import static texteditor.MainTextEditor.window;

public class UserInterface extends FibonacciInterface {
    public UserInterface() { }

    protected void setElements(){
        super.setElements();
        textArea.setStyle("-fx-font-size: 14pt;");
        textArea.setMaxWidth(500);

        fibonacci_area.setStyle("-fx-font-size: 14pt;");
        fibonacci_area.setMaxWidth(500);
        fibonacci_area.setEditable(false);

        JFXButton new_file = new JFXButton("New");
        new_file.setStyle("-fx-font-size: 12pt;");
        new_file.setPrefSize(150,40);
        new_file.setTextFill(Color.WHITE);
        new_file.setOnAction(event -> {
            textArea.clear();
            FileUtils.new_file();
        });

        JFXButton open = new JFXButton("Open");
        open.setStyle("-fx-font-size: 12pt;");
        open.setPrefSize(150,40);
        open.setTextFill(Color.WHITE);
        open.setOnAction(event -> {
            textArea.setText(FileUtils.open_file());
        });

        JFXButton save = new JFXButton("Save");
        save.setStyle("-fx-font-size: 12pt;");
        save.setTextFill(Color.WHITE);
        save.setPrefSize(150,40);
        save.setOnAction(event->{
            String text = textArea.getText().replace("\n", System.getProperty("line.separator"));
            FileUtils.save_file(text);
        });

        JFXButton save_as = new JFXButton("Save as...");
        save_as.setStyle("-fx-font-size: 12pt;");
        save_as.setTextFill(Color.WHITE);
        save_as.setPrefSize(150,40);
        save_as.setOnAction(event -> {
            String text = textArea.getText().replace("\n", System.getProperty("line.separator"));
            FileUtils.save_as(text);
        });

        JFXButton exit = new JFXButton("Exit");
        exit.setStyle("-fx-font-size: 12pt;");
        exit.setTextFill(Color.WHITE);
        exit.setPrefSize(150,40);
        exit.setOnAction(event -> {
            isCancelled = true;
            window.close();
        });


        HBox textEditor_box = new HBox(new_file, open, save, save_as, exit);
        textEditor_box.setSpacing(50);
        textEditor_box.setPadding(new Insets(10,10,10,10));

        VBox vBox = new VBox(textEditor_box, fibonacci_box);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10,10,10,10));
        root.setTop(vBox);
        root.setLeft(textArea);
        root.setRight(fibonacci_area);
    }
}