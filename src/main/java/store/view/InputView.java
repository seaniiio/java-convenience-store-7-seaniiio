package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.PromotionNotAppliedProduct;

public class InputView {

    public String orderInput() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public String printAddBuyCommand(PromotionNotAppliedProduct product) {
        System.out.println(String.format("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)", product.getName(), product.getGet()));
        return Console.readLine();
    }
}
