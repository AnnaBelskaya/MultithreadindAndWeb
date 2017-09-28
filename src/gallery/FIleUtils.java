package gallery;

import javafx.scene.image.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FIleUtils {
    private static String file_name = "files/images.txt";
    private static List<Image> imageList = new ArrayList<>();

    private FIleUtils(){}

    public static List<Image> load(){
        new Thread(()->{
            try (BufferedReader br = new BufferedReader(new FileReader(file_name))) {
                String line;
                while ((line = br.readLine()) != null) {
                    imageList.add(new Image(line));
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return imageList;
    }
}
