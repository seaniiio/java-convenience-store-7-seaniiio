package store.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product {

    private final String name;
    private final int price;
    private int stock;
    private final String promotion;

    public Product(String name, int price, int stock, String promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public static Product createProduct(String productInformation) {
        List<String> productInformations = new ArrayList<>(Arrays.stream(productInformation.split(",")).toList());

        return new Product(productInformations.get(0),
                Integer.parseInt(productInformations.get(1)),
                Integer.parseInt(productInformations.get(2)),
                productInformations.get(3));
    }

    public String getProductInformation() {
        if (promotion.equals("null")) {
            return String.format("- %s %,d원 %,d개", this.name, this.price, this.stock);
        }
        return String.format("- %s %,d원 %,d개 %s", this.name, this.price, this.stock, this.promotion);
    }
}
