package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private static List<Product> products;

    private Products(List<Product> products) {
        this.products = products;
    }

    public static Product getProduct(String productName) {
        for (Product product : products) {
            if (product.equalsTo(productName)) {
                return product;
            }
        }
        return null;
    }

    public static Products createProducts(List<String> givenProducts) {
        List<Product> products = new ArrayList<>();

        for (String givenProduct : givenProducts) {
            //이미 products에 존재하는 경우
            String[] productInformation = givenProduct.split(",");
            if (getProduct(productInformation[0]) != null) {
                addNewStockToProducts(givenProduct);
                continue;
            }

            Product product = Product.createProduct(givenProduct);
            products.add(product);
        }

        return new Products(products);
    }

    public static void addNewStockToProducts(String givenProduct) {
        String[] productInformation = givenProduct.split(",");
        Product product = getProduct(productInformation[0]);
        product.addNewStock(givenProduct);

    }

    public List<String> getInformations() {
        return products.stream()
                .map(Product::getProductInformation)
                .toList();
    }
}
