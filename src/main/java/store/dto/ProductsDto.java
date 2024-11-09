package store.dto;

public class ProductsDto {

    private String productName;
    private int quantity;
    private int amount;

    public ProductsDto(String productName, int quantity, int amount) {
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
