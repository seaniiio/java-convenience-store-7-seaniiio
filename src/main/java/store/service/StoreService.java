package store.service;

import java.util.List;
import store.domain.Products;

public class StoreService {

    private Products products;

    public StoreService() {
    }

    public void set(List<String> givenProducts, List<String> givenPromotions) {
        Products products = Products.createProducts(givenProducts);
    }
}
