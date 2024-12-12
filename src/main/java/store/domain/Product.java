package store.domain;

import java.util.List;

public class Product {

    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    private final String name;
    private final int price;
    private Promotion promotion;
    private int normalStock;
    private int promotionStock;

    private Product(String name, int price, Promotion promotion, int normalStock, int promotionStock) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.normalStock = normalStock;
        this.promotionStock = promotionStock;
    }

    public static Product createProduct(List<String> productRaw, Promotion promotion) {
        String name = productRaw.get(NAME_INDEX);
        int price = Integer.parseInt(productRaw.get(PRICE_INDEX));
        int quantity = Integer.parseInt(productRaw.get(QUANTITY_INDEX));
        if (promotion == null) {
            return new Product(name, price, promotion, quantity, 0);
        }
        return new Product(name, price, promotion, 0, quantity);
    }

    public boolean isNameEqualsTo(String name) {
        return this.name.equals(name);
    }

    public void addStockToProduct(List<String> productRaw, Promotion promotion) {
        int quantity = Integer.parseInt(productRaw.get(QUANTITY_INDEX));

        if (promotion == null) {
            this.normalStock = quantity;
            return;
        }
        this.promotion = promotion;
        this.promotionStock = quantity;
    }

    public boolean isLackStock(int buyQuantity) {
        if (isPromotionApply()) {
            return buyQuantity > normalStock + promotionStock;
        }
        return buyQuantity > normalStock;
    }

    public int getUnderPromotion(Integer buyQuantity) {
        if (promotion == null || !promotion.isApply() || (buyQuantity % (promotion.getCondition()) == 0)) { // 프로모션 적용 안되면 X
            return 0;
        }
        int lackQuantity = promotion.getCondition() - (buyQuantity % promotion.getCondition());
        if (buyQuantity + lackQuantity > promotionStock) { //재고 추가하면 프로모션 재고 초과하는 경우
            return 0;
        }
        return lackQuantity;
    }

    private boolean isPromotionApply() {
        return promotion != null && promotion.isApply();
    }

    public int getPromotionNotApplyQuantity(int buyQuantity) {
        if (promotion == null) {
            return 0;
        }
        int applyQuantity = (promotionStock / (promotion.getCondition())) * promotion.getCondition();
        if (applyQuantity >= buyQuantity) {
            return 0;
        }
        return buyQuantity - applyQuantity;
    }

    public int getMembershipApplyAmount(int quantity) {
        if (!isPromotionApply()) {
            return quantity * price;
        }

        if (getPromotionNotApplyQuantity(quantity) >= 0) {
            return getPromotionNotApplyQuantity(quantity) * price;
        }

        return (quantity % promotion.getCondition()) * price;
    }

    public Integer getGifts(Integer buyQuantity) {
        if (promotion == null || !promotion.isApply()) {
            return 0;
        }
        int applyQuantity = (promotionStock / (promotion.getCondition())) * promotion.getCondition();
        if (applyQuantity >= buyQuantity) {
            return buyQuantity / promotion.getCondition();
        }
        return applyQuantity;
    }

    public void buy(int quantity) {
        if (promotion != null && promotion.isApply()) {
            if (promotionStock >= quantity) {
                promotionStock -= quantity;
                return;
            }
            normalStock -= (quantity - promotionStock);
            promotionStock = 0;
            return;
        }
        normalStock -= quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getNormalStock() {
        return normalStock;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public boolean isPromotionStockExist() {
        return promotion != null;
    }

    public int getGetQuantity() {
        return this.promotion.getGetQuantity();
    }

    public Integer getBuyPrice(Integer quantity) {
        return price * quantity;
    }
}
