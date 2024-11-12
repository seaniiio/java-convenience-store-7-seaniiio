package store.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.constant.ErrorMessage;
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

    @DisplayName("재고를 초과하는 수량만큼 구매하는 경우 예외 테스트")
    @Test
    public void 재고_초과_구매_예외_테스트() {
        assertThatIllegalArgumentException().isThrownBy(() -> Purchase.createPurchase("콜라", 21))
                .withMessageContaining(ErrorMessage.OUT_OF_STOCK_ERROR.getMessage());
    }

    @DisplayName("존재하지 않는 상품을 구매하려 하는 경우 예외 테스트")
    @Test
    public void 미존재_상품_구매_예외_테스트() {
        assertThatIllegalArgumentException().isThrownBy(() -> Purchase.createPurchase("논픽션", 3))
                .withMessageContaining(ErrorMessage.PRODUCT_NOT_EXIST_ERROR.getMessage());
    }

    @DisplayName("구매 수량이 프로모션 혜택에 달성하지 못하는지 확인 테스트")
    @ParameterizedTest
    @CsvSource({
            "콜라, 1, true",
            "콜라, 2, true",
            "콜라, 3, false",
    })
    public void 프로모션_혜택_달성_테스트(String productName, int quantity, boolean expected) {
        Purchase purchase = Purchase.createPurchase(productName, quantity);

        Assertions.assertThat(purchase.isUnderPromotionQuantity())
                .isEqualTo(expected);
    }

    @DisplayName("프로모션 할인이 적용되지 않는 수량 확인 테스트")
    @ParameterizedTest
    @CsvSource({
            "콜라, 15, 6",
            "탄산수, 5, 2",
            "감자칩, 6, 2",
            "감자칩, 2, 0",
            "감자칩, 1, 1",
            "콜라, 14, 5",
            "오렌지주스, 3, 1"
    })
    public void 프로모션_할인_미적용_수량_테스트(String productName, int quantity, int expected) {
        Purchase purchase = Purchase.createPurchase(productName, quantity);

        Assertions.assertThat(purchase.getNotApplyPromotionCounts())
                .isEqualTo(expected);
    }

    @DisplayName("프로모션이 적용되지 않는 가격 확인")
    @ParameterizedTest
    @CsvSource({
            "콜라, 14, 5000",
            "감자칩, 2, 0",
            "사이다, 10, 4000",
            "콜라, 1, 1000"
    })
    public void 프로모션_미적용_가격_확인_테스트(String productName, int quantity, int expected) {
        Purchase purchase = Purchase.createPurchase(productName, quantity);

        Assertions.assertThat(purchase.getNotApplyPromotionAmounts())
                .isEqualTo(expected);
    }

    @DisplayName("프로모션을 통해 할인된 금액 확인")
    @Test
    public void 프로모션_사은품_할인_금액_확인_테스트() {
        Purchase purchase = Purchase.createPurchase("콜라", 14);
        purchase.applyPurchase();

        Assertions.assertThat(purchase.getGiftAmount())
                .isEqualTo(3000);
    }

    @DisplayName("구매량을 추가하여 프로모션 조건을 만족하는지 확인")
    @Test
    public void 프로모션_구매량_추가_테스트() {
        Purchase purchase = Purchase.createPurchase("콜라", 2);
        purchase.addQuantityForPromotion();

        Assertions.assertThat(purchase.isUnderPromotionQuantity())
                .isEqualTo(false);
    }

    @DisplayName("프로모션이 적용되지 않는 만큼 수량 제거하는지 확인")
    @Test
    public void 프로모션_구매량_취소_테스트() {
        Purchase purchase = Purchase.createPurchase("콜라", 13);
        int expectedAmount = 1000 * 13 - purchase.getNotApplyPromotionAmounts();
        purchase.subtractNotApplyQuantity();

        Assertions.assertThat(purchase.getAmount())
                .isEqualTo(expectedAmount);
    }

    @DisplayName("구매 시 프로모션 사은품 수와 가격 계산 확인")
    @Test
    public void 프로모션_사은품_수_가격_계산_테스트() {
        Purchase purchase = Purchase.createPurchase("콜라", 13);
        int expectedGiftNumber = 3;
        int expectedGiftAmount = 3000;

        purchase.applyPurchase();
        Assertions.assertThat(purchase.getGiftNumber())
                .isEqualTo(expectedGiftNumber);

        Assertions.assertThat(purchase.getGiftAmount())
                .isEqualTo(expectedGiftAmount);
    }
}