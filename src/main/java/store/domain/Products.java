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

    public static Product getProduct(String productName) {
        for (Product product : products) {
            if (product.equalsTo(productName)) {
                return product;
            }
        }
        return null;
    }

    public static boolean isExist(String productName) {
        return products.stream()
                .anyMatch(product -> product.equalsTo(productName));
    }

    public List<String> getInformations() {
        return products.stream()
                .map(Product::getProductInformation)
                .toList();
    }
}
