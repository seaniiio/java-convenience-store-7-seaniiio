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
        storeService.set(products, promotions);

        product = new Product("비요뜨", 2100, 5, "반짝할인");
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
        product.purchase(10);

        Assertions.assertThat(product.getProductInformation())
                .contains("- 비요뜨 2,100원 재고 없음 반짝할인", "- 비요뜨 2,100원 재고 없음");
    }

    @Test
    public void 구매_테스트_2() {
        product.purchase(8);

        Assertions.assertThat(product.getProductInformation())
                .contains("- 비요뜨 2,100원 재고 없음 반짝할인", "- 비요뜨 2,100원 2개");
    }

    @Test
    public void 구매_테스트_3() {
        product.purchase(3);

        Assertions.assertThat(product.getProductInformation())
                .contains("- 비요뜨 2,100원 2개 반짝할인", "- 비요뜨 2,100원 5개");
    }
}