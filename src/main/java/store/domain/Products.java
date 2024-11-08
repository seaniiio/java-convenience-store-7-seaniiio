package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private static List<Product> products = new ArrayList<>();

    public static void createProducts(List<String> givenProducts) {
        for (String givenProduct : givenProducts) {
            String[] productInformation = givenProduct.split(",");
            String productName = productInformation[0];

            //이미 products에 존재하는 경우
            if (of(productName) != null) {
                addNewStockToProducts(givenProduct);
                continue;
            }

            Product product = Product.createProduct(givenProduct);
            products.add(product);
        }
    }

    public static Product of(String productName) {
        for (Product product : products) {
            if (product.equalsTo(productName)) {
                return product;
            }
        }
        return null;
    }

    public static void addNewStockToProducts(String givenProduct) {
        String[] productInformation = givenProduct.split(",");
        Product product = of(productInformation[0]);
        product.addNewStock(givenProduct);

    }

    public List<String> getProductsInformation() {
        return products.stream()
                .map(Product::getProductInformation)
                .toList();
    }
}
