package store.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.service.StoreService;
import store.util.Reader;

class PurchaseTest {

    private StoreService storeService = new StoreService();

    @BeforeEach
    void set() {
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();
        storeService.set(products, promotions);
    }

    @Test
    public void 재고_초과_구매_예외_테스트() {
        Assertions.assertThatThrownBy(() -> Purchase.createPurchase("콜라", 21))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 프로모션_혜택_미달_테스트_1() {
        Purchase purchase = Purchase.createPurchase("콜라", 1);

        Assertions.assertThat(purchase.getPromotionState())
                .isEqualTo(true);
    }

    @Test
    public void 프로모션_혜택_미달_테스트_2() {
        Purchase purchase = Purchase.createPurchase("콜라", 2);

        Assertions.assertThat(purchase.getPromotionState())
                .isEqualTo(true);
    }

    @Test
    public void 프로모션_혜택_달성_테스트() {
        Purchase purchase = Purchase.createPurchase("콜라", 3);

        Assertions.assertThat(purchase.getPromotionState())
                .isEqualTo(false);
    }

    @Test
    public void 프로모션_할인_미적용_테스트_1() {
        Purchase purchase = Purchase.createPurchase("콜라", 15);

        Assertions.assertThat(purchase.getNotApplyPromotionCounts())
                .isEqualTo(6);
    }

    @Test
    public void 프로모션_할인_미적용_테스트_2() {
        Purchase purchase = Purchase.createPurchase("탄산수", 5);

        Assertions.assertThat(purchase.getNotApplyPromotionCounts())
                .isEqualTo(2);
    }

    @Test
    public void 프로모션_할인_미적용_테스트_3() {
        Purchase purchase = Purchase.createPurchase("감자칩", 6);

        Assertions.assertThat(purchase.getNotApplyPromotionCounts())
                .isEqualTo(2);
    }

    @Test
    public void 프로모션_할인_적용_테스트_3() {
        Purchase purchase = Purchase.createPurchase("감자칩", 2);

        Assertions.assertThat(purchase.getNotApplyPromotionCounts())
                .isEqualTo(0);
    }
}