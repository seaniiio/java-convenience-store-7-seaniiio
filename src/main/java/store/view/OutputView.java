package store.view;

import java.util.List;
import java.util.Map;
import store.dto.ProductDto;
import store.dto.Receipt;

public class OutputView {

    public void printWelcomeMessage(List<ProductDto> productDtos) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고있는 상품입니다." + System.lineSeparator());

        for (ProductDto productDto : productDtos) {
            System.out.println(productDto.getInformation());
        }
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void printReceipt(Receipt receipt) {
        System.out.println("=========== W 편의점 ============");
        System.out.println("상품명        수량     금액");
        Map<String, List<Integer>> buyProducts = receipt.getBuyProducts();
        for (String productName : buyProducts.keySet()) {
            if (buyProducts.get(productName).get(0) > 0) {
                System.out.println(String.format("%s      %,d    %,d", productName, buyProducts.get(productName).get(0), buyProducts.get(productName).get(1)));
            }
        }

        System.out.println("=========== 증    정 ============");
        Map<String, Integer> gifts = receipt.getGifts();
        for (String gift : gifts.keySet()) {
            System.out.println(String.format("%s      %d", gift, gifts.get(gift)));
        }

        System.out.println("================================");
        System.out.println(String.format("총구매액         %d    %,d",receipt.getTotalQuantity(), receipt.getTotalAmount()));
        System.out.println(String.format("행사할인               -%,d", receipt.getPromotionDiscount()));
        System.out.println(String.format("멤버십할인              -%,d", receipt.getMembershipDiscount()));
        System.out.println(String.format("내실돈                 -%,d", receipt.getPayAmount()));
    }
}
