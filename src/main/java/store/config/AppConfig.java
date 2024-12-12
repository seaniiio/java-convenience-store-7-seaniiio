package store.config;

import store.controller.StoreController;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {

    public StoreController storeController() {
        return new StoreController(inputView(), outputView());
    }

    private InputView inputView() {
        return new InputView();
    }

    private OutputView outputView() {
        return new OutputView();
    }
}
