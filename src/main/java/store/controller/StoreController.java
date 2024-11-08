package store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.service.PurchaseService;
import store.service.StoreService;
import store.util.Reader;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;
    private final PurchaseService purchaseService;

    public StoreController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.storeService = new StoreService();
        this.purchaseService = new PurchaseService();
    }

    public void run() {
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();

        storeService.set(products, promotions);

        outputView.printWelcomeMessage();
        outputView.printProductsInformation(storeService.getProductsInformation());

        continueUntilNormalInput(() -> purchaseService.setPurchase(inputView.getProductAndQuantity()));

        Map<String, Boolean> purchasePromotionStatus = purchaseService.getPurchasePromotionStatus(); // 현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
        for (String productName : purchasePromotionStatus.keySet()) {
            String addInput = inputView.inputProductAdd(productName);
            if (addInput.equals("Y")) {
                purchasePromotionStatus.replace(productName, true);
            }
        }
        purchaseService.setPurchasePromotionStatus(purchasePromotionStatus);

        Map<String, Integer> promotionStockStatus = purchaseService.getPromotionStockStatus(); // 현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
        Map<String, Boolean> purchaseConfirm = new HashMap<>();
        for (String productName : promotionStockStatus.keySet()) {
            String addInput = inputView.inputPurchaseConfirm(productName, promotionStockStatus.get(productName));
            if (addInput.equals("N")) {
                purchaseConfirm.put(productName, false);
            }
        }
        purchaseService.setPurchaseConfirmation(purchaseConfirm);

        purchaseService.applyMembershipSale(inputView.inputMembershipSale());

        purchaseService.supplyPurchases();
        outputView.printReceipt();
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
}
