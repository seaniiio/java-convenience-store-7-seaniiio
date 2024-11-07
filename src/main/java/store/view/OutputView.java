package store.view;

import java.util.List;
import java.util.Map;
import store.domain.Receipt;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public void printProductsInformation(List<String> productsInformation) {
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();

        for (String productInformation : productsInformation) {
            System.out.printf(productInformation);
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printReceipt() {
        System.out.println("===========W 편의점=============");
        System.out.println("상품명           수량      금액");

        List<String> pruchasesContent = Receipt.getPurchasesContent();
        for (String purchase : pruchasesContent) {
            System.out.println(purchase);
        }

        System.out.println("===========증	정=============");
        Map<String, Integer> gifts = Receipt.getGifts();
        for (String productName : gifts.keySet()) {
            System.out.println(String.format("%-15s%d", productName, gifts.get(productName)));
        }

        System.out.println("==============================");
    }
}
