package gallery;

import javafx.scene.image.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FIleUtils {
    private static ExecutorService threads = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static String file_name = "files/images.txt";

    private FIleUtils(){}

    public static List<String> load() {
        List<String> urlsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file_name))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String LINE = line;
                threads.submit(()->{
                    urlsList.add(LINE);
                });
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        threads.shutdown();
        return urlsList;
    }

    public static void add(String url) throws ExecutionException, InterruptedException {
        new Thread(()->{
            try {
                Files.write(Paths.get(file_name), ("\n"+url).getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
