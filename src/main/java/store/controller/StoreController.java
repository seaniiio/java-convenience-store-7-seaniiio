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
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();
        storeService.set(products, promotions);

        while (true) {
            printInformation();
            processInput();
            purchaseService.supplyPurchases();

            outputView.printReceipt(purchaseService.getPurchasesContent(), purchaseService.getGiftsContent());

            if (continueUntilNormalInput(this::processContinueInput)) {
                continue;
            }
            break;
        }
    }

    private void printInformation() {
        outputView.printWelcomeMessage();
        outputView.printProductsInformation(storeService.getProductsInformation());
    }

    private void processInput() {
        continueUntilNormalInput(this::processPurchaseInput);
        continueUntilNormalInput(this::processPromotionAddInput);
        continueUntilNormalInput(this::processPurchaseConfirmInput);
        continueUntilNormalInput(this::processMembershipConfirmInput);
    }

    private void processPurchaseInput() {
        String purchases = inputView.getProductAndQuantity();
        Map<String, Integer> purchasesInput = inputFormatter.formatPurchaseInput(purchases);
        purchaseService.setPurchase(purchasesInput);
    }

    private void processMembershipConfirmInput() {
        String confirm = inputView.inputMembershipSale();
        purchaseService.applyMembershipSale(inputFormatter.formatIntentionInput(confirm));
    }

    private void processPromotionAddInput() {
        // 현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
        Map<String, Boolean> purchasePromotionStatus = purchaseService.getPurchasePromotionStatus();
        for (String productName : purchasePromotionStatus.keySet()) {
            String addInput = inputView.inputProductAdd(productName);

            if (inputFormatter.formatIntentionInput(addInput)) {
                purchasePromotionStatus.replace(productName, true);
            }
        }
        purchaseService.setPurchasePromotionStatus(purchasePromotionStatus);
    }

    private void processPurchaseConfirmInput() {
        // 현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
        Map<String, Integer> promotionStockStatus = purchaseService.getPromotionStockStatus();
        Map<String, Boolean> purchaseConfirm = new HashMap<>();
        for (String productName : promotionStockStatus.keySet()) {
            String confirmInput = inputView.inputPurchaseConfirm(productName, promotionStockStatus.get(productName));

            if (!inputFormatter.formatIntentionInput(confirmInput)) {
                purchaseConfirm.put(productName, false);
            }
        }
        purchaseService.setPurchaseConfirmation(purchaseConfirm);
    }

    private Boolean processContinueInput() {
        String confirm = inputView.inputPurchaseMore();
        return inputFormatter.formatIntentionInput(confirm);
    }

    private void continueUntilNormalInput(Runnable processSpecificInput) {
        while (true) {
            try {
                processSpecificInput.run();
                break;
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }

    private <T> T continueUntilNormalInput(Supplier<T> processSpecificInput) {
        while (true) {
            try {
                return processSpecificInput.get();
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }
}
