package store.controller;

import store.service.OrderService;
import store.service.StoreService;
import store.util.InputProcessor;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService = new StoreService();
    private final OrderService orderService = new OrderService();

    public StoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        storeService.initStore();
        outputView.printWelcomeMessage(storeService.getProducts());

        InputProcessor.continueUntilNormalInput(this::processOrder, outputView::printErrorMessage);

    }

    private void processOrder() {
        String orderInput = inputView.orderInput();
        orderService.setOrders(orderInput);
    }
}
