package store.dto;

import java.util.List;
import java.util.Map;

public class Receipt {
    private Map<String, List<Integer>> buyProducts; // 수량, 금액
    private Map<String, Integer> gifts;
    private int totalAmount;
    private int promotionDiscount;
    private int membershipDiscount;
    private int payAmount;

    public Receipt(Map<String, List<Integer>> buyProducts, Map<String, Integer> gifts, int totalAmount,
                   int promotionDiscount, int membershipDiscount) {
        this.buyProducts = buyProducts;
        this.gifts = gifts;
        this.totalAmount = totalAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.payAmount = totalAmount - (promotionDiscount + membershipDiscount);
    }

    public Map<String, List<Integer>> getBuyProducts() {
        return buyProducts;
    }

    public Map<String, Integer> getGifts() {
        return gifts;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (String product : buyProducts.keySet()) {
            totalQuantity += buyProducts.get(product).get(0);
        }
        return totalQuantity;
    }
}
