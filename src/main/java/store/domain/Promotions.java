package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Promotions {

    private final List<Promotion> promotions;

    private Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public static Promotions createPromotions(List<String> givenPromotions) {
        List<Promotion> promotions = new ArrayList<>();

        givenPromotions.stream()
                .map(Promotion::createPromotion)
                .forEach(promotions::add);

        return new Promotions(promotions);
    }
}
