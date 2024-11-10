package store.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.service.StoreService;
import store.util.Reader;

class ProductsTest {

    private StoreService storeService = new StoreService();

    @BeforeEach
    void set() {
        List<String> products = Reader.readProducts();
        List<String> promotions = Reader.readPromotions();
        storeService.set(products, promotions);
    }

    @Test
    public void 이름으로_탐색_테스트() {
        Assertions.assertThat(Products.getProduct("감자칩"))
                .isNotNull();
    }

    @Test
    public void 이름으로_탐색_실패_테스트() {
        Assertions.assertThat(Products.getProduct("포키"))
                .isNull();
    }
}