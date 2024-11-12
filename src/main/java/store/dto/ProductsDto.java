package store.dto;

public class ProductsDto {
    
    private final String productName;
    private final int price;
    private final int stock;
    private final String promotionName;

    public ProductsDto(String productName, int price, int stock, String promotionName) {
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.promotionName = promotionName;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getPromotionName() {
        return promotionName;
    }
}
