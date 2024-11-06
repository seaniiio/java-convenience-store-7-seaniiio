package store.service;

import java.util.List;
import store.domain.Products;
import store.domain.Promotions;

public class StoreService {

    private Products products;

    public StoreService() {
    }

    public void set(List<String> givenProducts, List<String> givenPromotions) {
        this.products = Products.createProducts(givenProducts);
        Promotions promotions = Promotions.createPromotions(givenPromotions);
    }

    public List<String> getProductsInformation() {
        return products.getInformations();
    }
}
