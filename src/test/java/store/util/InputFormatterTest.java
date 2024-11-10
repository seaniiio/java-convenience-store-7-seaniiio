package store.util;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.constant.ErrorMessage;

class InputFormatterTest {

    private final InputFormatter inputFormatter = new InputFormatter();

    @DisplayName("입력한 구매 목록을 제대로 변환하는지 확인")
    @Test
    public void 입력한_구매_목록_변환_테스트() {
        String purchaseInput = "[콜라-3],[에너지바-5]";
        Map<String, Integer> expectedPurchases = new HashMap<>();
        expectedPurchases.put("콜라", 3);
        expectedPurchases.put("에너지바", 5);

        Assertions.assertThat(inputFormatter.formatPurchaseInput(purchaseInput))
                .containsAllEntriesOf(expectedPurchases);
    }

    @DisplayName("입력한 구매 목록이 형식에 맞지 않은 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"[콜라-3][에너지바-5]", "[콜라-3],,[에너지바-5]", "콜라-3,에너지바-5", "[]", "[,]"})
    public void 입력한_구매_목록_형식_예외_테스트(String productAndQuantity) {
        assertThatIllegalArgumentException().isThrownBy(() -> inputFormatter.formatPurchaseInput(productAndQuantity))
                .withMessageContaining(ErrorMessage.PURCHASE_INPUT_FORMAT_ERROR.getMessage());
    }

    @DisplayName("입력한 구매 목록에 물품이 중복된 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"[콜라-3],[콜라-5]"})
    public void 입력한_구매_목록_중복_예외_테스트(String productAndQuantity) {
        assertThatIllegalArgumentException().isThrownBy(() -> inputFormatter.formatPurchaseInput(productAndQuantity))
                .withMessageContaining(ErrorMessage.INPUT_OTHER_ERROR.getMessage());
    }

    @DisplayName("입력한 의사 변환 테스트")
    @Test
    public void 입력한_의사_변환_테스트() {
        String yesInput = "Y";
        String noInput = "N";

        Assertions.assertThat(inputFormatter.formatIntentionInput(yesInput))
                .isEqualTo(true);

        Assertions.assertThat(inputFormatter.formatIntentionInput(noInput))
                .isEqualTo(false);
    }

    @DisplayName("입력한 의사가 형식에 맞지 않은 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"몰라", "YY", "NN", "네", "아니오", "\n", " ", ""})
    public void 입력한_의사_형식_예외_테스트(String intention) {
        assertThatIllegalArgumentException().isThrownBy(() -> inputFormatter.formatIntentionInput(intention))
                .withMessageContaining(ErrorMessage.INPUT_OTHER_ERROR.getMessage());
    }
}