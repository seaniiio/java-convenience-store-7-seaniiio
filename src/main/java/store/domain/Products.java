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
            addInformation(product, productsInformation);
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

    private static void addInformation(Product product, List<ProductsDto> productsInformation) {
        // 프로모션 재고가 있는 경우 -> 프로모션 + 일반(재고없어도) 추가
        if (product.isPromotionStockExist()) {
            ProductsDto promotionInformation = product.getPromotionInformation();
            productsInformation.add(promotionInformation);
        }
        // 프로모션 재고가 없는 경우 -> 일반만 추가
        ProductsDto normalInformation = product.getNormalInformation();
        productsInformation.add(normalInformation);
    }
}
