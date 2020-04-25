package validator;

public class QuoteMismatchException extends Exception {

    @Override
    public String getMessage() {
        return "JSON quotes not balanced";
    }
}
