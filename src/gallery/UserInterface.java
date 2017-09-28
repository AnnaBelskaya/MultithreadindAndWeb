package gallery;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static gallery.Gallery.*;

public class UserInterface {
    private boolean slides = true;
    private Random r = new Random();
    private List<String> urlsList;
    private List<String> loadedImages = new ArrayList<>();
    private List<MyImages> myImagesList = new ArrayList<>();

    public UserInterface() throws IOException, ExecutionException, InterruptedException {
        urlsList = FIleUtils.load();
    }

    public void showGallery() throws IOException {
        tilePane.getChildren().clear();
        loadedImages.clear();
        myImagesList.clear();

        new Thread(() -> {
            while (urlsList.size() < 30){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 25; ) {
                String url = urlsList.get(r.nextInt(urlsList.size()));
                if (!loadedImages.contains(url)) {
                    ProgressIndicator progressInd = new ProgressIndicator(0);
                    progressInd.setMaxSize(50,50);
                    Platform.runLater(() -> tilePane.getChildren()
                            .add(progressInd));
                    Task task = createImage(url);
                    progressInd.progressProperty().bind(task.progressProperty());
                    task.setOnSucceeded(e -> {
                        progressInd.progressProperty().unbind();
                        progressInd.setProgress(1);
                        Platform.runLater(() -> tilePane.getChildren()
                                .remove(progressInd));
                    });
                    task.setOnFailed(e -> task.getException().printStackTrace());
                    Thread t = new Thread(task);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        }).start();
    }

    public Task createImage(String url) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                MyImages myImages = new MyImages(url);
                loadedImages.add(url);
                myImagesList.add(myImages);
                Platform.runLater(() -> tilePane.getChildren()
                        .add(myImages.getThumbnail()));
                return null;
            }
        };
    }

    public void addNewImage(String url) throws ExecutionException, InterruptedException {
        if (url.equals(""))
            return;
        MyImages myImages = new MyImages(url);
        urlsList.add(url);
        loadedImages.add(url);
        myImagesList.add(myImages);
        tilePane.getChildren().remove(0);
        tilePane.getChildren().add(myImages.getThumbnail());
        FIleUtils.add(url);
    }

    public void slideshow(){
        if (slides) {
            slides = !slides;
            slideshow.setText("Stop");
            new Thread(() -> {
                for (int i = 0; i < myImagesList.size(); i++) {
                    final int INDEX = i;
                    Platform.runLater(()->root.setCenter(myImagesList.get(INDEX).getFullSize()));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            slides = !slides;
            slideshow.setText("Slideshow");
            root.setCenter(scrollPane);
        }
    }
}