package store.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constant.Command;
import store.constant.ErrorMessage;
import store.domain.Product;
import store.dto.LackBuyQuantityProduct;
import store.dto.LackPromotionStockProduct;
import store.dto.Receipt;
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

    public List<LackBuyQuantityProduct> checkPromotionCondition() {
        List<LackBuyQuantityProduct> products = new ArrayList<>();
        Map<Product, Integer> orders = orderRepository.getOrders();
        for (Product product : orders.keySet()) {
            int under = product.getUnderPromotion(orders.get(product)); // 부족한 수
            if (under > 0) {
                products.add(new LackBuyQuantityProduct(product.getName(), under, product.getGetQuantity()));
            }
        }
        return products;
    }

    //리팩토링 필요...
    public void addBuyQuantity(List<LackBuyQuantityProduct> products) {
        for (LackBuyQuantityProduct addProduct : products) {
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

    public List<LackPromotionStockProduct> checkPromotionStock() {
        List<LackPromotionStockProduct> products = new ArrayList<>();
        Map<Product, Integer> orders = orderRepository.getOrders();
        for (Product product : orders.keySet()) {
            int under = product.getPromotionNotApplyQuantity(orders.get(product)); // 부족한 수
            if (under > 0) {
                products.add(new LackPromotionStockProduct(product.getName(),
                        product.getPromotionNotApplyQuantity(orders.get(product))));
            }
        }
        return products;
    }

    public void setBuyConfirm(List<LackPromotionStockProduct> products) {
        for (LackPromotionStockProduct addProduct : products) {
            if (!addProduct.isConfirmed()) {
                Map<Product, Integer> orders = orderRepository.getOrders();
                for (Product product : orders.keySet()) {
                    if (product.isNameEqualsTo(addProduct.getName())) {
                        orders.replace(product, orders.get(product) - addProduct.getPromotionNotApplyQuantity());
                    }
                }
            }
        }
    }

    public Receipt buy(Command isMembershipApply) {
        Map<Product, Integer> orders = orderRepository.getOrders();
        Map<String, List<Integer>> buyProducts = new HashMap<>();
        Map<String, Integer> gifts = new HashMap<>();

        int totalAmount = 0;
        int promotionDiscount = 0;
        int membershipDiscount = 0;
        int promotionNotApplyAmount= 0;
        for (Product product : orders.keySet()) {
            buyProducts.put(product.getName(), List.of(orders.get(product), product.getBuyPrice(orders.get(product))));
            gifts.put(product.getName(), product.getGifts(orders.get(product)));
            totalAmount += product.getBuyPrice(orders.get(product));
            promotionDiscount += product.getBuyPrice(product.getGifts(orders.get(product)));
            promotionNotApplyAmount += product.getMembershipApplyAmount(orders.get(product));
        }

        if (isMembershipApply.equals(Command.YES)) {
            membershipDiscount = (int) (promotionNotApplyAmount * 0.3);
        }

        return new Receipt(buyProducts, gifts, totalAmount, promotionDiscount, membershipDiscount);
    }
}
