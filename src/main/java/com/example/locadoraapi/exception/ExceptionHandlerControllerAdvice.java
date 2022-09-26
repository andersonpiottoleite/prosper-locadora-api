package com.example.locadoraapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/** Classe para configuração de exibição de exceptions mais amigáveis
 * as exibições estão configuradas por tipo de exception
 *
 * @author Anderson Piotto
 * @version 1.0.0
 * @since 22/09/2022
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LocacaoException.class)
    public ResponseEntity<ErrorMessage> resourceLocacaoException(LocacaoException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LocacaoNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceLocacaoNotFoundException(LocacaoNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody ResponseEntity<ErrorMessage> resourceException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }
}