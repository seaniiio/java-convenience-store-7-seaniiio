package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private static List<Product> products;

    private Products(List<Product> products) {
        this.products = products;
    }

    public static Products createProducts(List<String> givenProducts) {
        List<Product> products = new ArrayList<>();

        givenProducts.stream()
                .map(Product::createProduct)
                .forEach(products::add);

        return new Products(products);
    }

    public List<String> getInformations() {
        return products.stream()
                .map(Product::getProductInformation)
                .toList();
    }
}
