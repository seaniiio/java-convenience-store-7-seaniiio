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

            addStock(givenProduct, productName);
        }
    }

    public static List<ProductsDto> getProductsInformation() {
        List<ProductsDto> productsInformation = new ArrayList<>();
        for (Product product : products) {
            addPromotionInformation(product, productsInformation);
            addNormalInformation(product, productsInformation);
        }
        return productsInformation;
    }

    public static Product getProduct(String productName) {
        for (Product product : products) {
            if (product.equalsTo(productName)) {
                return product;
            }
        }
        return null;
    }

    private static void addStock(String givenProduct, String productName) {
        if (getProduct(productName) != null) {
            Product product = getProduct(productName);
            product.addNewStock(givenProduct);
            return;
        }

        products.add(Product.createProduct(givenProduct));
    }

    private static void addPromotionInformation(Product product, List<ProductsDto> productsInformation) {
        ProductsDto promotionInformation = product.getPromotionInformation();
        if (promotionInformation != null) {
            productsInformation.add(promotionInformation);
        }
    }

    private static void addNormalInformation(Product product, List<ProductsDto> productsInformation) {
        ProductsDto normalInformation = product.getNormalInformation();
        if (normalInformation != null) {
            productsInformation.add(normalInformation);
        }
    }
}
