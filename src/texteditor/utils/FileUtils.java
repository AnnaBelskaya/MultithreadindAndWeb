package texteditor.utils;

import com.jfoenix.controls.JFXPopup;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static texteditor.MainTextEditor.root;
import static texteditor.MainTextEditor.window;

public class FileUtils {
    private static File file = new File("");
    private static FileWriter fileWriter;
    private static FileChooser chooser = new FileChooser();

    private FileUtils(){}

    public static void new_file(){
        file = new File("");
        showPopup("New file.");
    }

    public static String open_file() {
        setChooser();
        FutureTask<String> futureTask;
        String text = "";
        file = chooser.showOpenDialog(window);
        try {
            futureTask = new FutureTask<>(()-> new String(Files.readAllBytes(Paths.get(file.getPath()))));
            if (!file.getPath().equals("")){
                new Thread(futureTask).start();
                text = futureTask.get();
                window.setTitle(file.getName());
                showPopup("The file is successfully opened.");
            }
        } catch (NullPointerException e){
            showPopup("The file is not chosen!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            showPopup("Oops... Something went wrong.");
        }
        return text;
    }


    public static void save_file(String text)  {
        setChooser();
        if (file.getPath().equals("")){
            save_as(text);
        } else {
            save(text);
        }
    }

    public static void save_as(String text) {
        setChooser();
        boolean isChosen = false;
        try {
            file = chooser.showSaveDialog(window);
            if (!file.getPath().equals(""))
                isChosen = true;
        } catch (NullPointerException e) {
            showPopup("The file is not chosen!");
        }
        if (isChosen) {
            save(text);
        }
    }

    private static void save(String text){
        new Thread(() -> {
            try {
                fileWriter = new FileWriter(file);
                fileWriter.write(text);
                fileWriter.close();
                showPopup("The file is successfully saved.");
            } catch (IOException | NullPointerException ex ) {
                ex.printStackTrace();
                showPopup("Oops... Something went wrong.");
            }
        }).start();
    }

    private static void setChooser(){
        chooser.getExtensionFilters()
                .addAll(
                        new FileChooser.ExtensionFilter("TXT files (*.TXT)", "*.TXT"),
                        new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"));
    }

    public static void showPopup(String message){
        new Thread(()->{
            JFXPopup popup = new JFXPopup();
            popup.setAutoFix(true);
            popup.setAutoHide(true);
            Label label = new Label(message);
            label.setMinSize(120,50);
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-background-color: rgba(92,128,38,0.40);" +
                    "-fx-font-size: 16;");
            popup.setPopupContent(label);

            Platform.runLater(()->popup.show(root,
                    JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.RIGHT));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}