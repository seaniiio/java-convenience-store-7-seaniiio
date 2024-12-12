package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Promotion {

    private static final int NAME_INDEX = 0;
    private static final int BUY_INDEX = 1;
    private static final int GET_INDEX = 2;
    private static final int START_DATE_INDEX = 3;
    private static final int END_DATE_INDEX = 4;

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion createPromotion(List<String> promotionRaw) {
        String name = promotionRaw.get(NAME_INDEX);
        int buy = Integer.parseInt(promotionRaw.get(BUY_INDEX));
        int get = Integer.parseInt(promotionRaw.get(GET_INDEX));
        LocalDate startDate = LocalDate.parse(promotionRaw.get(START_DATE_INDEX), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(promotionRaw.get(END_DATE_INDEX), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return new Promotion(name, buy, get, startDate, endDate);
    }

    public boolean isNameEqualsTo(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return this.name;
    }

    // 테스트 필요
    public boolean isApply() {
        return (DateTimes.now().isAfter(startDate.atStartOfDay()) && DateTimes.now().isBefore(endDate.atStartOfDay()));
    }

//    public static Promotion getNullPromotion() {
//        return new Promotion("null", 0, 0, LocalDate.of(2024, 12, 12), LocalDate.of(2024, 12, 12));
//    }
}
