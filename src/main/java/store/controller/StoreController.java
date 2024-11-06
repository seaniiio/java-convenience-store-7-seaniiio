package store.controller;

import java.util.List;
import store.util.Reader;

public class StoreController {

    public void run() {
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();
    }
}
