package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Promotions {

    private static List<Promotion> promotions = new ArrayList<>();

    public static void createPromotions(List<String> givenPromotions) {
        promotions = new ArrayList<>();

        givenPromotions.stream()
                .map(Promotion::createPromotion)
                .forEach(promotions::add);
    }

    public Promotion getPromotion(String name) {
        for (Promotion promotion : promotions) {
            if (promotion.of(name) != null) {
                return promotion.of(name);
            }
        }
        return null;
    }
}
