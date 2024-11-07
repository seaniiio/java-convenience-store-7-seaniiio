package store.domain;

public class NormalStock {

    private int stock;

    public NormalStock(int stock) {
        this.stock = stock;
    }

    public String getInformation() {
        if (stock == 0) {
            return "재고 없음";
        }
        return String.format("%,d개", this.stock);
    }
}
