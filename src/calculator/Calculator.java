package calculator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.ExecutionException;

public class Calculator extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #f1f1f1;");
        Scene scene = new Scene(root, 270, 320);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        JFXTextField aNumber = new JFXTextField();
        aNumber.setPromptText("Number A");
        aNumber.setLabelFloat(true);
        aNumber.setMaxWidth(110);

        JFXTextField bNumber = new JFXTextField();
        bNumber.setPromptText("Number B");
        bNumber.setLabelFloat(true);
        bNumber.setMaxWidth(110);

        HBox hBox = new HBox(aNumber, bNumber);
        hBox.setSpacing(25);

        TextField answer = new TextField();
        answer.setEditable(false);
        answer.setMaxWidth(250);

        //Buttons

        JFXButton add = new JFXButton("+");
        add.setStyle("-fx-background-color: #aeaeae;");
        add.setPrefSize(75, 50);
        add.setOnAction(event -> {
            try {
                if (!aNumber.getText().equals("") && !bNumber.getText().equals(""))
                    answer.setText(Double.toString(Counting.add(
                        Double.parseDouble(aNumber.getText()), 
                        Double.parseDouble(bNumber.getText()))));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        JFXButton subtract = new JFXButton("-");
        subtract.setStyle("-fx-background-color: #aeaeae;");
        subtract.setPrefSize(75, 50);
        subtract.setOnAction(event -> {
            try {
                if (!aNumber.getText().equals("") && !bNumber.getText().equals(""))
                    answer.setText(Double.toString(Counting.sub(Double.parseDouble(aNumber.getText()),
                        Double.parseDouble(bNumber.getText()))));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        JFXButton equals = new JFXButton("==");
        equals.setStyle("-fx-background-color: #aeaeae;");
        equals.setPrefSize(75, 50);
        equals.setOnAction(event -> {
            try {
                if (!aNumber.getText().equals("") && !bNumber.getText().equals(""))
                    answer.setText(Boolean.toString(Counting.equal(Double.parseDouble(aNumber.getText()),
                        Double.parseDouble(bNumber.getText()))));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        HBox hBox1 = new HBox(add, subtract, equals);
        hBox1.setSpacing(10);


        JFXButton multiply = new JFXButton("*");
        multiply.setStyle("-fx-background-color: #aeaeae;");
        multiply.setPrefSize(75, 50);
        multiply.setOnAction(event -> {
            try {
                if (!aNumber.getText().equals("") && !bNumber.getText().equals(""))
                    answer.setText(Double.toString(Counting.mul(Double.parseDouble(aNumber.getText()),
                        Double.parseDouble(bNumber.getText()))));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        JFXButton divide = new JFXButton("/");
        divide.setStyle("-fx-background-color: #aeaeae;");
        divide.setPrefSize(75, 50);
        divide.setOnAction(event -> {
            try {
                if (!aNumber.getText().equals("") && !bNumber.getText().equals(""))
                    answer.setText(Double.toString(Counting.div(Double.parseDouble(aNumber.getText()),
                        Double.parseDouble(bNumber.getText()))));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        JFXButton module = new JFXButton("%");
        module.setStyle("-fx-background-color: #aeaeae;");
        module.setPrefSize(75, 50);
        module.setOnAction(event -> {
            try {
                if (!aNumber.getText().equals("") && !bNumber.getText().equals(""))
                    answer.setText(Integer.toString(Counting.mod(Double.parseDouble(aNumber.getText()),
                        Double.parseDouble(bNumber.getText()))));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        

        HBox hBox2 = new HBox(multiply, divide, module);
        hBox2.setSpacing(10);

        JFXButton more_than = new JFXButton(">");
        more_than.setStyle("-fx-background-color: #aeaeae;");
        more_than.setPrefSize(75, 50);
        more_than.setOnAction(event -> {
            try {
                if (!aNumber.getText().equals("") && !bNumber.getText().equals(""))
                    answer.setText(Boolean.toString(Counting.more(Double.parseDouble(aNumber.getText()),
                        Double.parseDouble(bNumber.getText()))));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });


        JFXButton less_than = new JFXButton("<");
        less_than.setStyle("-fx-background-color: #aeaeae;");
        less_than.setPrefSize(75, 50);
        less_than.setOnAction(event -> {
            try {
                if (!aNumber.getText().equals("") && !bNumber.getText().equals(""))
                    answer.setText(Boolean.toString(Counting.less(Double.parseDouble(aNumber.getText()),
                        Double.parseDouble(bNumber.getText()))));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        JFXButton exit = new JFXButton("exit");
        exit.setStyle("-fx-background-color: #aeaeae;");
        exit.setOnAction(event -> stage.close());
        exit.setPrefSize(75, 50);

        HBox hBox3 = new HBox(more_than, less_than, exit);
        hBox3.setSpacing(10);

        VBox vBox = new VBox(hBox, answer, hBox1, hBox2, hBox3);
        vBox.setTranslateX(10);
        vBox.setTranslateY(20);
        vBox.setSpacing(20);

        root.getChildren().addAll(vBox);

        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
