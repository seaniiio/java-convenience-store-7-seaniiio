package store.constant;

public enum ErrorMessage {
    ORDER_DUPLICATED_ERROR("중복된 주문이 존재합니다."),
    OUT_OF_STOCK_ERROR("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_EXIST_ERROR("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INPUT_ERROR("잘못된 입력입니다. 다시 입력해 주세요.")
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
