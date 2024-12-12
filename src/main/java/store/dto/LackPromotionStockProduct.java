package store.dto;

public class LackPromotionStockProduct {

    private final String name;
    private final int promotionNotApplyQuantity;
    private boolean buyConfirm;

    public LackPromotionStockProduct(String name, int promotionNotApplyQuantity) {
        this.name = name;
        this.promotionNotApplyQuantity = promotionNotApplyQuantity;
        this.buyConfirm = false;
    }

    public void confirmToBuy() {
        this.buyConfirm = true;
    }

    public String getName() {
        return name;
    }

    public int getPromotionNotApplyQuantity() {
        return promotionNotApplyQuantity;
    }

    public boolean isConfirmed() {
        return buyConfirm;
    }
}
