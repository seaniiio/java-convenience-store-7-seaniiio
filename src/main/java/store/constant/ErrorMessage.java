package store.constant;

public enum ErrorMessage {
    ORDER_DUPLICATED_ERROR("중복된 주문이 존재합니다.");

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
