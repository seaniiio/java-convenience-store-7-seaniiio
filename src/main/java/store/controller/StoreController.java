package store.controller;

import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService = new StoreService();

    public StoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        storeService.initStore();
        outputView.printWelcomeMessage(storeService.getProducts());
    }
}
