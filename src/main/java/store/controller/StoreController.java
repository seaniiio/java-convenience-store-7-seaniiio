package store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import store.service.PurchaseService;
import store.service.StoreService;
import store.util.InputFormatter;
import store.util.Reader;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final InputFormatter inputFormatter;
    private final StoreService storeService;
    private final PurchaseService purchaseService;

    public StoreController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.inputFormatter = new InputFormatter();
        this.storeService = new StoreService();
        this.purchaseService = new PurchaseService();
    }

    public void run() {
        setStore();

        while (true) {
            processPurchase();
            if (!continueUntilNormalInput(this::processContinueInput)) {
                break;
            }
        }
    }

    private void processPurchase() {
        printStoreInformation();
        processInput();
        purchaseService.supplyPurchases();
        outputView.printReceipt(purchaseService.getPurchasesContent(), purchaseService.getGiftsContent());
    }

    private void setStore() {
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();
        storeService.set(products, promotions);
    }

    private void printStoreInformation() {
        outputView.printWelcomeMessage();
        outputView.printProductsInformation(storeService.getProductsInformation());
    }

    private void processInput() {
        purchaseService.setPurchase(continueUntilNormalInput(this::processPurchaseInput));
        purchaseService.setPurchasePromotionStatus(continueUntilNormalInput(this::processAddQuantityInput));
        purchaseService.setPurchaseConfirmation(continueUntilNormalInput(this::processPurchaseConfirmInput));
        purchaseService.applyMembershipSale(continueUntilNormalInput(this::processMembershipConfirmInput));
    }

    private Map<String, Integer> processPurchaseInput() {
        String purchases = inputView.getProductAndQuantity();
        return inputFormatter.formatPurchaseInput(purchases);
    }

    private Map<String, Boolean> processAddQuantityInput() {
        Map<String, Boolean> purchasePromotionStatus = purchaseService.getPurchasePromotionStatus();

        for (String productName : purchasePromotionStatus.keySet()) {
            String addInput = inputView.inputProductAdd(productName);
            purchasePromotionStatus.replace(productName, inputFormatter.formatIntentionInput(addInput));
        }

        return purchasePromotionStatus;
    }

    private Map<String, Boolean> processPurchaseConfirmInput() {
        Map<String, Integer> promotionStockStatus = purchaseService.getPromotionStockStatus();
        Map<String, Boolean> purchaseConfirm = new HashMap<>();

        for (String productName : promotionStockStatus.keySet()) {
            String confirmInput = inputView.inputPurchaseConfirm(productName, promotionStockStatus.get(productName));
            purchaseConfirm.put(productName, inputFormatter.formatIntentionInput(confirmInput));
        }

        return purchaseConfirm;
    }

    private Boolean processMembershipConfirmInput() {
        String confirm = inputView.inputMembershipSale();
        return inputFormatter.formatIntentionInput(confirm);
    }

    private Boolean processContinueInput() {
        String confirm = inputView.inputPurchaseMore();
        return inputFormatter.formatIntentionInput(confirm);
    }

    private <T> T continueUntilNormalInput(Supplier<T> processInput) {
        while (true) {
            try {
                return processInput.get();
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }
}
