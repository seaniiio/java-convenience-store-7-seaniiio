package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    public void 일반_상품_생성_테스트() {
        String product = "콜라,1000,10,null";
        String expectedProductInformation = "- 콜라 1,000원 10개";

        Assertions.assertThat(Product.createProduct(product).getProductInformation())
                .isEqualTo(expectedProductInformation);
    }

    @Test
    public void 프로모션_상품_생성_테스트() {
        String product = "탄산수,1200,5,탄산2+1";
        String expectedProductInformation = "- 탄산수 1,200원 5개 탄산2+1";

        Assertions.assertThat(Product.createProduct(product).getProductInformation())
                .isEqualTo(expectedProductInformation);
    }

    @Test
    public void 재고_없는_일반_상품_생성_테스트() {
        String product = "콜라,1000,0,null";
        String expectedProductInformation = "- 콜라 1,000원 재고 없음";

        Assertions.assertThat(Product.createProduct(product).getProductInformation())
                .isEqualTo(expectedProductInformation);
    }

    @Test
    public void 재고_없는_프로모션_상품_생성_테스트() {
        String product = "탄산수,1200,0,탄산2+1";
        String expectedProductInformation = "- 탄산수 1,200원 재고 없음 탄산2+1";

        Assertions.assertThat(Product.createProduct(product).getProductInformation())
                .isEqualTo(expectedProductInformation);
    }
}