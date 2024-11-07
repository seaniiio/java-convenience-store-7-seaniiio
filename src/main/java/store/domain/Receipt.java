package store.domain;

import java.util.List;
import java.util.Map;

public class Receipt {

    public static List<String> getPruchasesContent() {
        return Purchases.purchasesContent();
    }

    public static Map<String, Integer> getGifts() {
        return Purchases.getGiftsContent();
    }
}
