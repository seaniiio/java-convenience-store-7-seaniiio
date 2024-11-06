package store.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Purchases {

    private List<Purchase> purchases;

    private Purchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public static Purchases createPurchases(Map<String, Integer> rawPurchases) {
        List<Purchase> purchases = new ArrayList<>();

        for (String productName : rawPurchases.keySet()) {
            purchases.add(Purchase.createPurchase(productName, rawPurchases.get(productName)));
        }

        return new Purchases(purchases);
    }

    public void supplyPurchases() {
        this.purchases.forEach(Purchase::supplyPurchase);
    }
}
