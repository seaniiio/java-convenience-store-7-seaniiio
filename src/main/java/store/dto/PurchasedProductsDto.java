package store.dto;

public class PurchasedProductsDto {

    private String productName;
    private int quantity;
    private int amount;

    public PurchasedProductsDto(String productName, int quantity, int amount) {
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAmount() {
        return amount;
    }
}
