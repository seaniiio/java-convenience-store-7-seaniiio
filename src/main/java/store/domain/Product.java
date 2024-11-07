package store.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product {

    private final String name;
    private final int price;
    private NormalStock normalStock;
    private PromotionStock promotionStock;

    public Product(String name, int price, int stock, String promotion) {
        this.name = name;
        this.price = price;

        if (promotion.equals("null")) {
            this.normalStock = new NormalStock(stock);
            return;
        }

        this.promotionStock = new PromotionStock(promotion, stock);
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
        if (promotionStock != null) {
            information += String.format("- %s %,d원 ", this.name, this.price) + promotionStock.getInformation();
        }
        if (normalStock != null) {
            information += String.format("- %s %,d원 ", this.name, this.price) + normalStock.getInformation();
        }
        return information;
    }

    public void addNewStock(String productInformation) { // 새로운 프로모션 / 일반 재고 추가
        List<String> productInformations = new ArrayList<>(Arrays.stream(productInformation.split(",")).toList());
        // validate 필요
        int stock = Integer.parseInt(productInformations.get(2));
        String promotion = productInformations.get(3);

        if (promotion.equals("null")) {
            //일반 상품 추가
            this.normalStock = new NormalStock(stock);
            return;
        }
        //프로모션 상품 추가
        this.promotionStock = new PromotionStock(promotion, stock);
    }

    private boolean isPromotionApply() {
        return promotionStock != null && promotionStock.isPromotionApply();
    }

    public boolean isOverPromotionBuyQuantity(int quantity) {
        if (isPromotionApply() && !promotionStock.isOverBuyQuantity(quantity)) {
            return false;
        }
        return true;
    }

    public boolean canBuy(int quantity) {
        //프로모션이 적용되는 경우
        if (isPromotionApply()) {
            return quantity <= normalStock.getStock() + promotionStock.getStock();
        }
        //프로모션이 적용되지 않는 경우
        return quantity <= normalStock.getStock();
    }

    public void purchase(int quantity) {

    }

    public String getName() {
        return this.name;
    }

    public int getPromotionBuyQuantity() {
        return promotionStock.getPromotionBuyQuantity();
    }

    public int getAmount(int quantity) {
        return quantity * price;
    }
}
