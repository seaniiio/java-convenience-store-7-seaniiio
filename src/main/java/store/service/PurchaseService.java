package store.service;

import java.util.List;
import java.util.Map;
import store.domain.Purchases;

public class PurchaseService {

    private Purchases purchases;

    public void setPurchase(Map<String, Integer> purchasesInput) {
        this.purchases = new Purchases(purchasesInput);
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
        purchases.applyMembershipSale(confirm);
    }

    public List<String> getPurchasesContent() {
        return purchases.purchasesContent();
    }

    public Map<String, Integer> getGiftsContent() {
        return purchases.getGiftsContent();
    }
}
