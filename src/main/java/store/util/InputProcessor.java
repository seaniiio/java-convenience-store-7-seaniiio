package store.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class InputProcessor {
    public static void continueUntilNormalInput(Runnable processInput, Consumer<String> processIfErrorOccur) {
        while (true) {
            try {
                processInput.run();
                break;
            } catch (IllegalArgumentException e) {
                processIfErrorOccur.accept(e.getMessage());
            }
        }
    }

    public static <T> T continueUntilNormalInput(Supplier<T> processInput, Consumer<String> processIfErrorOccur) {
        while (true) {
            try {
                return processInput.get();
            } catch (IllegalArgumentException e) {
                processIfErrorOccur.accept(e.getMessage());
            }
        }
    }

    public static <T> void continueUntilNormalInput(Consumer<T> processInput, Consumer<String> processIfErrorOccur, T t) {
        while (true) {
            try {
                processInput.accept(t);
                break;
            } catch (IllegalArgumentException e) {
                processIfErrorOccur.accept(e.getMessage());
            }
        }
    }

    public static <T> T continueUntilNormalInput(Function<T, T> processInput, T print, Consumer<String> processIfErrorOccur) {
        while (true) {
            try {
                return processInput.apply(print);
            } catch (IllegalArgumentException e) {
                processIfErrorOccur.accept(e.getMessage());
            }
        }
    }
}
