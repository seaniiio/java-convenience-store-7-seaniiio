package store.domain;

public enum AmountInformation {

    TOTAL_AMOUNT("총구매액"),
    PROMOTION_DISCOUNT("행사할인"),
    MEMBERSHIP_DISCOUNT("멤버십할인"),
    PAY_AMOUNT("내실돈");


    private final String information;
    private int quantity = 0;
    private int amount = 0;

    AmountInformation(String information) {
        this.information = information;
    }

    public String getAmountInformation() {
        if (this.information.equals("총구매액")) {
            return String.format("%-14s%-8d%,6d", this.information, this.quantity, this.amount);
        }
        return String.format("%-22s%,6d", this.information, this.amount);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
