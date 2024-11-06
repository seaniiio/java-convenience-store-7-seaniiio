package store.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputFormatter {

    public Map<String, Integer> formatPurchaseInput(String productAndQuantity) {
        validatePurchaseInputFormat(productAndQuantity);
        List<String> purchases = Arrays.stream(productAndQuantity.split(","))
                .map(String::trim)
                .toList();
        purchases.forEach(this::validateProductFormat);
        return parsePurchase(purchases);
    }

    private void validatePurchaseInputFormat(String productAndQuantity) {
        try {
            productAndQuantity.split(",");
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private void validateProductFormat(String product) {
        if (!product.startsWith("[") || !product.endsWith("]")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        try {
            String[] productAndQuantity = product.substring(1, product.length() - 1).split("-");
            Integer.parseInt(productAndQuantity[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private Map<String, Integer> parsePurchase(List<String> purchases) {
        Map<String, Integer> productAndQuantity = new HashMap<>();

        for (String purchase : purchases) {
            String[] purchaseOne = purchase.substring(1, purchase.length() - 1).split("-");
            productAndQuantity.put(purchaseOne[0], Integer.parseInt(purchaseOne[1]));
        }

        return productAndQuantity;
    }
}
