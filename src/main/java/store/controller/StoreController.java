package store.controller;

import static store.util.InputProcessor.*;

import java.util.HashMap;
import java.util.Map;
import store.service.PurchaseService;
import store.service.StoreService;
import store.util.InputFormatter;
import store.util.Reader;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;
    private PurchaseService purchaseService;

    public StoreController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.storeService = new StoreService();
    }

    public void run() {
        setStore();

        while (true) {
            this.processPurchase();
            if (!continueUntilNormalInput(this::processContinueInput)) {
                break;
            }
        }
    }

    private void setStore() {
        storeService.set(Reader.readProducts(), Reader.readPromotions());
    }

    private void processPurchase() {
        this.purchaseService = new PurchaseService();
        printStoreInformation();
        processInput();
        purchaseService.supplyPurchases();
        outputView.printReceipt(purchaseService.getPurchasesContent(), purchaseService.getGiftsContent());
    }

    private void printStoreInformation() {
        outputView.printWelcomeMessage();
        outputView.printProductsInformation(storeService.getProductsInformation());
    }

    private void processInput() {
        continueUntilNormalInput(this::processPurchaseInput);
        continueUntilNormalInput(this::processAddQuantityInput);
        continueUntilNormalInput(this::processPurchaseConfirmInput);
        continueUntilNormalInput(this::processMembershipConfirmInput);
    }

    private void processPurchaseInput() {
        String purchases = inputView.getProductAndQuantity();
        purchaseService.setPurchase(InputFormatter.formatPurchaseInput(purchases));
    }

    private void processAddQuantityInput() {
        Map<String, Boolean> purchasePromotionStatus = purchaseService.getPurchasePromotionStatus();

        for (String productName : purchasePromotionStatus.keySet()) {
            String addInput = inputView.inputProductAdd(productName);
            purchasePromotionStatus.replace(productName, InputFormatter.formatIntentionInput(addInput));
        }

        purchaseService.setPurchasePromotionStatus(purchasePromotionStatus);
    }

    private void processPurchaseConfirmInput() {
        Map<String, Integer> promotionStockStatus = purchaseService.getPromotionStockStatus();
        Map<String, Boolean> purchaseConfirm = new HashMap<>();

        for (String productName : promotionStockStatus.keySet()) {
            String confirmInput = inputView.inputPurchaseConfirm(productName, promotionStockStatus.get(productName));
            purchaseConfirm.put(productName, InputFormatter.formatIntentionInput(confirmInput));
        }

        purchaseService.setPurchaseConfirmation(purchaseConfirm);
    }

    private void processMembershipConfirmInput() {
        String confirm = inputView.inputMembershipSale();
        purchaseService.applyMembershipSale(InputFormatter.formatIntentionInput(confirm));
    }

    private Boolean processContinueInput() {
        String confirm = inputView.inputPurchaseMore();
        return InputFormatter.formatIntentionInput(confirm);
    }
}
