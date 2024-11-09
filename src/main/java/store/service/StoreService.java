package store.service;

import java.util.List;
import store.domain.Products;
import store.domain.Promotions;

public class StoreService {

    public void set(List<String> givenProducts, List<String> givenPromotions) {
        Promotions.setPromotions(givenPromotions);
        Products.setProducts(givenProducts);
    }

    public List<String> getProductsInformation() {
        return Products.getProductsInformation();
    }
}
