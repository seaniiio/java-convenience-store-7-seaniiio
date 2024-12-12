package store.dto;

public class LackBuyQuantityProduct {

    private final String name;
    private final int lackQuantity;
    private final int get;
    private boolean addPurchase;

    public LackBuyQuantityProduct(String name, int lackQuantity, int get) {
        this.name = name;
        this.lackQuantity = lackQuantity;
        this.get = get;
        addPurchase = false;
    }

    public void setPurchase() {
        // 추가 구매 확정
        addPurchase = true;
    }

    public String getName() {
        return name;
    }

    public int getLackQuantity() {
        return lackQuantity;
    }

    public boolean isAddPurchase() {
        return addPurchase;
    }

    public int getGet() {
        return get;
    }
}
