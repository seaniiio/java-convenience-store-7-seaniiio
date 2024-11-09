package store.service;

import java.util.List;
import java.util.Map;
import store.domain.Purchases;
import store.dto.AmountsDto;
import store.dto.GiftsDto;
import store.dto.PurchasedProductsDto;

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

    public List<PurchasedProductsDto> getPurchasesContent() {
        return purchases.purchasesContent();
    }

    public List<GiftsDto> getGifts() {
        return purchases.getGiftsContent();
    }

    public List<AmountsDto> getAmounts() {
        return purchases.getAmounts();
    }
}
