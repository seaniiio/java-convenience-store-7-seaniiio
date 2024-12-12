package store.service;

import java.util.HashMap;
import java.util.Map;
import store.domain.Product;
import store.repository.OrderRepository;
import store.repository.StoreRepository;
import store.util.Parser;

public class OrderService {

    private final StoreRepository storeRepository = StoreRepository.getInstance();
    private final OrderRepository orderRepository = OrderRepository.getInstance();

    public void setOrders(String orderInput) {
        Map<String, Integer> ordersRaw = Parser.parseOrder(orderInput);
        Map<Product, Integer> orders = new HashMap<>();

        for (String productName : ordersRaw.keySet()) {
            Product product = storeRepository.findProductByName(productName);
            int wantToBuyQuantity = ordersRaw.get(productName);

            // 프로모션 적용 되는지 확인

            // 재고 충분한지 확인

            // 프로모션 - 조건 부족한지 확인(추가할건지)

            // 프로모션 - 재고 부족한지 확인(그냥 구매할건지)

            orders.put(product, wantToBuyQuantity);
        }

        orderRepository.saveOrders(orders);
    }
}
