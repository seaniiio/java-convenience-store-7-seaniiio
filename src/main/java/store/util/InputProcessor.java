package store.util;

import java.util.function.Supplier;

public class InputProcessor {

    public static void continueUntilNormalInput(Runnable processInput) {
        while (true) {
            try {
                processInput.run();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static <T> T continueUntilNormalInput(Supplier<T> processInput) {
        while (true) {
            try {
                return processInput.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
