package store.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constant.ErrorMessage;
import store.domain.Product;
import store.dto.PromotionNotAppliedProduct;
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
            if (product == null) {
                throw new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_EXIST_ERROR.getMessage());
            }
            int wantToBuyQuantity = ordersRaw.get(productName);
            orders.put(product, wantToBuyQuantity);
        }

        orderRepository.saveOrders(orders);
    }

    public void checkStock() {
        Map<Product, Integer> orders = orderRepository.getOrders();
        for (Product product : orders.keySet()) {
            if (product.isLackStock(orders.get(product))) {
                throw new IllegalArgumentException(ErrorMessage.OUT_OF_STOCK_ERROR.getMessage());
            }
        }
    }

    public List<PromotionNotAppliedProduct> checkPromotion() {
        List<PromotionNotAppliedProduct> products = new ArrayList<>();
        Map<Product, Integer> orders = orderRepository.getOrders();
        for (Product product : orders.keySet()) {
            int under = product.getUnderPromotion(orders.get(product)); // 부족한 수
            if (under > 0) {
                products.add(new PromotionNotAppliedProduct(product.getName(), under, product.getGetQuantity()));
            }
        }
        return products;
    }

    //리팩토링 필요...
    public void addBuyQuantity(List<PromotionNotAppliedProduct> products) {
        for (PromotionNotAppliedProduct addProduct : products) {
            if (addProduct.isAddPurchase()) {
                Map<Product, Integer> orders = orderRepository.getOrders();
                for (Product product : orders.keySet()) {
                    if (product.isNameEqualsTo(addProduct.getName())) {
                        orders.replace(product, orders.get(product) + addProduct.getLackQuantity());
                    }
                }
            }
        }
    }
}
