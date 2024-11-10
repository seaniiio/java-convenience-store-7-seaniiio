package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";

    public static List<String> readProducts() {
        return readFile(PRODUCTS_FILE_PATH);
    }

    public static List<String> readPromotions() {
        return readFile(PROMOTIONS_FILE_PATH);
    }

    private static List<String> readFile(String filePath) {
        List<String> contents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                contents.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return contents;
    }
}
