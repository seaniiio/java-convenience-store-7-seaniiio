package store.service;

import java.util.List;
import store.domain.Products;
import store.domain.Promotions;

public class StoreService {

    public void set(List<String> givenProducts, List<String> givenPromotions) {
        Promotions.createPromotions(givenPromotions);
        Products.createProducts(givenProducts);
    }

    public List<String> getProductsInformation() {
        Products products = new Products();
        return products.getProductsInformation();
    }
}
