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
            // 일반 재고 추가
            this.normalStock = quantity;
            return;
        }
        // 프로모션 재고 추가
        this.promotion = promotion;
        this.promotionStock = quantity;
    }

    public boolean isLackStock(int buyQuantity) {
        if (isPromotionApply()) {
            //프로모션 적용 -> 프로모션 재고까지 합해서 확인
            return buyQuantity > normalStock + promotionStock;
        }
        return buyQuantity > normalStock;
    }

    public int getUnderPromotion(Integer buyQuantity) {
        if (!promotion.isApply()) { // 프로모션 적용 안되면 X
            return 0;
        }
        if (buyQuantity % (promotion.getCondition()) == 0) { // 조건과 딱 맞는 경우
            return 0;
        }
        // (buy + get)으로 나누어떨어지지 않는 경우 -> 부족한 경우
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
        int applyQuantity = (promotionStock / (promotion.getCondition())) * promotion.getCondition();
        if (applyQuantity >= buyQuantity) {
            return 0;
        }
        return buyQuantity - applyQuantity;
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
}
