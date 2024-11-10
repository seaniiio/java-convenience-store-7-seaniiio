package store.domain;

import store.constant.ErrorMessage;

public class Purchase {

    private final Product product;
    private int quantity;
    private int giftNumber;

    private Purchase(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.giftNumber = 0;
    }

    public static Purchase createPurchase(String productName, int quantity) {
        validateProduct(productName, quantity);
        return new Purchase(Products.getProduct(productName), quantity);
    }

    public boolean isUnderPromotionQuantity() {
        // 프로모션이 적용되는데, 프로모션보다 적게 구입할 경우에 true
        return !product.isOverPromotionQuantity(this.quantity);
    }

    public void addQuantityForPromotion() {
        this.quantity = product.getPromotionQuantity();
    }

    public boolean equalName(String productName) {
        return this.product.getName().equals(productName);
    }

    public Integer getGiftNumber() {
        return this.giftNumber;
    }

    public int getNotApplyPromotionCounts() {
        // 재고가 없어서 프로모션이 적용되지 않는 물품 수 return
        return product.notApplyPromotionCounts(quantity);
    }

    public int getNotApplyPromotionAmounts() {
        // 재고가 없거나 조건보다 덜 구매해서 프로모션이 적용되지 않는 가격 return
        if (product.isPromotionApply()) {
            if (product.isOverPromotionQuantity(quantity)) {
                System.out.println(product.getAmount(getNotApplyPromotionCounts()));
                return product.getAmount(getNotApplyPromotionCounts());
            }
        }
        System.out.println(product.getAmount(quantity));
        return product.getAmount(quantity);
    }

    public void subtractNotApplyQuantity() {
        this.quantity -= getNotApplyPromotionCounts();
    }

    public void applyPurchase() {
        this.giftNumber = this.product.purchase(this.quantity);
    }

    public int getAmount() {
        return product.getAmount(quantity);
    }

    public int getGiftAmount() {
        return product.getAmount(this.giftNumber);
    }

    public String getProductName() {
        return product.getName();
    }

    public int getPurchasedQuantity() {
        return this.quantity;
    }

    private static void validateProduct(String productName, int quantity) {
        if (Products.getProduct(productName) == null) {
            throw new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_EXIST_ERROR.getMessage());
        }

        Product product = Products.getProduct(productName);
        if (!product.canBuy(quantity)) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_STOCK_ERROR.getMessage());
        }
    }
}
