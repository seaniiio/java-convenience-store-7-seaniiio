package store.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product {

    private final String name;
    private final int price;
    private int normalStock = -1;
    private int promotionStock = -1;
    private Promotion promotion;

    public Product(String name, int price, int stock, String promotion) {
        this.name = name;
        this.price = price;

        if (promotion.equals("null")) {
            this.normalStock = stock;
            return;
        }

        this.promotionStock = stock;
        Promotions promotions = new Promotions();
        this.promotion = promotions.getPromotion(promotion);
    }

    public static Product createProduct(String productInformation) {
        List<String> productInformations = new ArrayList<>(Arrays.stream(productInformation.split(",")).toList());

        return new Product(productInformations.get(0),
                Integer.parseInt(productInformations.get(1)),
                Integer.parseInt(productInformations.get(2)),
                productInformations.get(3));
    }

    public boolean equalsTo(String name) {
        return this.name.equals(name);
    }

    public String getProductInformation() {
        String information = "";

        if (promotion != null) {
            information += getPromotionStockInformation();
        }
        if (normalStock != -1) {
            information += getNormalStockInformation();
        }
        return information;
    }

    private String getPromotionStockInformation() {
        if (promotionStock == 0) {
            return String.format("- %s %,d원 재고 없음 %s\n", this.name, this.price, this.promotion.getName());
        }
        return String.format("- %s %,d원 %,d개 %s\n", this.name, this.price, this.promotionStock, this.promotion.getName());
    }

    private String getNormalStockInformation() {
        if (normalStock == 0) {
            return String.format("- %s %,d원 재고 없음\n", this.name, this.price);
        }
        return String.format("- %s %,d원 %,d개\n", this.name, this.price, this.promotionStock);
    }

    public void addNewStock(String productInformation) { // 새로운 프로모션 / 일반 재고 추가
        List<String> productInformations = new ArrayList<>(Arrays.stream(productInformation.split(",")).toList());
        // validate 필요
        int stock = Integer.parseInt(productInformations.get(2));
        String promotion = productInformations.get(3);

        if (promotion.equals("null")) {
            //일반 상품 추가
            this.normalStock = stock;
            return;
        }
        //프로모션 상품 추가
        this.promotionStock = stock;
        Promotions promotions = new Promotions();
        this.promotion = promotions.getPromotion(name);
    }

    private boolean isPromotionApply() {
        return promotionStock != -1 && promotion.isApply();
    }

    public boolean isOverPromotionBuyQuantity(int quantity) {
        if (isPromotionApply() && !promotion.isOverBuyQuantity(quantity)) {
            return false;
        }
        return true;
    }

    public boolean canBuy(int quantity) {
        //프로모션이 적용되는 경우
        if (isPromotionApply()) {
            return quantity <= normalStock + promotionStock;
        }
        //프로모션이 적용되지 않는 경우
        return quantity <= normalStock;
    }

    // 프로모션 적용되지 않는 물품 개수 return
    public int notApplyPromotionCounts(int quantity) {
        int maxGift = promotionStock / promotion.getBuyAndGetQuantity();

        if (quantity / promotion.getBuyAndGetQuantity() <= maxGift) {
            return 0;
        }
        return quantity - maxGift * (promotionStock / promotion.getBuyAndGetQuantity());
    }

    public int purchase(int quantity) {
        //현재 프로모션 적용되는 경우
        if (isPromotionApply()) {
            int gift = (quantity / promotion.getBuyQuantity());
            //프로모션 재고가 충분한 경우
            if (promotionStock >= quantity + gift) {
                promotionStock -= (quantity + gift);
                return gift;
            }
            //일반 재고도 필요한 경우
            normalStock = quantity - promotionStock - (gift - notApplyPromotionCounts(quantity));
            gift = promotionStock / promotion.getBuyAndGetQuantity();
            promotionStock = 0;
            return gift;
        }
        //프로모션 적용되지 않는 경우
        this.normalStock -= quantity;
        return 0;
    }

    public String getName() {
        return this.name;
    }

    public int getPromotionBuyQuantity() {
        return promotion.getBuyQuantity();
    }

    public int getAmount(int quantity) {
        return quantity * price;
    }
}
