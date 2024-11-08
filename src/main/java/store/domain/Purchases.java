package store.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Purchases {

    private static List<Purchase> purchases;
    private static int totalQuantity = 0;
    private static int totalAmount = 0;
    private static boolean membershipConfirmation = false;
    private static int membershipSaleAmount = 0;
    private static int promotionSaleAmount = 0;

    public static void createPurchases(Map<String, Integer> rawPurchases) {
        purchases = new ArrayList<>();
        for (String productName : rawPurchases.keySet()) {
            purchases.add(Purchase.createPurchase(productName, rawPurchases.get(productName)));
        }
    }

    public static Map<String, Integer> getGiftsContent() {
        Map<String, Integer> gifts = new HashMap<>();
        for (Purchase purchase : purchases) {
            if (purchase.canGetGift()) {
                gifts.put(purchase.getProductName(), purchase.getGiftNumber());
            }
        }
        return gifts;
    }

    public static List<String> getAmounts() {
        calculateAmounts();
        List<String> resultMessage = new ArrayList<>();
        resultMessage.add(String.format("%-14s%-8d%,6d", "총구매액", totalQuantity, totalAmount));
        resultMessage.add(String.format("%-21s-%,-6d", "행사할인", promotionSaleAmount));
        resultMessage.add(String.format("%-21s-%,-6d", "멤버십할인", membershipSaleAmount));
        resultMessage.add(String.format("%-21s%,-6d", "내실돈", totalAmount));
        return resultMessage;
    }

    public void supplyPurchases() {
        this.purchases.forEach(Purchase::supplyPurchase);
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
                //구매 취소
                from(productName).cancel();
            }
        }
    }

    public static List<String> purchasesContent() {
        List<String> content = new ArrayList<>();
        purchases.stream()
                .map(Purchase::purchaseContent)
                .forEach(content::add);

        return content;
    }

    public void applyMembershipSale() {
        this.membershipConfirmation = true;
    }

    private static void calculateAmounts() {
        totalQuantity = purchases.stream()
                .mapToInt(Purchase::getPurchasedQuantity)
                .sum();

        promotionSaleAmount = purchases.stream()
                .mapToInt(Purchase::getPurchasedGiftAmount)
                .sum();

        if (membershipConfirmation) {
            membershipSaleAmount = (int) Math.round((totalAmount - promotionSaleAmount) * 0.3);
            if (membershipSaleAmount > 8000) {
                membershipSaleAmount = 8000;
            }
        }

        totalAmount = purchases.stream()
                .mapToInt(Purchase::getPurchasedAmount)
                .sum();
        totalAmount -= (promotionSaleAmount + membershipSaleAmount);
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
