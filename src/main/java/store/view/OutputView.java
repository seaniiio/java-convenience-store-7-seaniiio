package store.view;

import java.util.List;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public void printProductsInformation(List<String> productsInformation) {
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();

        for (String productInformation : productsInformation) {
            System.out.println(productInformation);
        }
    }
}
