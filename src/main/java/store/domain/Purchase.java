package store.domain;

public class Purchase {

    private final Product product;
    private int quantity;
    private int giftNumber;
    private int amount;
    private int giftAmount;
    private boolean confirmation;

    private Purchase(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.giftNumber = 0;
        this.confirmation = true;
        this.amount = 0;
        this.giftAmount = 0;
    }

    public void supplyPurchase() {
        if (confirmation) {
            this.giftNumber = this.product.purchase(this.quantity);
            this.amount = product.getAmount(quantity);
            this.giftAmount = product.getAmount(this.giftNumber);
        }
    }

    public int getPurchasedAmount() {
        if (confirmation) {
            return this.amount;
        }
        return 0;
    }

    public int getPurchasedGiftAmount() {
        if (confirmation) {
            return this.giftAmount;
        }
        return 0;
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

    public String purchaseContent() {
        if (confirmation) {
            return String.format("%-15s%-9d%-,6d", this.product.getName(), this.quantity, getTotalAmount());
        }
        return null;
    }

    public boolean canGetGift() {
        return confirmation && product.getPromotionBuyQuantity() <= quantity;
    }

    public Integer getGiftNumber() {
        return this.giftNumber;
    }

    public int getNotApplyPromotionCounts() {
        // 프로모션이 적용되지 않는 물품 수 return
        return product.notApplyPromotionCounts(quantity);
    }

    public void cancel() {
        this.confirmation = false;
    }

    public static Purchase createPurchase(String productName, int quantity) {
        validateProduct(productName, quantity);
        return new Purchase(Products.of(productName), quantity);
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

    private int getTotalAmount() {
        return this.product.getAmount(this.quantity);
    }

    public int getPurchasedQuantity() {
        if (confirmation) {
            return this.quantity;
        }
        return 0;
    }
}
