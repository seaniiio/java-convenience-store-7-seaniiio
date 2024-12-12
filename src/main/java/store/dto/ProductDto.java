package store.dto;

public class ProductDto {

    private final String name;
    private final int price;
    private final int quantity;
    private final String promotion;

    public ProductDto(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getInformation() {
        if (quantity == 0) {
            return String.format("- %s %,d원 재고 없음 %s", name, price, promotion);
        }
        return String.format("- %s %,d원 %,d개 %s", name, price, quantity, promotion);
    }
}
