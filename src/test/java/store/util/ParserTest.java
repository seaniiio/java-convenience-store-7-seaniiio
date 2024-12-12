package store.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.constant.ErrorMessage;

class ParserTest {

    @Test
    void 주문_구분_테스트() {
        Map<String, Integer> orders = new HashMap<>();
        orders.put("김밥", 2);
        orders.put("라면", 1);

        Assertions.assertThat(Parser.parseOrder("[김밥-2],[라면-1]"))
                .containsAllEntriesOf(orders);
    }

    @Test
    void 중복된_주문_예외_테스트() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> Parser.parseOrder("[김밥-2],[김밥-3]"))
                .withMessageContaining(ErrorMessage.ORDER_DUPLICATED_ERROR.getMessage());
    }
}
