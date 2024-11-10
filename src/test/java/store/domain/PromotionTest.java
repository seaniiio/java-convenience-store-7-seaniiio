package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PromotionTest {

    @Test
    public void 프로모션_생성_테스트() {
        String promotionInformation = "MD추천상품,1,1,2024-01-01,2024-12-31";

        Assertions.assertThat(Promotion.createPromotion(promotionInformation));
    }

    @Test
    public void 프로모션_기간_적용_테스트() {
        String promotionInformation = "MD추천상품,1,1,2024-01-01,2025-12-31";
        Promotion promotion = Promotion.createPromotion(promotionInformation);

        Assertions.assertThat(promotion.isApply()).isEqualTo(true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MD추천상품,1,1,2024-01-01,2024-11-06", "MD추천상품,1,1,2025-10-01,2025-11-06"})
    public void 프로모션_기간_미적용_테스트(String promotionInformation) {
        Promotion promotion = Promotion.createPromotion(promotionInformation);

        Assertions.assertThat(promotion.isApply()).isEqualTo(false);
    }

    @Test
    public void 프로모션_적용_개수_얻기_테스트() {
        String promotionInformation = "MD추천상품,3,1,2024-01-01,2025-12-31";
        Promotion promotion = Promotion.createPromotion(promotionInformation);

        Assertions.assertThat(promotion.getPromotionApplyQuantity())
                .isEqualTo(4);
    }
}