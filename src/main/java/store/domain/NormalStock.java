package store.domain;

public class NormalStock {

    private int stock;

    public NormalStock(int stock) {
        this.stock = stock;
    }

    public String getInformation() {
        if (stock == 0) {
            return "재고 없음\n";
        }
        return String.format("%,d개\n", this.stock);
    }

    public int getStock() {
        return stock;
    }
}
