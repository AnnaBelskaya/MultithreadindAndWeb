package gallery;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static gallery.Gallery.scrollPane;
import static gallery.Gallery.tilePane;
import static gallery.Gallery.root;
import static gallery.Gallery.delete;


public class UserInterface {
    private Random r = new Random();
    private List<Image> imageList;
    private List<Image> loadedList = new ArrayList<>();

    public UserInterface() throws IOException, ExecutionException, InterruptedException {
        imageList = FIleUtils.load();
    }

    public void show() throws IOException {
        loadedList.clear();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setTranslateX(550);
        progressIndicator.setTranslateY(150);
        tilePane.getChildren().add(progressIndicator);
        new Thread(() -> {
            while (imageList.size() < 30){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> tilePane.getChildren().remove(progressIndicator));

            for (int i = 0; i < 25; ) {
                Image image = imageList.get(r.nextInt(imageList.size()));
                if (!loadedList.contains(image)) {
                    i++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loadedList.add(image);
                    Platform.runLater(() -> tilePane.getChildren().add(getImage(image)));
                }
            }
        }).start();
    }

    private ImageView getImage(Image image){
        ImageView thumbnail = crop(new ImageView(image), 225, 225);
        ImageView bigImage = crop(new ImageView(image), 600, 600);

        thumbnail.setOnMouseClicked((MouseEvent mouseEvent) -> {
            delete.setDisable(false);
            delete.setOnMouseClicked(event -> {
                tilePane.getChildren().remove(thumbnail);
                imageList.remove(image);
                root.setCenter(scrollPane);
                delete.setDisable(true);
            });

            bigImage.setOnMouseClicked(event -> {
                root.setCenter(scrollPane);
                delete.setDisable(true);
            });
            root.setCenter(bigImage);
        });

        return thumbnail;
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

    public void addToList(String url) throws ExecutionException, InterruptedException {
        if (!url.equals("")) {
            Image image = new Image(url);
            imageList.add(image);
            tilePane.getChildren().remove(0);
            tilePane.getChildren().add(getImage(image));
            loadedList.remove(0);
            loadedList.add(image);
            FIleUtils.add(url);
        }
    }
}
