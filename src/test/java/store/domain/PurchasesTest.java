package store.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("프로모션 구매 조건에 미달한 물품 저장")
    @Test
    public void 프로모션_구매_달성_여부_확인_테스트() {
        Assertions.assertThat(purchases.getUnderPurchasedProducts())
                .containsEntry("콜라", false);
    }

    @DisplayName("프로모션 재고가 부족한 물품 저장")
    @Test
    public void 프로모션_재고_부족_테스트() {
        Assertions.assertThat(purchases.getNotAppliedPromotionProducts())
                .containsEntry("사이다", 4);
    }

    @DisplayName("주문을 기반으로 총구매액, 할인금액, 내야할 돈 계싼")
    @Test
    public void 구매액_계산_테스트() {
        int totalAmount = 14000;
        int totalQuantity = 13;
        int promotionDiscountAmount = (-1) * (1500 + 1000 * 2);
        int membershipDiscountAmount = -1500;
        int totalPayAmount = totalAmount + promotionDiscountAmount + membershipDiscountAmount;

        purchases.applyMembershipSale(true);
        purchases.applyPurchases();
        List<AmountsDto> amounts = purchases.getAmounts();

        for (AmountsDto amount : amounts) {
            if (amount.getInformation().equals("총구매액")) {
                Assertions.assertThat(amount.getAmount())
                        .isEqualTo(totalAmount);

                Assertions.assertThat(amount.getQuantity())
                        .isEqualTo(totalQuantity);
                continue;
            }

            if (amount.getInformation().equals("행사할인")) {
                Assertions.assertThat(amount.getAmount())
                        .isEqualTo(promotionDiscountAmount);
                continue;
            }

            if (amount.getInformation().equals("멤버십할인")) {
                Assertions.assertThat(amount.getAmount())
                        .isEqualTo(membershipDiscountAmount);
                continue;
            }

            if (amount.getInformation().equals("내실돈")) {
                Assertions.assertThat(amount.getAmount())
                        .isEqualTo(totalPayAmount);
            }
        }
    }
}