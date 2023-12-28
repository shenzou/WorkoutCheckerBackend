package com.shenzou.workoutcheckerbackend;

import com.shenzou.workoutcheckerbackend.exception.FileNotFoundException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> map =  new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError ->
                        map.put(fieldError.getField(), fieldError.getDefaultMessage())
                );

        return map;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Map<String, String> handleInvalidArgument(SQLIntegrityConstraintViolationException exception) {
        Map<String, String> map =  new HashMap<>();
        if(exception.getMessage().contains("Duplicate entry")){
            map.put("message", "This entry already exists. Please verify input parameters.");
        }
        return map;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MalformedJwtException.class)
    public Map<String, String> handleInvalidArgument(MalformedJwtException exception) {
        Map<String, String> map =  new HashMap<>();
        map.put("message", "Please verify token.");
        return map;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileNotFoundException.class)
    public String handleInvalidArgument(FileNotFoundException exception) {
        return "Internal Server Error.";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleInvalidArgument(Exception exception) {
        return "Internal Server Error.";
    }

}
