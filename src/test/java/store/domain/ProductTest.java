package store.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.service.StoreService;
import store.util.Reader;

class ProductTest {

    private StoreService storeService = new StoreService();
    private Product product;

    @BeforeEach
    void set() {
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();
        promotions.add("기간지난할인,1,1,2023-11-01,2023-11-30");
        storeService.set(products, promotions);

        product = Product.createProduct("비요뜨,2100,5,반짝할인");
        product.addNewStock("비요뜨,2100,5,null");
    }

    @Test
    public void 재고_구매_가능_판단_테스트_1() {
        Assertions.assertThat(product.canBuy(5))
                .isEqualTo(true);
    }

    @Test
    public void 재고_구매_가능_판단_테스트_2() {
        Assertions.assertThat(product.canBuy(11))
                .isEqualTo(false);
    }

    @Test
    public void 구매_테스트_1() {
        int expectedGifts = 2;

        Assertions.assertThat(product.purchase(10))
                .isEqualTo(expectedGifts);
    }

    @Test
    public void 구매_테스트_2() {
        int expectedGifts = 1;

        Assertions.assertThat(product.purchase(2))
                .isEqualTo(expectedGifts);
    }

    @Test
    public void 구매_테스트_3() {
        product = Product.createProduct("비요뜨,2100,5,기간지난할인");
        product.addNewStock("비요뜨,2100,5,null");

        int expectedGifts = 0;

        Assertions.assertThat(product.purchase(5))
                .isEqualTo(expectedGifts);
    }

    @Test
    public void 프로모션이_적용되지_않는_물건_수_테스트_1() {
        int expectedCounts = 1;

        Assertions.assertThat(product.notApplyPromotionOutOfStockCounts(5))
                .isEqualTo(expectedCounts);
    }

    @Test
    public void 프로모션이_적용되지_않는_물건_수_테스트_2() {
        int expectedCounts = 4;

        Assertions.assertThat(product.notApplyPromotionOutOfStockCounts(8))
                .isEqualTo(expectedCounts);
    }

    @Test
    public void 프로모션_재고_추가_테스트() {
        product = Product.createProduct("비요뜨,2100,5,null");
        product.addNewStock("비요뜨,2100,5,반짝할인");

        Assertions.assertThat(product.isPromotionApply())
                .isEqualTo(true);
    }

    @Test
    public void 프로모션_적용_여부_확인_테스트_1() {
        product = Product.createProduct("비요뜨,2100,5,null");

        Assertions.assertThat(product.isPromotionApply())
                .isEqualTo(false);
    }

    @Test
    public void 프로모션_적용_여부_확인_테스트_2() {
        product = Product.createProduct("비요뜨,2100,5,기간지난할인");

        Assertions.assertThat(product.isPromotionApply())
                .isEqualTo(false);
    }

    @Test
    public void 구매_금액_확인_테스트() {
        Assertions.assertThat(product.getAmount(4))
                .isEqualTo(8400);
    }

    @Test
    public void 프로모션_적용_개수_확인_테스트() {
        Assertions.assertThat(product.getPromotionQuantity())
                .isEqualTo(2);
    }

    @Test
    public void 프로모션_적용_최소_개수_넘는지_확인_테스트() {
        Assertions.assertThat(product.isOverPromotionQuantity(2))
                .isEqualTo(true);

        Assertions.assertThat(product.isOverPromotionQuantity(1))
                .isEqualTo(false);
    }

    @Test
    public void 물품_이름으로_검색_테스트() {
        Assertions.assertThat(product.equalsTo("비요뜨"))
                .isEqualTo(true);
    }
}