package store.domain;

public class Purchase {

    private final Product product;
    private final int quantity;

    private Purchase(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static Purchase createPurchase(String productName, int quantity) {
        validateProductExist(productName);
        return new Purchase(Products.getProduct(productName), quantity);
    }

    private static void validateProductExist(String productName) {
        if (!Products.isExist(productName)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }
}
