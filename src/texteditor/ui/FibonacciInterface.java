package texteditor.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import texteditor.utils.FileUtils;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FibonacciInterface {
    protected TextArea textArea = new TextArea();
    protected TextArea fibonacci_area = new TextArea();
    protected static boolean isStopped = false;
    protected static boolean isCancelled = false;
    protected JFXTextField x_input = new JFXTextField();
    protected JFXButton generate = new JFXButton("Generate");
    protected JFXButton stop = new JFXButton("Stop");
    protected JFXButton cancel = new JFXButton("Cancel");
    protected JFXButton save_fib = new JFXButton("Save sequence");
    protected Label state = new Label("State: NOT RUNNING");
    protected HBox fibonacci_box = new HBox(state, x_input, generate, stop,
            cancel, save_fib);

    public FibonacciInterface() {
        setElements();
    }

    protected void setElements(){
        state.setTextFill(Color.WHITE);
        state.setStyle("-fx-font-size: 12pt");
        state.setPrefSize(300,40);

        x_input.setPromptText("Index of the number");
        x_input.setLabelFloat(true);
        x_input.setStyle("-fx-text-fill: white;");
        x_input.setFocusColor(Color.WHITESMOKE);
        x_input.setUnFocusColor(Color.WHITESMOKE);
        x_input.setPrefSize(150,30);
        x_input.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!"0123456789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });

        generate.setPrefSize(150,40);
        generate.setTextFill(Color.WHITE);
        generate.setStyle("-fx-font-size: 12pt;");
        generate.setOnAction(event -> {
            if (x_input.getText().equals("")){
                FileUtils.showPopup("You should enter the index first.");
            } else {
                fibonacci_area.clear();
                new Thread(() -> {
                    try {
                        fibonacci_area.setText(findNumber(Long.parseLong(x_input.getText())));
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        cancel.setPrefSize(150,40);
        cancel.setDisable(true);
        cancel.setTextFill(Color.WHITE);
        cancel.setStyle("-fx-font-size: 12pt;");
        cancel.setOnAction(event -> {
            isCancelled = true;
            isStopped = true;
        });

        stop.setPrefSize(150,40);
        stop.setDisable(true);
        stop.setStyle("-fx-font-size: 12pt;");
        stop.setTextFill(Color.WHITE);
        stop.setOnAction(event->{
            isStopped = true;
        });

        save_fib.setPrefSize(150,40);
        save_fib.setTextFill(Color.WHITE);
        save_fib.setStyle("-fx-font-size: 12pt;");
        save_fib.setOnAction(event -> {
            String text = fibonacci_area.getText().replace("\n", System.getProperty("line.separator"));
            FileUtils.save_as(text);
        });

        fibonacci_box.setMaxHeight(50);
    }

    public String findNumber(long index) throws ExecutionException, InterruptedException {
        isStopped = false;
        isCancelled = false;
        FutureTask<String> task = new FutureTask<>(()->{
            Platform.runLater(()->{
                state.setText("STATE: running");
                generate.setDisable(true);
                save_fib.setDisable(true);
                stop.setDisable(false);
                cancel.setDisable(false);
            });
            String result = "0 - 0\n";
            long i = 2;
            if (index >= 1)
                result+=("1 - 1\n") ;
            BigInteger n1 = BigInteger.ZERO, n2 = BigInteger.ONE, x = n2;
            while (i++ < index && !isStopped) {
                x = n1.add(n2);
                n1 = n2;
                n2 = x;
                result+=(i + " - " + x + "\n") ;
                final long I = i;
                Platform.runLater(()-> state.setText("STATE: counting " + I));
            }

            Platform.runLater(()->{
                state.setText("STATE: not running");
                generate.setDisable(false);
                save_fib.setDisable(false);
                cancel.setDisable(true);
                stop.setDisable(true);
            });

            if (isStopped)
                Platform.runLater(()->{
                    state.setText("STATE: stopped");
                });

            if (isCancelled) {
                result = "";
                Platform.runLater(()->{
                    state.setText("STATE: cancelled");
                });
            }

            return result;
        });

        new Thread(task).start();

        return task.get();
    }
}