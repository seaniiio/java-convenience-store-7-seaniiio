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

    public void applyPurchases() {
        purchases.applyPurchases();
    }

    public Map<String, Boolean> getUnderPurchasedProducts() {
        return purchases.getUnderPurchasedProducts();
    }

    public void addPurchaseQuantity(Map<String, Boolean> products) {
        purchases.addPurchaseQuantity(products);
    }

    public Map<String, Integer> getNotAppliedPromotionProducts() {
        return purchases.getNotAppliedPromotionProducts();
    }

    public void setPurchaseConfirmation(Map<String, Boolean> status) {
        purchases.setPurchaseConfirmation(status);
    }

    public void applyMembershipSale(boolean confirm) {
        purchases.applyMembershipSale(confirm);
    }

    public List<PurchasedProductsDto> getPurchasesContent() {
        return purchases.getPurchasesContent();
    }

    public List<GiftsDto> getGifts() {
        return purchases.getGiftsContent();
    }

    public List<AmountsDto> getAmounts() {
        return purchases.getAmounts();
    }
}
