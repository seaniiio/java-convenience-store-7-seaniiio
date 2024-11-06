package store.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class ProductsTest {

    @Test
    public void 물품_목록에_대한_객체_생성_테스트() {
        List<String> givenProducts = List.of("콜라,1000,10,null", "탄산수,1200,5,탄산2+1");
        List<String> expectedInformations = List.of("- 콜라 1,000원 10개", "- 탄산수 1,200원 5개 탄산2+1");

        assertThat(Products.createProducts(givenProducts).getInformations())
                .containsAll(expectedInformations);
    }
}