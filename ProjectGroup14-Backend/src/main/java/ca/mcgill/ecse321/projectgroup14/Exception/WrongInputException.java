package ca.mcgill.ecse321.projectgroup14.Exception;
import org.springframework.http.HttpStatus;

public class WrongInputException extends IllegalArgumentException {
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public WrongInputException(String message) {
        super(message);
    }

    public WrongInputException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}