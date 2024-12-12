package store.repository;

import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.Promotion;

public class StoreRepository {

    private static final StoreRepository instance = new StoreRepository();
    private List<Promotion> promotions;
    private List<Product> products;

    private StoreRepository() {
        this.promotions = new ArrayList<>();
        this.products = new ArrayList<>();
    };

    public static StoreRepository getInstance() {
        return instance;
    }

    public void savePromotions(List<Promotion> promotions) {
        this.promotions = new ArrayList<>(promotions);
    }

    public Promotion findPromotionByName(String name) {
        for (Promotion promotion : promotions) {
            if (promotion.isNameEqualsTo(name)) {
                return promotion;
            }
        }
        return null;
    }

    public Product findProductByName(String name) {
        for (Product product : products) {
            if (product.isNameEqualsTo(name)) {
                return product;
            }
        }
        return null;
    }
}
