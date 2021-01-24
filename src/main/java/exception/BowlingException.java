package exception;

public class BowlingException extends Exception {

    private String message;

    public BowlingException(String message){
        super();
        this.message = message;


    }
}
