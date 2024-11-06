package store.util;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputFormatterTest {

    private final InputFormatter inputFormatter = new InputFormatter();

    @Test
    public void 입력한_구매_목록_변환_테스트() {
        String purchaseInput = "[콜라-3],[에너지바-5]";
        Map<String, Integer> expectedPurchases = new HashMap<>();
        expectedPurchases.put("콜라", 3);
        expectedPurchases.put("에너지바", 5);

        Assertions.assertThat(inputFormatter.formatPurchaseInput(purchaseInput))
                .containsAllEntriesOf(expectedPurchases);
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-3][에너지바-5]", "[콜라-3],,[에너지바-5]", "콜라-3,에너지바-5"})
    public void 입력한_구매_목록_형식_예외_테스트(String productAndQuantity) {
        Assertions.assertThatThrownBy(() -> inputFormatter.formatPurchaseInput(productAndQuantity))
                .isInstanceOf(IllegalArgumentException.class);
    }
}