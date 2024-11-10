package store.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.dto.AmountsDto;
import store.dto.GiftsDto;
import store.dto.PurchasedProductsDto;

public class Purchases {

    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private static final int MEMBERSHIP_DISCOUNT_MAXIMUM = 8000;

    private List<Purchase> purchases;
    private boolean membershipConfirmation = false;
    private int totalQuantity = 0;
    private int totalAmount = 0;
    private int promotionDiscountAmount = 0;
    private int membershipDiscountAmount = 0;
    private int totalPayAmount = 0;

    public Purchases(Map<String, Integer> rawPurchases) {
        purchases = new ArrayList<>();
        for (String productName : rawPurchases.keySet()) {
            purchases.add(Purchase.createPurchase(productName, rawPurchases.get(productName)));
        }
    }

    public Map<String, Boolean> getUnderPurchasedProducts() {
        Map<String, Boolean> status = new HashMap<>();
        for (Purchase purchase : purchases) {
            if (purchase.isUnderPromotionQuantity()) {
                status.put(purchase.getProductName(), false);
            }
        }

        return status;
    }

    public void addPurchaseQuantity(Map<String, Boolean> status) {
        for (String productName : status.keySet()) {
            if (status.get(productName)) {
                Purchase purchase = getPurchase(productName);
                purchase.addQuantityForPromotion();
            }
        }
    }

    public Map<String, Integer> getNotAppliedPromotionProducts() {
        Map<String, Integer> status = new HashMap<>();
        for (Purchase purchase : purchases) {
            int notApplyPromotionCounts = purchase.getNotApplyPromotionCounts();
            if (notApplyPromotionCounts != 0) {
                status.put(purchase.getProductName(), notApplyPromotionCounts);
            }
        }
        return status;
    }

    public void setPurchaseConfirmation(Map<String, Boolean> confirm) {
        for (String productName : confirm.keySet()) {
            if (!confirm.get(productName)) {
                getPurchase(productName).subtractNotApplyQuantity();
            }
        }
    }

    public void applyMembershipSale(boolean confirm) {
        this.membershipConfirmation = confirm;
    }

    public void applyPurchases() {
        this.purchases.forEach(Purchase::applyPurchase);
        calculateAmounts();
    }

    public List<PurchasedProductsDto> getPurchasesContent() {
        List<PurchasedProductsDto> receipts = new ArrayList<>();
        for (Purchase purchase : purchases) {
            receipts.add(new PurchasedProductsDto(purchase.getProductName(), purchase.getPurchasedQuantity(), purchase.getAmount()));
        }
        return receipts;
    }

    public List<GiftsDto> getGiftsContent() {
        List<GiftsDto> gifts = new ArrayList<>();
        for (Purchase purchase : purchases) {
            if (purchase.getGiftNumber() > 0) {
                gifts.add(new GiftsDto(purchase.getProductName(), purchase.getGiftNumber()));
            }
        }
        return gifts;
    }

    public List<AmountsDto> getAmounts() {
        List<AmountsDto> amounts = new ArrayList<>();

        amounts.add(new AmountsDto("총구매액", this.totalQuantity, this.totalAmount));
        amounts.add(new AmountsDto("행사할인", -1, (-1) * this.promotionDiscountAmount));
        amounts.add(new AmountsDto("멤버십할인", -1, (-1) * this.membershipDiscountAmount));
        amounts.add(new AmountsDto("내실돈", -1, this.totalPayAmount));

        return amounts;
    }

    private void calculateAmounts() {
        calculateTotalQuantity();
        calculateTotalAmount();
        calculatePromotionDiscountAmount();
        calculateMembershipDiscountAmount();
        calculateTotalPayAmount();
    }

    private void calculateTotalQuantity() {
        this.totalQuantity = purchases.stream()
                .mapToInt(Purchase::getPurchasedQuantity)
                .sum();
    }

    private void calculateTotalAmount() {
        this.totalAmount = purchases.stream()
                .mapToInt(Purchase::getAmount)
                .sum();
    }

    private void calculatePromotionDiscountAmount() {
        this.promotionDiscountAmount = purchases.stream()
                .mapToInt(Purchase::getGiftAmount)
                .sum();
    }

    private void calculateMembershipDiscountAmount() {
        if (membershipConfirmation) {
            int noPromotionAmounts = purchases.stream()
                    .mapToInt(Purchase::getNotApplyPromotionAmounts)
                    .sum();

            this.membershipDiscountAmount = Math.min((int) (noPromotionAmounts * MEMBERSHIP_DISCOUNT_RATE), MEMBERSHIP_DISCOUNT_MAXIMUM);
        }
    }

    private void calculateTotalPayAmount() {
        this.totalPayAmount = purchases.stream()
                .mapToInt(Purchase::getAmount)
                .sum();

        totalPayAmount -= (this.promotionDiscountAmount + this.membershipDiscountAmount);
    }

    private Purchase getPurchase(String productName) {
        for (Purchase purchase : purchases) {
            if (purchase.equalName(productName)) {
                return purchase;
            }
        }
        return null;
    }
}
