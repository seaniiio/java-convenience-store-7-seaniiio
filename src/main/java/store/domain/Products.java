package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.dto.ProductsDto;

public class Products {

    private static List<Product> products = new ArrayList<>();

    private Products() {}

    public static void setProducts(List<String> givenProducts) {
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

    public static List<ProductsDto> getProductsInformation() {
        List<ProductsDto> productsInformation = new ArrayList<>();
        for (Product product : products) {
            if (product.hasPromotionStock()) {
                productsInformation.add(product.getPromotionInformation());
            }
            if (product.hasNormalStock()) {
                productsInformation.add(product.getNormalInformation());
            }
        }
        return productsInformation;
    }
}
