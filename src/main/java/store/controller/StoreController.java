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
        purchaseService.applyPurchases();
        outputView.printReceipt(purchaseService.getPurchasesContent(), purchaseService.getGifts(), purchaseService.getAmounts());
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
        Map<String, Boolean> underPurchasedProducts = purchaseService.getUnderPurchasedProducts();

        for (String productName : underPurchasedProducts.keySet()) {
            String addInput = inputView.inputProductAdd(productName);
            underPurchasedProducts.replace(productName, InputFormatter.formatIntentionInput(addInput));
        }

        purchaseService.addPurchaseQuantity(underPurchasedProducts);
    }

    private void processPurchaseConfirmInput() {
        Map<String, Integer> notAppliedPromotionProducts = purchaseService.getNotAppliedPromotionProducts();
        Map<String, Boolean> purchaseConfirmedProducts = new HashMap<>();

        for (String productName : notAppliedPromotionProducts.keySet()) {
            String confirmInput = inputView.inputPurchaseConfirm(productName, notAppliedPromotionProducts.get(productName));
            purchaseConfirmedProducts.put(productName, InputFormatter.formatIntentionInput(confirmInput));
        }

        purchaseService.setPurchaseConfirmation(purchaseConfirmedProducts);
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
