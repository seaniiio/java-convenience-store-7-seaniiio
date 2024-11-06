package store.domain;

import java.util.List;
import org.junit.jupiter.api.Test;

class PromotionsTest {

    @Test
    public void 프로모션_목록_생성_테스트() {
        List<String> givenPromotions = List.of("MD추천상품,1,1,2024-01-01,2024-12-31", "반짝할인,1,1,2024-11-01,2024-11-30");

        Promotions.createPromotions(givenPromotions);
    }
}