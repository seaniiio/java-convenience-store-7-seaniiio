package store.view;

import java.util.List;
import store.dto.ProductDto;

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
}
