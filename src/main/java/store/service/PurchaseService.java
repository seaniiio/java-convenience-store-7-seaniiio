package store.service;

import java.util.Map;
import store.domain.Purchases;
import store.util.InputFormatter;

public class PurchaseService {

    private final InputFormatter inputFormatter;
    private final Purchases purchases;

    public PurchaseService() {
        this.inputFormatter = new InputFormatter();
        this.purchases = new Purchases();
    }

    public void setPurchase(String productAndQuantity) {
        Map<String, Integer> purchasesInput = inputFormatter.formatPurchaseInput(productAndQuantity);
        Purchases.createPurchases(purchasesInput);
    }

    public void supplyPurchases() {
        purchases.supplyPurchases();
    }

    public Map<String, Boolean> getPurchasePromotionStatus() {
        return purchases.getPurchasePromotionStatus();
    }

    public void setPurchasePromotionStatus(Map<String, Boolean> status) {
        purchases.setPurchasePromotionStatus(status);
    }

    public Map<String, Integer> getPromotionStockStatus() {
        return purchases.getPromotionStockStatus();
    }

    public void setPurchaseConfirmation(Map<String, Boolean> status) {
        purchases.setPurchaseConfirmation(status);
    }
}
