package store.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.service.StoreService;
import store.util.Reader;

/*
class PromotionsTest {

    private StoreService storeService = new StoreService();

    @BeforeEach
    void set() {
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();
        storeService.set(products, promotions);
    }

    @Test
    public void 프로모션_이름으로_탐색_테스트() {
        Assertions.assertThat(Promotions.getPromotion("반짝할인").getName())
                .isEqualTo("반짝할인");
    }
}

 */