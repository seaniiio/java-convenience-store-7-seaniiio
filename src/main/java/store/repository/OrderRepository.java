package store.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import store.domain.Product;

public class OrderRepository {

    private static final OrderRepository instance = new OrderRepository();
    private Map<Product, Integer> orders;

    private OrderRepository() {
        this.orders = new HashMap<>();
    };

    public static OrderRepository getInstance() {
        return instance;
    }

    public void saveOrders(Map<Product, Integer> orders) {
        this.orders = orders;
    }
}
