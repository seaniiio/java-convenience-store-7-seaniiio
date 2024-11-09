package store.view;

import java.util.List;
import store.dto.AmountsDto;
import store.dto.GiftsDto;
import store.dto.ProductsDto;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public void printProductsInformation(List<String> productsInformation) {
        System.out.println("현재 보유하고 있는 상품입니다." + System.lineSeparator());

        for (String productInformation : productsInformation) {
            System.out.printf(productInformation);
        }
    }

    public void printReceipt(List<ProductsDto> purchasesContent, List<GiftsDto> gifts, List<AmountsDto> amounts) {
        printProductReceipt(purchasesContent);
        printGiftReceipt(gifts);
        printAmountReceipt(amounts);
    }

    private void printAmountReceipt(List<AmountsDto> amounts) {
        System.out.println("==============================");
        for (AmountsDto amount : amounts) {
            if (amount.getInformation().equals("총구매액")) {
                System.out.println(String.format("%-14s%-8d%,6d", amount.getInformation(), amount.getQuantity(), amount.getAmount()));
                continue;
            }
            System.out.println(String.format("%-22s%-,6d", amount.getInformation(), amount.getAmount()));
        }
    }

    private void printGiftReceipt(List<GiftsDto> gifts) {
        System.out.println("===========증	정=============");
        for (GiftsDto gift : gifts) {
            System.out.println(String.format("%-14s%d", gift.getProductName(), gift.getQuantity()));
        }
    }

    private void printProductReceipt(List<ProductsDto> purchasesContent) {
        System.out.println("===========W 편의점=============");
        System.out.println(String.format("%-14s%-8s%-6s", "상품명", "수량", "금액"));
        for (ProductsDto purchase : purchasesContent) {
            System.out.println(String.format("%-14s%,-8d%,-6d", purchase.getProductName(), purchase.getQuantity(), purchase.getAmount()));
        }
    }
}
