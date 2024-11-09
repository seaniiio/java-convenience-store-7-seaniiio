package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Promotions {

    private static List<Promotion> promotions = new ArrayList<>();

    private Promotions() {}

    public static void setPromotions(List<String> givenPromotions) {
        promotions = new ArrayList<>();

        givenPromotions.stream()
                .map(Promotion::createPromotion)
                .forEach(promotions::add);
    }

    public static Promotion getPromotion(String name) {
        for (Promotion promotion : promotions) {
            if (promotion.of(name) != null) {
                return promotion.of(name);
            }
        }
        return null;
    }
}
