package kr.mapo.eco100.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    //request data가 유효하지 않을 경우 발생하는 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> requestParamsNotValidExceptionHalder(MethodArgumentNotValidException e) {
        
        return ResponseEntity.badRequest().body(new ErrorResponse(400,e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<String> ImageNotFoundExceptionHandler(ImageNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChallengeNotFoundException.class)
    public ResponseEntity<String> ChallengeNotFoundExceptionHandler(ChallengeNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChallengeCntAlreadyTwoException.class)
    public ResponseEntity<String> ChallengeCntAlreadyTwoExceptionHandler(ChallengeCntAlreadyTwoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<String> BoardNotFoundExceptionHandler(BoardNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
