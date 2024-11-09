package store.service;

import java.util.List;
import store.domain.Products;
import store.domain.Promotions;
import store.dto.ProductsDto;

public class StoreService {

    public void set(List<String> givenProducts, List<String> givenPromotions) {
        Promotions.setPromotions(givenPromotions);
        Products.setProducts(givenProducts);
    }

    public List<ProductsDto> getProductsInformation() {
        return Products.getProductsInformation();
    }
}
