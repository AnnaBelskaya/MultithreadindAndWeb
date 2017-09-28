package gallery;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class UserInterface {
    private boolean isOpened = false;
    private Random r = new Random();
    private List<Image> imageList;

    public UserInterface() throws IOException, ExecutionException, InterruptedException {
        imageList = FIleUtils.load();
    }

    public void show() throws IOException {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setTranslateX(550);
        progressIndicator.setTranslateY(150);
        Gallery.tilePane.getChildren().add(progressIndicator);
        new Thread(() -> {
            while (imageList.size() < 10){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> Gallery.tilePane.getChildren().remove(progressIndicator));

            for (int i = 0; i < 25; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> Gallery.tilePane.getChildren().add(getImage()));
            }
        }).start();
    }

    private ImageView getImage(){

        ImageView imageView = new ImageView(imageList.get(r.nextInt(imageList.size())));
        double newMeasure = (imageView.getImage().getWidth() < imageView.getImage().getHeight())
                ? imageView.getImage().getWidth()
                : imageView.getImage().getHeight();
        double x = (imageView.getImage().getWidth() - newMeasure) / 2;
        double y = (imageView.getImage().getHeight() - newMeasure) / 2;
        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        imageView.setViewport(rect);
        imageView.setFitWidth(225);
        imageView.setFitHeight(225);
        imageView.setSmooth(true);

        imageView.setOnMouseClicked(event -> {
            if (!isOpened) {
                imageView.setFitWidth(500);
                imageView.setPreserveRatio(true);
                Gallery.root.setCenter(imageView);
                isOpened = true;
            } else {
                isOpened = false;
                Gallery.root.setCenter(Gallery.scrollPane);
            }
        });

        return imageView;
    }

    private ImageView crop(ImageView imageView, int w, int h){
        double newMeasure = (imageView.getImage().getWidth() < imageView.getImage().getHeight())
                ? imageView.getImage().getWidth()
                : imageView.getImage().getHeight();
        double x = (imageView.getImage().getWidth() - newMeasure) / 2;
        double y = (imageView.getImage().getHeight() - newMeasure) / 2;
        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        imageView.setViewport(rect);
        imageView.setFitWidth(w);
        imageView.setFitHeight(h);
        imageView.setSmooth(true);
        return imageView;
    }
}
