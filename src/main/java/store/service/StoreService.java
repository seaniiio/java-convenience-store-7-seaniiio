package store.service;

import java.util.List;
import java.util.Map;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.Purchases;
import store.util.InputFormatter;

public class StoreService {

    private final InputFormatter inputFormatter;
    private Products products;
    private Purchases purchases;

    public StoreService() {
        this.inputFormatter = new InputFormatter();
    }

    public void set(List<String> givenProducts, List<String> givenPromotions) {
        this.products = Products.createProducts(givenProducts);
        Promotions.createPromotions(givenPromotions);
    }

    public List<String> getProductsInformation() {
        return products.getInformations();
    }

    public void setPurchase(String productAndQuantity) {
        Map<String, Integer> purchasesInput = inputFormatter.formatPurchaseInput(productAndQuantity);
        this.purchases = Purchases.createPurchases(purchasesInput);
        this.purchases.supplyPurchases();
    }
}
