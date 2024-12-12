package store.domain;

import java.util.List;

public class Product {

    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int PROMOTION_INDEX = 3;

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

//    public static Product getNullProduct() {
//        return new Product("null", 0, Promotion.getNullPromotion(), 0, 0);
//    }
//
//    public boolean isNullProduct() {
//        return this.name.equals("null");
//    }

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

    private boolean isPromotionApply() {
        return promotion != null && promotion.isApply();
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
}
