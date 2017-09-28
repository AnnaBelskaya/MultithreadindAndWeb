package gallery;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static gallery.Gallery.*;

public class MyImages {
    private Image image;
    private ImageView thumbnail;
    private ImageView fullSize;

    public MyImages(String url) {
        image = new javafx.scene.image.Image(url);
        thumbnail = crop(225);
        fullSize = crop(700);
        setActions();
    }

    private ImageView crop(int a){
        ImageView imageView = new ImageView(image);
        double newMeasure = (imageView.getImage().getWidth() < imageView.getImage().getHeight())
                ? imageView.getImage().getWidth()
                : imageView.getImage().getHeight();
        double x = (imageView.getImage().getWidth() - newMeasure) / 2;
        double y = (imageView.getImage().getHeight() - newMeasure) / 2;
        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        imageView.setViewport(rect);
        imageView.setFitWidth(a);
        imageView.setFitHeight(a);
        imageView.setSmooth(true);
        return imageView;
    }

    private void setActions(){
        fullSize.setOnMouseClicked(event -> {
            root.setCenter(scrollPane);
        });

        thumbnail.setOnMouseClicked(event -> {
            root.setCenter(fullSize);
        });
    }

    public ImageView getThumbnail() {
        return thumbnail;
    }

    public ImageView getFullSize() {
        return fullSize;
    }
}
