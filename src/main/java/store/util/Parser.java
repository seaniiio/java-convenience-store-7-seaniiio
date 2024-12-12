package store.util;

import java.util.Arrays;
import java.util.List;

public class Parser {

    public static List<String> splitByComma(String input) {
        return Arrays.stream(input.split(",")).toList();
    }
}
