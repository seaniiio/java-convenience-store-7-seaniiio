package store.constant;

public enum ErrorMessage {
    ORDER_DUPLICATED_ERROR("중복된 주문이 존재합니다."),
    OUT_OF_STOCK_ERROR("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    PRODUCT_NOT_EXIST_ERROR("해당하는 물품은 존재하지 않습니다."),
    ;

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
