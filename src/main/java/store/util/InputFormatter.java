package store.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constant.ErrorMessage;

public class InputFormatter {

    public static Map<String, Integer> formatPurchaseInput(String productAndQuantity) {
        validatePurchaseInputFormat(productAndQuantity);
        List<String> purchases = Arrays.stream(productAndQuantity.split(","))
                .map(String::trim)
                .toList();
        purchases.forEach(InputFormatter::validateProductFormat);
        return parsePurchase(purchases);
    }

    public static boolean formatIntentionInput(String intentionInput) {
        validateIntentionInputFormat(intentionInput);
        if (intentionInput.equals("Y")) {
            return true;
        }
        return false;
    }

    private static void validatePurchaseInputFormat(String productAndQuantity) {
        try {
            productAndQuantity.split(",");
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_INPUT_FORMAT_ERROR.getMessage());
        }
    }

    private static void validateProductFormat(String product) {
        if (!product.startsWith("[") || !product.endsWith("]")) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_INPUT_FORMAT_ERROR.getMessage());
        }

        try {
            String[] productAndQuantity = product.substring(1, product.length() - 1).split("-");
            Integer.parseInt(productAndQuantity[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_INPUT_FORMAT_ERROR.getMessage());
        }
    }

    private static Map<String, Integer> parsePurchase(List<String> purchases) {
        Map<String, Integer> productAndQuantity = new HashMap<>();

        for (String purchase : purchases) {
            String[] purchaseOne = purchase.substring(1, purchase.length() - 1).split("-");
            productAndQuantity.put(purchaseOne[0], Integer.parseInt(purchaseOne[1]));
        }

        return productAndQuantity;
    }

    private static void validateIntentionInputFormat(String intentionInput) {
        if (!(intentionInput.equals("Y") | intentionInput.equals("N"))) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_OTHER_ERROR.getMessage());
        }
    }
}
