package store.domain;

public class PromotionStock {

    private int stock;
    private Promotion promotion;

    public PromotionStock(String promotionName, int stock) {
        Promotions promotions = new Promotions();

        this.stock = stock;
        this.promotion = promotions.getPromotion(promotionName);
    }

    public String getInformation() {
        if (stock == 0) {
            return String.format("재고 없음 %s", this.promotion);
        }
        return String.format("%,d개 %s",this.stock, this.promotion);
    }
}
