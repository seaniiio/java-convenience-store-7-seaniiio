package store.service;

import java.util.List;
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
        this.purchases.setPurchases(purchasesInput);
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

    public void applyMembershipSale(boolean confirm) {
        if (confirm) {
            purchases.applyMembershipSale();
        }
    }

    public List<String> getPurchasesContent() {
        return purchases.purchasesContent();
    }

    public Map<String, Integer> getGiftsContent() {
        return purchases.getGiftsContent();
    }

    public List<String> getAmountsContent() {
        return purchases.getAmountsContent();
    }
}
