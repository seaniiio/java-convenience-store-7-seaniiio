package store.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.dto.ProductsDto;

public class Product {

    private static final String NO_PROMOTION = "null";
    private static final String PRODUCT_DELIMITER = ",";

    private final String name;
    private final int price;
    private int normalStock = 0;
    private int promotionStock = -1;
    private Promotion promotion;

    private Product(String name, int price, int stock, String promotion) {
        this.name = name;
        this.price = price;
        classifyNormalOrPromotion(stock, promotion);
    }

    public static Product createProduct(String productInformation) {
        List<String> productInformations = new ArrayList<>(Arrays.stream(productInformation.split(PRODUCT_DELIMITER)).toList());

        return new Product(productInformations.get(0),
                Integer.parseInt(productInformations.get(1)),
                Integer.parseInt(productInformations.get(2)),
                productInformations.get(3));
    }

    public boolean equalsTo(String name) {
        return this.name.equals(name);
    }

    public void addNewStock(String productInformation) {
        List<String> productInformations = new ArrayList<>(Arrays.stream(productInformation.split(",")).toList());

        int stock = Integer.parseInt(productInformations.get(2));
        String promotion = productInformations.get(3);

        classifyNormalOrPromotion(stock, promotion);
    }

    public boolean isOverPromotionQuantity(int quantity) {
        if (isPromotionApply() && promotion.getPromotionApplyQuantity() > quantity) {
            return false;
        }
        return true;
    }

    public boolean canBuy(int quantity) {
        if (isPromotionApply()) {
            return quantity <= normalStock + promotionStock;
        }
        return quantity <= normalStock;
    }

    public int notApplyPromotionOutOfStockCounts(int quantity) {
        if (!isPromotionApply()) {
            return 0;
        }
        int maxPromotionApplyQuantity = (promotionStock / promotion.getPromotionApplyQuantity()) * promotion.getPromotionApplyQuantity();
        if (quantity <= maxPromotionApplyQuantity) {
            return 0;
        }
        return quantity - maxPromotionApplyQuantity;
    }

    public int notApplyPromotionCounts(int quantity) {
        if (!isPromotionApply()) {
            return 0;
        }
        int maxPromotionApplyQuantity = (promotionStock / promotion.getPromotionApplyQuantity()) * promotion.getPromotionApplyQuantity();
        if (quantity <= maxPromotionApplyQuantity) {
            return quantity % promotion.getPromotionApplyQuantity();
        }
        return quantity - maxPromotionApplyQuantity;
    }

    public int purchase(int quantity) {
        if (isPromotionApply()) {
            return purchaseWithPromotion(quantity);
        }
        this.normalStock -= quantity;
        return 0;
    }

    public String getName() {
        return this.name;
    }

    public int getAmount(int quantity) {
        return quantity * price;
    }

    public boolean isPromotionApply() {
        return promotion != null && promotion.isApply();
    }

    public int getPromotionQuantity() {
        if (promotion == null) {
            return 0;
        }
        return promotion.getPromotionApplyQuantity();
    }

    public boolean isPromotionStockExist() {
        return promotion != null;
    }

    public ProductsDto getNormalInformation() {
        return new ProductsDto(this.name, this.price, this.normalStock, "");
    }

    public ProductsDto getPromotionInformation() {
        return new ProductsDto(this.name, this.price, this.promotionStock, this.promotion.getName());
    }

    public int getPromotionStock() {
        return this.promotionStock;
    }

    private int purchaseWithPromotion(int quantity) {
        int maxGift = (promotionStock / promotion.getPromotionApplyQuantity());

        if (promotionStock >= quantity) {
            promotionStock -= quantity;
            return quantity / promotion.getPromotionApplyQuantity();
        }

        normalStock -= (quantity - promotionStock);
        promotionStock = 0;
        return maxGift;
    }

    private void classifyNormalOrPromotion(int stock, String promotion) {
        if (promotion.equals(NO_PROMOTION)) {
            this.normalStock = stock;
            return;
        }

        this.promotionStock = stock;
        this.promotion = Promotions.getPromotion(promotion);
    }
}
