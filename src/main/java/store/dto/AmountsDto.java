package store.dto;

public class AmountsDto {

    private final String information;
    private final int quantity;
    private final int amount;

    public AmountsDto(String information, int quantity, int amount) {
        this.information = information;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getInformation() {
        return information;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAmount() {
        return amount;
    }
}
