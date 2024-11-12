package store.view;

import java.util.List;
import store.dto.AmountsDto;
import store.dto.GiftsDto;
import store.dto.ProductsDto;
import store.dto.PurchasedProductsDto;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public void printProductsInformation(List<ProductsDto> products) {
        System.out.println("현재 보유하고 있는 상품입니다." + System.lineSeparator());

        for (ProductsDto product : products) {
            if (product.getPromotionName().isBlank()) {
                printNormalProduct(product);
                continue;
            }
            printPromotionProduct(product);
        }
    }

    private void printNormalProduct(ProductsDto product) {
        if (product.getStock() == 0) {
            System.out.println(String.format("- %s %,d원 재고 없음", product.getProductName(), product.getPrice()));
            return;
        }
        System.out.println(String.format("- %s %,d원 %,d개", product.getProductName(), product.getPrice(), product.getStock()));
    }

    private void printPromotionProduct(ProductsDto product) {
        if (product.getStock() == 0) {
            System.out.println(String.format("- %s %,d원 재고 없음 %s", product.getProductName(), product.getPrice(),
                    product.getPromotionName()));
            return;
        }
        System.out.println(String.format("- %s %,d원 %,d개 %s", product.getProductName(), product.getPrice(), product.getStock(), product.getPromotionName()));
    }

    public void printReceipt(List<PurchasedProductsDto> purchasesContent, List<GiftsDto> gifts, List<AmountsDto> amounts) {
        printProductReceipt(purchasesContent);
        printGiftReceipt(gifts);
        printAmountReceipt(amounts);
    }

    private void printProductReceipt(List<PurchasedProductsDto> purchasesContent) {
        System.out.println("==============W 편의점================");
        System.out.println(String.format("%-18s%-8s%-6s", "상품명", "수량", "금액"));
        for (PurchasedProductsDto purchase : purchasesContent) {
            System.out.println(String.format("%s%,-10d%,-6d", getFormattedMessage(purchase.getProductName(), 21), purchase.getQuantity(), purchase.getAmount()));
        }
    }

    private void printGiftReceipt(List<GiftsDto> gifts) {
        System.out.println("=============증      정===============");
        for (GiftsDto gift : gifts) {
            System.out.println(String.format("%s%d", getFormattedMessage(gift.getProductName(), 21), gift.getQuantity()));
        }
    }

    private void printAmountReceipt(List<AmountsDto> amounts) {
        System.out.println("====================================");
        for (AmountsDto amount : amounts) {
            System.out.println(getAmountMessage(amount));
        }
    }

    private String getAmountMessage(AmountsDto amount) {
        if (amount.getInformation().equals("총구매액")) {
            return String.format("%s%,-10d%,-6d", getFormattedMessage("총구매액", 21), amount.getQuantity(),
                    amount.getAmount());
        }
        if (amount.getAmount() != 0) {
            return String.format("%s%-,6d", getFormattedMessage(amount.getInformation(), 31), amount.getAmount());
        }
        return String.format("%s-%-,6d", getFormattedMessage(amount.getInformation(), 31), amount.getAmount());
    }

    private String getFormattedMessage(String message, int defaultLength) {
        int newLength = defaultLength - message.length();

        return String.format("%-" + newLength + "s", message);
    }
}
