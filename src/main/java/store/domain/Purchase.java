package store.domain;

public class Purchase {

    private final Product product;
    private int quantity;
    private int giftNumber;
    private int amount;
    private int giftAmount;
    private int promotionNotAppliedQuantity;

    private Purchase(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.giftNumber = 0;
        this.amount = 0;
        this.giftAmount = 0;
    }

    public static Purchase createPurchase(String productName, int quantity) {
        validateProduct(productName, quantity);
        return new Purchase(Products.of(productName), quantity);
    }

    public boolean getPromotionState() {
        // 프로모션이 적용되는데, 프로모션보다 적게 구입할 경우에 true
        return !product.isOverPromotionBuyQuantity(this.quantity);
    }
    // 상품 이름과 관련된 주문 내역 return

    public boolean equalName(String productName) {
        return this.product.getName().equals(productName);
    }

    public void addQuantityForPromotion() {
        this.quantity = product.getPromotionQuantity();
    }

    public boolean canGetGift() {
        return product.getPromotionQuantity() <= quantity;
    }

    public Integer getGiftNumber() {
        return this.giftNumber;
    }

    public int getNotApplyPromotionCounts() {
        // 프로모션이 적용되지 않는 물품 수 return
        this.promotionNotAppliedQuantity = product.notApplyPromotionCounts(quantity);
        return promotionNotAppliedQuantity;
    }

    public int getNotApplyPromotionAmounts() {
        //프로모션이 적용되지 않는 가격 return
        if (product.isPromotion()) {
            return product.getAmount(promotionNotAppliedQuantity);
        }
        return product.getAmount(quantity);
    }

    public void cancel() {
        this.quantity -= getNotApplyPromotionCounts();
    }

    public void supplyPurchase() {
        this.giftNumber = this.product.purchase(this.quantity);
        this.amount = product.getAmount(quantity);
        this.giftAmount = product.getAmount(this.giftNumber);
    }

    public int getPurchasedQuantity() {
        return this.quantity;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getGiftAmount() {
        return this.giftAmount;
    }

    public String getProductName() {
        return product.getName();
    }

    private static void validateProduct(String productName, int quantity) {
        if (Products.of(productName) == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }

        Product product = Products.of(productName);
        if (!product.canBuy(quantity)) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }
}
