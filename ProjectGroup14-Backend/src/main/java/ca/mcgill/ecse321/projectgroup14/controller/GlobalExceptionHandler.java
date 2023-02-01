package ca.mcgill.ecse321.projectgroup14.controller;

import ca.mcgill.ecse321.projectgroup14.Exception.WrongInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {WrongInputException.class})
    public ResponseEntity<?> handleBlogAlreadyExistsException(WrongInputException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
