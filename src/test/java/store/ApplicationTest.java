package store;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import store.constant.ErrorMessage;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                "- 콜라 1,000원 10개 탄산2+1",
                "- 콜라 1,000원 10개",
                "- 사이다 1,000원 8개 탄산2+1",
                "- 사이다 1,000원 7개",
                "- 오렌지주스 1,800원 9개 MD추천상품",
                "- 오렌지주스 1,800원 재고 없음",
                "- 탄산수 1,200원 5개 탄산2+1",
                "- 탄산수 1,200원 재고 없음",
                "- 물 500원 10개",
                "- 비타민워터 1,500원 6개",
                "- 감자칩 1,500원 5개 반짝할인",
                "- 감자칩 1,500원 5개",
                "- 초코바 1,200원 5개 MD추천상품",
                "- 초코바 1,200원 5개",
                "- 에너지바 2,000원 5개",
                "- 정식도시락 6,400원 8개",
                "- 컵라면 1,700원 1개 MD추천상품",
                "- 컵라면 1,700원 10개"
            );
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("한 번의 주문에 중복된 상품을 여러 번 주문하는 경우 예외 발생")
    @Test
    void 중복_상품_주문_예외_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-1],[콜라-2]", "N", "N");
            assertThat(output()).contains(ErrorMessage.INPUT_OTHER_ERROR.getMessage());
        });
    }

    @DisplayName("사용자의 의사를 묻는 질문에 올바르지 않은 형식을 입력한 경우 예외 발생")
    @Test
    void 의사_형식_예외_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-3]", "네", "N");
            assertThat(output()).contains(ErrorMessage.INPUT_OTHER_ERROR.getMessage());
        });
    }

    @DisplayName("존재하지 않는 사움을 주문할 경우 예외 발생")
    @Test
    void 미존재_상품_주문_예외_테스트() {
        assertSimpleTest(() -> {
            runException("[요아정-3]", "N", "N");
            assertThat(output()).contains(ErrorMessage.PRODUCT_NOT_EXIST_ERROR.getMessage());
        });
    }

    @DisplayName("상품 주문 형식이 올바르지 않을 경우 예외 발생")
    @Test
    void 상품_주문_형식_예외_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-3][사이다-3]", "N", "N");
            assertThat(output()).contains(ErrorMessage.PURCHASE_INPUT_FORMAT_ERROR.getMessage());
        });
    }

    @DisplayName("0개를 주문할 경우 예외 바생")
    @Test
    void 상품_주문_개수_예외_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-3],[사이다-0]", "N", "N");
            assertThat(output()).contains(ErrorMessage.PURCHASE_INPUT_FORMAT_ERROR.getMessage());
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
