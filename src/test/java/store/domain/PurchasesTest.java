package store.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.dto.AmountsDto;
import store.service.StoreService;
import store.util.Reader;

class PurchasesTest {

    private StoreService storeService = new StoreService();
    private Purchases purchases;

    @BeforeEach
    void set() {
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();
        storeService.set(products, promotions);

        Map<String, Integer> purchasesInput = new HashMap<>();
        purchasesInput.put("콜라", 1);
        purchasesInput.put("감자칩", 2);
        purchasesInput.put("사이다", 10);
        purchases = new Purchases(purchasesInput);
    }

    @Test
    public void 프로모션_구매_달성_여부_확인_테스트() {
        Assertions.assertThat(purchases.getUnderPurchasedProducts())
                .containsEntry("콜라", false);
    }

    @Test
    public void 프로모션_재고_부족_테스트() {
        Assertions.assertThat(purchases.getNotAppliedPromotionProducts())
                .containsEntry("사이다", 4);
    }

    @Test
    public void 구매액_계산_테스트() {
        int totalAmount = 14000;
        int totalQuantity = 13;
        purchases.applyPurchases();

        List<AmountsDto> amounts = purchases.getAmounts();

        for (AmountsDto amount : amounts) {
            if (amount.getInformation().equals("총구매액")) {
                Assertions.assertThat(amount.getAmount())
                        .isEqualTo(totalAmount);

                Assertions.assertThat(amount.getQuantity())
                        .isEqualTo(totalQuantity);
            }
        }
    }
}