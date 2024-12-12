package store.constant;

public enum Command {
    YES("Y"),
    NO("N")
    ;

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command findCommand(String inputCommand) {
        if (inputCommand.equals("Y")) {
            return YES;
        }
        if (inputCommand.equals("N")) {
            return NO;
        }
        throw new IllegalArgumentException(ErrorMessage.INPUT_ERROR.getMessage());
    }
}
