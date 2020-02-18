package lk.ijse.dep.pos.advice;

import lk.ijse.dep.pos.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@CrossOrigin
@RestControllerAdvice
public class AppWideExceptionHandler {

/*    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String globalExceptionHandler(Exception e){
        e.printStackTrace();
        return "Something went wrong plz contact developer team";
    }*/

    @ExceptionHandler({NotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity handleNotFoundException(Exception e) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
