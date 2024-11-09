package store.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.dto.ProductsDto;

public class Product {

    private final String name;
    private final int price;
    private int normalStock = -1;
    private int promotionStock = -1;
    private Promotion promotion;

    public Product(String name, int price, int stock, String promotion) {
        this.name = name;
        this.price = price;
        classifyNormalOrPromotion(stock, promotion);
    }

    private void classifyNormalOrPromotion(int stock, String promotion) {
        if (promotion.equals("null")) {
            this.normalStock = stock;
            return;
        }

        this.promotionStock = stock;
        this.promotion = Promotions.of(promotion);
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

    public void addNewStock(String productInformation) { // 새로운 프로모션 / 일반 재고 추가
        List<String> productInformations = new ArrayList<>(Arrays.stream(productInformation.split(",")).toList());
        // validate 필요
        int stock = Integer.parseInt(productInformations.get(2));
        String promotion = productInformations.get(3);

        classifyNormalOrPromotion(stock, promotion);
    }

    private boolean isPromotionApply() {
        return promotion != null && promotion.isApply();
    }

    public boolean isOverPromotionBuyQuantity(int quantity) {
        if (isPromotionApply() && !promotion.isOverPromotionQuantity(quantity)) {
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
        if (promotion == null) {
            return 0;
        }
        int maxPromotionApplyAmount = (promotionStock / promotion.getBuyAndGetQuantity()) * promotion.getBuyAndGetQuantity();
        if (quantity <= maxPromotionApplyAmount) {
            return 0;
        }
        return quantity - maxPromotionApplyAmount;
    }

    //수정 필요!!!
    public int purchase(int quantity) {
        //현재 프로모션 적용되는 경우
        if (isPromotionApply()) {
            int maxGift = (promotionStock / promotion.getBuyAndGetQuantity());
            //프로모션 재고가 충분한 경우
            if (promotionStock >= quantity) {
                promotionStock -= quantity;
                return quantity / promotion.getBuyAndGetQuantity();
            }
            //일반 재고도 필요한 경우
            normalStock -= (quantity - promotionStock);
            promotionStock = 0;
            return maxGift;
        }
        //프로모션 적용되지 않는 경우
        this.normalStock -= quantity;
        return 0;
    }

    public String getName() {
        return this.name;
    }

    public int getAmount(int quantity) {
        return quantity * price;
    }

    public boolean isPromotion() {
        return !(this.promotion == null);
    }

    public int getPromotionQuantity() {
        if (promotion == null) {
            return 0;
        }
        return promotion.getPromotionQuantity();
    }

    public boolean hasNormalStock() {
        return normalStock >= 0;
    }

    public boolean hasPromotionStock() {
        return promotionStock >= 0;
    }

    public ProductsDto getNormalInformation() {
        return new ProductsDto(this.name, this.price, this.normalStock, "");
    }

    public ProductsDto getPromotionInformation() {
        return new ProductsDto(this.name, this.price, this.promotionStock, this.promotion.getName());
    }
}
