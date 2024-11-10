package store.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constant.ErrorMessage;

public class InputFormatter {

    private static final String YES_INPUT = "Y";
    private static final String NO_INPUT = "N";
    private static final String PRODUCT_DELIMITER = ",";
    private static final String QUANTITY_DELIMITER = "-";
    private static final String PRODUCT_PREFIX = "[";
    private static final String PRODUCT_SUFFIX = "]";


    public static Map<String, Integer> formatPurchaseInput(String productAndQuantity) {
        validatePurchaseInputFormat(productAndQuantity);
        List<String> purchases = Arrays.stream(productAndQuantity.split(PRODUCT_DELIMITER))
                .map(String::trim)
                .toList();
        purchases.forEach(InputFormatter::validateProductFormat);
        return parsePurchase(purchases);
    }

    public static boolean formatIntentionInput(String intentionInput) {
        validateIntentionInputFormat(intentionInput);
        if (intentionInput.equals(YES_INPUT)) {
            return true;
        }
        return false;
    }

    private static void validatePurchaseInputFormat(String productAndQuantity) {
        try {
            productAndQuantity.split(PRODUCT_DELIMITER);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_INPUT_FORMAT_ERROR.getMessage());
        }
    }

    private static void validateProductFormat(String product) {
        if (!product.startsWith(PRODUCT_PREFIX) || !product.endsWith(PRODUCT_SUFFIX)) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_INPUT_FORMAT_ERROR.getMessage());
        }

        try {
            String[] productAndQuantity = product.substring(1, product.length() - 1).split(QUANTITY_DELIMITER);
            Integer.parseInt(productAndQuantity[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_INPUT_FORMAT_ERROR.getMessage());
        }
    }

    private static Map<String, Integer> parsePurchase(List<String> purchases) {
        Map<String, Integer> productAndQuantity = new HashMap<>();

        for (String purchase : purchases) {
            String[] purchaseOne = purchase.substring(1, purchase.length() - 1).split(QUANTITY_DELIMITER);
            productAndQuantity.put(purchaseOne[0], Integer.parseInt(purchaseOne[1]));
        }

        return productAndQuantity;
    }

    private static void validateIntentionInputFormat(String intentionInput) {
        if (!(intentionInput.equals(YES_INPUT) | intentionInput.equals(NO_INPUT))) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_OTHER_ERROR.getMessage());
        }
    }
}
