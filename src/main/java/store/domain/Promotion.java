package store.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Promotion {

    private final String name;
    private final int buyQuantity;
    private final int getQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(String name, int buyQuantity, int getQuantity, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion createPromotion(String promotionInformation) {
        List<String> promotionInformations = Arrays.stream(promotionInformation.split(",")).toList();

        return new Promotion(promotionInformations.get(0),
                Integer.parseInt(promotionInformations.get(1)),
                Integer.parseInt(promotionInformations.get(2)),
                LocalDate.parse(promotionInformations.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.parse(promotionInformations.get(4), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
