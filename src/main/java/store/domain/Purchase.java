package store.domain;

public class Purchase {

    private final Product product;
    private int quantity;

    private Purchase(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static Purchase createPurchase(String productName, int quantity) {
        validateProduct(productName, quantity);
        return new Purchase(Products.getProduct(productName), quantity);
    }

    private static void validateProduct(String productName, int quantity) {
        if (Products.getProduct(productName) == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }

        Product product = Products.getProduct(productName);
        if (!product.canBuy(quantity)) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public void supplyPurchase() {
        this.product.purchase(this.quantity);
    }

    public String getProductName() {
        return product.getName();
    }

    public boolean getPromotionState() {
        // 프로모션이 적용되는데, 프로모션의 조건보다 적게 구입할 경우에 true
        return !product.isOverPromotionBuyQuantity(this.quantity);
    }

    // 이름과 관련된 주문 내역 return
    public boolean equalName(String productName) {
        return this.product.getName().equals(productName);
    }

    public void addQuantityForPromotion() {
        this.quantity = product.getPromotionBuyQuantity();
    }

    private int getTotalAmount() {
        return this.product.getAmount(this.quantity);
    }

    public String purchaseContent() {
        return String.format("%-15s%-9d%-,6d", this.product.getName(), this.quantity, getTotalAmount());
    }

    public boolean canGetGift() {
        return product.getPromotionBuyQuantity() <= quantity;
    }
}
