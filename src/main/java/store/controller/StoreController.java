package store.controller;

import java.util.List;
import store.service.StoreService;
import store.util.Reader;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;

    public StoreController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.storeService = new StoreService();
    }

    public void run() {
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();

        storeService.set(products, promotions);

        outputView.printWelcomeMessage();
        outputView.printProductsInformation(storeService.getProductsInformation());

        storeService.setPurchase(inputView.getProductAndQuantity());
    }
}
