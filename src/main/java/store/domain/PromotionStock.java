package store.domain;

public class PromotionStock {

    private int stock;
    private Promotion promotion;

    public PromotionStock(String promotionName, int stock) {
        Promotions promotions = new Promotions();

        this.stock = stock;
        this.promotion = promotions.getPromotion(promotionName);
    }

    public boolean isPromotionApply() {
        return promotion.isApply();
    }

    public String getInformation() {
        if (stock == 0) {
            return String.format("재고 없음 %s\n", this.promotion);
        }
        return String.format("%,d개 %s\n",this.stock, this.promotion.getName());
    }

    public int getStock() {
        return stock;
    }

    public boolean isOverBuyQuantity(int quantity) {
        return promotion.isisOverBuyQuantity(quantity);
    }

    public int getPromotionBuyQuantity() {
        return promotion.getBuyQuantity();
    }
}
