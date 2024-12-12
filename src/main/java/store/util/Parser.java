package store.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constant.ErrorMessage;

public class Parser {

    public static List<String> splitByComma(String input) {
        return Arrays.stream(input.split(",")).toList();
    }

    /* 리팩토링 필요 */
    public static Map<String, Integer> parseOrder(String orderInput) {
        Map<String, Integer> orders = new HashMap<>();
        List<String> ordersRaw = Arrays.stream(orderInput.split(",")).toList();
        try {
            for (String orderRaw : ordersRaw) {
                if (orderRaw.startsWith("[") && orderRaw.endsWith("]")) {
                    orderRaw = orderRaw.substring(1, orderRaw.length() - 1);
                }
                String[] orderInfo = orderRaw.split("-");

                if (orders.containsKey(orderInfo[0])) {
                    throw new IllegalArgumentException(ErrorMessage.ORDER_DUPLICATED_ERROR.getMessage());
                }
                orders.put(orderInfo[0], Integer.parseInt(orderInfo[1]));
            }

            return orders;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_ERROR.getMessage());
        }
    }
}
