package store.controller;

import java.util.List;
import store.constant.Command;
import store.dto.LackBuyQuantityProduct;
import store.dto.LackPromotionStockProduct;
import store.dto.Receipt;
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
        do {
            processStore();
        } while (InputProcessor.continueUntilNormalInput(this::processContinue, outputView::printErrorMessage));
    }

    private void processStore() {
        outputView.printWelcomeMessage(storeService.getProducts());
        processOrder();
        InputProcessor.continueUntilNormalInput(this::processBuy, outputView::printErrorMessage);
    }

    private void processOrder() {
        InputProcessor.continueUntilNormalInput(this::processOrderInput, outputView::printErrorMessage);
        InputProcessor.continueUntilNormalInput(this::processLackCondition, outputView::printErrorMessage);
        InputProcessor.continueUntilNormalInput(this::processLackPromotionStock, outputView::printErrorMessage);
    }

    private void processOrderInput() {
        String orderInput = inputView.orderInput();
        orderService.setOrders(orderInput);
        orderService.checkStock();
    }

    private void processLackCondition() {
        List<LackBuyQuantityProduct> products = orderService.checkPromotionCondition();
        for (LackBuyQuantityProduct product : products) {
            InputProcessor.continueUntilNormalInput(this::processAddBuyQuantity, outputView::printErrorMessage, product);
        }

        orderService.addBuyQuantity(products);
    }

    private void processLackPromotionStock() {
        List<LackPromotionStockProduct> products = orderService.checkPromotionStock();
        for (LackPromotionStockProduct product : products) {
            InputProcessor.continueUntilNormalInput(this::processBuyConfirm, outputView::printErrorMessage, product);
        }
        orderService.setBuyConfirm(products);
    }

    private void processBuy() {
        String input = inputView.membershipConfirmInput();
        Command command = Command.findCommand(input);
        Receipt receipt = orderService.buy(command);
        outputView.printReceipt(receipt);
    }

    private void processAddBuyQuantity(LackBuyQuantityProduct product) {
        String input = inputView.addBuyInput(product);
        Command command = Command.findCommand(input);
        if (command.equals(Command.YES)) {
            product.setPurchase();
        }
    }

    private void processBuyConfirm(LackPromotionStockProduct product) {
        String input = inputView.buyConfirmInput(product);
        Command command = Command.findCommand(input);
        if (command.equals(Command.YES)) {
            product.confirmToBuy();
        }
    }

    private boolean processContinue() {
        String input = inputView.continueInput();
        Command command = Command.findCommand(input);
        return command.equals(Command.YES);
    }
}
