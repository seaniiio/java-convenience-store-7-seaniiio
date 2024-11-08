package store.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Purchases {

    private List<Purchase> purchases;
    private boolean membershipConfirmation = false;

    public void setPurchases(Map<String, Integer> rawPurchases) {
        purchases = new ArrayList<>();
        for (String productName : rawPurchases.keySet()) {
            purchases.add(Purchase.createPurchase(productName, rawPurchases.get(productName)));
        }
    }

    public void supplyPurchases() {
        this.purchases.forEach(Purchase::supplyPurchase);
        calculateAmounts();
    }

    public Map<String, Boolean> getPurchasePromotionStatus() {
        Map<String, Boolean> status = new HashMap<>();
        for (Purchase purchase : purchases) {
            // 프로모션이 적용되는데, 프로모션의 조건보다 적게 구입할 경우만 put
            if (purchase.getPromotionState()) {
                status.put(purchase.getProductName(), false);
            }
        }

        return status;
    }

    public void setPurchasePromotionStatus(Map<String, Boolean> status) {
        // true인 값들에 대해 구매 quantity 추가
        for (String productName : status.keySet()) {
            if (status.get(productName)) {
                Purchase purchase = from(productName);
                purchase.addQuantityForPromotion();
            }
        }
    }

    public Map<String, Integer> getPromotionStockStatus() {
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
                //정가로 결제해야하는 수량만큼 제외한 후 결제를 진행
                from(productName).cancel();
            }
        }
    }

    public List<String> purchasesContent() {
        List<String> content = new ArrayList<>();
        purchases.stream()
                .map(Purchase::purchaseContent)
                .forEach(content::add);

        return content;
    }

    public Map<String, Integer> getGiftsContent() {
        Map<String, Integer> gifts = new HashMap<>();
        for (Purchase purchase : purchases) {
            if (purchase.canGetGift()) {
                gifts.put(purchase.getProductName(), purchase.getGiftNumber());
            }
        }
        return gifts;
    }

    public void applyMembershipSale() {
        this.membershipConfirmation = true;
    }

    private void calculateAmounts() {
        int totalQuantity = purchases.stream()
                .mapToInt(Purchase::getPurchasedQuantity)
                .sum();
        AmountInformation.TOTAL_AMOUNT.setQuantity(totalQuantity);

        int totalAmount = purchases.stream()
                .mapToInt(Purchase::getAmount)
                .sum();
        AmountInformation.TOTAL_AMOUNT.setAmount(totalAmount);

        int promotionSaleAmount = purchases.stream()
                .mapToInt(Purchase::getGiftAmount)
                .sum();
        AmountInformation.PROMOTION_DISCOUNT.setAmount(promotionSaleAmount);

        int membershipSaleAmount = 0;
        if (membershipConfirmation) {
            membershipSaleAmount = (int) Math.round((totalAmount - promotionSaleAmount) * 0.3);
            if (membershipSaleAmount > 8000) {
                membershipSaleAmount = 8000;
            }
            AmountInformation.MEMBERSHIP_DISCOUNT.setAmount(membershipSaleAmount);
        }

        int payAmount = purchases.stream()
                .mapToInt(Purchase::getAmount)
                .sum();
        payAmount -= (promotionSaleAmount + membershipSaleAmount);

        AmountInformation.PAY_AMOUNT.setAmount(payAmount);
    }

    private Purchase from(String productName) {
        for (Purchase purchase : purchases) {
            if (purchase.equalName(productName)) {
                return purchase;
            }
        }
        return null;
    }
}
