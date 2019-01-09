public class IncorrectThreadNumberException extends IllegalArgumentException {

    public IncorrectThreadNumberException() {
        super();
    }

    public IncorrectThreadNumberException(String errorMessage) {
        super(errorMessage);
    }
}
