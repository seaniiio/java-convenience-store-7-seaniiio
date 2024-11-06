package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @Test
    public void 프로모션_생성_테스트() {
        String promotionInformation = "MD추천상품,1,1,2024-01-01,2024-12-31";

        Assertions.assertThat(Promotion.createPromotion(promotionInformation));
    }
}