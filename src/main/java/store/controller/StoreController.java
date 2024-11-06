package store.controller;

import java.util.List;
import store.service.StoreService;
import store.util.Reader;

public class StoreController {

    private final StoreService storeService;

    public StoreController() {
        this.storeService = new StoreService();
    }

    public void run() {
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();

        storeService.set(products, promotions);
    }
}
